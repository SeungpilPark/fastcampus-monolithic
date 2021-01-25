package com.fastcampus.mobility.repository;

import com.fastcampus.mobility.dto.UserDto;
import com.fastcampus.mobility.dto.search.UserSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

  Page<UserDto> findBySearchCondition(UserSearchDto userSearchDto,
      Pageable pageable);
}
