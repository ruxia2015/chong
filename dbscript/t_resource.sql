/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : chong

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2015-05-28 13:47:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` varchar(256) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `type` varchar(4) DEFAULT NULL,
  `accessState` varchar(2) DEFAULT NULL COMMENT ' -1 等待检测   1 可以访问  2  不可以访问',
  `registerState` varchar(2) DEFAULT NULL COMMENT ' -1 等待    1 可以注册  2 不可以注册',
  `otherState` varchar(2) DEFAULT NULL COMMENT '其他状态',
  `PR` varchar(3) DEFAULT NULL,
  `categoryId` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

