DROP TABLE IF EXISTS `zuul_id_limiter`;
CREATE TABLE `zuul_id_limiter` (
	`id` BIGINT(20) AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  `zuul_id` VARCHAR(255) NOT NULL UNIQUE COMMENT 'zuul服务id',
  `permits_per_second` DECIMAL(10,2) NOT NULL COMMENT '每秒限制数量',
  `permits` INT(11) NOT NULL COMMENT '获取许可数量',
  `timeout` BIGINT(20) NOT NULL COMMENT '获取许可超时时间',
  `time_unit` VARCHAR(20) NOT NULL COMMENT '获取许可超时时间单位',
  `status_code` INT(11) NOT NULL COMMENT '超过限流时的错误码',
  `error_cause` VARCHAR(255) NOT NULL COMMENT '超过限流时的错误原因',
  `enable` TINYINT(1) NOT NULL COMMENT '是否启用'
) ENGINE=INNODB DEFAULT charset=utf8;

DROP TABLE IF EXISTS `zuul_path_limiter`;
CREATE TABLE `zuul_path_limiter` (
	`id` BIGINT(20) AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  `zuul_id` VARCHAR(255) NOT NULL COMMENT '所属的zuul路由配置的id',
	`path` VARCHAR(255) NOT NULL COMMENT '访问路径',
  `permits_per_second` DECIMAL(10,2) NOT NULL COMMENT '每秒限制数量',
  `permits` INT(11) NOT NULL COMMENT '获取许可数量',
  `timeout` BIGINT(20) NOT NULL COMMENT '获取许可超时时间',
  `time_unit` VARCHAR(20) NOT NULL COMMENT '获取许可超时时间单位',
  `status_code` INT(11) NOT NULL COMMENT '超过限流时的错误码',
  `error_cause` VARCHAR(255) NOT NULL COMMENT '超过限流时的错误原因',
  `enable` TINYINT(1) NOT NULL COMMENT '是否启用',
	UNIQUE KEY `uq_zuul_id_path` (`zuul_id`,`path`) COMMENT '同一服务的访问路径唯一'
) ENGINE=INNODB DEFAULT charset=utf8;

ALTER TABLE `zuul_path_limiter` ADD INDEX `idx_zuul_id`(`zuul_id`) COMMENT '路由配置id索引';

ALTER TABLE `zuul_path_limiter` ADD INDEX `idx_path`(`path`) COMMENT '访问路径索引';
