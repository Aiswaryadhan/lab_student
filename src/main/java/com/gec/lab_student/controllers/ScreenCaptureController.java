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
    @RequestMapping(value = "/findIpAddress",method = RequestMethod.GET)
    public String findIpAddress() throws UnknownHostException {
        String ip=null;
        try {
            Enumeration<NetworkInterface> nics = NetworkInterface
                    .getNetworkInterfaces();
            while (nics.hasMoreElements()) {
                NetworkInterface nic = nics.nextElement();
                if (!nic.isLoopback()) {
                    Enumeration<InetAddress> addrs = nic.getInetAddresses();
                    while (addrs.hasMoreElements()) {
                        InetAddress addr = addrs.nextElement();
                        ip= addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }

        @RequestMapping("/sitesBlock")
    public void sitesBlock() throws InterruptedException, IOException, JMSException {
        System.out.println("Sites Block");
        siteSubscriber.getMsg();
    }
}
