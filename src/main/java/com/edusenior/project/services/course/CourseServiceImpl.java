package com.edusenior.project.services.course;

import com.edusenior.project.Exceptions.AdditionOfClassToCourseException;
import com.edusenior.project.Exceptions.DuplicateEntryException;
import com.edusenior.project.Exceptions.NotFound.CourseNotFoundException;
import com.edusenior.project.Exceptions.NotFound.SchoolClassNotFoundException;
import com.edusenior.project.Mappings.CourseMapping;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataAccessObjects.classes.SchoolClassJpaRepository;
import com.edusenior.project.dataAccessObjects.courses.CourseJpaDAO;
import com.edusenior.project.dataTransferObjects.AddClassToCourseDTO;
import com.edusenior.project.dataTransferObjects.CourseDTO;
import com.edusenior.project.entities.Course;
import com.edusenior.project.entities.SchoolClass;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseJpaDAO courseJpaDAO;
    private SchoolClassJpaRepository schoolClassJpaRepository;

    @Autowired
    public CourseServiceImpl(CourseJpaDAO c,SchoolClassJpaRepository schoolClassJpaRepository){
        this.courseJpaDAO = c;
        this.schoolClassJpaRepository = schoolClassJpaRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<ServerResponse> createCourse(CourseDTO cDTO) throws DuplicateEntryException{
        Course c = CourseMapping.INSTANCE.courseDTOToCourse(cDTO);
        try{
            courseJpaDAO.save(c);
            return new ResponseEntity<ServerResponse>(new ServerResponse("success",new ArrayList<>()), HttpStatus.OK);
        }
        catch(DataIntegrityViolationException ex){
            throw new DuplicateEntryException("This course name is already in use");
        }

    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> addClassToCourse(AddClassToCourseDTO cDTO) throws SchoolClassNotFoundException,CourseNotFoundException,AdditionOfClassToCourseException{
        Course course = courseExistsById(cDTO.getCourseId());
        SchoolClass schoolClass = schoolClassExistsById(cDTO.getClassId());

        course.addSchoolClass(schoolClass);
        schoolClass.setCourse(course);

        try{
            courseJpaDAO.save(course);
        }catch (DataAccessException ex){
            throw new AdditionOfClassToCourseException("Could not add class to course");
        }
        return new ResponseEntity<ServerResponse>(new ServerResponse("success",new ArrayList<>()),HttpStatus.OK);
    }
    private Course courseExistsById(String id) throws CourseNotFoundException{
        Optional<Course> courseOptional = courseJpaDAO.findById(id);
        if(courseOptional.isEmpty()){
            throw new CourseNotFoundException("Invalid Course ID");
        }
        return courseOptional.get();
    }
    private SchoolClass schoolClassExistsById(String id) throws SchoolClassNotFoundException{
        Optional<SchoolClass> schoolClassOptional = schoolClassJpaRepository.findById(id);
        if(schoolClassOptional.isEmpty()){
            throw new CourseNotFoundException("Invalid Course ID");
        }
        return schoolClassOptional.get();
    }
}
