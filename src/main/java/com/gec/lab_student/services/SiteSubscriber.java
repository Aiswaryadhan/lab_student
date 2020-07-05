package com.gec.lab_student.services;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.stereotype.Service;
import javax.jms.*;
import java.io.*;

@Service
public class SiteSubscriber {
    TopicConnection conn = null;
    TopicSession session = null;
    Topic topic = null;
    public static String msg1=null;
    public static int len=0;
    public void getMsg() throws Exception {
        TopicConnection topicConnection = null;
        // Producer
        TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://192.168.42.215:61616");
        topicConnection = connectionFactory.createTopicConnection();
        topicConnection.start();
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
        }

        // print the message
        System.out.println("Message received: " + message);

        // close the topic connection
//        topicConnection.close();
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