-- 계정 생성
CREATE USER 'triple'@'localhost' identified BY '1q2w3e4r!';
-- 권한 설정
GRANT ALL PRIVILEGES ON *.* TO 'triple'@'localhost';
flush PRIVILEGES;
-- 비밀번호 형식 변경
ALTER user 'triple'@'localhost' IDENTIFIED WITH mysql_native_password by '1q2w3e4r!';
-- 데이터베이스 생성
CREATE DATABASE triple;


-- 첨부파일 테이블 생성
CREATE TABLE `attachment` (
    `seq` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `review_id` bigint DEFAULT NULL COMMENT '리뷰 ID',
    `file_uuid` varchar(255) DEFAULT NULL COMMENT '파일 UUID',
    INDEX `idx_1_attachment_1` (`seq`),
    INDEX `idx_2_attachment_2` (`seq`,`review_id`)
)  ENGINE=INNODB;

-- 리뷰 테이블 생성
CREATE TABLE `review` (
    `seq` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `review_uuid` varchar(255) DEFAULT NULL COMMENT '리뷰 UUID',
    `user_uuid` varchar(255) DEFAULT NULL COMMENT '유저 UUID',
    `place_uuid` varchar(255) DEFAULT NULL COMMENT '장소 UUID',
    `status` varchar(255) DEFAULT NULL COMMENT '리뷰 상태값',
    `content` varchar(255) DEFAULT NULL COMMENT '내용',
    `reg_date` datetime(6) DEFAULT NULL COMMENT '등록날짜',
    `mod_date` datetime(6) DEFAULT NULL COMMENT '수정날짜',
    INDEX `idx_1_review` (`seq`),
    INDEX `idx_2_review` (`seq`,`review_uuid`),
    INDEX `idx_3_review` (`seq`,`review_uuid`, `user_uuid`),
    INDEX `idx_4_review` (`seq`,`review_uuid`, `user_uuid`, `place_uuid`)
) ENGINE=InnoDB;

-- 포인트 테이블 생성
CREATE TABLE `triple_point` (
    `seq` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `review_id` bigint DEFAULT NULL COMMENT '리뷰 ID',
    `user_uuid` varchar(255) DEFAULT NULL COMMENT '유저 UUID',
    `point_status` varchar(255) NOT NULL COMMENT '포인트 상태값',
    `point` int NOT NULL COMMENT '포인트',
    `reason` varchar(255) DEFAULT NULL COMMENT '포인트 생성 이유',
    `return_reason` varchar(255) DEFAULT NULL COMMENT '포인트 반환 이유',
    `reg_date` datetime(6) DEFAULT NULL COMMENT '등록날짜',
    `mod_date` datetime(6) DEFAULT NULL COMMENT '수정날짜',
    INDEX `idx_1_triple_point` (`seq`),
    INDEX `idx_2_triple_point` (`seq`,`user_uuid`),
    INDEX `idx_3_triple_point` (`seq`,`review_id`, `user_uuid`),
    INDEX `idx_4_triple_point` (`seq`,`review_id`, `user_uuid`, `point_status`)
)  ENGINE=INNODB;


