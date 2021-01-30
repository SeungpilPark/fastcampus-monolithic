package com.fastcampus.mobility.service

import com.fastcampus.mobility.IntegrationTestSupport
import com.fastcampus.mobility.dto.command.DrivingAddCommand
import com.fastcampus.mobility.dto.command.VehicleAddCommand
import com.fastcampus.mobility.entity.DrivingStatus
import com.fastcampus.mobility.service.spec.DrivingDomainService
import com.fastcampus.mobility.service.spec.VehicleDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class DrivingDomainServiceIntegrationTest extends IntegrationTestSupport {

    @Autowired
    DrivingDomainService drivingDomainService

    @Autowired
    VehicleDomainService vehicleDomainService

    def "운행이 등록되고 차량 한대 배차요청을 1회 진행할시 운행의 배차요청은 하나이고 배차진행 상태가 된다"() {
        given:
        def vehicleAddCommand = new VehicleAddCommand()
        vehicleAddCommand.setLicense("A1234")
        vehicleAddCommand.setCoordinates("127.1302485,37.3752388")
        def vehicleDto = vehicleDomainService.insert(vehicleAddCommand)


        def addCommand = new DrivingAddCommand()
        addCommand.setBoardingCoordinates("127.1296048,37.3807975")
        addCommand.setDestinationCoordinates("127.1166015,37.3815819")
        def drivingDto = drivingDomainService.insert(addCommand)

        drivingDomainService.updateDispatchRequest(drivingDto.id, 1, List.of(vehicleDto))

        when:
        def updateDrivingDto = drivingDomainService.get(drivingDto.id)

        then:
        updateDrivingDto.status == DrivingStatus.배차중
        updateDrivingDto.dispatchAttempts == 1
        updateDrivingDto.dispatchVehicleCount == 1
        updateDrivingDto.getDrivingRequests().get(0).vehicleId == vehicleDto.id
    }
}
