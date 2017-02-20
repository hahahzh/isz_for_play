-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.14 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 isz 的数据库结构
CREATE DATABASE IF NOT EXISTS `isz` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `isz`;


-- 导出  表 isz.appadmin 结构
CREATE TABLE IF NOT EXISTS `appadmin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8a30ttrvnnvgh7m5jw988aq1d` (`role_id`),
  CONSTRAINT `FK_8a30ttrvnnvgh7m5jw988aq1d` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.appadmin 的数据：~1 rows (大约)
DELETE FROM `appadmin`;
/*!40000 ALTER TABLE `appadmin` DISABLE KEYS */;
INSERT INTO `appadmin` (`id`, `auth`, `name`, `pwd`, `role_id`) VALUES
	(3, '', 'admin', '111111', 1);
/*!40000 ALTER TABLE `appadmin` ENABLE KEYS */;


-- 导出  表 isz.appsession 结构
CREATE TABLE IF NOT EXISTS `appsession` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `sessionID` varchar(255) DEFAULT NULL,
  `user_openid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4y5t6fhwao65kbmhc5dwds52m` (`user_openid`),
  KEY `idx_sessionID` (`sessionID`),
  KEY `FK_4y5t6fhwao65kbmhc5dwds52m` (`user_openid`),
  CONSTRAINT `FK_4y5t6fhwao65kbmhc5dwds52m` FOREIGN KEY (`user_openid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  isz.appsession 的数据：~0 rows (大约)
DELETE FROM `appsession`;
/*!40000 ALTER TABLE `appsession` DISABLE KEYS */;
/*!40000 ALTER TABLE `appsession` ENABLE KEYS */;


-- 导出  表 isz.bloods 结构
CREATE TABLE IF NOT EXISTS `bloods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.bloods 的数据：~4 rows (大约)
DELETE FROM `bloods`;
/*!40000 ALTER TABLE `bloods` DISABLE KEYS */;
INSERT INTO `bloods` (`id`, `name`) VALUES
	(1, 'O型'),
	(2, 'A型'),
	(3, 'B型'),
	(4, 'AB型');
/*!40000 ALTER TABLE `bloods` ENABLE KEYS */;


-- 导出  表 isz.checkdigit 结构
CREATE TABLE IF NOT EXISTS `checkdigit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `d` int(11) NOT NULL,
  `m` varchar(255) DEFAULT NULL,
  `updatetime` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `checkdigit_d` (`d`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.checkdigit 的数据：~17 rows (大约)
DELETE FROM `checkdigit`;
/*!40000 ALTER TABLE `checkdigit` DISABLE KEYS */;
INSERT INTO `checkdigit` (`id`, `d`, `m`, `updatetime`) VALUES
	(1, 111111, '15000993473', 0),
	(2, 28332, NULL, 1486053977556),
	(3, 94690, NULL, 1486054053306),
	(4, 68087, NULL, 1486054108268),
	(5, 166672, NULL, 1486054220824),
	(6, 86083, '15000993473', 1486055260581),
	(7, 107935, '15000993473', 1486055329799),
	(8, 89924, '15000993473', 1486055422574),
	(9, 91476, '15000993473', 1486055537883),
	(10, 2211, '15000993473', 1486055724316),
	(11, 60831, '15000993473', 1486055751934),
	(12, 139184, '15000993473', 1486055963961),
	(13, 157688, '15000993473', 1486056053654),
	(14, 657, '15000993473', 1486056141363),
	(15, 145572, '15000993473', 1486056174030),
	(16, 34566, '15000993473', 1486056345019),
	(17, 135390, '15000993473', 1486056509764),
	(18, 1234, '15000993473', 1486057851579);
/*!40000 ALTER TABLE `checkdigit` ENABLE KEYS */;


-- 导出  表 isz.constellations 结构
CREATE TABLE IF NOT EXISTS `constellations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.constellations 的数据：~12 rows (大约)
DELETE FROM `constellations`;
/*!40000 ALTER TABLE `constellations` DISABLE KEYS */;
INSERT INTO `constellations` (`id`, `name`) VALUES
	(1, '白羊'),
	(2, '金牛'),
	(3, '双子'),
	(4, '巨蟹'),
	(5, '狮子'),
	(6, '处女'),
	(7, '天秤'),
	(8, '天蝎'),
	(9, '射手'),
	(10, '摩羯'),
	(11, '水瓶'),
	(12, '双鱼');
/*!40000 ALTER TABLE `constellations` ENABLE KEYS */;


-- 导出  表 isz.gender 结构
CREATE TABLE IF NOT EXISTS `gender` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.gender 的数据：~2 rows (大约)
DELETE FROM `gender`;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` (`id`, `name`) VALUES
	(1, '男'),
	(2, '女');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;


-- 导出  表 isz.goods_info 结构
CREATE TABLE IF NOT EXISTS `goods_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `date_end` datetime DEFAULT NULL,
  `date_from` datetime DEFAULT NULL,
  `goods_unit` varchar(255) DEFAULT NULL,
  `isShow` int(11) NOT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nameplate_price` double DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `rebate` float DEFAULT NULL,
  `sales_price` double DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `goods_num` int(11) DEFAULT NULL,
  `storehouse_goods_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_32el26m9dxoimek916vfdyxdb` (`storehouse_goods_id`),
  CONSTRAINT `FK_32el26m9dxoimek916vfdyxdb` FOREIGN KEY (`storehouse_goods_id`) REFERENCES `storehouse` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.goods_info 的数据：~2 rows (大约)
DELETE FROM `goods_info`;
/*!40000 ALTER TABLE `goods_info` DISABLE KEYS */;
INSERT INTO `goods_info` (`id`, `code`, `createDate`, `date_end`, `date_from`, `goods_unit`, `isShow`, `memo`, `name`, `nameplate_price`, `num`, `rebate`, `sales_price`, `updateDate`, `goods_num`, `storehouse_goods_id`) VALUES
	(1, 'dsjiojs-21321d-12', '2017-02-20 21:45:19', '2017-04-30 00:00:00', '2017-02-25 00:00:00', '件', 0, '测试测试', 'ZARA白色连衣裙', 500, NULL, 0.1, 350.1, '2017-02-20 21:45:19', 1, 3),
	(2, 'dsjiojs-21321d-12', '2017-02-20 21:49:03', '2017-02-23 00:00:00', '2017-02-21 00:00:00', '件', 0, '分阶段销售', 'ZARA白色连衣裙', 500, NULL, 0.2, 300, '2017-02-20 21:49:03', 0, 3);
/*!40000 ALTER TABLE `goods_info` ENABLE KEYS */;


-- 导出  表 isz.goods_info_bu 结构
CREATE TABLE IF NOT EXISTS `goods_info_bu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `date_end` datetime DEFAULT NULL,
  `date_from` datetime DEFAULT NULL,
  `goods_num` int(11) DEFAULT NULL,
  `goods_unit` varchar(255) DEFAULT NULL,
  `isShow` int(11) NOT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nameplate_price` double DEFAULT NULL,
  `rebate` float DEFAULT NULL,
  `sales_price` double DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  isz.goods_info_bu 的数据：~0 rows (大约)
DELETE FROM `goods_info_bu`;
/*!40000 ALTER TABLE `goods_info_bu` DISABLE KEYS */;
/*!40000 ALTER TABLE `goods_info_bu` ENABLE KEYS */;


-- 导出  表 isz.goods_purchase 结构
CREATE TABLE IF NOT EXISTS `goods_purchase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_period` varchar(255) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `goods_code` varchar(255) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_price` double DEFAULT NULL,
  `goods_type` varchar(255) DEFAULT NULL,
  `purchase_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `purchase_men` varchar(255) DEFAULT NULL,
  `purchase_num` int(11) DEFAULT NULL,
  `purchase_price` double DEFAULT NULL,
  `supplier` varchar(255) DEFAULT NULL,
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  isz.goods_purchase 的数据：~0 rows (大约)
DELETE FROM `goods_purchase`;
/*!40000 ALTER TABLE `goods_purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `goods_purchase` ENABLE KEYS */;


-- 导出  表 isz.logistics 结构
CREATE TABLE IF NOT EXISTS `logistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.logistics 的数据：~4 rows (大约)
DELETE FROM `logistics`;
/*!40000 ALTER TABLE `logistics` DISABLE KEYS */;
INSERT INTO `logistics` (`id`, `name`) VALUES
	(1, '未出库'),
	(2, '已出库'),
	(3, '在途'),
	(4, '签收');
/*!40000 ALTER TABLE `logistics` ENABLE KEYS */;


-- 导出  表 isz.order_info 结构
CREATE TABLE IF NOT EXISTS `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `isShow` int(11) NOT NULL,
  `logistics` int(11) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `order_unit` varchar(255) DEFAULT NULL,
  `pay` int(11) DEFAULT NULL,
  `sales_date` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `user_tmp` varchar(255) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `logistics_id` bigint(20) DEFAULT NULL,
  `paystatus_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4qqhl26bt4qour3wg2v34cuc9` (`goods_id`),
  KEY `FK_pn95lldcxl4p6976cqlm3kifv` (`user_id`),
  KEY `FK_qk7ekfyb2q3a52xg3sf1fd3vj` (`logistics_id`),
  KEY `FK_2orahfer0vb3ixoau2qf2tgho` (`paystatus_id`),
  CONSTRAINT `FK_2orahfer0vb3ixoau2qf2tgho` FOREIGN KEY (`paystatus_id`) REFERENCES `pay_status` (`id`),
  CONSTRAINT `FK_4qqhl26bt4qour3wg2v34cuc9` FOREIGN KEY (`goods_id`) REFERENCES `goods_info` (`id`),
  CONSTRAINT `FK_pn95lldcxl4p6976cqlm3kifv` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_qk7ekfyb2q3a52xg3sf1fd3vj` FOREIGN KEY (`logistics_id`) REFERENCES `pay_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.order_info 的数据：~1 rows (大约)
DELETE FROM `order_info`;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
INSERT INTO `order_info` (`id`, `code`, `createDate`, `isShow`, `logistics`, `memo`, `num`, `order_unit`, `pay`, `sales_date`, `updateDate`, `user_tmp`, `goods_id`, `user_id`, `order_num`, `logistics_id`, `paystatus_id`) VALUES
	(1, 'dsjiojs-21321d-12', '2017-02-20 22:40:48', 1, NULL, '测试订单', NULL, '件', NULL, '2017-02-20 00:00:00', '2017-02-20 22:40:48', '15000993473', 1, 11, 1, 2, 2);
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;


-- 导出  表 isz.pay_status 结构
CREATE TABLE IF NOT EXISTS `pay_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.pay_status 的数据：~3 rows (大约)
DELETE FROM `pay_status`;
/*!40000 ALTER TABLE `pay_status` DISABLE KEYS */;
INSERT INTO `pay_status` (`id`, `name`) VALUES
	(1, '未支付'),
	(2, '已支付'),
	(3, '取消');
/*!40000 ALTER TABLE `pay_status` ENABLE KEYS */;


-- 导出  表 isz.purchase_info 结构
CREATE TABLE IF NOT EXISTS `purchase_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_period` varchar(255) DEFAULT NULL,
  `attribute` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `isShow` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nameplate_price` double DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `purchase_price` double DEFAULT NULL,
  `purchase_unit` varchar(255) DEFAULT NULL,
  `purchasing_agent` varchar(255) DEFAULT NULL,
  `purchasing_date` datetime DEFAULT NULL,
  `rebate` float DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `purchase_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.purchase_info 的数据：~2 rows (大约)
DELETE FROM `purchase_info`;
/*!40000 ALTER TABLE `purchase_info` DISABLE KEYS */;
INSERT INTO `purchase_info` (`id`, `account_period`, `attribute`, `code`, `company`, `isShow`, `name`, `nameplate_price`, `num`, `purchase_price`, `purchase_unit`, `purchasing_agent`, `purchasing_date`, `rebate`, `updateDate`, `purchase_num`) VALUES
	(6, '半年', '白色，长裙，白色，XXL', 'dsjiojs-21321d-12', 'ＺＡＲＡ苏州工厂', 1, 'ZARA白色连衣裙', 500, NULL, 300.21, '件', 'Running', '2017-02-12 00:00:00', 0, '2017-02-20 20:59:39', 100),
	(7, '半年', '白色，长裙，白色，XXL', 'dsjiojs-21321d-12', 'ＺＡＲＡ苏州工厂', 1, 'ZARA白色连衣裙', 500, NULL, 250, '件', 'Running', '2017-02-16 00:00:00', 0.1, '2017-02-20 21:00:40', 100);
/*!40000 ALTER TABLE `purchase_info` ENABLE KEYS */;


-- 导出  表 isz.purchase_info_bu 结构
CREATE TABLE IF NOT EXISTS `purchase_info_bu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_period` varchar(255) DEFAULT NULL,
  `attribute` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `isShow` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nameplate_price` double DEFAULT NULL,
  `purchase_num` int(11) DEFAULT NULL,
  `purchase_price` double DEFAULT NULL,
  `purchase_unit` varchar(255) DEFAULT NULL,
  `purchasing_agent` varchar(255) DEFAULT NULL,
  `purchasing_date` datetime DEFAULT NULL,
  `rebate` float DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.purchase_info_bu 的数据：~1 rows (大约)
DELETE FROM `purchase_info_bu`;
/*!40000 ALTER TABLE `purchase_info_bu` DISABLE KEYS */;
INSERT INTO `purchase_info_bu` (`id`, `account_period`, `attribute`, `code`, `company`, `isShow`, `name`, `nameplate_price`, `purchase_num`, `purchase_price`, `purchase_unit`, `purchasing_agent`, `purchasing_date`, `rebate`, `updateDate`) VALUES
	(2, '半年', '白色，长裙，白色，XXL', 'dsjiojs-21321d-12', 'ＺＡＲＡ苏州工厂', 0, 'ZARA白色连衣裙', 500, 100, 500, '件', 'Running', '2017-02-20 00:00:00', 0, '2017-02-20 17:45:33');
/*!40000 ALTER TABLE `purchase_info_bu` ENABLE KEYS */;


-- 导出  表 isz.role 结构
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.role 的数据：~1 rows (大约)
DELETE FROM `role`;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`) VALUES
	(1, '管理员');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


-- 导出  表 isz.sales 结构
CREATE TABLE IF NOT EXISTS `sales` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `discount` double DEFAULT NULL,
  `goods_code` varchar(255) NOT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_price` double DEFAULT NULL,
  `goods_type` varchar(255) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pk1fbn7f01bgdnh9qb4k4aotd` (`goods_code`),
  KEY `FKu5lyewcf0mgbldqrf8rhmjf6` (`user_id`),
  CONSTRAINT `FKu5lyewcf0mgbldqrf8rhmjf6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  isz.sales 的数据：~0 rows (大约)
DELETE FROM `sales`;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;


-- 导出  表 isz.storehouse 结构
CREATE TABLE IF NOT EXISTS `storehouse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `storehouse_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `storehouse_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.storehouse 的数据：~1 rows (大约)
DELETE FROM `storehouse`;
/*!40000 ALTER TABLE `storehouse` DISABLE KEYS */;
INSERT INTO `storehouse` (`id`, `code`, `createDate`, `name`, `num`, `updateDate`, `storehouse_num`) VALUES
	(3, 'dsjiojs-21321d-12', '2017-02-20 17:45:33', 'ZARA白色连衣裙', NULL, '2017-02-20 22:40:48', 199);
/*!40000 ALTER TABLE `storehouse` ENABLE KEYS */;


-- 导出  表 isz.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `email` varchar(255) DEFAULT NULL,
  `exp` bigint(20) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `mobile` bigint(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `os` int(11) NOT NULL,
  `point` int(11) DEFAULT NULL,
  `portrait` varchar(255) DEFAULT NULL,
  `pwd` varchar(30) NOT NULL,
  `school` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `wx_id` varchar(255) DEFAULT NULL,
  `cd` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `gender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_email` (`email`),
  KEY `idx_mobile` (`mobile`),
  KEY `FK_cue3a2wa73brrx5xjsjuvojur` (`gender_id`),
  CONSTRAINT `FK_cue3a2wa73brrx5xjsjuvojur` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user 的数据：~0 rows (大约)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `address`, `birthday`, `create_date`, `email`, `exp`, `level`, `mobile`, `name`, `nickname`, `os`, `point`, `portrait`, `pwd`, `school`, `type`, `update_date`, `wx_id`, `cd`, `createDate`, `updateDate`, `gender_id`) VALUES
	(11, NULL, '2017-02-07', '2017-02-20 23:28:47', 'hanzhao7726562@163.com', NULL, NULL, 15000993473, '帅哥', '刺客2', 0, NULL, NULL, '111111', '西贝', '', '2017-02-03 16:12:26', '', NULL, NULL, NULL, 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- 导出  表 isz.user_body 结构
CREATE TABLE IF NOT EXISTS `user_body` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `body_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_body 的数据：~6 rows (大约)
DELETE FROM `user_body`;
/*!40000 ALTER TABLE `user_body` DISABLE KEYS */;
INSERT INTO `user_body` (`id`, `body_name`) VALUES
	(1, '可乐瓶'),
	(2, '鸭梨'),
	(3, '标准'),
	(4, '草莓'),
	(5, '丝瓜'),
	(6, '苹果');
/*!40000 ALTER TABLE `user_body` ENABLE KEYS */;


-- 导出  表 isz.user_detail 结构
CREATE TABLE IF NOT EXISTS `user_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `body_id` bigint(20) DEFAULT NULL,
  `face_id` bigint(20) DEFAULT NULL,
  `frequency_id` bigint(20) DEFAULT NULL,
  `hair_id` bigint(20) DEFAULT NULL,
  `hierarchy_id` bigint(20) DEFAULT NULL,
  `inclination_id` bigint(20) DEFAULT NULL,
  `post_day_id` bigint(20) DEFAULT NULL,
  `skin_color_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `height` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `size_id` bigint(20) DEFAULT NULL,
  `blood_id` bigint(20) DEFAULT NULL,
  `constellation_id` bigint(20) DEFAULT NULL,
  `skincolor_id` bigint(20) DEFAULT NULL,
  `pricerange_id` bigint(20) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `postDay_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdpobsmms9p0tickf6c28tii3m` (`body_id`),
  KEY `FK4g5ksh7jwjasr6qkvm3olh3su` (`face_id`),
  KEY `FK8y0ra5grd8jalxk88rksy6n48` (`frequency_id`),
  KEY `FKe0c8xhil93k1tbd686d02okhg` (`hair_id`),
  KEY `FK8nplrjp66j2trgmvoj59u2det` (`hierarchy_id`),
  KEY `FK66e20psld19la3wiw8gk56pfi` (`inclination_id`),
  KEY `FKce9e72gtgifo3bdlanxw7m8k7` (`post_day_id`),
  KEY `FKcc4i4m61a5r3sghpgh2fj1lle` (`skin_color_id`),
  KEY `FKc2fr118twu8aratnm1qop1mn9` (`user_id`),
  KEY `FKimo1y2hvj8alpk5l96s26ntu8` (`size_id`),
  KEY `FK8t88qrrvy9t3bimxgq8hjwd49` (`blood_id`),
  KEY `FK9w0ymv970i8r7kny37d484ck3` (`constellation_id`),
  KEY `FKl4pul1wr0drscio5me6s5poj5` (`skincolor_id`),
  KEY `FKqed0c70hr4itqclrw8m0dynnk` (`pricerange_id`),
  KEY `FK_8x3sy9s5l4blqg7mfh7xlah53` (`postDay_id`),
  CONSTRAINT `FK_8x3sy9s5l4blqg7mfh7xlah53` FOREIGN KEY (`postDay_id`) REFERENCES `user_postday` (`id`),
  CONSTRAINT `FK4g5ksh7jwjasr6qkvm3olh3su` FOREIGN KEY (`face_id`) REFERENCES `user_face` (`id`),
  CONSTRAINT `FK66e20psld19la3wiw8gk56pfi` FOREIGN KEY (`inclination_id`) REFERENCES `user_inclination` (`id`),
  CONSTRAINT `FK8nplrjp66j2trgmvoj59u2det` FOREIGN KEY (`hierarchy_id`) REFERENCES `user_hierarchy` (`id`),
  CONSTRAINT `FK8t88qrrvy9t3bimxgq8hjwd49` FOREIGN KEY (`blood_id`) REFERENCES `bloods` (`id`),
  CONSTRAINT `FK8y0ra5grd8jalxk88rksy6n48` FOREIGN KEY (`frequency_id`) REFERENCES `user_frequency` (`id`),
  CONSTRAINT `FK9w0ymv970i8r7kny37d484ck3` FOREIGN KEY (`constellation_id`) REFERENCES `constellations` (`id`),
  CONSTRAINT `FKc2fr118twu8aratnm1qop1mn9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKcc4i4m61a5r3sghpgh2fj1lle` FOREIGN KEY (`skin_color_id`) REFERENCES `user_skincolor` (`id`),
  CONSTRAINT `FKce9e72gtgifo3bdlanxw7m8k7` FOREIGN KEY (`post_day_id`) REFERENCES `user_postday` (`id`),
  CONSTRAINT `FKdpobsmms9p0tickf6c28tii3m` FOREIGN KEY (`body_id`) REFERENCES `user_body` (`id`),
  CONSTRAINT `FKe0c8xhil93k1tbd686d02okhg` FOREIGN KEY (`hair_id`) REFERENCES `user_hair` (`id`),
  CONSTRAINT `FKimo1y2hvj8alpk5l96s26ntu8` FOREIGN KEY (`size_id`) REFERENCES `user_size` (`id`),
  CONSTRAINT `FKl4pul1wr0drscio5me6s5poj5` FOREIGN KEY (`skincolor_id`) REFERENCES `user_skincolor` (`id`),
  CONSTRAINT `FKqed0c70hr4itqclrw8m0dynnk` FOREIGN KEY (`pricerange_id`) REFERENCES `user_pricerange` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_detail 的数据：~0 rows (大约)
DELETE FROM `user_detail`;
/*!40000 ALTER TABLE `user_detail` DISABLE KEYS */;
INSERT INTO `user_detail` (`id`, `update_date`, `body_id`, `face_id`, `frequency_id`, `hair_id`, `hierarchy_id`, `inclination_id`, `post_day_id`, `skin_color_id`, `user_id`, `address`, `city`, `height`, `weight`, `size_id`, `blood_id`, `constellation_id`, `skincolor_id`, `pricerange_id`, `updateDate`, `postDay_id`) VALUES
	(10, '2017-02-20 23:33:47', 2, 3, NULL, 3, NULL, 2, NULL, NULL, 11, '沈阳市彰武县彰武镇', '上海', 175, 0, 4, 1, 9, 4, 2, NULL, NULL);
/*!40000 ALTER TABLE `user_detail` ENABLE KEYS */;


-- 导出  表 isz.user_face 结构
CREATE TABLE IF NOT EXISTS `user_face` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `face_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_face 的数据：~6 rows (大约)
DELETE FROM `user_face`;
/*!40000 ALTER TABLE `user_face` DISABLE KEYS */;
INSERT INTO `user_face` (`id`, `face_name`) VALUES
	(1, '圆形'),
	(2, '椭圆形'),
	(3, '长形'),
	(4, '标准'),
	(5, '心形'),
	(6, '梨形'),
	(7, '方形');
/*!40000 ALTER TABLE `user_face` ENABLE KEYS */;


-- 导出  表 isz.user_frequency 结构
CREATE TABLE IF NOT EXISTS `user_frequency` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `frequency_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_frequency 的数据：~0 rows (大约)
DELETE FROM `user_frequency`;
/*!40000 ALTER TABLE `user_frequency` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_frequency` ENABLE KEYS */;


-- 导出  表 isz.user_hair 结构
CREATE TABLE IF NOT EXISTS `user_hair` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hair_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_hair 的数据：~8 rows (大约)
DELETE FROM `user_hair`;
/*!40000 ALTER TABLE `user_hair` DISABLE KEYS */;
INSERT INTO `user_hair` (`id`, `hair_name`) VALUES
	(1, '短直发'),
	(2, '短卷发'),
	(3, '波波头'),
	(4, '刘海短发'),
	(5, '刘海长发'),
	(6, '马尾辫'),
	(7, '丸子头'),
	(8, '长直头');
/*!40000 ALTER TABLE `user_hair` ENABLE KEYS */;


-- 导出  表 isz.user_hierarchy 结构
CREATE TABLE IF NOT EXISTS `user_hierarchy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hierarchy_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_hierarchy 的数据：~0 rows (大约)
DELETE FROM `user_hierarchy`;
/*!40000 ALTER TABLE `user_hierarchy` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_hierarchy` ENABLE KEYS */;


-- 导出  表 isz.user_inclination 结构
CREATE TABLE IF NOT EXISTS `user_inclination` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inclination_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_inclination 的数据：~13 rows (大约)
DELETE FROM `user_inclination`;
/*!40000 ALTER TABLE `user_inclination` DISABLE KEYS */;
INSERT INTO `user_inclination` (`id`, `inclination_name`) VALUES
	(1, '日系'),
	(2, '韩系'),
	(3, '欧美'),
	(4, '中国风'),
	(5, '嘻哈'),
	(6, '朋克'),
	(7, '休闲'),
	(8, '正装'),
	(9, '舒适'),
	(10, '性感'),
	(11, '优雅'),
	(12, '年轻'),
	(13, '成熟');
/*!40000 ALTER TABLE `user_inclination` ENABLE KEYS */;


-- 导出  表 isz.user_postday 结构
CREATE TABLE IF NOT EXISTS `user_postday` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `postday_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_postday 的数据：~0 rows (大约)
DELETE FROM `user_postday`;
/*!40000 ALTER TABLE `user_postday` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_postday` ENABLE KEYS */;


-- 导出  表 isz.user_pricerange 结构
CREATE TABLE IF NOT EXISTS `user_pricerange` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pricerange_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_pricerange 的数据：~6 rows (大约)
DELETE FROM `user_pricerange`;
/*!40000 ALTER TABLE `user_pricerange` DISABLE KEYS */;
INSERT INTO `user_pricerange` (`id`, `pricerange_name`) VALUES
	(1, '200元以内'),
	(2, '200~500元'),
	(3, '500~800元'),
	(4, '800~1200元'),
	(5, '1200~1500元'),
	(6, '1500~2000元'),
	(7, '2000元以上');
/*!40000 ALTER TABLE `user_pricerange` ENABLE KEYS */;


-- 导出  表 isz.user_size 结构
CREATE TABLE IF NOT EXISTS `user_size` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `size_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_size 的数据：~6 rows (大约)
DELETE FROM `user_size`;
/*!40000 ALTER TABLE `user_size` DISABLE KEYS */;
INSERT INTO `user_size` (`id`, `size_name`) VALUES
	(1, 'XS'),
	(2, 'S'),
	(3, 'M'),
	(4, 'L'),
	(5, 'XL'),
	(6, 'XXL');
/*!40000 ALTER TABLE `user_size` ENABLE KEYS */;


-- 导出  表 isz.user_skincolor 结构
CREATE TABLE IF NOT EXISTS `user_skincolor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `skincolor_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 正在导出表  isz.user_skincolor 的数据：~6 rows (大约)
DELETE FROM `user_skincolor`;
/*!40000 ALTER TABLE `user_skincolor` DISABLE KEYS */;
INSERT INTO `user_skincolor` (`id`, `skincolor_name`) VALUES
	(1, '亮白'),
	(2, '粉白'),
	(3, '标准'),
	(4, '自然黄'),
	(5, '自然红'),
	(6, '小麦色'),
	(7, '古铜色');
/*!40000 ALTER TABLE `user_skincolor` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
