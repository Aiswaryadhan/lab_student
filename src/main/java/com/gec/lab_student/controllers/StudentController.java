package com.gec.lab_student.controllers;

import com.gec.lab_student.models.Student;
import org.springframework.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class StudentController {

    final Logger logger = LoggerFactory.getLogger(StudentController.class);

        @Autowired
        RestTemplate restTemplate;

//        @Autowired
//        Student stud;
    private Student responseBody;

    @RequestMapping(method = RequestMethod.POST,value ="/student/login")
    public String login(@RequestBody Student student) {
        logger.info("hai");
        String url="http://localhost:8080/student/login";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-Type", "application/json");
        HttpEntity<?> httpEntity = new HttpEntity<Object>(student, headers);
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,Student.class);



        Student stud=(Student)response.getBody();
        String name=stud.getName();
        int sem=stud.getSem();
        String res=name+","+sem;
        return res;
    }

    @RequestMapping(method = RequestMethod.POST,value ="/student/getTeacherName")
    public String getTeacherName() {
        logger.info("hai");
        String url="http://localhost:8080/student/getTeacherName";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-Type", "application/json");
        HttpEntity<?> httpEntity = new HttpEntity<Object>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,String.class);



        String res=response.getBody();
        System.out.println(res);
        return res;
    }


}
