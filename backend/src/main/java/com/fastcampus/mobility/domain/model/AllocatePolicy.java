package com.fastcampus.mobility.domain.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AllocatePolicy {

    public static List<Allocate> recommendVehicle(Driving driving) {
        List<Allocate> allocates = Stream.of(new Allocate()).collect(Collectors.toList());
        return allocates;
    }
}
