package com.fastcampus.mobility.service

import com.fastcampus.mobility.IntegrationTestSupport
import com.fastcampus.mobility.dto.command.UserAddCommand
import com.fastcampus.mobility.entity.UserRole
import com.fastcampus.mobility.service.spec.UserDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

@Transactional
class UserDomainServiceIntegrationTest extends IntegrationTestSupport {

    @Autowired
    UserDomainService userDomainService

    @Autowired
    PasswordEncoder passwordEncoder

    def "사용자 등록시 패스워드가 암호화되고 입력받은 비밀번호와 암호화된 비밀번호가 일치함을 알 수 있다."() {
        given:
        def addCommand = new UserAddCommand()
        addCommand.setName("name")
        addCommand.setLoginId("loginId")
        addCommand.setLoginPassword("password")
        addCommand.setRole(UserRole.일반관리)

        when:
        def userDto = userDomainService.insert(addCommand)

        then:
        print 'encodedPassword: ' + userDto.loginPassword + ' :'
        passwordEncoder.matches("password", userDto.loginPassword)
    }
}
