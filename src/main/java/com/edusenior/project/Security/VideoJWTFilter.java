package com.edusenior.project.Security;

import com.edusenior.project.Exceptions.BadAuthorizationException;
import com.edusenior.project.Utility.JWTManager;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


import java.io.IOException;
import java.util.List;

@Component
public class VideoJWTFilter extends OncePerRequestFilter {

    private final JWTManager jwtManager;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    public VideoJWTFilter(JWTManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return !pathMatcher.match("/students/classes/*/chapters/*/video/**", path);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Extract token from the query parameter 'token'
            String token = request.getParameter("token");
            if (token == null || token.isBlank()) {
                throw new BadAuthorizationException("Missing or blank JWT token");
            }

            String role = jwtManager.getRoleFromToken(token);
            String email = jwtManager.getEmailFromToken(token);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    email, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (BadAuthorizationException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"operationStatus\":\"failed\", \"errors\":[\"Invalid JWT\"]}");
            response.setContentType("application/json");
            response.getWriter().flush();
        } catch (JwtException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"operationStatus\":\"failed\", \"errors\":[\"Bad JWT\"]}");
            response.setContentType("application/json");
            response.getWriter().flush();
        }
    }
}

