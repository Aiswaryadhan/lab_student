package com.gec.lab_student.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.lab_student.utilities.ZipUtility;
import org.apache.activemq.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.List;

@Service
public class ActivemqConsumerService {

    Logger logger = LoggerFactory.getLogger(com.gec.lab_student.services.ActivemqConsumerService.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(com.gec.lab_student.services.ActivemqConsumerService.class);

    @Autowired
    EventSimulator eventSimulator;

    ObjectMapper mapper = new ObjectMapper();

    @JmsListener(destination = "events_queue", containerFactory = "activeMQContainerFactory")
    public void processMessage(Message message) throws Exception {

        if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) message;
            int messageLength = new Long(bytesMessage.getBodyLength()).intValue();
            byte[] textBytes = new byte[messageLength];
            bytesMessage.readBytes(textBytes, messageLength);

            eventSimulator.updateData(ZipUtility.byteArrayToObject(textBytes));
        }

    }

    /*@JmsListener(destination = "topic_blocked_site", containerFactory = "activeMQContainerFactory")
    public void processUrl(Message message) throws Exception {

            List<String> textMessage = (List<String>) message;
        System.out.println(" received " + textMessage);
    }*/

        /*if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) message;
            int messageLength = new Long(bytesMessage.getBodyLength()).intValue();
            byte[] textBytes = new byte[messageLength];
            bytesMessage.readBytes(textBytes, messageLength);

            eventSimulator.updateData(ZipUtility.byteArrayToObject(textBytes));
        }*/


}
