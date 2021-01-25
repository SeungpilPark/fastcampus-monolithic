package com.fastcampus.mobility.controller;

import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.command.VehicleAddCommand;
import com.fastcampus.mobility.dto.command.VehicleUpdateCommand;
import com.fastcampus.mobility.dto.search.VehicleSearchDto;
import com.fastcampus.mobility.service.spec.VehicleCommandService;
import com.fastcampus.mobility.service.spec.VehicleDomainService;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle")
@Validated
public class VehicleController {

  private final VehicleCommandService vehicleCommandService;
  private final VehicleDomainService vehicleDomainService;

  @Autowired
  public VehicleController(
      final VehicleCommandService vehicleCommandService,
      final VehicleDomainService vehicleDomainService
  ) {
    this.vehicleCommandService = vehicleCommandService;
    this.vehicleDomainService = vehicleDomainService;
  }

  @GetMapping(value = "")
  public ResponseEntity<Page<VehicleDto>> search(
      final VehicleSearchDto vehicleSearchDto,
      final @PageableDefault(size = 50) Pageable pageable) {
    return new ResponseEntity<>(
        vehicleDomainService.search(vehicleSearchDto, pageable), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<VehicleDto> get(
      final @PathVariable("id") @NotNull @Min(1) Long id) {
    return new ResponseEntity<>(vehicleDomainService.get(id), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<VehicleDto> add(
      @Valid final @RequestBody VehicleAddCommand addCommand) {
    return new ResponseEntity<>(vehicleCommandService.vehicleAdd(addCommand), HttpStatus.OK);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<VehicleDto> update(
      final @PathVariable("id") @NotNull @Min(1) Long id,
      @Valid final @RequestBody VehicleUpdateCommand updateCommand) {
    updateCommand.setVehicleId(id);
    return new ResponseEntity<>(vehicleCommandService.vehicleUpdate(updateCommand), HttpStatus.OK);
  }
}

