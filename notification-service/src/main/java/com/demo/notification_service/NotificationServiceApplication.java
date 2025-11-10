package com.demo.notification_service;

import com.demo.notification_service.model.Product; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@EnableRabbit  // activa el procesamiento de @RabbitListener
@SpringBootApplication
public class NotificationServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceApplication.class);

    @Value("${app.rabbitmq.queue}")
    private String queueName;

    @Value("${app.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${app.rabbitmq.routingkey}")
    private String routingKey;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    // --- Listener que recibe los mensajes ---
    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void onProductCreated(Product product) {
        log.info(" --- EVENTO RECIBIDO ---");
        log.info(" ID: {}", product.id());
        log.info(" Producto: {}", product.name());
    }

    // --- Configuración de RabbitMQ ---

    // Cola (no persistente)
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    // Exchange tipo "topic"
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    // Binding: une la cola y el exchange con la routing key
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}
