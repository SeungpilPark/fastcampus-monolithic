package com.fastcampus.mobility.domain.model.impl;

import com.fastcampus.mobility.domain.model.Money;
import com.fastcampus.mobility.domain.model.RatePolicy;

public class SmartCallRatePolicy extends CallRatePolicy {

    public SmartCallRatePolicy(RatePolicy prevRatePolicy) {
        super(prevRatePolicy);
    }

    @Override
    protected Money getCallFee() {
        return null;
    }
}
