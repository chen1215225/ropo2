/*
 Navicat Premium Data Transfer

 Source Server         : 钚氪测试环境
 Source Server Type    : MySQL
 Source Server Version : 100310
 Source Host           : 10.110.77.170:3306
 Source Schema         : bees_tops

 Target Server Type    : MySQL
 Target Server Version : 100310
 File Encoding         : 65001

 Date: 14/01/2019 08:43:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for Bit_Signal_Config
-- ----------------------------
DROP TABLE IF EXISTS `Bit_Signal_Config`;
CREATE TABLE `Bit_Signal_Config` (
  `Id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Parent_Name` varchar(20) NOT NULL COMMENT '组信号',
  `Key` varchar(20) NOT NULL COMMENT '实际信号',
  `PLC_Address` varchar(20) NOT NULL COMMENT 'PLC地址',
  `Remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `Val` int(32) NOT NULL COMMENT '位值',
  `Create_Time` datetime DEFAULT NULL COMMENT '时间时间',
  `Last_Update_Time` datetime DEFAULT NULL,
  `Enable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UK_Key_Signal` (`Parent_Name`,`Key`)
) ENGINE=InnoDB AUTO_INCREMENT=724 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
