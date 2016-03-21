/*
Navicat MySQL Data Transfer

Source Server         : lanqiuba
Source Server Version : 50173
Source Host           : 121.42.186.170:3306
Source Database       : lanqiu_man

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2016-03-22 01:09:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for play_ground
-- ----------------------------
DROP TABLE IF EXISTS `play_ground`;
CREATE TABLE `play_ground` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '状态 0：请求失败；1：请求成功',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `location` varchar(255) DEFAULT NULL COMMENT '经纬度 , 隔开',
  `tel` varchar(100) DEFAULT NULL,
  `pcode` varchar(50) DEFAULT NULL COMMENT 'poi所在省份编码',
  `pname` varchar(100) DEFAULT NULL COMMENT 'poi所在省份名称',
  `citycode` varchar(50) DEFAULT NULL COMMENT '城市编码',
  `cityname` varchar(100) DEFAULT NULL COMMENT '城市名',
  `adcode` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `adname` varchar(100) DEFAULT NULL COMMENT '区域名称',
  `parking_type` varchar(100) DEFAULT NULL COMMENT '停车场类型展示停车场类型，包括：地下、地面、路边',
  `indoor_map` varchar(100) DEFAULT NULL COMMENT '是否有室内地图标志indoor_map=1表示有室内地图',
  `feature` varchar(100) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
