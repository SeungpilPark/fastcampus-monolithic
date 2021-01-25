package com.fastcampus.mobility.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security")
@Validated
public class SecurityController {

  private final CustomUserDetailsHelper userDetailsHelper;

  @Autowired
  public SecurityController(
      final CustomUserDetailsHelper userDetailsHelper) {
    this.userDetailsHelper = userDetailsHelper;
  }

  @GetMapping("/session")
  public ResponseEntity<SessionDto> getSession() {
    return new ResponseEntity<>(userDetailsHelper.getLoginSession(), HttpStatus.OK);
  }
}

