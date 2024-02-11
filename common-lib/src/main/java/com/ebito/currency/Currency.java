package com.ebito.currency;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {

    RUB("RUB", "Рубль РФ"),
    USD("USD", "Доллар США"),
    EURO("EURO", "Евро");

    private final String currencyCode;
    private final String currencyFullName;
}
