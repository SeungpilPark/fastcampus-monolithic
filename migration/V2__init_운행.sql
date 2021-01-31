-- driving Table Create SQL
CREATE TABLE driving
(
    `id`                     BIGINT      NOT NULL AUTO_INCREMENT COMMENT '아이디',
    `status`                 VARCHAR(45) NOT NULL COMMENT '운행상태',
    `vehicle_id`             BIGINT      NOT NULL DEFAULT 0 COMMENT '차량아이디',
    `boarding_yn`            BIT         NOT NULL DEFAULT false COMMENT '승객탑승여부',
    `dispatch_attempts`      TINYINT     NOT NULL DEFAULT 0 COMMENT '배차요청회차',
    `dispatch_vehicle_count` TINYINT     NOT NULL COMMENT '배차요청수',
    `driving_start_date`     DATETIME(6) NULL COMMENT '운행시작시간',
    `driving_end_date`       DATETIME(6) NULL COMMENT '운행종료시간',
    `create_date`            DATETIME(6) NOT NULL COMMENT '등록일시',
    `update_date`            DATETIME(6) NOT NULL COMMENT '수정일시',
    PRIMARY KEY (id)
);

ALTER TABLE driving
    COMMENT '운행정보';

CREATE INDEX ix_driving_vehicle_id ON driving (vehicle_id);
CREATE INDEX ix_driving_create_date ON driving (create_date);

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

-- dispatch_request Table Create SQL
CREATE TABLE dispatch_request
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT '아이디',
    `driving_id`        BIGINT       NOT NULL COMMENT '운행아이디',
    `vehicle_id`        BIGINT       NOT NULL COMMENT '차량아이디',
    `dispatch_attempts` TINYINT      NOT NULL COMMENT '배차요청회차',
    `coordinates`       VARCHAR(200) NOT NULL COMMENT '차량위치',
    `create_date`       DATETIME(6)  NOT NULL COMMENT '등록일시',
    `update_date`       DATETIME(6)  NOT NULL COMMENT '수정일시',
    PRIMARY KEY (id)
);

ALTER TABLE dispatch_request
    COMMENT '배차요청정보';

ALTER TABLE dispatch_request
    ADD CONSTRAINT UC_driving_id_vehicle_id UNIQUE (driving_id, vehicle_id);

CREATE INDEX ix_dispatch_requests_driving_id ON dispatch_request (driving_id);
