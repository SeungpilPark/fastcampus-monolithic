package com.fastcampus.mobility.map

import com.fastcampus.mobility.IntegrationTestSupport
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
@Slf4j
class NaverClientIntegrationTest extends IntegrationTestSupport {

    @Autowired
    NaverClient naverClient

    def "네이버 경로찾기 API 호출 테스트"() {
        given:
        def start = "127.1302485,37.3752388"
        def wayPoint = "127.1296048,37.3807975"
        def destination = "127.1166015,37.3815819"

        when:
        def response = naverClient.getRoute(start, wayPoint, destination)

        then:
        log.info(response)
        1 == 1
    }
}
