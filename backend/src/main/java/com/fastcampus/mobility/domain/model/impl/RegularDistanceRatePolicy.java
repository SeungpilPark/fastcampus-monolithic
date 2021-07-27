package com.fastcampus.mobility.domain.model.impl;

import com.fastcampus.mobility.domain.model.Driving;
import com.fastcampus.mobility.domain.model.Money;

public class RegularDistanceRatePolicy extends DistanceRatePolicy {

    public RegularDistanceRatePolicy(Driving driving) {
        super(driving);
    }

    @Override
    public Money getBaseFee() {
        return null;
    }

    @Override
    public Integer getMeterUnit() {
        return null;
    }

    @Override
    public Money getFeeOfMeterUnit() {
        return null;
    }
}