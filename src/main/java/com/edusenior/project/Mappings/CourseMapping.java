package com.edusenior.project.Mappings;

import com.edusenior.project.dataTransferObjects.CourseDTO;
import com.edusenior.project.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapping {

    CourseMapping INSTANCE = Mappers.getMapper(CourseMapping.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "banner", target = "banner")

    Course courseDTOToCourse(CourseDTO c);

}
