package com.fastcampus.mobility.domain.service;

import com.fastcampus.mobility.domain.repository.DispatchRequestRepository;
import com.fastcampus.mobility.domain.repository.DrivingRepository;
import com.fastcampus.mobility.domain.repository.DrivingRouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class DrivingService {

    private final DrivingRepository drivingRepository;
    private final DrivingRouteRepository drivingRouteRepository;
    private final DispatchRequestRepository dispatchRequestRepository;

    @Autowired
    public DrivingService(
            final DrivingRepository drivingRepository,
            final DrivingRouteRepository drivingRouteRepository,
            final DispatchRequestRepository dispatchRequestRepository
    ) {
        this.drivingRepository = drivingRepository;
        this.drivingRouteRepository = drivingRouteRepository;
        this.dispatchRequestRepository = dispatchRequestRepository;
    }

//    public DrivingDto drivingAdd(DrivingAddCommand addCommand) {
//        DrivingDto drivingDto = drivingDomainService.insert(addCommand);
//        try {
//            dispatchScheduleService.addSchedule(drivingDto.getId(), 1);
//        } catch (SchedulerException e) {
//            throw new RuntimeException(e);
//        }
//        return drivingDto;
//    }
//
//    // 인서트: 드라이빙 -> 인서트
//    // 업데이트: 드라이빙 -> 업데이트
//    // 겟 : 엔티티 조회 -> 팩토리 생성
//
//    @Transactional(readOnly = true)
//    public DrivingDto get(Long drivingId) {
//        DrivingDto drivingDto = AbstractDto
//                .fromEntity(DrivingDto.class, drivingRepository.findById(drivingId)
//                        .orElseThrow(EntityNotFoundException::new));
//
//        drivingDto.setDrivingRoute(
//                AbstractDto.fromEntity(DrivingRouteDto.class, drivingRouteRepository.findById(drivingId)
//                        .orElseThrow(EntityNotFoundException::new))
//        );
//
//        drivingDto.setDrivingRequests(
//                AbstractDto.fromListEntities(DispatchRequestDto.class,
//                        dispatchRequestRepository.findAllByDrivingId(drivingId))
//        );
//        return drivingDto;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Page<DrivingDto> search(DrivingSearchDto drivingSearchDto, Pageable pageable) {
//        return drivingRepository.findBySearchCondition(drivingSearchDto, pageable);
//    }
//
//    @Override
//    public DrivingDto insert(final DrivingAddCommand addCommand) {
//        DrivingEntity drivingEntity = drivingRepository.save(
//                DrivingEntity.builder()
//                        .status(DrivingStatus.배차중)
//                        .vehicleId(0L)
//                        .boardingYn(false)
//                        .dispatchAttempts(0)
//                        .dispatchVehicleCount(0)
//                        .build()
//        );
//        drivingRouteRepository.save(
//                DrivingRouteEntity.builder()
//                        .drivingId(drivingEntity.getId())
//                        .startCoordinates("")
//                        .boardingCoordinates(addCommand.getBoardingCoordinates())
//                        .destinationCoordinates(addCommand.getDestinationCoordinates())
//                        .paths("")
//                        .boardingIndex(0)
//                        .build()
//        );
//        return this.get(drivingEntity.getId());
//    }
//
//    @Override
//    public void updateSystemFail(final Long drivingId) {
//        DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
//                .orElseThrow(EntityNotFoundException::new);
//        drivingEntity.setStatus(DrivingStatus.운행실패);
//        drivingRepository.save(drivingEntity);
//    }
//
//    @Override
//    public void updateDispatchFail(final Long drivingId) {
//        DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
//                .orElseThrow(EntityNotFoundException::new);
//        drivingEntity.setStatus(DrivingStatus.배차실패);
//        drivingRepository.save(drivingEntity);
//    }
//
//    @Override
//    public void updateDispatchRequest(final Long drivingId, final Integer attempts,
//                                      final List<VehicleDto> candidateVehicles) {
//        DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
//                .orElseThrow(EntityNotFoundException::new);
//        drivingEntity.setDispatchAttempts(attempts);
//        drivingEntity.setDispatchVehicleCount(
//                drivingEntity.getDispatchVehicleCount() + candidateVehicles.size()
//        );
//        drivingRepository.save(drivingEntity);
//
//        candidateVehicles.forEach(vehicle -> dispatchRequestRepository.save(
//                AllocateRequestEntity.builder()
//                        .drivingId(drivingId)
//                        .vehicleId(vehicle.getId())
//                        .dispatchAttempts(attempts)
//                        .coordinates(vehicle.getVehicleCoordinates().getCoordinates())
//                        .build()
//        ));
//    }
//
//    @Override
//    public void updateDriving(final Long drivingId, final Long vehicleId,
//                              final RouteResponse routeResponse) {
//        DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
//                .orElseThrow(EntityNotFoundException::new);
//        drivingEntity.setStatus(DrivingStatus.운행중);
//        drivingEntity.setBoardingYn(false);
//        drivingEntity.setVehicleId(vehicleId);
//        drivingEntity.setDrivingStartDate(LocalDateTime.now());
//        drivingRepository.save(drivingEntity);
//
//        DrivingRouteEntity drivingRouteEntity = drivingRouteRepository.findById(drivingId)
//                .orElseThrow(EntityNotFoundException::new);
//        drivingRouteEntity.setStartCoordinates(routeResponse.getStartCoordinates());
//        drivingRouteEntity.setBoardingCoordinates(routeResponse.getBoardingCoordinates());
//        drivingRouteEntity.setDestinationCoordinates(routeResponse.getDestinationCoordinates());
//        drivingRouteEntity.setPaths(routeResponse.getPaths());
//        drivingRouteEntity.setBoardingIndex(routeResponse.getBoardingIndex());
//        drivingRouteRepository.save(drivingRouteEntity);
//    }
//
//    @Override
//    public void updateBoarding(Long drivingId) {
//        DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
//                .orElseThrow(EntityNotFoundException::new);
//        drivingEntity.setStatus(DrivingStatus.운행중);
//        drivingEntity.setBoardingYn(true);
//        drivingRepository.save(drivingEntity);
//    }
//
//    @Override
//    public void updateDrivingEnd(Long drivingId) {
//        DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
//                .orElseThrow(EntityNotFoundException::new);
//        drivingEntity.setStatus(DrivingStatus.운행종료);
//        drivingEntity.setDrivingEndDate(LocalDateTime.now());
//        drivingRepository.save(drivingEntity);
//    }
//
//    @Override
//    public void updateDrivingCancel(Long drivingId) {
//        DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
//                .orElseThrow(EntityNotFoundException::new);
//        drivingEntity.setStatus(DrivingStatus.운행중지);
//        drivingRepository.save(drivingEntity);
//    }
}
