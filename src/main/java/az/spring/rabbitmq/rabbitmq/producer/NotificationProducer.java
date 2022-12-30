package az.spring.rabbitmq.rabbitmq.producer;

import az.spring.rabbitmq.rabbitmq.model.Notification;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class NotificationProducer {

    @Value("${sr.rabbit.routing.name}")
    private String routingName;

    @Value("${sr.rabbit.exchange.name}")
    private String exchangeName;

    @PostConstruct
    public void init() {
        Notification notification = new Notification();
        notification.setNotificationId(UUID.randomUUID().toString());
        notification.setCreatedAt(new Date());
        notification.setMessage("Welcome to RabbitMQ platform");
        notification.setSeen(Boolean.FALSE);

        sendToQueue(notification);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendToQueue(Notification notification) {
        System.out.println("Notification sent id : " + notification.getNotificationId());
        rabbitTemplate.convertAndSend(exchangeName, routingName, notification);
    }

}