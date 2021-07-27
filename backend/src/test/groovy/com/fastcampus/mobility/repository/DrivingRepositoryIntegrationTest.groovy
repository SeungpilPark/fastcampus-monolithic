package com.fastcampus.mobility.repository

import com.fastcampus.mobility.IntegrationTestSupport
import com.fastcampus.mobility.dto.command.DrivingAddCommand
import com.fastcampus.mobility.dto.command.VehicleAddCommand
import com.fastcampus.mobility.dto.search.DrivingSearchDto
import com.fastcampus.mobility.entity.DrivingStatus
import com.fastcampus.mobility.map.RouteResponse
import com.fastcampus.mobility.service.spec.DrivingDomainService
import com.fastcampus.mobility.service.spec.VehicleDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

@Transactional
class DrivingRepositoryIntegrationTest extends IntegrationTestSupport {

    @Autowired
    DrivingRepository drivingRepository

    @Autowired
    DrivingDomainService drivingDomainService

    @Autowired
    VehicleDomainService vehicleDomainService

    def "QueryDsl 조회 테스트"() {
        given:
        def vehicleAddCommand = new VehicleAddCommand()
        vehicleAddCommand.setLicense("A1234")
        vehicleAddCommand.setCoordinates("127.1302485,37.3752388")
        def vehicleDto = vehicleDomainService.insert(vehicleAddCommand)


        def addCommand = new DrivingAddCommand()
        addCommand.setBoardingCoordinates("127.1296048,37.3807975")
        addCommand.setDestinationCoordinates("127.1166015,37.3815819")
        def drivingDto = drivingDomainService.insert(addCommand)

        def routeResponse = new RouteResponse()
        routeResponse.startCoordinates = ""
        routeResponse.boardingCoordinates = ""
        routeResponse.destinationCoordinates = ""
        routeResponse.paths = ""
        routeResponse.boardingIndex = 0
        drivingDomainService.updateDriving(drivingDto.id, vehicleDto.id, routeResponse)

        when:
        def searchDto = DrivingSearchDto.builder()
                .status(DrivingStatus.운행중)
                .license("A1234")
                .build()
        def page = drivingRepository.findBySearchCondition(searchDto, PageRequest.of(0, 20))

        then:
        page.content.size() == 1
        page.content.get(0).status == DrivingStatus.운행중
        page.content.get(0).vehicle.license == "A1234"
    }
}
