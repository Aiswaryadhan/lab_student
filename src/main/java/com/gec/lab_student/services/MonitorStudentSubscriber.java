package com.gec.lab_student.services;

import com.gec.lab_student.controllers.ScreenCaptureController;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.IOException;

@Service
public class MonitorStudentSubscriber {

    @Autowired
    MonitorScreenCapture monitorScreenCapture;

    @Autowired
    ScreenCaptureController screenCaptureController;
    TopicConnection conn = null;
    TopicSession session = null;
    Topic topic = null;
    static Boolean IS_RUNNING=false;
    //    WebsiteBlocking websiteBlocking;
    public void check(String studId) throws JMSException, IOException, InterruptedException {
        TopicConnection topicConnection = null;
        // Producer
        TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://192.168.42.215:61616");
        topicConnection = connectionFactory.createTopicConnection();
//        topicConnection.setClientID("JMSTOPIC");

        TopicSession topicConsumerSession = topicConnection.createTopicSession(
                false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = topicConsumerSession.createTopic("monitorTopic");


        // create a topic subscriber
        TopicSubscriber topicSubscriber = topicConsumerSession.createSubscriber(topic);

        // start the connection
        topicConnection.start();

        // receive the message
        Message message =topicSubscriber.receive();
        String msg = ((ActiveMQTextMessage)message).getText();
        String msg1 = msg.replaceAll("\\[","").replaceAll("\\]","").replaceAll(" ","");
        System.out.println(msg1);
        int len=msg1.length();
        if(len!=0){
            if(msg.equalsIgnoreCase(msg1)){
                screenCaptureController.startScreenCaptureMonitoring();

            }
            else{
                screenCaptureController.
                        stopScreenCaptureMonitoring();
            }
        }

        // print the message
        System.out.println("Message received: " + message);

        // close the topic connection
//        topicConnection.close();
    }
}
