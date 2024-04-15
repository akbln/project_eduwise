package com.edusenior.project.entities.Users;

import com.edusenior.project.entities.SchoolClass;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers",schema = "edusenior")
@PrimaryKeyJoinColumn(name = "user_id")
public class Teacher extends User {

    public Teacher(){
        super();
    }

    @OneToMany(mappedBy = "teacher")
    private List<SchoolClass> schoolClasses;

    public void addClass(SchoolClass c){
        if (schoolClasses == null){
            schoolClasses = new ArrayList<>();
        }
        schoolClasses.add(c);
        c.setTeacher(this);
    }

}
