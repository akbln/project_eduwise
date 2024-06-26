package com.edusenior.project.Mappings;

import com.edusenior.project.dataTransferObjects.NewStudentDTO;
import com.edusenior.project.entities.Users.Student;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "gender", target = "gender")
//    @Mapping(source = "level", target = "level")


    Student newStudentDtoToStudent(NewStudentDTO newStudentDTO);
}


