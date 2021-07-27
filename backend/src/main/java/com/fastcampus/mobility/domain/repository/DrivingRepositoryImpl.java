package com.fastcampus.mobility.domain.repository;


import com.fastcampus.mobility.domain.entity.DrivingEntity;
import com.fastcampus.mobility.domain.entity.QDrivingEntity;
import com.fastcampus.mobility.domain.entity.QVehicleEntity;
import com.fastcampus.mobility.dto.DrivingDto;
import com.fastcampus.mobility.dto.QDrivingDto;
import com.fastcampus.mobility.dto.search.DrivingSearchDto;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.fastcampus.mobility.common.querydsl.QueryDslHelper.optionalWhen;

@Slf4j
public class DrivingRepositoryImpl extends QuerydslRepositorySupport implements
        DrivingRepositoryCustom {

  private static QVehicleEntity vehicle = QVehicleEntity.vehicleEntity;
  private static QDrivingEntity driving = QDrivingEntity.drivingEntity;

  public DrivingRepositoryImpl() {
    super(DrivingEntity.class);
  }

  @Override
  public Page<DrivingDto> findBySearchCondition(DrivingSearchDto drivingSearchDto,
      Pageable pageable) {

    assert (getQuerydsl() != null);

    JPQLQuery<DrivingDto> query = getQuerydsl().createQuery()
        .select(new QDrivingDto(driving, vehicle))
        .from(driving)
        .leftJoin(vehicle)
        .on(vehicle.id.eq(driving.vehicleId));

    optionalWhen(drivingSearchDto.getId())
        .then(param -> query.where(driving.id.eq(param)));

    optionalWhen(drivingSearchDto.getStatus())
        .then(param -> query.where(driving.status.eq(param)));

    optionalWhen(drivingSearchDto.getLicense())
        .then(param -> query.where(vehicle.license.contains(param)));

    query.orderBy(driving.createDate.desc());
    List<DrivingDto> resultList = getQuerydsl().applyPagination(
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), query).fetch();
    return new PageImpl<>(resultList, pageable, query.fetchCount());
  }
}
