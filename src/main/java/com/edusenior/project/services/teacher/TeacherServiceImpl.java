package com.edusenior.project.services.teacher;

import com.edusenior.project.Mappings.TeacherMapper;
import com.edusenior.project.Utility.BcryptPasswordEncoder;
import com.edusenior.project.dataAccessObjects.teacher.TeacherDAO;
import com.edusenior.project.dataTransferObjects.NewTeacherDTO;
import com.edusenior.project.entities.Teacher;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService{

    private TeacherDAO teacherDAO;
    private BcryptPasswordEncoder encoder;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO, BcryptPasswordEncoder encoder) {
        this.teacherDAO = teacherDAO;
        this.encoder = encoder;
    }

    @Transactional
    public void registerTeacher(NewTeacherDTO tDTO) {
        Teacher t = Mappers.getMapper(TeacherMapper.class).newTeacherDtoToTeacher(tDTO);
        t.setHash(encoder.passwordEncoder().encode(tDTO.getPassword()));
        teacherDAO.createTeacher(t);
    }
}
