package com.fastcampus.mobility.service.spec;


import com.fastcampus.mobility.dto.UserDto;
import com.fastcampus.mobility.dto.command.UserAddCommand;
import com.fastcampus.mobility.dto.command.UserUpdateCommand;
import com.fastcampus.mobility.dto.search.UserSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDomainService {

  UserDto get(final Long userId);

  Page<UserDto> search(UserSearchDto userSearchDto, Pageable pageable);

  UserDto insert(final UserAddCommand addCommand);

  UserDto update(final UserUpdateCommand updateCommand);
}
