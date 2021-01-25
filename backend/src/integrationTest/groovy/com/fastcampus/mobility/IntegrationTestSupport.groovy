package com.fastcampus.mobility


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("local")
@AutoConfigureMockMvc
@ComponentScan(basePackages = ["com.fastcampus.mobility"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
abstract class IntegrationTestSupport extends Specification {
}
