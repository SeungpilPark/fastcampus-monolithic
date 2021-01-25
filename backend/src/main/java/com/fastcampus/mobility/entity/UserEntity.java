package com.fastcampus.mobility.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserEntity extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Column(name = "login_id", nullable = false)
  private String loginId;

  @Column(name = "login_password", nullable = false)
  private String loginPassword;

  @Column(name = "name", nullable = false)
  private String name;

  @Builder(toBuilder = true)
  public UserEntity(long id, UserRole role, String loginId, String loginPassword,
      String name, LocalDateTime createDate, LocalDateTime updateDate) {
    this.id = id;
    this.role = role;
    this.loginId = loginId;
    this.loginPassword = loginPassword;
    this.name = name;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }
}

