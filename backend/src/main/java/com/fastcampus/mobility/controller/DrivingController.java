package com.fastcampus.mobility.controller;

import com.fastcampus.mobility.dto.DrivingDto;
import com.fastcampus.mobility.dto.command.DispatchAcceptanceCommand;
import com.fastcampus.mobility.dto.command.DrivingAddCommand;
import com.fastcampus.mobility.dto.search.DrivingSearchDto;
import com.fastcampus.mobility.service.spec.DrivingCommandService;
import com.fastcampus.mobility.service.spec.DrivingDomainService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/driving")
@Validated
public class DrivingController {

  private final DrivingCommandService drivingCommandService;
  private final DrivingDomainService drivingDomainService;

  @Autowired
  public DrivingController(
      final DrivingCommandService drivingCommandService,
      final DrivingDomainService drivingDomainService
  ) {
    this.drivingCommandService = drivingCommandService;
    this.drivingDomainService = drivingDomainService;
  }

  @GetMapping(value = "")
  public ResponseEntity<Page<DrivingDto>> search(
      final DrivingSearchDto drivingSearchDto,
      final @PageableDefault(size = 50) Pageable pageable) {
    return new ResponseEntity<>(
        drivingDomainService.search(drivingSearchDto, pageable), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<DrivingDto> get(
      final @PathVariable("id") @NotNull @Min(1) Long id) {
    return new ResponseEntity<>(drivingDomainService.get(id), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<DrivingDto> add(
      @Valid final @RequestBody DrivingAddCommand addCommand) {
    return new ResponseEntity<>(drivingCommandService.drivingAdd(addCommand), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<DrivingDto> cancel(
      final @PathVariable("id") @NotNull @Min(1) Long id) {
    return new ResponseEntity<>(drivingCommandService.cancel(id), HttpStatus.OK);
  }

  @PostMapping(value = "/acceptance")
  public ResponseEntity<Void> dispatchAcceptance(
      @Valid final @RequestBody DispatchAcceptanceCommand acceptanceCommand) {
    drivingCommandService.dispatchAcceptance(acceptanceCommand);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

