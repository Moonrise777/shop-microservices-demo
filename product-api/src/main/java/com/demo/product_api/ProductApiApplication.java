package com.demo.product_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

// Un DTO (Objeto de Transferencia de Datos) simple para el producto
record Product(String id, String name) {
}

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class ProductApiApplication {

    private static final Logger log = LoggerFactory.getLogger(ProductApiApplication.class);
    // Inyectamos el "Template" de RabbitMQ para enviar mensajes
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Leemos los nombres de las properties
    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routingkey}")
    private String routingKey;

    public static void main(String[] args) {
        SpringApplication.run(ProductApiApplication.class, args);
    }

    // El endpoint REST
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        log.info("RECIBIDO: Producto {}", product.name());

        // Enviamos el objeto "product" al "exchange"
        // con la "routing key". Spring lo convierte a JSON automáticamente.
        rabbitTemplate.convertAndSend(exchange, routingKey, product);

        log.info("EVENTO ENVIADO: Producto {}", product.id());
        return product;
    }
}