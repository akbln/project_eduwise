package com.edusenior.project.Mappings;

import com.edusenior.project.dataTransferObjects.NewTeacherDTO;

import com.edusenior.project.entities.Users.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "gender", target = "gender")
    Teacher newTeacherDtoToTeacher(NewTeacherDTO tDTO);
}