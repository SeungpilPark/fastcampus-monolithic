package com.fastcampus.mobility.security;

import com.fastcampus.mobility.entity.UserEntity;
import com.fastcampus.mobility.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public CustomUserDetailsService(
      final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> userEntity = userRepository.findByLoginId(username);
    userEntity.ifPresent(entity -> log.info("Login username: {}, id: {}",
        entity.getLoginId(), entity.getId()));

    return userEntity
        .map(entity -> {
          List<GrantedAuthority> authorities = AuthorityUtils
              .createAuthorityList(entity.getRole().name());
          return new CustomUserDetail(
              entity.getLoginId(),
              entity.getLoginPassword(),
              authorities,
              new SessionDto(entity)
          );
        })
        .orElseThrow(() -> new UsernameNotFoundException(
            String.format("로그인 아이디 %s 를 찾을 수 없습니다.", username)
        ));
  }
}
