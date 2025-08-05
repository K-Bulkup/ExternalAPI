DROP DATABASE IF EXISTS assetdb;
CREATE DATABASE assetdb;
USE assetdb;

-- ✅ USERS
CREATE TABLE `users` (
                         `user_id` BIGINT AUTO_INCREMENT NOT NULL,
                         `bank` ENUM('국민은행', '신한은행', '기업은행', '농협은행', '우리은행', '하나은행') NULL,
                         `created_at` DATETIME NULL,
                         CONSTRAINT `PK_USERS` PRIMARY KEY (`user_id`)
);

-- ✅ PORTFOLIOS
CREATE TABLE `portfolios` (
                              `user_id` BIGINT NOT NULL,
                              `fintech_use_num` VARCHAR(100) NOT NULL,
                              CONSTRAINT `PK_PORTFOLIOS` PRIMARY KEY (`user_id`, `fintech_use_num`),
                              CONSTRAINT `UQ_PORTFOLIOS_FINTECH_USE_NUM` UNIQUE (`fintech_use_num`),
                              CONSTRAINT `FK_users_TO_portfolios` FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) ON DELETE CASCADE
);

-- ✅ TRANSACTION_POOLS
CREATE TABLE `transaction_pools` (
                                     `transaction_id` BIGINT NOT NULL AUTO_INCREMENT,
                                     `user_id` BIGINT NULL,
                                     `transaction_type` ENUM('입금', '출금') NULL,
                                     `amount` BIGINT NOT NULL,
                                     `transaction_category` ENUM(
                                         '식비', '교통비', '주거/공과금', '생필품',
                                         '의료/건강', '패션/미용', '문화생활/여가', '기타',
                                         '월급', '부수입'
                                         ) NULL,
                                     `tran_date` DATETIME NULL,
                                     CONSTRAINT `PK_TRANSACTION_POOLS` PRIMARY KEY (`transaction_id`),
                                     CONSTRAINT `FK_users_TO_transaction_pools` FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) ON DELETE CASCADE
);

-- ✅ SNAPSHOT_POOLS
CREATE TABLE `snapshot_pools` (
                                  `snapshot_id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `user_id` BIGINT NULL,
                                  `balance` BIGINT NULL,
                                  `snapshot_date` DATETIME NULL,
                                  CONSTRAINT `PK_SNAPSHOT_POOLS` PRIMARY KEY (`snapshot_id`),
                                  CONSTRAINT `FK_users_TO_snapshot_pools` FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) ON DELETE CASCADE
);

-- ✅ COMPOSITION_POOLS
CREATE TABLE `composition_pools` (
                                     `composition_id` BIGINT NOT NULL AUTO_INCREMENT,
                                     `user_id` BIGINT NULL,
                                     `asset_composition` JSON NOT NULL,
                                     CONSTRAINT `PK_COMPOSITION_POOLS` PRIMARY KEY (`composition_id`),
                                     CONSTRAINT `FK_users_TO_composition_pools` FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) ON DELETE CASCADE
);

-- 거래 내역 관련 인덱스
CREATE INDEX idx_transaction_user ON transaction_pools(user_id);
CREATE INDEX idx_transaction_date ON transaction_pools(tran_date);

-- 자산 추이 관련 인덱스
CREATE INDEX idx_snapshot_user ON snapshot_pools(user_id);
CREATE INDEX idx_snapshot_date ON snapshot_pools(snapshot_date);

-- 자산 구성 인덱스
CREATE INDEX idx_composition_user ON composition_pools(user_id);

INSERT INTO users (user_id) VALUES (1);
INSERT INTO users (user_id) VALUES (2);
INSERT INTO users (user_id) VALUES (3);
INSERT INTO users (user_id) VALUES (4);
INSERT INTO users (user_id) VALUES (5);
INSERT INTO users (user_id) VALUES (6);
INSERT INTO users (user_id) VALUES (7);
INSERT INTO users (user_id) VALUES (8);
INSERT INTO users (user_id) VALUES (9);
INSERT INTO users (user_id) VALUES (10);
INSERT INTO users (user_id) VALUES (11);
INSERT INTO users (user_id) VALUES (12);
INSERT INTO users (user_id) VALUES (13);
INSERT INTO users (user_id) VALUES (14);
INSERT INTO users (user_id) VALUES (15);
INSERT INTO users (user_id) VALUES (16);
INSERT INTO users (user_id) VALUES (17);
INSERT INTO users (user_id) VALUES (18);
INSERT INTO users (user_id) VALUES (19);
INSERT INTO users (user_id) VALUES (20);
INSERT INTO users (user_id) VALUES (21);
INSERT INTO users (user_id) VALUES (22);
INSERT INTO users (user_id) VALUES (23);
INSERT INTO users (user_id) VALUES (24);
INSERT INTO users (user_id) VALUES (25);
INSERT INTO users (user_id) VALUES (26);
INSERT INTO users (user_id) VALUES (27);
INSERT INTO users (user_id) VALUES (28);
INSERT INTO users (user_id) VALUES (29);
INSERT INTO users (user_id) VALUES (30);
INSERT INTO users (user_id) VALUES (31);
INSERT INTO users (user_id) VALUES (32);
INSERT INTO users (user_id) VALUES (33);
INSERT INTO users (user_id) VALUES (34);
INSERT INTO users (user_id) VALUES (35);
INSERT INTO users (user_id) VALUES (36);
INSERT INTO users (user_id) VALUES (37);
INSERT INTO users (user_id) VALUES (38);
INSERT INTO users (user_id) VALUES (39);
INSERT INTO users (user_id) VALUES (40);
INSERT INTO users (user_id) VALUES (41);
INSERT INTO users (user_id) VALUES (42);
INSERT INTO users (user_id) VALUES (43);
INSERT INTO users (user_id) VALUES (44);
INSERT INTO users (user_id) VALUES (45);
INSERT INTO users (user_id) VALUES (46);
INSERT INTO users (user_id) VALUES (47);
INSERT INTO users (user_id) VALUES (48);
INSERT INTO users (user_id) VALUES (49);
INSERT INTO users (user_id) VALUES (50);

-- 초기화
UPDATE composition_pools
SET user_id = NULL;

DELETE FROM snapshot_pools;
DELETE FROM portfolios;

-- 테스트
select * from users;
select * from portfolios;