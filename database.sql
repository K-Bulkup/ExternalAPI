DROP DATABASE IF EXISTS assetdb;
CREATE DATABASE assetdb;
USE assetdb;

-- portfolios
CREATE TABLE `portfolios`
(
    `portfolio_id`    BIGINT                                           NOT NULL AUTO_INCREMENT,
    `fintech_use_num` VARCHAR(100)                                     NOT NULL,
    `bank_code`       ENUM ('국민은행','신한은행','기업은행','농협은행','우리은행','하나은행') NULL,
    `account_number`  VARCHAR(50)                                      NULL,
    `created_at`      DATETIME                                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `PK_portfolios` PRIMARY KEY (`portfolio_id`),
    CONSTRAINT `UQ_portfolios_fintech` UNIQUE (`fintech_use_num`),
    CONSTRAINT `UQ_portfolios_bank_acct` UNIQUE (`bank_code`, `account_number`)
);

-- snapshot_pools : 복합 PK(계좌+일시)
CREATE TABLE `snapshot_pools`
(
    `fintech_use_num` VARCHAR(100) NOT NULL,
    `balance`         BIGINT       NOT NULL,
    `snapshot_date`   DATETIME     NOT NULL,
    CONSTRAINT `PK_snapshot_pools` PRIMARY KEY (`fintech_use_num`, `snapshot_date`),
    CONSTRAINT `FK_snapshot_fintech`
        FOREIGN KEY (`fintech_use_num`) REFERENCES `portfolios` (`fintech_use_num`)
            ON UPDATE CASCADE ON DELETE CASCADE
);

-- composition_pools : 1계좌 1행이면 단일 PK 유지
CREATE TABLE `composition_pools`
(
    `fintech_use_num`   VARCHAR(100) NOT NULL,
    `asset_composition` JSON         NOT NULL,
    CONSTRAINT `PK_composition_pools` PRIMARY KEY (`fintech_use_num`),
    CONSTRAINT `FK_composition_fintech`
        FOREIGN KEY (`fintech_use_num`) REFERENCES `portfolios` (`fintech_use_num`)
            ON UPDATE CASCADE ON DELETE CASCADE
);

-- transaction_pools : 거래 ID PK
CREATE TABLE `transaction_pools`
(
    `transaction_id`       BIGINT           NOT NULL AUTO_INCREMENT,
    `fintech_use_num`      VARCHAR(100)     NOT NULL,
    `transaction_type`     ENUM ('입금','출금') NOT NULL,
    `amount`               BIGINT           NOT NULL,
    `transaction_category` ENUM ('식비','교통비','주거/공과금','생필품',
        '의료/건강','패션/미용','문화생활/여가','기타',
        '월급','부수입')                         NOT NULL,
    `tran_date`            DATETIME         NOT NULL,
    CONSTRAINT `PK_transaction_pools` PRIMARY KEY (`transaction_id`),
    CONSTRAINT `FK_transaction_fintech`
        FOREIGN KEY (`fintech_use_num`) REFERENCES `portfolios` (`fintech_use_num`)
            ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO portfolios (fintech_use_num)
VALUES ('1'),
       ('2'),
       ('3'),
       ('4'),
       ('5'),
       ('6'),
       ('7'),
       ('8'),
       ('9'),
       ('10'),
       ('11'),
       ('12'),
       ('13'),
       ('14'),
       ('15'),
       ('16'),
       ('17'),
       ('18'),
       ('19'),
       ('20'),
       ('21'),
       ('22'),
       ('23'),
       ('24'),
       ('25'),
       ('26'),
       ('27'),
       ('28'),
       ('29'),
       ('30'),
       ('31'),
       ('32'),
       ('33'),
       ('34'),
       ('35'),
       ('36'),
       ('37'),
       ('38'),
       ('39'),
       ('40'),
       ('41'),
       ('42'),
       ('43'),
       ('44'),
       ('45'),
       ('46'),
       ('47'),
       ('48'),
       ('49'),
       ('50'),
       ('51'),
       ('52'),
       ('53'),
       ('54'),
       ('55'),
       ('56'),
       ('57'),
       ('58'),
       ('59'),
       ('60'),
       ('61'),
       ('62'),
       ('63'),
       ('64'),
       ('65'),
       ('66'),
       ('67'),
       ('68'),
       ('69'),
       ('70'),
       ('71'),
       ('72'),
       ('73'),
       ('74'),
       ('75'),
       ('76'),
       ('77'),
       ('78'),
       ('79'),
       ('80'),
       ('81'),
       ('82'),
       ('83'),
       ('84'),
       ('85'),
       ('86'),
       ('87'),
       ('88'),
       ('89'),
       ('90'),
       ('91'),
       ('92'),
       ('93'),
       ('94'),
       ('95'),
       ('96'),
       ('97'),
       ('98'),
       ('99'),
       ('100');
