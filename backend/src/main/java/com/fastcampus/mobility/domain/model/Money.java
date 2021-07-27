package com.fastcampus.mobility.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Money {

    public static final Money ZERO = Money.of(BigDecimal.ZERO);

    private final BigDecimal amount;

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money of(int amount) {
        return new Money(BigDecimal.valueOf(amount));
    }
}
