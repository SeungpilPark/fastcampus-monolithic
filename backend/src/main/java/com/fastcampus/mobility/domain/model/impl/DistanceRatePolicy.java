package com.fastcampus.mobility.domain.model.impl;

import com.fastcampus.mobility.domain.model.Driving;
import com.fastcampus.mobility.domain.model.Money;
import com.fastcampus.mobility.domain.model.RatePolicy;

public abstract class DistanceRatePolicy implements RatePolicy {

    private Driving driving;

    protected DistanceRatePolicy(Driving driving) {
        this.driving = driving;
    }

    @Override
    public Money calculateFee() {
        // 운행시작, 종료, 주행거리
        return null;
    }

    public abstract Money getBaseFee();

    public abstract Integer getMeterUnit();

    public abstract Money getFeeOfMeterUnit();
}
