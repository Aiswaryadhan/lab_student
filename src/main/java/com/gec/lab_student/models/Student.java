package com.gec.lab_student.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Student {



    private String id;

    private String name;

    private String password;

    private Integer sem;


}
