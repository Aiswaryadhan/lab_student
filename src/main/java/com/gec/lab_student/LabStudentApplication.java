package com.gec.lab_student;

import com.gec.lab_student.services.ScreenCapture;
import com.gec.lab_student.services.SiteSubscriber;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import com.vnetpublishing.java.suapp.SuperUserApplication;

@SpringBootApplication
@EnableJms
@EnableAsync
public class LabStudentApplication extends SuperUserApplication {

    @Value("${spring.activemq.broker-url}")
    private String activeMQUrl;

//    @Value("${activemq.user}")
//    private String activemqUser;
//
//    @Value("${activemq.password}")
//    private String activemqPassword;

    @Autowired
    private static ScreenCapture screenCapture=new ScreenCapture();
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
    public static void main(String[] args) throws IOException, NamingException, JMSException {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(LabStudentApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

    }
    public int run(String[] args) {
        System.out.println("RUN AS ADMIN! YAY!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Bean
    JmsListenerContainerFactory<?> activeMQContainerFactory(@Qualifier("activeMQ") ConnectionFactory connectionFactory) throws JMSException {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean(name = "activeMQ")
    public ConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(activeMQUrl);
//        connectionFactory.setUserName(activemqUser);
//        connectionFactory.setPassword(activemqPassword);
        connectionFactory.setUseAsyncSend(true);
        connectionFactory.setOptimizeAcknowledge(true);
        return connectionFactory;
    }


}
