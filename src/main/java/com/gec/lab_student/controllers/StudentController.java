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
        public String login(@RequestBody Student student) {
            logger.info("hai");
            String url="http://localhost:8080/student/login";
//            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.set("Content-Type", "application/json");
//            HttpEntity entity = new HttpEntity(body,headers);
            HttpEntity<?> httpEntity = new HttpEntity<Object>(student, headers);
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            return result.getBody();
        }

}
