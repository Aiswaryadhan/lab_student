package com.gec.lab_student.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class ActivemqProducerService {


    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(com.gec.lab_student.services.ActivemqProducerService.class);

    @PostConstruct
    public void test(){
        this.send("sample message");
    }

    public void send(String message) {
        LOGGER.info("sending message='{}'", message);
        jmsTemplate.convertAndSend("test_queue", message);
    }

    public void send(byte[] bytes) {
		jmsTemplate.send("image_queue", new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            BytesMessage bytesMessage = session.createBytesMessage();
                            bytesMessage.writeBytes(bytes);
                            return bytesMessage;
                        }
                    });
    }

    ObjectMapper objectMapper = new ObjectMapper();


}