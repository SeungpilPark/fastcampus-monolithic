package com.fastcampus.mobility.domain.model.impl;

import com.fastcampus.mobility.domain.model.Money;
import com.fastcampus.mobility.domain.model.RatePolicy;

public class BlueCallRatePolicy extends CallRatePolicy {

    public BlueCallRatePolicy(RatePolicy prevRatePolicy) {
        super(prevRatePolicy);
    }

    @Override
    protected Money getCallFee() {
        return null;
    }
}
