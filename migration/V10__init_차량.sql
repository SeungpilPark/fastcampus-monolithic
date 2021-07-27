-- vehicle Table Create SQL
CREATE TABLE vehicle
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT COMMENT '차량아이디',
    `vehicle_type` VARCHAR(45) NOT NULL COMMENT '차종',
    `license`      VARCHAR(45) NOT NULL COMMENT '라이선스',
    `grade`        VARCHAR(45) NOT NULL COMMENT '평점',
    `driving_yn`   BIT         NOT NULL DEFAULT false COMMENT '운행여부',
    `create_date`  DATETIME(6) NOT NULL COMMENT '등록일시',
    `update_date`  DATETIME(6) NOT NULL COMMENT '수정일시',
    PRIMARY KEY (id)
);

ALTER TABLE vehicle
    COMMENT '차량정보';

ALTER TABLE vehicle
    ADD CONSTRAINT UC_license UNIQUE (license);
CREATE INDEX ix_vehicle_create_date ON vehicle (create_date);


CREATE TABLE vehicle_coordinates
(
    `vehicle_id`  BIGINT       NOT NULL COMMENT '차량아아디',
    `coordinates` VARCHAR(200) NOT NULL DEFAULT '127.1302485,37.3752388' COMMENT '차량위치',
    `create_date` DATETIME(6)  NOT NULL COMMENT '등록일시',
    `update_date` DATETIME(6)  NOT NULL COMMENT '수정일시',
    PRIMARY KEY (vehicle_id)
);

ALTER TABLE vehicle_coordinates
    COMMENT '차량위치정보';
