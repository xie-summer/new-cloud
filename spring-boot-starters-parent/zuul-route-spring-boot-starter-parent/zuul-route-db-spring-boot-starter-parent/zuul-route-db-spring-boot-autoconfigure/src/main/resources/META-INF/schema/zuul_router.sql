drop table if exists `zuul_route_config`;
create table `zuul_route_config` (
  `id` varchar(50) not null comment '路由ID',
  `path` varchar(255) not null comment '访问路径',
  `service_id` varchar(50) not null default '' comment '注册中心的服务ID',
  `url` varchar(255) not null default '' comment '路由目的地址',
  `strip_prefix` tinyint(1) not null default 0 comment '是否去掉前缀',
  `retryable` tinyint(1) not null default 0 comment '是否重试',
  `sensitive_headers` varchar(255) not null default '' comment '敏感头信息',
  `custom_sensitive_headers` tinyint(1) not null default 0 comment '是否自定义敏感头信息',
  `enable` tinyint(1) not null default 0 comment '是否启用',
  `router_name` varchar(255) not null default '' comment '路由配置名称',
  primary key (`id`)
) engine=innodb default charset=utf8;

drop table if exists `zuul_route_config_rule`;
create table `zuul_route_config_rule` (
  `id` varchar(50) not null comment '规则ID',
  `route_id` varchar(50) not null comment '路由配置ID',
  `rule` varchar(512) not null default '' comment '规则(freemarker表达式)',
  `expected_result` varchar(255) not null default '' comment '期望规则结果',
  `location` varchar(255) not null default '' comment '匹配规则后的路由目的地址，为空表示使用路由配置的目的地址',
  `enable` tinyint(1) not null default 1 comment '是否启用',
  `sort_num` int(11) not null default 0 comment '规则优先级，数字越小，优先级越高',
  primary key (`id`)
) engine=innodb default charset=utf8;