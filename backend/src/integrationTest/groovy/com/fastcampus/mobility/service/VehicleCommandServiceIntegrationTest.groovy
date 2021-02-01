package com.fastcampus.mobility.service

import com.fastcampus.mobility.IntegrationTestSupport
import com.fastcampus.mobility.dto.command.VehicleAddCommand
import com.fastcampus.mobility.dto.command.VehicleUpdateCommand
import com.fastcampus.mobility.service.spec.VehicleCommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class VehicleCommandServiceIntegrationTest extends IntegrationTestSupport {

    @Autowired
    VehicleCommandService vehicleCommandService

    def "차량이 등록될 때 주어진 좌표가 없다면 논현역 인근 전철역 중 하나로 지정된다. "() {
        given:
        def addCommand = new VehicleAddCommand()
        addCommand.setLicense("A1234")

        when:
        def vehicleDto = vehicleCommandService.vehicleAdd(addCommand)

        then:
        vehicleDto.vehicleCoordinates.coordinates != null
        println(vehicleDto.vehicleCoordinates.coordinates)
    }

    def "차량이 업데이트 될 때 주어진 좌표가 없다면 좌표는 변경되지 않는다"() {
        given:
        def addCommand = new VehicleAddCommand()
        addCommand.setLicense("A1234")
        def vehicleDto = vehicleCommandService.vehicleAdd(addCommand)

        def updateCommand = new VehicleUpdateCommand()
        updateCommand.setVehicleId(vehicleDto.id)
        updateCommand.setLicense("B1234")

        when:
        def updated = vehicleCommandService.vehicleUpdate(updateCommand)

        then:
        updated.vehicleCoordinates.coordinates == vehicleDto.vehicleCoordinates.coordinates
        updated.license == "B1234"
    }
}
