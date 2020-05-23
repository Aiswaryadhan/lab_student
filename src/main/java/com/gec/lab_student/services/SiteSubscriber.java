package com.gec.lab_student.services;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class SiteSubscriber {
    TopicConnection conn = null;
    TopicSession session = null;
    Topic topic = null;
//    WebsiteBlocking websiteBlocking;
    public void getMsg() throws JMSException, IOException {
//        System.out.println("hello");
        TopicConnection topicConnection = null;
        // Producer
        TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:61616");
        topicConnection = connectionFactory.createTopicConnection();
//        topicConnection.setClientID("JMSTOPIC");

        TopicSession topicConsumerSession = topicConnection.createTopicSession(
                false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = topicConsumerSession.createTopic("customerTopic");


        // create a topic subscriber
        TopicSubscriber topicSubscriber = topicConsumerSession.createSubscriber(topic);

        // start the connection
        topicConnection.start();

        // receive the message
        Message message =topicSubscriber.receive();
        String msg = ((ActiveMQTextMessage)message).getText();
        String msg1 = msg.replaceAll("\\[","").replaceAll("\\]","").replaceAll(" ","");
        System.out.println(msg1);
//        String msg ="hai";
        int len=msg1.length();
        if(len!=0){
            String arr[]=msg1.split(",");
            int l=arr.length;
            for(int i=0;i<l;i++) {
                System.out.println(arr[i]);
                String OS = System.getProperty("os.name").toLowerCase();

                // Use OS name to find correct location of hosts file
                String hostsFile = "";
                if ((OS.indexOf("win") >= 0)) {
                    // Doesn't work before Windows 2000
                    hostsFile = "C:\\Windows\\System32\\drivers\\etc\\hosts";
                } else if ((OS.indexOf("mac") >= 0)) {
                    // Doesn't work before OS X 10.2
                    hostsFile = "etc/hosts";
                } else if ((OS.indexOf("nux") >= 0)) {
                    hostsFile = "/etc/hosts";
                } else {
                    // Handle error when platform is not Windows, Mac, or Linux
                    System.err.println("Sorry, but your OS doesn't support blocking.");
                    System.exit(0);
                }

                // Actually block site
                Files.write(Paths.get(hostsFile),
                        ("127.0.0.1 " + arr[i] + "\n").getBytes(),
                        StandardOpenOption.APPEND);
            }
        }

//        System.out.println("hai");
        // print the message
        System.out.println("Message received: " + message);

        // close the topic connection
        topicConnection.close();
    }
}
