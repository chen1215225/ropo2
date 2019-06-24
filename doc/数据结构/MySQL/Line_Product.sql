/*
 Navicat Premium Data Transfer

 Source Server         : 钚氪测试
 Source Server Type    : MariaDB
 Source Server Version : 100310
 Source Host           : 10.110.77.170:3306
 Source Schema         : bees_tops

 Target Server Type    : MariaDB
 Target Server Version : 100310
 File Encoding         : 65001

 Date: 24/03/2019 12:31:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for Line_Product
-- ----------------------------
DROP TABLE IF EXISTS `Line_Product`;
CREATE TABLE `Line_Product` (
  `Id` int(11) NOT NULL COMMENT '主键',
  `On` datetime NOT NULL COMMENT '开始时间',
  `Off` datetime NOT NULL COMMENT '结束时间',
  `OEE` varchar(20) NOT NULL DEFAULT '0%' COMMENT '设备总体效率',
  `EFF` varchar(20) NOT NULL DEFAULT '0%' COMMENT '生产效率',
  `Products` bigint(20) unsigned zerofill NOT NULL COMMENT '产量',
  `Rejected` bigint(20) unsigned zerofill NOT NULL COMMENT '剔除',
  `Loss` bigint(20) unsigned zerofill NOT NULL COMMENT '损失',
  `Time` varchar(20) NOT NULL COMMENT '时间',
  `Stacker_Product` bigint(20) unsigned zerofill NOT NULL COMMENT '码垛机产量',
  `Bottle_Blowing_Product` bigint(20) unsigned zerofill NOT NULL COMMENT '吹瓶机产量',
  `Implanter_Product` bigint(20) unsigned zerofill NOT NULL COMMENT '注入机产量',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UK_ON_OFF` (`On`,`Off`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
