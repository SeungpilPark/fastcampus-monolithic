-- user Table Create SQL
CREATE TABLE user
(
    `id`              BIGINT          NOT NULL    AUTO_INCREMENT COMMENT '아이디',
    `role`            VARCHAR(45)     NOT NULL    COMMENT '역할',
    `login_id`        VARCHAR(45)     NOT NULL    COMMENT '로그인아이디',
    `login_password`  VARCHAR(255)    NOT NULL    COMMENT '패스워드',
    `name`            VARCHAR(45)     NOT NULL    COMMENT '이름',
    `create_date`     DATETIME(6)     NOT NULL    COMMENT '등록일시',
    `update_date`     DATETIME(6)     NOT NULL    COMMENT '수정일시',
    PRIMARY KEY (id)
);

ALTER TABLE user COMMENT '관리자';

ALTER TABLE user
    ADD CONSTRAINT UC_login_id UNIQUE (login_id);

INSERT INTO user (id, role, login_id, login_password, name, create_date, update_date)
VALUES (1, '시스템관리', 'admin', '$2a$10$bwMeSScMt2SclvRC4.Jqg.nB5I7hYh6oS7vxCDhBwqATwJDup5hD6', 'ADMIN', now(), now());

INSERT INTO user (id, role, login_id, login_password, name, create_date, update_date)
VALUES (2, '일반관리', 'member', '$2a$10$bwMeSScMt2SclvRC4.Jqg.nB5I7hYh6oS7vxCDhBwqATwJDup5hD6', 'MEMBER', now(), now());
