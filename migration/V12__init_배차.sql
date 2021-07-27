-- allocate Table Create SQL
CREATE TABLE allocate
(
    `id`                BIGINT      NOT NULL AUTO_INCREMENT COMMENT '아이디',
    `driving_id`        BIGINT      NOT NULL DEFAULT 0 COMMENT '운행아이디',
    `status`            VARCHAR(45) NOT NULL COMMENT '배차상태',
    `allocate_attempts` TINYINT     NOT NULL DEFAULT 0 COMMENT '배차요청회차',
    `vehicle_id`        BIGINT      NOT NULL DEFAULT 0 COMMENT '배차차량아아디',
    `create_date`       DATETIME(6) NOT NULL COMMENT '등록일시',
    `update_date`       DATETIME(6) NOT NULL COMMENT '수정일시',
    PRIMARY KEY (id)
);

ALTER TABLE allocate
    COMMENT '배차정보';

CREATE INDEX ix_allocate_driving_id ON allocate (driving_id);
CREATE INDEX ix_allocate_vehicle_id ON allocate (vehicle_id);
CREATE INDEX ix_allocate_create_date ON allocate (create_date);

-- allocate_request Table Create SQL
CREATE TABLE allocate_request
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT '아이디',
    `driving_id`        BIGINT       NOT NULL COMMENT '운행아이디',
    `vehicle_id`        BIGINT       NOT NULL COMMENT '차량아이디',
    `allocate_attempts` TINYINT      NOT NULL COMMENT '배차요청회차',
    `coordinates`       VARCHAR(200) NOT NULL COMMENT '차량위치',
    `create_date`       DATETIME(6)  NOT NULL COMMENT '등록일시',
    `update_date`       DATETIME(6)  NOT NULL COMMENT '수정일시',
    PRIMARY KEY (id)
);

ALTER TABLE allocate_request
    COMMENT '배차요청정보';

ALTER TABLE allocate_request
    ADD CONSTRAINT UC_driving_id_vehicle_id UNIQUE (driving_id, vehicle_id);

CREATE INDEX ix_dispatch_requests_driving_id ON allocate_request (driving_id);
