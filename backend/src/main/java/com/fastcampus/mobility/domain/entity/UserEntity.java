package com.fastcampus.mobility.domain.entity;

import com.fastcampus.mobility.domain.enums.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
}

