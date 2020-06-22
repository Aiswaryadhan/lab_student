package com.gec.lab_student.services;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.tomcat.jni.Proc;
import org.json.JSONObject;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Year;
import java.util.*;
import java.util.logging.Logger;

@Service
public class SiteSubscriber {
    TopicConnection conn = null;
    TopicSession session = null;
    Topic topic = null;
    //    WebsiteBlocking websiteBlocking;
    public static String msg1=null;
    public static int len=0;
    public void getMsg() throws Exception {
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
        msg1 = msg.replaceAll("\\[","").replaceAll("\\]","").replaceAll(" ","");
        System.out.println(msg1);
//        String msg ="hai";
        len=msg1.length();
        if(len!=0){
            String arr[]=msg1.split(",");
            System.out.println(msg1);
            int l=arr.length;
            try{
                String prg="from __future__ import with_statement\n" +
                        "import sys\n" +
                        "import time \n" +
                        "from datetime import datetime as dt \n" +
                        "def get_platform(arr):\n" +
                        "    website_list=list(arr.split(\",\"))\n" +
                        "    platforms = {\n" +
                        "        'linux1' : 'Linux',\n" +
                        "        'linux2' : 'Linux',\n" +
                        "        'darwin' : 'OS X',\n" +
                        "        'win32' : 'Windows'\n" +
                        "    }\n" +
                        "    if sys.platform not in platforms:\n" +
                        "        res=sys.platform\n" +
                        "    res=platforms[sys.platform]\n" +
                        "    if res=='Linux' or res=='OS X':\n" +
                        "        hosts_path = \"/etc/hosts\"\n" +
                        "    elif res=='Windows':\n" +
                        "        hosts_path =  \"C:\\Windows\\System32\\drivers\\etc\"\n" +
                        "    else:\n" +
                        "        pass \n" +
                        "    redirect = \"127.0.0.1\"\n" +
                        "    while True: \n" +
                        "        print(\"Working hours...\") \n" +
                        "        with open(hosts_path, 'r+') as file: \n" +
                        "            content = file.read() \n" +
                        "            for website in website_list: \n" +
                        "                if website in content: \n" +
                        "                    pass\n" +
                        "                else: \n" +
                        "                    file.write(redirect + \" \" + website + \"\\n\") \n" +
                        "if __name__==\"__main__\":\n" +
                        "    sit=sys.argv[1]\n" +
                        "    get_platform(sit)\t";
                BufferedWriter out=new BufferedWriter(new FileWriter("platform.py"));
                out.write(prg);
                out.close();
                    System.out.println(msg1);
                   Process p= Runtime.getRuntime().exec("python platform.py " + msg1);
                    BufferedReader in= new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String ret=in.readLine();
                    System.out.println("value"+ret);


            }catch (Exception e){
                e.printStackTrace();
            }

//            interpreter.execfile("/home/aiswarya/platf.py");
//            PyObject someFunc = interpreter.get("get_platform");
//            if (someFunc == null) {
//                throw new Exception("Could not find Python function: " + "get_platform");
//            }
//            try {
//                someFunc.__call__(new PyList(Arrays.asList(arr)));
//            } catch (PyException e) {
//                e.printStackTrace();
//            }
//            for(int i=0;i<l;i++) {
//                System.out.println(arr[i]);
//                String OS = System.getProperty("os.name").toLowerCase();
//
//                // Use OS name to find correct location of hosts file
//                String hostsFile = "";
//                if ((OS.indexOf("win") >= 0)) {
//                    // Doesn't work before Windows 2000
//                    hostsFile = "C:\\Windows\\System32\\drivers\\etc\\hosts";
//                } else if ((OS.indexOf("mac") >= 0)) {
//                    // Doesn't work before OS X 10.2
//                    hostsFile = "etc/hosts";
//                } else if ((OS.indexOf("nux") >= 0)) {
//                    hostsFile = "/etc/hosts";
//                } else {
//                    // Handle error when platform is not Windows, Mac, or Linux
//                    System.err.println("Sorry, but your OS doesn't support blocking.");
//                    System.exit(0);
//                }
//
//                File file = new File(hostsFile);
//
//                //Set write permission on File for all.
//                if (file.exists()) {
//                    boolean bval = file.setWritable(true,false);
//                    System.out.println("set the every user write permission: "+ bval);
//                } else {
//                    System.out.println("File not exists: ");
//                }
//                // Actually block site
//                Files.write(Paths.get(hostsFile),
//                        ("127.0.0.1 " + arr[i] + "\n").getBytes(),
//                        StandardOpenOption.APPEND);
//            }
        }

//        System.out.println("hai");
        // print the message
        System.out.println("Message received: " + message);

        // close the topic connection
        topicConnection.close();
    }
    public void unblock(){
        System.out.println("unblock");
        if(len!=0) {
            System.out.println(msg1);
            try {
                String prg = "from __future__ import with_statement\n" +
                        "import sys\n" +
                        "import time \n" +
                        "from datetime import datetime as dt \n" +
                        "def get_platform(arr):\n" +
                        "    website_list=list(arr.split(\",\"))\n" +
                        "    platforms = {\n" +
                        "        'linux1' : 'Linux',\n" +
                        "        'linux2' : 'Linux',\n" +
                        "        'darwin' : 'OS X',\n" +
                        "        'win32' : 'Windows'\n" +
                        "    }\n" +
                        "    if sys.platform not in platforms:\n" +
                        "        res=sys.platform\n" +
                        "    res=platforms[sys.platform]\n" +
                        "    if res=='Linux' or res=='OS X':\n" +
                        "        hosts_path = \"/etc/hosts\"\n" +
                        "    elif res=='Windows':\n" +
                        "        hosts_path =  \"C:\\Windows\\System32\\drivers\\etc\"\n" +
                        "    else:\n" +
                        "        pass \n" +
                        "    redirect = \"127.0.0.1\"\n" +
                        "    while True: \n" +
                        "        with open(hosts_path, 'r+') as file: \n" +
                        "            content=file.readlines() \n" +
                        "            file.seek(0) \n" +
                        "            for line in content: \n" +
                        "                if not any(website in line for website in website_list): \n" +
                        "                    file.write(line) \n" +
                        "            file.truncate() \n" +
                        "        print(\"Fun hours...\") \t\n" +
                        "if __name__==\"__main__\":\n" +
                        "    sit=sys.argv[1]\n" +
                        "    get_platform(sit)\t";
                BufferedWriter out = new BufferedWriter(new FileWriter("unblock.py"));
                out.write(prg);
                out.close();
                System.out.println(msg1);
                Process p = Runtime.getRuntime().exec("python unblock.py " + msg1);
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String ret = in.readLine();
                System.out.println("value" + ret);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}