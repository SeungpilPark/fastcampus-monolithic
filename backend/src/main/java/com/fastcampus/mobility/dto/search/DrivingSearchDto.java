package com.fastcampus.mobility.dto.search;

import com.fastcampus.mobility.entity.DrivingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DrivingSearchDto {

  private Long id;
  private DrivingStatus status;
  private String license;

  @Builder
  public DrivingSearchDto(Long id, DrivingStatus status, String license) {
    this.id = id;
    this.status = status;
    this.license = license;
  }
}
