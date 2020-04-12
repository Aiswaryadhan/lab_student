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

        @RequestMapping(method = RequestMethod.POST,value ="/student/login")
        public Student login(@RequestBody Student student) {
            logger.info("hai");
            String url="http://localhost:8080/student/login";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.set("Content-Type", "application/json");
            HttpEntity<?> httpEntity = new HttpEntity<Object>(student, headers);
           ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,Student.class);
//           String arr[]=new String[10];
////           int len=result.getBody().length();
////           int i;
////            for(i=0;i<len;i++) {
//                arr=result.getBody().split(",");
            return response.getBody();
        }

//    @RequestMapping(method = RequestMethod.GET,value ="/student/getName/{studId}")
//    public String findStudent(@PathVariable String studId) {
//        logger.info("student");
//        String uri="http://localhost:8080/student/getName/18MCA01";
//        String result = restTemplate.getForObject(uri,String.class);
//        return result;
//    }

}
