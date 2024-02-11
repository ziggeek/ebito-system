package com.ebito.orchestrator.rabbitmq;

import com.ebito.orchestrator.api.controller.response.PrintedGuids;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqListener {

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receiver(PrintedGuids msg) {
        log.info("Got msg from data-aggregator = {}",  msg);

    }

}
