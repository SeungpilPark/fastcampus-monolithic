-- driving Table Create SQL
CREATE TABLE driving
(
    `id`                 BIGINT         NOT NULL AUTO_INCREMENT COMMENT '아이디',
    `call_type`          VARCHAR(45)    NOT NULL COMMENT '호출타입',
    `vehicle_type`       VARCHAR(45)    NOT NULL COMMENT '요청차종',
    `status`             VARCHAR(45)    NOT NULL COMMENT '운행상태',
    `driving_start_date` DATETIME(6)    NULL COMMENT '운행시작시간',
    `driving_end_date`   DATETIME(6)    NULL COMMENT '운행종료시간',
    `fee`                DECIMAL(19, 2) NOT NULL COMMENT '요금',
    `create_date`        DATETIME(6)    NOT NULL COMMENT '등록일시',
    `update_date`        DATETIME(6)    NOT NULL COMMENT '수정일시',
    PRIMARY KEY (id)
);

ALTER TABLE driving
    COMMENT '운행정보';

CREATE
    INDEX ix_driving_create_date ON driving (create_date);

-- driving_route Table Create SQL
CREATE TABLE driving_route
(
    `driving_id`              BIGINT      NOT NULL COMMENT '운행아이디',
    `start_coordinates`       VARCHAR(45) NOT NULL COMMENT '출발위치',
    `boarding_coordinates`    VARCHAR(45) NOT NULL COMMENT '탑승위치',
    `destination_coordinates` VARCHAR(45) NOT NULL COMMENT '도착위치',
    `paths`                   LONGTEXT    NOT NULL COMMENT '경로정보',
    `boarding_index`          INT         NOT NULL COMMENT '탑승위치인덱스',
    `create_date`             DATETIME(6) NOT NULL COMMENT '등록일시',
    `update_date`             DATETIME(6) NOT NULL COMMENT '수정일시',
    PRIMARY KEY (driving_id)
);


ALTER TABLE driving_route
    COMMENT '운행경로정보';
CREATE
    INDEX ix_driving_route_driving_id ON driving_route (driving_id);
