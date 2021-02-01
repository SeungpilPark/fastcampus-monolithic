package com.fastcampus.mobility.service

import com.fastcampus.mobility.IntegrationTestSupport
import com.fastcampus.mobility.dto.command.DispatchAcceptanceCommand
import com.fastcampus.mobility.dto.command.DrivingAddCommand
import com.fastcampus.mobility.dto.command.VehicleAddCommand
import com.fastcampus.mobility.entity.DrivingStatus
import com.fastcampus.mobility.service.spec.DrivingCommandService
import com.fastcampus.mobility.service.spec.DrivingDomainService
import com.fastcampus.mobility.service.spec.VehicleCommandService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
@Slf4j
class DrivingCommandServiceIntegrationTest extends IntegrationTestSupport {

    @Autowired
    DrivingCommandService drivingCommandService

    @Autowired
    DrivingDomainService drivingDomainService

    @Autowired
    VehicleCommandService vehicleCommandService

    def "운행등록시 차량이 배차요청을 받게 되고 차량이 배차수락시 운행상태로 변경된다"() {
        given:
        def vehicleAddCommand = new VehicleAddCommand()
        vehicleAddCommand.setLicense("A1234")
        vehicleAddCommand.setCoordinates("127.1302485,37.3752388")
        def vehicleDto = vehicleCommandService.vehicleAdd(vehicleAddCommand)

        def addCommand = new DrivingAddCommand()
        addCommand.setBoardingCoordinates("127.1296048,37.3807975")
        addCommand.setDestinationCoordinates("127.1166015,37.3815819")
        def drivingDto = drivingCommandService.drivingAdd(addCommand)

        drivingCommandService.dispatchRequest(drivingDto.id, 1)

        def acceptanceCommand = new DispatchAcceptanceCommand()
        acceptanceCommand.setVehicleId(vehicleDto.id)
        acceptanceCommand.setDrivingId(drivingDto.id)
        drivingCommandService.dispatchAcceptance(acceptanceCommand)

        when:
        def updateDrivingDto = drivingDomainService.get(drivingDto.id)

        then:
        updateDrivingDto.status == DrivingStatus.운행중
        updateDrivingDto.dispatchAttempts == 1
        updateDrivingDto.dispatchVehicleCount == 1
        updateDrivingDto.getDrivingRequests().get(0).vehicleId == vehicleDto.id
    }
}
