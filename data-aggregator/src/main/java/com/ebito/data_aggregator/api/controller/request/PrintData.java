package com.ebito.data_aggregator.api.controller.request;

import com.ebito.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Общий класс-родитель для классов передачи данных для справок
 */
@Getter
@Setter
@SuperBuilder
public abstract class PrintData {
    private String form;
    private Channel channel;
    private long clientId;
}
