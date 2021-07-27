package com.fastcampus.mobility.service

import com.fastcampus.mobility.IntegrationTestSupport
import com.fastcampus.mobility.dto.command.VehicleAddCommand
import com.fastcampus.mobility.dto.command.VehicleUpdateCommand
import com.fastcampus.mobility.repository.VehicleCoordinatesRepository
import com.fastcampus.mobility.repository.VehicleRepository
import com.fastcampus.mobility.service.spec.VehicleDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class VehicleDomainServiceIntegrationTest extends IntegrationTestSupport {

    @Autowired
    VehicleDomainService vehicleDomainService

    @Autowired
    VehicleRepository vehicleRepository

    @Autowired
    VehicleCoordinatesRepository vehicleCoordinatesRepository

    def "차량 도메인이 등록 되면 차량과 좌표정보가 생성된다."() {
        given:
        def addCommand = new VehicleAddCommand()
        addCommand.setLicense("A1234")
        addCommand.setCoordinates("127.1302485,37.3752388")

        when:
        def vehicleDto = vehicleDomainService.insert(addCommand)

        then:
        vehicleRepository.findById(vehicleDto.id).get().license == "A1234"
        vehicleCoordinatesRepository.findById(vehicleDto.id).get().coordinates == "127.1302485,37.3752388"
    }

    def "차량 도메인이 갱신 되면 차량과 좌표정보가 갱신된다."() {
        given:
        def addCommand = new VehicleAddCommand()
        addCommand.setLicense("A1234")
        addCommand.setCoordinates("127.1302485,37.3752388")
        def vehicleDto = vehicleDomainService.insert(addCommand)

        def updateCommand = new VehicleUpdateCommand()
        updateCommand.setVehicleId(vehicleDto.id)
        updateCommand.setLicense("B1234")
        updateCommand.setCoordinates("127.10000,37.10000")

        when:
        def updated = vehicleDomainService.update(updateCommand)

        then:
        vehicleRepository.findById(updated.id).get().license == "B1234"
        vehicleCoordinatesRepository.findById(updated.id).get().coordinates == "127.10000,37.10000"
    }
}
