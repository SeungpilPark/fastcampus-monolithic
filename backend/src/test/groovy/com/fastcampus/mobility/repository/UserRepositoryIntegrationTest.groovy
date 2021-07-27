package com.fastcampus.mobility.repository

import com.fastcampus.mobility.IntegrationTestSupport
import com.fastcampus.mobility.dto.search.UserSearchDto
import com.fastcampus.mobility.entity.UserEntity
import com.fastcampus.mobility.entity.UserRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

@Transactional
class UserRepositoryIntegrationTest extends IntegrationTestSupport {

    @Autowired
    UserRepository userRepository

    def "QueryDsl 조회 테스트"() {
        given:
        userRepository.save(
                UserEntity.builder()
                        .name("name")
                        .role(UserRole.일반관리)
                        .loginId("loginId")
                        .loginPassword("loginPassword")
                        .build()
        )
        when:
        def searchDto = UserSearchDto.builder()
                .name("name")
                .loginId("loginId")
                .role(UserRole.일반관리)
                .build()
        def page = userRepository.findBySearchCondition(searchDto, PageRequest.of(0, 20))

        then:
        page.content.size() == 1
        page.content.get(0).name == "name"
        page.content.get(0).loginId == "loginId"
        page.content.get(0).role == UserRole.일반관리
    }
}
