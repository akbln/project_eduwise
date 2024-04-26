package com.edusenior.project.dataAccessObjects.teacher;

import com.edusenior.project.entities.Users.Teacher;

public interface TeacherDAO {
    public Teacher fetchTeacherById(String id);
    public Teacher fetchTeacherByEmail(String email);
    public void createTeacher(Teacher t);
//    public CredentialsDTO fetchCredentials(String email);
//    public boolean checkExistingEmail(String email);
}
