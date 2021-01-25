package com.fastcampus.mobility.service;

import com.fastcampus.mobility.common.exception.EntityNotFoundException;
import com.fastcampus.mobility.dto.AbstractDto;
import com.fastcampus.mobility.dto.UserDto;
import com.fastcampus.mobility.dto.command.UserAddCommand;
import com.fastcampus.mobility.dto.command.UserUpdateCommand;
import com.fastcampus.mobility.dto.search.UserSearchDto;
import com.fastcampus.mobility.entity.UserEntity;
import com.fastcampus.mobility.repository.UserRepository;
import com.fastcampus.mobility.service.spec.UserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@Transactional
public class UserDomainServiceImpl implements UserDomainService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserDomainServiceImpl(
      final UserRepository userRepository,
      final PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDto get(Long userId) {
    UserDto userDto = AbstractDto.fromEntity(UserDto.class,
        userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
    userDto.setLoginPassword("");
    return userDto;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<UserDto> search(UserSearchDto userSearchDto, Pageable pageable) {
    return userRepository.findBySearchCondition(userSearchDto, pageable);
  }

  @Override
  public UserDto insert(UserAddCommand addCommand) {
    return AbstractDto.fromEntity(
        UserDto.class,
        userRepository.save(
            UserEntity.builder()
                .loginId(addCommand.getLoginId())
                .loginPassword(passwordEncoder.encode(addCommand.getLoginPassword()))
                .name(addCommand.getName())
                .role(addCommand.getRole())
                .build()
        ));
  }

  @Override
  public UserDto update(UserUpdateCommand updateCommand) {
    UserEntity userEntity = userRepository.findById(updateCommand.getUserId())
        .orElseThrow(EntityNotFoundException::new);
    return AbstractDto.fromEntity(
        UserDto.class,
        userRepository.save(
            userEntity.toBuilder()
                .loginPassword(
                    StringUtils.isEmpty(updateCommand.getLoginPassword()) ?
                        userEntity.getLoginPassword() :
                        passwordEncoder.encode(updateCommand.getLoginPassword())
                )
                .name(updateCommand.getName())
                .role(updateCommand.getRole())
                .build()
        ));
  }
}
