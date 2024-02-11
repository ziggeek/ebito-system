package com.ebito.data_aggregator.rabbitmq;

import com.ebito.data_aggregator.rabbitmq.model.PrintFormGenerationRequest;
import com.ebito.data_aggregator.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqListener {

    private final CommonService commonService;
    private final RabbitMqSender rabbitMqSender;

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receiver(PrintFormGenerationRequest msg) {
        log.info("Got msg from orchestrator = {}", msg);
        final var printedForm = commonService.selectPrintForm(msg);
        rabbitMqSender.send(printedForm);
    }

}
