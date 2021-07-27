package com.fastcampus.mobility.domain.model.impl;

import com.fastcampus.mobility.domain.model.Money;
import com.fastcampus.mobility.domain.model.RatePolicy;

public abstract class CallRatePolicy extends NextRatePolicy {


    protected CallRatePolicy(RatePolicy prevRatePolicy) {
        super(prevRatePolicy);
    }

    protected abstract Money getCallFee();

    @Override
    public Money calculateNextFee(Money money) {
        Money callFee = this.getCallFee();
        return null;
    }
}
