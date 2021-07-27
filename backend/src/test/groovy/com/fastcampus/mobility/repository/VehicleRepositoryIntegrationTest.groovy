package com.fastcampus.mobility.repository

import com.fastcampus.mobility.IntegrationTestSupport
import com.fastcampus.mobility.dto.command.VehicleAddCommand
import com.fastcampus.mobility.dto.search.BooleanCondition
import com.fastcampus.mobility.dto.search.VehicleSearchDto
import com.fastcampus.mobility.repository.VehicleRepository
import com.fastcampus.mobility.service.spec.VehicleDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

@Transactional
class VehicleRepositoryIntegrationTest extends IntegrationTestSupport {

    @Autowired
    VehicleRepository vehicleRepository

    @Autowired
    VehicleDomainService vehicleDomainService

    def "QueryDsl 조회 테스트"() {
        given:
        def addCommand1 = new VehicleAddCommand()
        addCommand1.setLicense("A1234")
        addCommand1.setCoordinates("127.1302485,37.3752388")
        vehicleDomainService.insert(addCommand1)

        def addCommand2 = new VehicleAddCommand()
        addCommand2.setLicense("B1234")
        addCommand2.setCoordinates("127.1302485,37.3752388")
        vehicleDomainService.insert(addCommand2)

        when:
        def searchDto = VehicleSearchDto.builder()
                .license("1234")
                .drivingYn(BooleanCondition.N)
                .build()
        def page = vehicleRepository.findBySearchCondition(searchDto, PageRequest.of(0, 20))

        then:
        page.content.size() == 2
        page.content.get(0).license == "B1234"
        page.content.get(0).vehicleCoordinates != null

        page.content.get(1).license == "A1234"
        page.content.get(1).vehicleCoordinates != null
    }
}
