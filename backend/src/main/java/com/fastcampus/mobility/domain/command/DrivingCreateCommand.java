package com.fastcampus.mobility.domain.command;

import com.fastcampus.mobility.domain.enums.CallType;
import com.fastcampus.mobility.domain.enums.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class DrivingCreateCommand {

    @NotNull
    private VehicleType vehicleType;
    @NotNull
    private CallType callType;
    @NotBlank(message = "{driving.NotBlank.boardingCoordinates}")
    private String boardingCoordinates;
    @NotBlank(message = "{driving.NotBlank.destinationCoordinates}")
    private String destinationCoordinates;
}

