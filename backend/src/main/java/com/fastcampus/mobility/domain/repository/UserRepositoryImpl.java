package com.fastcampus.mobility.domain.repository;


import com.fastcampus.mobility.domain.entity.QUserEntity;
import com.fastcampus.mobility.domain.entity.UserEntity;
import com.fastcampus.mobility.dto.QUserDto;
import com.fastcampus.mobility.dto.UserDto;
import com.fastcampus.mobility.dto.search.UserSearchDto;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.fastcampus.mobility.common.querydsl.QueryDslHelper.optionalWhen;

@Slf4j
public class UserRepositoryImpl extends QuerydslRepositorySupport implements
        UserRepositoryCustom {

  private static QUserEntity user = QUserEntity.userEntity;

  public UserRepositoryImpl() {
    super(UserEntity.class);
  }

  @Override
  public Page<UserDto> findBySearchCondition(UserSearchDto userSearchDto,
      Pageable pageable) {

    assert (getQuerydsl() != null);

    JPQLQuery<UserDto> query = getQuerydsl().createQuery()
        .select(new QUserDto(user))
        .from(user);

    optionalWhen(userSearchDto.getLoginId())
        .then(param -> query.where(user.loginId.eq(param)));

    optionalWhen(userSearchDto.getName())
        .then(param -> query.where(user.name.contains(param)));

    optionalWhen(userSearchDto.getRole())
        .then(param -> query.where(user.role.eq(param)));

    query.orderBy(user.createDate.desc());
    List<UserDto> resultList = getQuerydsl().applyPagination(
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), query).fetch();
    return new PageImpl<>(resultList, pageable, query.fetchCount());
  }
}
