package com.edusenior.project.Security;

import com.edusenior.project.Exceptions.BadAuthorizationException;
import com.edusenior.project.Utility.JWTManager;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTManager jwtManager;
    private final AntPathMatcher pathMatcher;
    private final List<String> SKIP_URLS;

    @Autowired
    public JWTAuthenticationFilter(JWTManager jwtManager) {
        this.jwtManager = jwtManager;
        this.pathMatcher= new AntPathMatcher();
        this.SKIP_URLS = new ArrayList<>(Arrays.asList(new String[]{
                "/login",
                "/**/register"
        }));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();
        return SKIP_URLS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");
            if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
                throw new BadAuthorizationException("Invalid JWT");
            }
            token = token.substring(7);
            String role = jwtManager.getRoleFromToken(token);
            String email = jwtManager.getEmailFromToken(token);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    email, null, List.of(new SimpleGrantedAuthority("ROLE_"+role)));
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (BadAuthorizationException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"operationStatus\":\"failed\", \"errors\":[\"Invalid JWT\"]}");
            response.setContentType("application/json");
            response.getWriter().flush();
        }
        catch (JwtException ex){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"operationStatus\":\"failed\", \"errors\":[\"Bad JWT\"]}");
            response.setContentType("application/json");
            response.getWriter().flush();
        }
    }
}
