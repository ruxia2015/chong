/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : chong

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2015-05-28 13:47:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_resource_tg`
-- ----------------------------
DROP TABLE IF EXISTS `t_resource_tg`;
CREATE TABLE `t_resource_tg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tgId` int(11) DEFAULT NULL,
  `resourceId` int(11) DEFAULT NULL,
  `account` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

