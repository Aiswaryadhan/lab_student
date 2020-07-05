package com.gec.lab_student.controllers;

import com.gec.lab_student.services.MonitorScreenCapture;
import com.gec.lab_student.services.MonitorStudentSubscriber;
import com.gec.lab_student.services.ScreenCapture;
import com.gec.lab_student.services.SiteSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

@RestController
public class ScreenCaptureController {

    @Autowired
    ScreenCapture screenCapture;

    @Autowired
    MonitorScreenCapture monitorScreenCapture;

    @Autowired
    MonitorStudentSubscriber monitorStudentSubscriber;

    @Autowired
    SiteSubscriber siteSubscriber;

    static Boolean IS_RUNNING=false;

    @RequestMapping("/start")
    public void startScreenCapture() throws InterruptedException, IOException {
        IS_RUNNING = Boolean.TRUE;
        while (IS_RUNNING){
            Thread.sleep(200);
            System.out.println("image");
            screenCapture.publishScreen();
        }
    }

//    @RequestMapping("/studentMonitorStart")
    public void startScreenCaptureMonitoring() throws InterruptedException, IOException {
        IS_RUNNING = Boolean.TRUE;
        while (IS_RUNNING){
            Thread.sleep(200);
            monitorScreenCapture.publishScreen();
        }
    }

    @RequestMapping("/subscriberCheck/{studId}")
    public void subscriberCheck(@PathVariable String studId) throws IOException, JMSException, InterruptedException {

            monitorStudentSubscriber.check(studId);


    }

    @RequestMapping("/stop")
    public void stopScreenCapture() throws InterruptedException, IOException {
        IS_RUNNING = Boolean.FALSE;
    }

    public void stopScreenCaptureMonitoring() throws InterruptedException, IOException {
        IS_RUNNING = Boolean.FALSE;
    }

    @RequestMapping(value = "/findIpAddress",method = RequestMethod.POST)
    public String findIpAddress() throws UnknownHostException {
        InetAddress ip = null;
        String hostname;
        String IPAddress = null;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            IPAddress = ip.getHostAddress();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        return IPAddress;
    }

    @RequestMapping("/sitesBlock")
    public void sitesBlock() throws Exception {
        System.out.println("Sites Block");
        siteSubscriber.getMsg();
    }
    @RequestMapping("/sitesUnblock")
    public void sitesUnblock() throws Exception {
        System.out.println("Sites Unblock");
        siteSubscriber.unblock();
    }
}
