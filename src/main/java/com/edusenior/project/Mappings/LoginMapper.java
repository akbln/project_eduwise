package com.edusenior.project.Mappings;

import com.edusenior.project.dataTransferObjects.LoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LoginMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    public LoginDTO toLoginDto(String email, String password);
}
