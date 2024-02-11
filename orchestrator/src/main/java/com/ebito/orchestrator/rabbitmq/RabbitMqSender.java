package com.ebito.orchestrator.rabbitmq;

import com.ebito.orchestrator.api.controller.request.PrintFormGenerationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMqSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routingkey}")
    private String routingKey;

    public void send(PrintFormGenerationRequest msg) {
        amqpTemplate.convertAndSend(exchange, routingKey, msg);
        log.info("Send msg = {}", msg);
    }
}
