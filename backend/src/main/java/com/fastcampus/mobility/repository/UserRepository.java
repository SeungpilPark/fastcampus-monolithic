package com.fastcampus.mobility.repository;

import com.fastcampus.mobility.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>,
    UserRepositoryCustom {

  Optional<UserEntity> findByLoginId(final String loginId);
}
