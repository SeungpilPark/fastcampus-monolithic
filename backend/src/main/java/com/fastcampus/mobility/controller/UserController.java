package com.fastcampus.mobility.controller;

import com.fastcampus.mobility.dto.UserDto;
import com.fastcampus.mobility.dto.command.UserAddCommand;
import com.fastcampus.mobility.dto.command.UserUpdateCommand;
import com.fastcampus.mobility.dto.search.UserSearchDto;
import com.fastcampus.mobility.service.spec.UserDomainService;
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
@RequestMapping("/api/user")
@Validated
public class UserController {

  private final UserDomainService userDomainService;

  @Autowired
  public UserController(
      final UserDomainService userDomainService
  ) {
    this.userDomainService = userDomainService;
  }

  @GetMapping(value = "")
  public ResponseEntity<Page<UserDto>> search(
      final UserSearchDto userSearchDto,
      final @PageableDefault(size = 50) Pageable pageable) {
    return new ResponseEntity<>(
        userDomainService.search(userSearchDto, pageable), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDto> get(
      final @PathVariable("id") @NotNull @Min(1) Long id) {
    return new ResponseEntity<>(userDomainService.get(id), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<UserDto> add(
      @Valid final @RequestBody UserAddCommand addCommand) {
    return new ResponseEntity<>(userDomainService.insert(addCommand), HttpStatus.OK);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<UserDto> update(
      final @PathVariable("id") @NotNull @Min(1) Long id,
      @Valid final @RequestBody UserUpdateCommand updateCommand) {
    updateCommand.setUserId(id);
    return new ResponseEntity<>(userDomainService.update(updateCommand), HttpStatus.OK);
  }
}

