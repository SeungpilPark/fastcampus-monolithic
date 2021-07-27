package com.fastcampus.mobility.domain.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter
public class MoneyToBigDecimalConverter implements AttributeConverter<Money, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(Money attribute) {
        return attribute.getAmount();
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal dbData) {
        return Money.of(dbData);
    }
}
