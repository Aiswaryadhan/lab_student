package com.gec.lab_student.controllers;

import com.gec.lab_student.services.ScreenCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ScreenCaptureController {

    @Autowired
    ScreenCapture screenCapture;

    static Boolean IS_RUNNING=false;

    @RequestMapping("/start")
    public void startScreenCpature() throws InterruptedException, IOException {
        IS_RUNNING = Boolean.TRUE;
        while (IS_RUNNING){
            Thread.sleep(200);
            screenCapture.publishScreen();
        }
    }

    @RequestMapping("/stop")
    public void stopScreenCapture() throws InterruptedException, IOException {
        IS_RUNNING = Boolean.FALSE;
    }
}
