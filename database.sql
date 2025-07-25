-- 0. 유저 테이블
CREATE TABLE `Users` ( 
                         `user_id` BIGINT NOT NULL,
                         `bank` ENUM('국민은행', '신한은행', '카카오뱅크', '토스뱅크', '농협', '우리은행', '하나은행', '기타') NOT NULL,
                         `account_num` VARCHAR(50) NULL,
                         `fintech_use_num` VARCHAR(100) NULL,
                         # `refresh_token` VARCHAR(100) NULL,
                         `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT `PK_USERS` PRIMARY KEY (`user_id`)
);

-- 1. 유저 자산 테이블
CREATE TABLE `user_assets` (
                              `user_id` BIGINT NOT NULL,
                              CONSTRAINT `PK_USERASSETS` PRIMARY KEY (`user_id`),
                              CONSTRAINT `FK_Users_TO_UserAssets_1` FOREIGN KEY (`user_id`)
                                  REFERENCES `Users` (`user_id`)
                                  ON DELETE CASCADE
);

-- 2. 자산 추이 더미 테이블
CREATE TABLE `snapshot_pools` (
                                 `snapshot_id` BIGINT NOT NULL,
                                 `user_id` BIGINT,
                                 `balance` BIGINT NOT NULL,
                                 `snapshot_date` DATETIME NOT NULL,
                                 CONSTRAINT `PK_SNAPSHOTPOOLS` PRIMARY KEY (`snapshot_id`),
                                 CONSTRAINT `FK_snapshot_UserAssets` FOREIGN KEY (`user_id`)
                                     REFERENCES `user_assets` (`user_id`)
                                     ON DELETE CASCADE,
                                 CONSTRAINT `UQ_user_snapshot` UNIQUE (`user_id`, `snapshot_date`)
);

-- 3. 자산 구성 테이블
CREATE TABLE `composition_pools` (
                                    `composition_id` BIGINT NOT NULL,
                                    `user_id` BIGINT,
                                    `asset_composition` JSON NOT NULL,
                                    CONSTRAINT `PK_COMPOSITIONPOOLS` PRIMARY KEY (`composition_id`),
                                    CONSTRAINT `FK_composition_UserAssets` FOREIGN KEY (`user_id`)
                                        REFERENCES `user_assets` (`user_id`)
                                        ON DELETE CASCADE
);

-- 4. 출금 내역 테이블
CREATE TABLE `withdrawal_pools` (
                                   `transaction_id` BIGINT NOT NULL,
                                   `user_id` BIGINT,
                                   `amount` BIGINT NOT NULL,
                                   `transaction_category` ENUM(
                                        '식비', '교통비', '주거/공과금', '생필품',
                                        '의료/건강', '패션/미용', '문화생활/여가', '기타'
                                    ) NOT NULL,
                                   `transaction_date` DATETIME NOT NULL,
                                   CONSTRAINT `PK_WITHDRAWALPOOLS` PRIMARY KEY (`transaction_id`),
                                   CONSTRAINT `FK_withdrawal_UserAssets` FOREIGN KEY (`user_id`)
                                       REFERENCES `user_assets` (`user_id`)
                                       ON DELETE CASCADE
);

-- 인덱스 생성
CREATE INDEX idx_snapshot_user ON snapshot_pools(user_id);
CREATE INDEX idx_withdrawal_user ON withdrawal_pools(user_id);
CREATE INDEX idx_composition_user ON composition_pools(user_id);

-- 토큰 테스트
select * from users;
delete from users;

-- 매핑 테스트
SELECT * FROM user_assets;
INSERT INTO user_assets (user_id) VALUES (1);

select count(*) from snapshot_pools where user_id = 1; #10 ~ 30
select count(*) from withdrawal_pools where user_id = 1; #20 ~ 50

-- 1. snapshot_pools 초기화
UPDATE snapshot_pools
SET user_id = NULL
WHERE user_id = 1;

-- 2. withdrawal_pools 초기화
UPDATE withdrawal_pools
SET user_id = NULL
WHERE user_id = 1;

-- 3. composition_pools 초기화
UPDATE composition_pools
SET user_id = NULL
WHERE user_id = 1;

-- user_assets 초기화
delete from user_assets;