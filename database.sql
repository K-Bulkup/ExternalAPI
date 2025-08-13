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
                              CONSTRAINT `PK_PORTFOLIOS` PRIMARY KEY (`user_id`),
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

INSERT INTO users (user_id)
VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10),
       (11), (12), (13), (14), (15), (16), (17), (18), (19), (20),
       (21), (22), (23), (24), (25), (26), (27), (28), (29), (30),
       (31), (32), (33), (34), (35), (36), (37), (38), (39), (40),
       (41), (42), (43), (44), (45), (46), (47), (48), (49), (50),
       (51), (52), (53), (54), (55), (56), (57), (58), (59), (60),
       (61), (62), (63), (64), (65), (66), (67), (68), (69), (70),
       (71), (72), (73), (74), (75), (76), (77), (78), (79), (80),
       (81), (82), (83), (84), (85), (86), (87), (88), (89), (90),
       (91), (92), (93), (94), (95), (96), (97), (98), (99), (100);

-- 초기화
UPDATE composition_pools
SET user_id = NULL;

DELETE FROM snapshot_pools;
DELETE FROM portfolios;

-- 테스트
select * from users;
select * from portfolios;