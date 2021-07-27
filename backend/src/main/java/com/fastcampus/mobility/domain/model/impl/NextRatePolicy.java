package com.fastcampus.mobility.domain.model.impl;

import com.fastcampus.mobility.domain.model.Money;
import com.fastcampus.mobility.domain.model.RatePolicy;

public abstract class NextRatePolicy implements RatePolicy {

    private RatePolicy prevRatePolicy;

    protected NextRatePolicy(RatePolicy prevRatePolicy) {
        this.prevRatePolicy = prevRatePolicy;
    }

    @Override
    public Money calculateFee() {
        Money money = prevRatePolicy.calculateFee();
        return this.calculateNextFee(money);
    }

    public abstract Money calculateNextFee(Money money);
}
