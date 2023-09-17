package com.consumer.listerner;

import com.consumer.model.User;
import com.consumer.util.JsonUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
@Slf4j
public class MessageConsumer {

    @JmsListener(destination = "topic-test")
    public void messageReceiver(Session session, Message message){

        //This is from javax.jms.Message
        //Message receive: ActiveMQTextMessage {commandId = 7, responseRequired = true, messageId = ID:DESKTOP-EG264E4-54350-1694936163064-1:1:1:1:3, originalDestination = null, originalTransactionId = null, producerId = ID:DESKTOP-EG264E4-54350-1694936163064-1:1:1:1, destination = queue://topic-test, transactionId = null, expiration = 0, timestamp = 1694936757056, arrival = 0, brokerInTime = 1694936757056, brokerOutTime = 1694936757057, correlationId = null, replyTo = null, persistent = true, type = null, priority = 4, groupID = null, groupSequence = 0, targetConsumerId = null, compressed = false, userID = null, content = org.apache.activemq.util.ByteSequence@3fb38a17, marshalledProperties = null, dataStructure = null, redeliveryCounter = 0, size = 0, properties = null, readOnlyProperties = true, readOnlyBody = true, droppable = false, jmsXGroupFirstForConsumer = false, text = {"name":"conred","age":99}}

        if (message instanceof TextMessage) {
            try {
                TextMessage textMessage = (TextMessage) message;
                String messageReceive = textMessage.getText();
                log.info("Message string: {}", messageReceive);
                User user = JsonUtility.toObject(messageReceive,User.class);
                log.info("Message transfer to entity: {}", user);
                message.acknowledge();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }


    }
}
