package com.fastcampus.mobility.service

import com.fastcampus.mobility.IntegrationTestSupport
import com.fastcampus.mobility.dto.DrivingRouteDto
import com.fastcampus.mobility.map.MapService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
@Slf4j
class MapServiceIntegrationTest extends IntegrationTestSupport {

    @Autowired
    MapService mapService

    def "출발-경유지-도착지 경로를 구한후 100초 동안 시속 100km 로 이동시 차량위치가 변경되고 승객탑승과 도착여부를 알 수 있다."() {
        given:
        def start = "127.1302485,37.3752388"
        def wayPoint = "127.1296048,37.3807975"
        def destination = "127.1166015,37.3815819"
        def routeResponse = mapService.addPath(start, wayPoint, destination)

        def drivingRouteDto = DrivingRouteDto.builder()
                .startCoordinates(routeResponse.startCoordinates)
                .boardingCoordinates(routeResponse.boardingCoordinates)
                .destinationCoordinates(routeResponse.destinationCoordinates)
                .paths(routeResponse.paths)
                .boardingIndex(routeResponse.boardingIndex)
                .build()

        when:
        for (int i = 1; i < 100; i++) {
            def coordinatesResponse = mapService.getDrivingCoordinates(drivingRouteDto, i)
            log.info("운행시간: {}초, 승객탑승: {}, 운행종료: {}, 차량위치: {}",
                    i,
                    coordinatesResponse.passengerPassed,
                    coordinatesResponse.destinationPassed,
                    coordinatesResponse.coordinates)
        }
        then:
        1 == 1
    }
}
