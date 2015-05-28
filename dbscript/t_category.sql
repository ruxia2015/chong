/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : chong

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2015-05-28 13:47:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_category`
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Category` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

