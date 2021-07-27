package com.fastcampus.mobility.domain.repository;

import com.fastcampus.mobility.domain.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>,
        UserRepositoryCustom {

  Optional<UserEntity> findByLoginId(final String loginId);
}
