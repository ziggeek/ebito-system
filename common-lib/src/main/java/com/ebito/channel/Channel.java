package com.ebito.channel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Канал формирования документа
 */
@Getter
@RequiredArgsConstructor
public enum Channel {

    /**
     * Личный кабинет на сайте
     */
    ONLINE("Личный кабинет", "личном кабинете"),

    /**
     * Мобильное приложение
     */
    MOBILE("Мобильное приложение", "мобильном приложении"),

    /**
     * Отделение
     */
    BRANCH("Отделение","отделении");

    private final String channelName;
    private final String channelNameForForm;
}
