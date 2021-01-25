package com.fastcampus.mobility.repository;

import static com.fastcampus.mobility.common.querydsl.QueryDslHelper.optionalWhen;

import com.fastcampus.mobility.dto.QVehicleDto;
import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.search.VehicleSearchDto;
import com.fastcampus.mobility.entity.QVehicleCoordinatesEntity;
import com.fastcampus.mobility.entity.QVehicleEntity;
import com.fastcampus.mobility.entity.VehicleEntity;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Slf4j
public class VehicleRepositoryImpl extends QuerydslRepositorySupport implements
    VehicleRepositoryCustom {

  private static QVehicleEntity vehicle = QVehicleEntity.vehicleEntity;
  private static QVehicleCoordinatesEntity vehicleCoordinates = QVehicleCoordinatesEntity.vehicleCoordinatesEntity;

  public VehicleRepositoryImpl() {
    super(VehicleEntity.class);
  }

  @Override
  public Page<VehicleDto> findBySearchCondition(VehicleSearchDto vehicleSearchDto,
      Pageable pageable) {
    JPQLQuery<VehicleDto> query = this.createQuery(vehicleSearchDto);
    query.orderBy(vehicle.createDate.desc());

    assert getQuerydsl() != null;
    List<VehicleDto> resultList = getQuerydsl().applyPagination(
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), query).fetch();
    return new PageImpl<>(resultList, pageable, query.fetchCount());
  }

  @Override
  public List<VehicleDto> findByBulkSearchCondition(VehicleSearchDto vehicleSearchDto) {
    JPQLQuery<VehicleDto> query = this.createQuery(vehicleSearchDto);
    query.orderBy(vehicle.createDate.desc());

    assert getQuerydsl() != null;
    return query.fetch();
  }

  private JPQLQuery<VehicleDto> createQuery(VehicleSearchDto vehicleSearchDto) {
    assert (getQuerydsl() != null);

    JPQLQuery<VehicleDto> query = getQuerydsl().createQuery()
        .select(new QVehicleDto(vehicle, vehicleCoordinates))
        .from(vehicle)
        .join(vehicleCoordinates)
        .on(vehicle.id.eq(vehicleCoordinates.vehicleId));

    optionalWhen(vehicleSearchDto.getLicense())
        .then(param -> query.where(vehicle.license.contains(param)));

    optionalWhen(vehicleSearchDto.getDrivingYn())
        .then(
            param -> {
              switch (param) {
                case Y:
                  query.where(vehicle.drivingYn.isTrue());
                  break;
                case N:
                  query.where(vehicle.drivingYn.isFalse());
                  break;
              }
            }
        );
    return query;
  }
}
