/*
Navicat MySQL Data Transfer

Source Server         : lanqiuba
Source Server Version : 50173
Source Host           : 121.42.186.170:3306
Source Database       : lanqiu_man

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2016-04-07 01:00:51
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_1` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_match
-- ----------------------------
DROP TABLE IF EXISTS `tb_match`;
CREATE TABLE `tb_match` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `home_team_id` bigint(20) DEFAULT NULL,
  `home_team_name` varchar(200) DEFAULT NULL,
  `guest_team_id` bigint(20) DEFAULT NULL,
  `guest_team_name` varchar(20) DEFAULT NULL,
  `home_team_score` int(20) DEFAULT NULL,
  `guest_team_score` int(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `match_time` datetime DEFAULT NULL,
  `feature` varchar(100) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_team
-- ----------------------------
DROP TABLE IF EXISTS `tb_team`;
CREATE TABLE `tb_team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `current_count` int(50) DEFAULT NULL,
  `people_count` int(50) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '球队状态：1满员，2招人',
  `lost_times` int(100) DEFAULT NULL,
  `win_times` int(100) DEFAULT NULL,
  `img_url` varchar(500) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `feature` varchar(100) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
