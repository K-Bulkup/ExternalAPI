-- ✅ USERS
CREATE TABLE `users` (
                         `user_id` BIGINT NOT NULL,
                         `bank` ENUM('국민은행', '신한은행', '기업은행', '농협은행', '우리은행', '하나은행') NOT NULL,
                         `fintech_use_num` VARCHAR(100) NULL,
                         `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT `PK_USERS` PRIMARY KEY (`user_id`)
);

-- ✅ PORTFOLIOS
CREATE TABLE `portfolios` (
                              `user_id` BIGINT NOT NULL,
                              CONSTRAINT `PK_PORTFOLIOS` PRIMARY KEY (`user_id`),
                              CONSTRAINT `FK_users_TO_portfolios` FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) ON DELETE CASCADE
);

-- ✅ TRANSACTION_POOLS
CREATE TABLE `transaction_pools` (
                                     `transaction_id` BIGINT NOT NULL,
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
                                     `composition_id` BIGINT NOT NULL,
                                     `user_id` BIGINT NULL,
                                     `asset_composition` JSON NOT NULL,
                                     CONSTRAINT `PK_COMPOSITION_POOLS` PRIMARY KEY (`composition_id`),
                                     CONSTRAINT `FK_users_TO_composition_pools` FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

-- 거래 내역 관련 인덱스
CREATE INDEX idx_transaction_user ON transaction_pools(user_id);
CREATE INDEX idx_transaction_date ON transaction_pools(tran_date);

-- 자산 추이 관련 인덱스
CREATE INDEX idx_snapshot_user ON snapshot_pools(user_id);
CREATE INDEX idx_snapshot_date ON snapshot_pools(snapshot_date);

-- 자산 구성 인덱스
CREATE INDEX idx_composition_user ON composition_pools(user_id);

-- 핀테크번호는 유니크 인덱스 권장
CREATE UNIQUE INDEX idx_users_fintech_use_num ON users(fintech_use_num);

DELETE FROM portfolios;

UPDATE transaction_pools
SET user_id = NULL
WHERE user_id = 1;

UPDATE composition_pools
SET user_id = NULL
WHERE user_id = 1;

DELETE FROM snapshot_pools;

-- 토큰 테스트
select * from users;
delete from users;