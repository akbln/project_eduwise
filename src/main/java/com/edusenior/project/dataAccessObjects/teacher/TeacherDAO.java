package com.edusenior.project.dataAccessObjects.teacher;

import com.edusenior.project.dataTransferObjects.CredentialsDTO;
import com.edusenior.project.entities.Teacher;

public interface TeacherDAO {
    public Teacher fetchTeacherById(String id);
    public Teacher fetchTeacherByEmail(String email);
    public void createTeacher(Teacher t);
    public CredentialsDTO fetchCredentials(String email);
}
