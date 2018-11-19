/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50554
Source Host           : localhost:3306
Source Database       : cbd

Target Server Type    : MYSQL
Target Server Version : 50554
File Encoding         : 65001

Date: 2018-10-25 00:34:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(20) NOT NULL,
  `account` varchar(30) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `department_id` varchar(30) DEFAULT NULL,
  `department_name` varchar(30) DEFAULT NULL,
  `salt` varchar(30) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
11-19
CREATE TABLE `sys_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(100) NOT NULL COMMENT '唯一的权限代码（命令方式参照controller的mapping+方法名）例如admin:user:page',
  `name` varchar(20) NOT NULL COMMENT '权限名称',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-页面菜单，1-功能操作',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 无效，1 有效，2 删除',
  `is_open` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否公开(菜单需要前端配合)（功能操作公开直接不加记录就行）',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '上级菜单ID',
  `tree_code` varchar(30) NOT NULL COMMENT 'AAABBBCCCDDD....方便搜索',
  `page_url` varchar(256) DEFAULT NULL COMMENT '前端url',
  `order_num` tinyint(4) NOT NULL COMMENT '顺序',
  `style` varchar(16) DEFAULT NULL COMMENT '前端样式',
  `rmk` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_permissions` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='功能权限信息';