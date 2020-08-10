/*
Navicat MySQL Data Transfer

Source Server         : 151-mysql
Source Server Version : 50730
Source Host           : 192.168.1.151:3306
Source Database       : smartwf-hm

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-08-06 08:56:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for fault_code_base
-- ----------------------------
DROP TABLE IF EXISTS `fault_code_base`;
CREATE TABLE `fault_code_base` (
  `id` varchar(36) COLLATE utf8_croatian_ci NOT NULL COMMENT '主键',
  `model` varchar(50) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '风电机组模型号',
  `protocol_no` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '协议号',
  `iec_path` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT 'IEC路径',
  `fault_code` varchar(50) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '故障代码',
  `fault_name` varchar(250) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '故障名称',
  `eng_name` varchar(250) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '英文名称',
  `component_name` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '部件名称',
  `pms_am_id` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '资产型号ID',
  `pms_ai_id` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '风机部件ID',
  `remark` varchar(500) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '备注',
  `enable` tinyint(4) unsigned zerofill DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '状态 0默认  1审核通过',
  `checker` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '审批人',
  `check_time` datetime DEFAULT NULL COMMENT '审批时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '创建人姓名',
  `tenant_domain` varchar(50) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '租户域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci COMMENT='风机故障代码表';

-- ----------------------------
-- Records of fault_code_base
-- ----------------------------

-- ----------------------------
-- Table structure for fault_file_record
-- ----------------------------
DROP TABLE IF EXISTS `fault_file_record`;
CREATE TABLE `fault_file_record` (
  `id` varchar(36) COLLATE utf8_croatian_ci NOT NULL COMMENT '主键',
  `pid` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '报警码\r\n            ',
  `file_path` varchar(500) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '报警源',
  `remark` varchar(500) COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL,
  `tenant_domain` varchar(50) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '租户域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

-- ----------------------------
-- Records of fault_file_record
-- ----------------------------
INSERT INTO `fault_file_record` VALUES ('00ce1e0bd59fabaa57cdcb9539970e09', '7e4b756f1f3b96becd76324efd278fb4', 'images\\1595234393262.jpg', null, '2020-07-20 16:39:53', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('086f41fe04dcdb28f1fc878d1928a895', 'ecda3a9612217c1d9559b49709517862', 'images\\1595234285468.jpg', null, '2020-07-20 16:38:06', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('0eb658eb80de90c646d456128e847c7a', '242766fa2e3d53c1ae2129ebed2ee6b6', 'images\\1595230922234.jpg', null, '2020-07-20 15:42:02', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('0f80f6082c61d73517d654b04cc532b0', '7e4b756f1f3b96becd76324efd278fb4', 'images\\1595234393263.jpg', null, '2020-07-20 16:39:53', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('21630d7272e8bcfe28aadb92c141edf6', '463ee54442f43af3b36d38104ca07d23', 'images/1595316679074.jpg', null, '2020-07-21 15:31:19', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('2358170a1aec0d7fd1ca54b09338c10e', '03f36d22bbf0e1ac7b828de76e1f8cdf', 'images/1595227451842.jpg', null, '2020-07-20 14:44:12', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('2861b1a525906ddb8e91efe925b2e3c1', '008980f685af2a9c4e3ce39dda3f3e53', 'images/1595230002053.jpg', null, '2020-07-20 15:26:42', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('2b1d8ca8cb2528ce4c18b5786cd99668', '03f36d22bbf0e1ac7b828de76e1f8cdf', 'images/1595227451843.jpg', null, '2020-07-20 14:44:12', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('2d3e8b215a4d2d2cfbd3870738854ae4', '4be17672f1b2c84f2e24f071eb3980d8', 'images/1595229832867.jpg', null, '2020-07-20 15:23:53', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('355f43fd6680ea9ec0bf35f3913dd92d', 'f5f8d506440ba19cd1ac99098b1ff57c', 'images/1595229537624.jpg', null, '2020-07-20 15:18:58', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('379617e78b3d237f52c9186b0bfd1d63', 'ecda3a9612217c1d9559b49709517862', 'images\\1595234285466.jpg', null, '2020-07-20 16:38:06', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('54eadbab1af086c7ad4055c8fe84b999', 'fc976673717fbb0969ca331433c8cbc3', 'images\\1595234696643.jpg', null, '2020-07-20 16:44:57', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('5a4cb789b77a27fb210f95e2de2ebc91', '61a6cc05e21937a3d81a884800c35916', 'images/1595229736606.jpg', null, '2020-07-20 15:22:17', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('5e1549cc2d4b84d22eecba225bc06b9e', '870982652396f5bf3427ec300cba0670', 'images/1595313966919.jpg', null, '2020-07-21 14:46:07', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('5ef74ef5a959c1010caf2211462fd258', '7e4b756f1f3b96becd76324efd278fb4', 'images\\1595234393267.jpg', null, '2020-07-20 16:39:53', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('61bbe502612f9bf62e4bc2a3859998e9', '03f36d22bbf0e1ac7b828de76e1f8cdf', 'images/1595227451843.jpg', null, '2020-07-20 14:44:12', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('6a63b638584ae4e29f961f51d589f1e8', 'f5f8d506440ba19cd1ac99098b1ff57c', 'images/1595229537624.jpg', null, '2020-07-20 15:18:58', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('6a65362ff734d46984f287e8b114c854', '11fe845d998089cfeb026d442e85e5b5', 'images\\1595320581960.jpg', null, '2020-07-21 16:37:20', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('6a9b94ea990c8a66486c892555620380', 'ecda3a9612217c1d9559b49709517862', 'images\\1595234285464.jpg', null, '2020-07-20 16:38:06', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('724d67351a3e74b565c75014417b9b85', '870982652396f5bf3427ec300cba0670', 'images/1595313966923.jpg', null, '2020-07-21 14:46:07', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('72b0865e52e868df137412cc44358ce7', '870982652396f5bf3427ec300cba0670', 'images/1595313966923.jpg', null, '2020-07-21 14:46:07', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('75731a796aaf2b0dbc045d7948b8e93a', '4be17672f1b2c84f2e24f071eb3980d8', 'images/1595229832867.jpg', null, '2020-07-20 15:23:53', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('7c17a00d80afc853f58f950f242017e6', 'fc976673717fbb0969ca331433c8cbc3', 'images\\1595234696641.jpg', null, '2020-07-20 16:44:57', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('7cb1b5dea6c3c57f22d39ff31fdde9a1', '03f36d22bbf0e1ac7b828de76e1f8cdf', 'images/1595227451842.jpg', null, '2020-07-20 14:44:12', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('89b5af630eab65748e08ed2466a079d7', '6899b579b4c5e6376cf266a5392ceae6', 'images/1590728572778.jpg', null, '2020-05-29 13:02:53', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('9a06be9fa4e0e1b19c1bd979fba9d059', '8c6c81d056defdb661b3956f9f173a61', 'images/1595317128803.jpg', null, '2020-07-21 15:38:49', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('ac84c78d42a6cf50b94af1355a6e09a8', 'fc976673717fbb0969ca331433c8cbc3', 'images\\1595234696645.jpg', null, '2020-07-20 16:44:57', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('af7c8fee87114e6d4507214619945cf7', '03f36d22bbf0e1ac7b828de76e1f8cdf', 'images/1595227451843.jpg', null, '2020-07-20 14:44:12', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('afe9cf1664a1a0078ade45db8c6f1d33', '11fe845d998089cfeb026d442e85e5b5', 'images\\1595320604618.jpg', null, '2020-07-21 16:37:23', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('bc7085353fe1eac9738cc49cdcd2f1ca', '8c6c81d056defdb661b3956f9f173a61', 'images/1595317128803.jpg', null, '2020-07-21 15:38:49', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('be0d8df7a6d5719b4600531ee37fa500', '7e4b756f1f3b96becd76324efd278fb4', 'images\\1595234393260.jpg', null, '2020-07-20 16:39:53', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('c29aee03aaf806c5bafa282624d6940d', '7e4b756f1f3b96becd76324efd278fb4', 'images\\1595234393265.jpg', null, '2020-07-20 16:39:53', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('c6659f91c9c0ae62299b854c1e418aeb', '870982652396f5bf3427ec300cba0670', 'images/1595313966924.jpg', null, '2020-07-21 14:46:07', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('ca833e08ac47aa93bdf93c9bcf2f1e18', 'fc976673717fbb0969ca331433c8cbc3', 'images\\1595234696646.jpg', null, '2020-07-20 16:44:57', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('d1b60d058e20028ac370859786f00f41', '463ee54442f43af3b36d38104ca07d23', 'images/1595316679074.jpg', null, '2020-07-21 15:31:19', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('d4922fb8183bc5e9f544396e38134ccf', '03f36d22bbf0e1ac7b828de76e1f8cdf', 'images/1595227451819.jpg', null, '2020-07-20 14:44:12', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('d58faffc63c1da6fefee013c0c3edad0', 'fc976673717fbb0969ca331433c8cbc3', 'images\\1595234696648.jpg', null, '2020-07-20 16:44:57', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('e6faf0df7239ff349bde562f7e663af1', '008980f685af2a9c4e3ce39dda3f3e53', 'images/1595230002052.jpg', null, '2020-07-20 15:26:42', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('e8a1418981938bb0a27033a66a870c84', '61a6cc05e21937a3d81a884800c35916', 'images/1595229736605.jpg', null, '2020-07-20 15:22:17', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('e8b5ab1084269343a5cc80de4ce4843e', '03f36d22bbf0e1ac7b828de76e1f8cdf', 'images/1595227451843.jpg', null, '2020-07-20 14:44:12', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('ede12a512e8cb74d67d2b7cb9ae3aea2', '242766fa2e3d53c1ae2129ebed2ee6b6', 'images\\1595230922236.jpg', null, '2020-07-20 15:42:02', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('efe8a9b41c0153c22efb306a3258b162', '8c6c81d056defdb661b3956f9f173a61', 'images/1595317128803.jpg', null, '2020-07-21 15:38:49', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('f2df9f1feab22c001e21d2758f712a80', 'ecda3a9612217c1d9559b49709517862', 'images\\1595234285472.jpg', null, '2020-07-20 16:38:06', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('fbfe94ad10cafa3c21681611fa835ccd', 'ecda3a9612217c1d9559b49709517862', 'images\\1595234285469.jpg', null, '2020-07-20 16:38:06', null, 'carbon.super');
INSERT INTO `fault_file_record` VALUES ('fc702901b57e7dd1bc27942df452546d', '2cde7e1216c321649e8ca9bb7ce4e427', 'images/1590729471420.jpg', null, '2020-05-29 13:17:51', null, 'carbon.super');

-- ----------------------------
-- Table structure for fault_information
-- ----------------------------
DROP TABLE IF EXISTS `fault_information`;
CREATE TABLE `fault_information` (
  `id` varchar(36) COLLATE utf8_croatian_ci NOT NULL COMMENT '主键',
  `alarm_code` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '报警码\r\n            ',
  `alarm_source` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '报警源',
  `alarm_description` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '报警描述',
  `alarm_location` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '报警部位',
  `start_time` datetime DEFAULT NULL COMMENT '故障起始时间',
  `end_time` datetime DEFAULT NULL COMMENT '故障结束时间',
  `incident_type` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `alarm_level` tinyint(4) DEFAULT NULL COMMENT '报警级别\r\n0红：危急\r\n1橙：严重\r\n2紫：一般\r\n3灰：未知\r\n            ',
  `fault_type` tinyint(4) DEFAULT NULL COMMENT '故障类型\r\n0-系统报警\r\n1-预警信息\r\n2-人工提交  ',
  `manufacturers` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '厂家',
  `device_code` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '设备编码',
  `device_name` varchar(50) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '设备名称',
  `wind_farm` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '风场',
  `asset_number` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL,
  `order_number` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL,
  `alarm_status` tinyint(4) DEFAULT NULL COMMENT '故障状态\r\n5待审核\r\n6驳回\r\n0未处理\r\n1已转工单\r\n2处理中\r\n3已处理\r\n4已关闭\r\n7回收站\r\n8未解决\r\n',
  `operating_status` tinyint(4) DEFAULT '0' COMMENT '操作状态\r\n0默认\r\n1重点关注 ',
  `discoverer_id` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `discoverer_name` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `remark` text COLLATE utf8_croatian_ci,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_user_name` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_user_name` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL,
  `tenant_domain` varchar(50) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '租户表id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci COMMENT='故障报警表';

-- ----------------------------
-- Records of fault_information
-- ----------------------------
INSERT INTO `fault_information` VALUES ('004e76ca65e290ee56e0b76c3280ec98', 'aaa', 'N1塔基础（MF01）', '测试004e76ca65e290ee56e0b76c3280ec98', 'N1塔基础（MF01）', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '2', '1', '2', null, null, 'N1塔基础（MF01）', null, 'XXFDFDDFDFFDD', null, '0', '0', 'rO5H0oUmEeqeLJAyS4CWlg', '郑爽', null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('008980f685af2a9c4e3ce39dda3f3e53', null, 'N1塔基础（MF01）', 'last dance008980f685af2a9c4e3ce39dda3f3e53', 'N1塔基础（MF01）', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '2', '2', '2', null, null, 'N1塔基础（MF01）', null, 'XXFDFDDFDFFDD', null, '0', '0', '04PSkoUkEeqAM5AyS4CWlg,04PSkIUkEeq2h5AyS4CWlg', '李桂英,王超', null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('03f36d22bbf0e1ac7b828de76e1f8cdf', 'aaa', '风电机组', 'API 测试缺陷上报03f36d22bbf0e1ac7b828de76e1f8cdf', 'N1塔基础（MF01）', '2020-08-04 02:34:55', '2020-08-04 02:34:55', '1', '0', '2', null, null, '升压站2', null, 'CDBdlF6mT4KqhueXnKOSxg', null, '0', '0', 'A5R4AYUkEeqYp5AyS4CWlg', '李杰', '', '2020-08-04 02:27:01', '1', '平台管理员', '2020-08-04 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('1', '507.0', 'A01风机', '1#WP4084通道1振动超限（2级）1', '齿轮箱', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '1', '0', '1', '1', '升压站2', '1', 'CDBdlF6mT4KqhueXnKOSxg', null, '4', '0', null, null, '测试数据', '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 13:34:41', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('11', '507.0', 'A01风机', '1#WP4084通道1振动超限（2级）11', '齿轮箱', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '1', '0', '1', '1', '升压站2', '1', 'CDBdlF6mT4KqhueXnKOSxg', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('11fe845d998089cfeb026d442e85e5b5', null, '测试上报缺陷用-四级资产', '测试图片上传11fe845d998089cfeb026d442e85e5b5', '测试上报缺陷用-四级资产', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '2', '0', '2', null, null, '测试上报缺陷用-四级资产', null, 'XXFDFDDFDFFDD', null, '0', '0', 'A5R4AYUkEeqYp5AyS4CWlg', '李杰', null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('1211', '194.0', 'A13风机', '变频器紧停1211', '发电机', '2020-07-09 00:00:00', '2020-07-09 00:00:00', '1', '2', '1', '1', '1', '升压站2', '1', 'CDBdlF6mT4KqhueXnKOSxg', null, '0', '0', null, null, null, '2020-07-09 00:00:00', '1', '平台管理员', '2020-07-09 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('1212', '194.0', 'A13风机', '变频器紧停1212', '发电机', '2020-07-09 00:00:00', '2020-07-09 00:00:00', '1', '1', '1', '1', '1', '升压站2', '1', 'CDBdlF6mT4KqhueXnKOSxg', null, '0', '0', null, null, null, '2020-07-09 00:00:00', '1', '平台管理员', '2020-07-09 00:00:00', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('1213', '194.0', 'A13风机', '变频器紧停1213', '发电机', '2020-07-09 00:00:00', '2020-07-09 00:00:00', '1', '21', '1', '1', '1', '升压站2', '1', 'CDBdlF6mT4KqhueXnKOSxg', null, '0', '0', null, null, '关闭测试', '2020-07-09 00:00:00', '1', '平台管理员', '2020-07-09 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('1214', '194.0', 'A13风机', '变频器紧停1214', '发电机', '2020-07-09 00:00:00', '2020-07-09 00:00:00', '1', '2', '1', '1', '1', '升压站2', '1', 'CDBdlF6mT4KqhueXnKOSxg', null, '0', '0', null, null, null, '2020-07-09 00:00:00', '1', '平台管理员', '2020-07-09 00:00:00', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('1215', '194.0', 'A13风机', '变频器紧停1215', '发电机', '2020-07-09 00:00:00', '2020-07-09 00:00:00', '1', '1', '1', '1', '1', '升压站2', '1', 'CDBdlF6mT4KqhueXnKOSxg', null, '0', '0', null, null, null, '2020-07-09 00:00:00', '1', '平台管理员', '2020-07-09 00:00:00', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('1216', '194.0', 'A13风机', '变频器紧停1216', '发电机', '2020-07-09 00:00:00', '2020-07-09 00:00:00', '1', '2', '1', '1', '1', '测试升压站', '1', 'Z/o626wiTqO+gYUZjON3+w', '202007044', '0', '0', null, null, null, '2020-07-09 00:00:00', '1', '平台管理员', '2020-07-09 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('1217', '194.0', 'A13风机', '变频器紧停1217', '发电机', '2020-07-09 00:00:00', '2020-07-09 00:00:00', '1', '2', '1', '1', '1', '测试升压站', '1', 'Z/o626wiTqO+gYUZjON3+w', null, '0', '0', null, null, null, '2020-07-09 00:00:00', '1', '平台管理员', '2020-07-09 00:00:00', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('1218', '194.0', 'A13风机', '变频器紧停1218', '发电机', '2020-07-09 00:00:00', '2020-07-09 00:00:00', '1', '2', '1', '1', '1', '测试升压站', '1', 'Z/o626wiTqO+gYUZjON3+w', null, '0', '0', null, null, null, '2020-07-09 00:00:00', '1', '平台管理员', '2020-07-09 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('1219', '194.0', 'A13风机', '变频器紧停1219', '发电机', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '2', '1', '1', '1', '测试升压站', '1', 'Z/o626wiTqO+gYUZjON3+w', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('13', '204.0', 'A06风机', '变桨系统轴1驱动器level1故障13', '发电机', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '0', '1', '1', '1', '风电机组', '1', 'idV9Il0tQQKUFm+8Xq/W2w', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('15', '151.0', 'A37风机', '变频器非指令脱网15', '变频器', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '3', '1', '1', '1', '风电机组', '1', 'idV9Il0tQQKUFm+8Xq/W2w', '2.02007045E8', '3', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 13:47:34', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('16', '281.0', 'A09风机', '偏航马达总保护16', '偏航系统', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '2', '3', '0', '1', '1', null, '1', 'XXFDFDDFDFFDD', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('17', '507.0', 'A01风机', '1#WP4084通道1振动超限（2级）17', '齿轮箱', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '1', '0', '1', '1', '风电机组', '1', 'idV9Il0tQQKUFm+8Xq/W2w', null, '2', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 13:56:37', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('19', '204.0', 'A06风机', '变桨系统轴1驱动器level1故障19', '发电机', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '0', '1', '1', '1', '风电机组', '1', 'idV9Il0tQQKUFm+8Xq/W2w', null, '0', '0', null, null, '', '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 13:05:22', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('20', '111.0', 'A11风机', '机舱消防系统230VAC电源故障20', '变桨系统', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '2', '0', '2', '1', '1', null, '1', 'XXFDFDDFDFFDD', null, '0', '0', null, null, null, '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('21', '151.0', 'A37风机', '变频器非指令脱网21', '变频器', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '1', '0', '1', '1', '1', '风电机组', '1', 'idV9Il0tQQKUFm+8Xq/W2w', null, '0', '0', null, null, null, '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('22', '281.0', 'A09风机', '偏航马达总保护22', '偏航系统', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '1', '3', '0', '1', '1', '风电机组', '1', 'idV9Il0tQQKUFm+8Xq/W2w', null, '0', '1', null, null, null, '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('23', '507.0', 'A01风机', '1#WP4084通道1振动超限（2级）23', '齿轮箱', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '2', '1', '0', '1', '1', null, '1', 'XXFDFDDFDFFDD', null, '0', '0', null, null, null, '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('242766fa2e3d53c1ae2129ebed2ee6b6', null, 'N1塔基础（MF01）', 'dddd242766fa2e3d53c1ae2129ebed2ee6b6', 'N1塔基础（MF01）', '2020-08-04 02:34:55', '2020-08-04 02:34:55', '2', '1', '2', null, null, 'N1塔基础（MF01）', null, 'XXFDFDDFDFFDD', null, '0', '0', 'A5R4AYUkEeqYp5AyS4CWlg,A5R4A4UkEeqtgJAyS4CWlg', '李杰,张强', null, '2020-08-04 02:27:01', '1', '平台管理员', '2020-08-04 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('246b581d4437bd247b1727a575ede43b', 'aaa', '风电机组', 'k8s测试数据246b581d4437bd247b1727a575ede43b', 'N1塔基础（MF01）', '2020-08-04 02:34:55', '2020-08-04 02:34:55', '1', '1', '2', null, null, '箱变', null, 'C/AEtJ33R5SAMeAcRJHQ9Q', null, '0', '0', 'rO5H0oUmEeqeLJAyS4CWlg', '郑爽', '在关闭一个', '2020-08-04 02:27:01', '1', '平台管理员', '2020-08-04 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('25', '204.0', 'A06风机', '变桨系统轴1驱动器level1故障25', '发电机', '2020-08-04 02:34:55', '2020-08-04 02:34:55', '1', '0', '1', '1', '1', '箱变', '1', 'C/AEtJ33R5SAMeAcRJHQ9Q', null, '0', '0', null, null, null, '2020-08-04 02:27:01', '1', '平台管理员', '2020-08-04 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('26', '111.0', 'A11风机', '机舱消防系统230VAC电源故障26', '变桨系统', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '3', '2', '1', '1', '箱变', '1', 'C/AEtJ33R5SAMeAcRJHQ9Q', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('27', '151.0', 'A37风机', '变频器非指令脱网27', '变频器', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '3', '1', '1', '1', '箱变', '1', 'C/AEtJ33R5SAMeAcRJHQ9Q', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('28', '281.0', 'A09风机', '偏航马达总保护28', '偏航系统', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '3', '0', '1', '1', '箱变', '1', 'C/AEtJ33R5SAMeAcRJHQ9Q', null, '0', '0', null, null, '关闭它，解说', '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 13:05:25', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('29', '507.0', 'A01风机', '1#WP4084通道1振动超限（2级）29', '齿轮箱', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '1', '0', '1', '1', '箱变', '1', 'C/AEtJ33R5SAMeAcRJHQ9Q', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('2cde7e1216c321649e8ca9bb7ce4e427', 'aaa', 'N1塔基础（MF01）', '测试22cde7e1216c321649e8ca9bb7ce4e427', 'N1塔基础（MF01）', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '2', '1', '2', null, null, 'N1塔基础（MF01）', null, 'XXFDFDDFDFFDD', null, '0', '0', 'rO5H0oUmEeqeLJAyS4CWlg', '郑爽', null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('3', '204.0', 'A06风机', '变桨系统轴1驱动器level1故障3', '发电机', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '0', '1', '1', '1', '箱变', '1', 'C/AEtJ33R5SAMeAcRJHQ9Q', null, '4', '0', null, null, '', '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 13:34:43', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('31', '204.0', 'A06风机', '变桨系统轴1驱动器level1故障31', '发电机', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '2', '0', '1', '1', '1', null, '1', 'XXFDFDDFDFFDD', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('32', '111.0', 'A11风机', '机舱消防系统230VAC电源故障32', '变桨系统', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '3', '2', '1', '1', '1号主集线电路（11BAA）', '1', '3HABV/UpST63230s7b3WAA', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 13:05:24', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('33', '151.0', 'A37风机', '变频器非指令脱网33', '变频器', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '1', '3', '1', '1', '1', '1号主集线电路（11BAA）', '1', '3HABV/UpST63230s7b3WAA', null, '0', '0', null, null, '测试处理记录', '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('34', '281.0', 'A09风机', '偏航马达总保护34', '偏航系统', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '1', '3', '0', '1', '1', '1号主集线电路（11BAA）', '1', '3HABV/UpST63230s7b3WAA', null, '0', '0', null, null, '', '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('35', '507.0', 'A01风机', '1#WP4084通道1振动超限（2级）35', '齿轮箱', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '2', '1', '2', '1', '1', null, '1', 'XXFDFDDFDFFDD', null, '0', '0', null, null, null, '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('37', '204.0', 'A06风机', '变桨系统轴1驱动器level1故障37', '发电机', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '0', '1', '1', '1', '1号主集线电路（11BAA）', '1', '3HABV/UpST63230s7b3WAA', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 13:05:27', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('38', '111.0', 'A11风机', '机舱消防系统230VAC电源故障38', '变桨系统', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '2', '3', '2', '1', '1', null, '1', 'XXFDFDDFDFFDD', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('39', '151.0', 'A37风机', '变频器非指令脱网39', '变频器', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '2', '1', '1', '1', '1号主集线电路（11BAA）', '1', '3HABV/UpST63230s7b3WAA', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 13:05:18', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('4', '111.0', 'A11风机', '机舱消防系统230VAC电源故障4', '变桨系统', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '2', '3', '2', '1', '1', null, '1', 'XXFDFDDFDFFDD', null, '2', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 14:21:28', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('40', '281.0', 'A09风机', '偏航马达总保护40', '偏航系统', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '1', '2', '0', '1', '1', '1号主集线电路（11BAA）', '1', '3HABV/UpST63230s7b3WAA', null, '0', '0', null, null, null, '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('41', '111.0', 'A11风机', '机舱消防系统230VAC电源故障41', '变桨系统', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '2', '3', '2', '1', '1', null, '1', 'XXFDFDDFDFFDD', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('411', '507.0', 'A01风机', '1#WP4084通道1振动超限（2级）411', '齿轮箱', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '1', '1', '0', '1', '1', '1号主集线电路（11BAA）', '1', '3HABV/UpST63230s7b3WAA', null, '0', '0', null, null, null, '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('43', '204.0', 'A06风机', '变桨系统轴1驱动器level1故障43', '发电机', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '1', '0', '1', '1', '1', '1号主集线电路（11BAA）', '1', '3HABV/UpST63230s7b3WAA', null, '0', '0', null, null, '测试处理记录', '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('44', '111.0', 'A11风机', '机舱消防系统230VAC电源故障44', '变桨系统', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '1', '3', '2', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('45', '151.0', 'A37风机', '变频器非指令脱网45', '变频器', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '1', '3', '1', '1', 'equ001', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', '202007042', '0', '0', null, null, null, '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('46', '281.0', 'A09风机', '偏航马达总保护46', '偏航系统', '2020-07-11 00:00:00', '2020-07-11 00:00:00', '1', '3', '0', '1', 'equ004', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-07-11 00:00:00', '1', '平台管理员', '2020-07-11 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('463ee54442f43af3b36d38104ca07d23', null, '测试上报缺陷用-四级资产', '测试用户信息463ee54442f43af3b36d38104ca07d23', '测试上报缺陷用-四级资产', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '2', '0', '2', null, null, '测试上报缺陷用-四级资产', null, 'XXFDFDDFDFFDD', null, '0', '0', 'rO5H1IUmEeqILZAyS4CWlg,rO5H1oUmEeq865AyS4CWlg', '赵英明,唐国庆', null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('47', '507.0', 'A01风机', '1#WP4084通道1振动超限（2级）47', '齿轮箱', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '1', '0', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('49', '204.0', 'A06风机', '变桨系统轴1驱动器level1故障49', '发电机', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '0', '1', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('5', '151.0', 'A37风机', '变频器非指令脱网5', '变频器', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '3', '1', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '2', '0', null, null, null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 14:47:42', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('50', '111.0', 'A11风机', '机舱消防系统230VAC电源故障50', '变桨系统', '2020-07-13 00:00:00', '2020-07-13 00:00:00', '1', '2', '2', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-07-13 00:00:00', '1', '平台管理员', '2020-07-13 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('51', '151.0', 'A37风机', '变频器非指令脱网51', '变频器', '2020-07-13 00:00:00', '2020-07-13 00:00:00', '1', '2', '1', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-07-13 00:00:00', '1', '平台管理员', '2020-07-13 00:00:00', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('52', '281.0', 'A09风机', '偏航马达总保护52', '偏航系统', '2020-07-13 00:00:00', '2020-07-13 00:00:00', '1', '3', '0', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-07-13 00:00:00', '1', '平台管理员', '2020-07-13 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('53', '507.0', 'A01风机', '1#WP4084通道1振动超限（2级）53', '齿轮箱', '2020-07-13 00:00:00', '2020-07-13 00:00:00', '1', '1', '0', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-07-13 00:00:00', '1', '平台管理员', '2020-07-13 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('55', '204.0', 'A06风机', '变桨系统轴1驱动器level1故障55', '发电机', '2020-07-13 00:00:00', '2020-07-13 00:00:00', '1', '0', '1', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-07-13 00:00:00', '1', '平台管理员', '2020-07-13 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('56', '111.0', 'A11风机', '机舱消防系统230VAC电源故障56', '变桨系统', '2020-07-13 00:00:00', '2020-07-13 00:00:00', '1', '2', '2', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-07-13 00:00:00', '1', '平台管理员', '2020-07-13 00:00:00', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('57', '151.0', 'A37风机', '变频器非指令脱网57', '变频器', '2020-07-13 00:00:00', '2020-07-13 00:00:00', '1', '2', '1', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-07-13 00:00:00', '1', '平台管理员', '2020-07-13 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('58', '281.0', 'A09风机', '偏航马达总保护58', '偏航系统', '2020-07-13 00:00:00', '2020-07-13 00:00:00', '1', '2', '0', '1', '1', '1101号箱式变压器（1 1***01）', '1', 'NEjbnDpCRoqdyQE5T6ggPQ', null, '0', '0', null, null, null, '2020-07-13 00:00:00', '1', '平台管理员', '2020-07-13 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('5f5f1b894c4ec0a4ac445457adf4c81b', 'aaa', '风电机组', '一般问题5f5f1b894c4ec0a4ac445457adf4c81b', '风机变桨电机1（-M01）', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '2', '2', null, null, '风机变桨电机1（-M01）', null, 'dML8F64ZQuO6EciWoJECIA', null, '0', '0', 'rO5H0oUmEeqeLJAyS4CWlg', '郑爽', null, '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 02:27:01', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('6', '281.0', 'A09风机', '偏航马达总保护6', '偏航系统', '2020-08-05 02:34:55', '2020-08-05 02:34:55', '1', '3', '0', '1', '1', '35KV线1A分集线电路（11BAA01）', '1', 'dML8F64ZQuO6EciWoJECIA', null, '0', '0', null, null, '测试操作流程', '2020-08-05 02:27:01', '1', '平台管理员', '2020-08-05 11:37:09', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('6899b579b4c5e6376cf266a5392ceae6', 'aaa', 'N1塔基础（MF01）', '测试26899b579b4c5e6376cf266a5392ceae6', 'N1塔基础（MF01）', '2020-07-17 00:00:00', '2020-07-17 00:00:00', '2', '1', '2', null, null, 'N1塔基础（MF01）', null, 'XXFDFDDFDFFDD', null, '0', '0', 'rO5H0oUmEeqeLJAyS4CWlg', '郑爽', null, '2020-07-17 00:00:00', '1', '平台管理员', '2020-07-17 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('7837b3a5d09feb4c283e3187ec4e913d', 'aaa', '风机部件1', 'gfghf7837b3a5d09feb4c283e3187ec4e913d', '风机部件1', '2020-07-17 00:00:00', '2020-07-17 00:00:00', '2', '1', '2', null, null, '风机部件1', null, 'XXFDFDDFDFFDD', null, '0', '0', 'rO5H0oUmEeqeLJAyS4CWlg', '郑爽', null, '2020-07-17 00:00:00', '1', '平台管理员', '2020-07-17 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('7e4b756f1f3b96becd76324efd278fb4', null, 'N1塔基础（MF01）', '我是一个很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的描述7e4b756f1f3b96becd76324efd278fb4', 'N1塔基础（MF01）', '2020-07-17 00:00:00', '2020-07-17 00:00:00', '2', '1', '2', null, null, 'N1塔基础（MF01）', null, 'XXFDFDDFDFFDD', null, '0', '0', 'A5R4AIUkEeqZgpAyS4CWlg,04PSkIUkEeq2h5AyS4CWlg', '刘伟,王超', null, '2020-07-17 00:00:00', '1', '平台管理员', '2020-07-17 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('870982652396f5bf3427ec300cba0670', null, '测试上报缺陷用-四级资产', '杨磊-测试上报故障-001870982652396f5bf3427ec300cba0670', '测试上报缺陷用-四级资产', '2020-07-17 00:00:00', '2020-07-17 00:00:00', '2', '1', '2', null, null, '测试上报缺陷用-四级资产', null, 'XXFDFDDFDFFDD', null, '0', '0', 'A5R4AIUkEeqZgpAyS4CWlg,04PSkYUkEeqjHpAyS4CWlg', '刘伟,李丽 ', null, '2020-07-17 00:00:00', '1', '平台管理员', '2020-07-17 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('8c6c81d056defdb661b3956f9f173a61', null, '测试上报缺陷用-四级资产', '测试用户信息8c6c81d056defdb661b3956f9f173a61', '测试上报缺陷用-四级资产', '2020-07-17 00:00:00', '2020-07-17 00:00:00', '2', '0', '2', null, null, '测试上报缺陷用-四级资产', null, 'XXFDFDDFDFFDD', null, '0', '0', 'rO5H1IUmEeqILZAyS4CWlg,rO5H1oUmEeq865AyS4CWlg', '赵英明,唐国庆', null, '2020-07-17 00:00:00', '1', '平台管理员', '2020-07-17 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('b6691f759f82c785cb5254ca6b710c1f', 'aaa', 'N1塔基础（MF01）', '测试2b6691f759f82c785cb5254ca6b710c1f', 'N1塔基础（MF01）', '2020-07-17 00:00:00', '2020-07-17 00:00:00', '2', '1', '2', null, null, 'N1塔基础（MF01）', null, 'XXFDFDDFDFFDD', null, '0', '0', 'rO5H0oUmEeqeLJAyS4CWlg', '郑爽', null, '2020-07-17 00:00:00', '1', '平台管理员', '2020-07-17 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('bd841820ff7765e6ece6962f319ea057', 'aaa', 'N1塔基础（MF01）', '测试bd841820ff7765e6ece6962f319ea057', 'N1塔基础（MF01）', '2020-07-17 00:00:00', '2020-07-17 00:00:00', '2', '1', '2', null, null, 'N1塔基础（MF01）', null, 'XXFDFDDFDFFDD', null, '0', '0', 'rO5H0oUmEeqeLJAyS4CWlg', '郑爽', null, '2020-07-17 00:00:00', '1', '平台管理员', '2020-07-17 00:00:00', '1', '平台管理员', 'tianliwindpower.com.cn');
INSERT INTO `fault_information` VALUES ('ecda3a9612217c1d9559b49709517862', null, 'N1塔基础（MF01）', '测试图片地址ecda3a9612217c1d9559b49709517862', 'N1塔基础（MF01）', '2020-07-17 00:00:00', '2020-07-17 00:00:00', '2', '1', '2', null, null, 'N1塔基础（MF01）', null, 'XXFDFDDFDFFDD', null, '0', '0', 'A5R4AIUkEeqZgpAyS4CWlg,04PSkIUkEeq2h5AyS4CWlg', '刘伟,王超', null, '2020-07-17 00:00:00', '1', '平台管理员', '2020-07-17 00:00:00', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_information` VALUES ('fc976673717fbb0969ca331433c8cbc3', null, '风电机组', '我是一个很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的描述fc976673717fbb0969ca331433c8cbc3', 'N1塔基础（MF01）', '2020-07-17 00:00:00', '2020-07-17 00:00:00', '1', '1', '2', null, null, '35KV线1A分集线电路（11BAA01）', null, 'dML8F64ZQuO6EciWoJECIA', null, '0', '0', 'A5R4AIUkEeqZgpAyS4CWlg,04PSkIUkEeq2h5AyS4CWlg', '刘伟,王超', '', '2020-07-17 00:00:00', '1', '平台管理员', '2020-07-17 00:00:00', '1', '平台管理员', 'carbon.super');

-- ----------------------------
-- Table structure for fault_operation_record
-- ----------------------------
DROP TABLE IF EXISTS `fault_operation_record`;
CREATE TABLE `fault_operation_record` (
  `id` varchar(36) COLLATE utf8_croatian_ci NOT NULL COMMENT '主键',
  `fault_info_id` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '故障主键',
  `closure_status` tinyint(4) DEFAULT NULL COMMENT '操作状态\r\n5待审核\r\n6驳回\r\n0未处理\r\n1已转工单\r\n2处理中\r\n3已处理\r\n4已关闭\r\n7回收站\r\n8未解决',
  `closure_reason` varchar(1000) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '关闭原因',
  `closure_type` tinyint(4) DEFAULT NULL,
  `remark` varchar(1000) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_user_name` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '创建人',
  `tenant_domain` varchar(50) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '租户域',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci ROW_FORMAT=DYNAMIC COMMENT='故障操作记录表';

-- ----------------------------
-- Records of fault_operation_record
-- ----------------------------
INSERT INTO `fault_operation_record` VALUES ('0d3c9de0105c1922939ecb9a14dcd805', '5', '2', '已转工单，在处理中', '1', null, '2020-08-05 14:47:42', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_operation_record` VALUES ('5423dffb52d9b80dccbfcc308ca33a8d', '4', '2', '已转工单，在处理中', '1', null, '2020-08-05 14:21:28', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_operation_record` VALUES ('6073cb323ef6b1d1e354ee545c8948e6', '3', null, '是否超过标准上下限？', '2', null, '2020-08-05 13:08:59', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_operation_record` VALUES ('64e604751462c60602de440bbb8845e6', '3', '4', '误报', '1', '', '2020-08-05 13:34:43', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_operation_record` VALUES ('6e698a283e6a34ffa8df1323ce57f8cd', '17', '2', '已转工单，在处理中', '1', null, '2020-08-05 13:56:37', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_operation_record` VALUES ('88f322f7f9c834e66345eddde09d55f9', '1', '4', '格式不符规范,重复消息', '1', '测试数据', '2020-08-05 13:07:58', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_operation_record` VALUES ('a41d8a76f214dc3b74ed84a1f616d34f', '15', '2', '已转工单，在处理中', '1', null, '2020-08-05 13:47:34', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_operation_record` VALUES ('a82bf3a3a6122c27136d91de2c23edfa', '15', '3', '已处理', '1', null, '2020-08-05 13:55:57', '1', '平台管理员', 'carbon.super');
INSERT INTO `fault_operation_record` VALUES ('f7803bb039c4644b3476af0fecbeecc2', '3', null, '该故障触发机制是什么？', '2', null, '2020-08-05 13:08:38', '1', '平台管理员', 'carbon.super');

-- ----------------------------
-- Table structure for fault_solution_info
-- ----------------------------
DROP TABLE IF EXISTS `fault_solution_info`;
CREATE TABLE `fault_solution_info` (
  `id` varchar(36) COLLATE utf8_croatian_ci NOT NULL COMMENT '主键',
  `fault_code` varchar(50) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '故障代码表id',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型\r\n            1触发条件\r\n            2故障原因\r\n            3故障处理',
  `content` varchar(1000) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '内容',
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `remark` varchar(500) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '备注',
  `enable` tinyint(4) unsigned zerofill DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '状态 0默认  1审核通过',
  `checker` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '审批人',
  `check_time` datetime DEFAULT NULL COMMENT '审批时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(25) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '创建人姓名',
  `tenant_domain` varchar(50) COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '租户域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci COMMENT='故障解决方案';

-- ----------------------------
-- Records of fault_solution_info
-- ----------------------------

-- ----------------------------
-- Table structure for key_position
-- ----------------------------
DROP TABLE IF EXISTS `key_position`;
CREATE TABLE `key_position` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `device_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '设备编码',
  `device_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '设备名称',
  `asset_number` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '资产编码',
  `status` tinyint(4) DEFAULT '0' COMMENT '0-运行  1-停止',
  `remark` varchar(250) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(32) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人姓名',
  `tenant_domain` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '租户域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='重点机位表';

-- ----------------------------
-- Records of key_position
-- ----------------------------
INSERT INTO `key_position` VALUES ('053118759f4b39beb00c2bbb25017cda', '', '升压站2', 'CDBdlF6mT4KqhueXnKOSxg', '0', null, '2020-08-05 15:11:32', null, null, 'carbon.super');
INSERT INTO `key_position` VALUES ('24f741b8624c2cdba4907490eebf2e43', '', '测试升压站', 'Z/o626wiTqO+gYUZjON3+w', '0', null, '2020-08-05 15:09:43', null, null, 'carbon.super');
INSERT INTO `key_position` VALUES ('302cca9882f8ce9587e88836478d9f81', '', '风电机组', 'idV9Il0tQQKUFm+8Xq/W2w', '0', null, '2020-07-31 15:16:15', null, null, 'carbon.super');
INSERT INTO `key_position` VALUES ('30f773baaaca6a7b7d0ff13caf0c9a32', '', '箱变', 'C/AEtJ33R5SAMeAcRJHQ9Q', '0', null, '2020-08-05 15:11:27', null, null, 'carbon.super');
INSERT INTO `key_position` VALUES ('75c8f97a7b5c508035875da161097c5d', '', '1号主集线电路（11BAA）', '3HABV/UpST63230s7b3WAA', '0', null, '2020-08-05 15:10:15', null, null, 'carbon.super');
INSERT INTO `key_position` VALUES ('a28db7d4ea735789da1108a4d552834c', '', '1101号箱式变压器（1 1***01）', 'NEjbnDpCRoqdyQE5T6ggPQ', '0', null, '2020-08-05 15:09:59', null, null, 'carbon.super');
INSERT INTO `key_position` VALUES ('bd9287d0f29ce6e6262c5854d830f141', '', 'jianzhuces', 'YYgh7fVXSrWDOOIj9FjPhw', '0', null, '2020-08-05 15:10:11', null, null, 'carbon.super');
INSERT INTO `key_position` VALUES ('caf9ec8e982e76792669eac608aa7d0d', '', '35KV线1A分集线电路（11BAA01）', 'dML8F64ZQuO6EciWoJECIA', '0', null, '2020-08-05 15:11:52', null, null, 'carbon.super');
INSERT INTO `key_position` VALUES ('e696893b5e6820518a78ecdf643546af', '', 'yanglei测试顶级分类', 'iOD3XnIHRsOPJkH1q6WWHQ', '0', null, '2020-08-05 15:11:23', null, null, 'carbon.super');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_url` varchar(50) DEFAULT NULL,
  `log_user` varchar(25) DEFAULT NULL,
  `log_content` varchar(255) DEFAULT NULL,
  `log_json` varchar(500) DEFAULT NULL,
  `opration_time` datetime DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `result` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', '/user/updateSysUser', null, '修改系统用户', '\"{\\\"id\\\":1,\\\"name\\\":\\\"系统管理员\\\"}\"', '2019-10-25 15:55:08', '192.168.3.48', '200');
INSERT INTO `sys_log` VALUES ('2', '/user/updateSysUser', null, '修改系统用户', '\"{\\\"id\\\":1,\\\"name\\\":\\\"系统管理员\\\"}\"', '2019-10-25 15:55:11', '192.168.3.48', '200');

-- ----------------------------
-- Table structure for USER
-- ----------------------------
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名',
  `age` int(10) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

-- ----------------------------
-- Records of USER
-- ----------------------------
INSERT INTO `USER` VALUES ('16', 'a', '1');
INSERT INTO `USER` VALUES ('17', 'b', '2');
INSERT INTO `USER` VALUES ('18', 'c', '3');
INSERT INTO `USER` VALUES ('19', 'd', '4');
INSERT INTO `USER` VALUES ('20', 'e', '5');
INSERT INTO `USER` VALUES ('21', 'a', '1');
INSERT INTO `USER` VALUES ('22', 'b', '2');
INSERT INTO `USER` VALUES ('23', 'c', '3');
INSERT INTO `USER` VALUES ('24', 'd', '4');
INSERT INTO `USER` VALUES ('25', 'e', '5');
INSERT INTO `USER` VALUES ('26', 'a', '1');
INSERT INTO `USER` VALUES ('27', 'b', '2');
INSERT INTO `USER` VALUES ('28', 'c', '3');
INSERT INTO `USER` VALUES ('29', 'd', '4');
INSERT INTO `USER` VALUES ('30', 'e', '5');
INSERT INTO `USER` VALUES ('31', 'a', '1');
INSERT INTO `USER` VALUES ('32', 'b', '2');
INSERT INTO `USER` VALUES ('33', 'c', '3');
INSERT INTO `USER` VALUES ('34', 'd', '4');
INSERT INTO `USER` VALUES ('35', 'e', '5');
INSERT INTO `USER` VALUES ('36', 'a', '1');
INSERT INTO `USER` VALUES ('37', 'b', '2');
INSERT INTO `USER` VALUES ('38', 'c', '3');
INSERT INTO `USER` VALUES ('39', 'd', '4');
INSERT INTO `USER` VALUES ('40', 'e', '5');
INSERT INTO `USER` VALUES ('41', 'a', '1');
INSERT INTO `USER` VALUES ('42', 'b', '2');
INSERT INTO `USER` VALUES ('43', 'c', '3');
INSERT INTO `USER` VALUES ('44', 'd', '4');
INSERT INTO `USER` VALUES ('45', 'e', '5');
INSERT INTO `USER` VALUES ('46', 'a', '1');
INSERT INTO `USER` VALUES ('47', 'b', '2');
INSERT INTO `USER` VALUES ('48', 'c', '3');
INSERT INTO `USER` VALUES ('49', 'd', '4');
INSERT INTO `USER` VALUES ('50', 'e', '5');
INSERT INTO `USER` VALUES ('51', 'a', '1');
INSERT INTO `USER` VALUES ('52', 'b', '2');
INSERT INTO `USER` VALUES ('53', 'c', '3');
INSERT INTO `USER` VALUES ('54', 'd', '4');
INSERT INTO `USER` VALUES ('55', 'e', '5');
INSERT INTO `USER` VALUES ('56', 'a', '1');
INSERT INTO `USER` VALUES ('57', 'b', '2');
INSERT INTO `USER` VALUES ('58', 'c', '3');
INSERT INTO `USER` VALUES ('59', 'd', '4');
INSERT INTO `USER` VALUES ('60', 'e', '5');
INSERT INTO `USER` VALUES ('61', 'a', '1');
INSERT INTO `USER` VALUES ('62', 'b', '2');
INSERT INTO `USER` VALUES ('63', 'c', '3');
INSERT INTO `USER` VALUES ('64', 'd', '4');
INSERT INTO `USER` VALUES ('65', 'e', '5');
INSERT INTO `USER` VALUES ('66', 'a', '1');
INSERT INTO `USER` VALUES ('67', 'b', '2');
INSERT INTO `USER` VALUES ('68', 'c', '3');
INSERT INTO `USER` VALUES ('69', 'd', '4');
INSERT INTO `USER` VALUES ('70', 'e', '5');
INSERT INTO `USER` VALUES ('71', 'a', '1');
INSERT INTO `USER` VALUES ('72', 'b', '2');
INSERT INTO `USER` VALUES ('73', 'c', '3');
INSERT INTO `USER` VALUES ('74', 'd', '4');
INSERT INTO `USER` VALUES ('75', 'e', '5');
INSERT INTO `USER` VALUES ('76', 'a', '1');
INSERT INTO `USER` VALUES ('77', 'b', '2');
INSERT INTO `USER` VALUES ('78', 'c', '3');
INSERT INTO `USER` VALUES ('79', 'd', '4');
INSERT INTO `USER` VALUES ('80', 'e', '5');
INSERT INTO `USER` VALUES ('81', 'a', '1');
INSERT INTO `USER` VALUES ('82', 'b', '2');
INSERT INTO `USER` VALUES ('83', 'c', '3');
INSERT INTO `USER` VALUES ('84', 'd', '4');
INSERT INTO `USER` VALUES ('85', 'e', '5');
INSERT INTO `USER` VALUES ('86', 'a', '1');
INSERT INTO `USER` VALUES ('87', 'a', '1');
INSERT INTO `USER` VALUES ('88', 'b', '2');
INSERT INTO `USER` VALUES ('89', 'b', '2');
INSERT INTO `USER` VALUES ('90', 'c', '3');
INSERT INTO `USER` VALUES ('91', 'd', '4');
INSERT INTO `USER` VALUES ('92', 'c', '3');
INSERT INTO `USER` VALUES ('93', 'd', '4');
INSERT INTO `USER` VALUES ('94', 'e', '5');
INSERT INTO `USER` VALUES ('95', 'e', '5');
INSERT INTO `USER` VALUES ('96', 'a', '1');
INSERT INTO `USER` VALUES ('97', 'b', '2');
INSERT INTO `USER` VALUES ('98', 'c', '3');
INSERT INTO `USER` VALUES ('99', 'd', '4');
INSERT INTO `USER` VALUES ('100', 'e', '5');
INSERT INTO `USER` VALUES ('101', 'a', '1');
INSERT INTO `USER` VALUES ('102', 'b', '2');
INSERT INTO `USER` VALUES ('103', 'c', '3');
INSERT INTO `USER` VALUES ('104', 'd', '4');
INSERT INTO `USER` VALUES ('105', 'e', '5');
INSERT INTO `USER` VALUES ('106', 'a', '1');
INSERT INTO `USER` VALUES ('107', 'b', '2');
INSERT INTO `USER` VALUES ('108', 'c', '3');
INSERT INTO `USER` VALUES ('109', 'd', '4');
INSERT INTO `USER` VALUES ('110', 'e', '5');
INSERT INTO `USER` VALUES ('111', 'a', '1');
INSERT INTO `USER` VALUES ('112', 'b', '2');
INSERT INTO `USER` VALUES ('113', 'c', '3');
INSERT INTO `USER` VALUES ('114', 'd', '4');
INSERT INTO `USER` VALUES ('115', 'e', '5');
INSERT INTO `USER` VALUES ('116', 'a', '1');
INSERT INTO `USER` VALUES ('117', 'b', '2');
INSERT INTO `USER` VALUES ('118', 'c', '3');
INSERT INTO `USER` VALUES ('119', 'd', '4');
INSERT INTO `USER` VALUES ('120', 'e', '5');
INSERT INTO `USER` VALUES ('121', 'a', '1');
INSERT INTO `USER` VALUES ('122', 'b', '2');
INSERT INTO `USER` VALUES ('123', 'c', '3');
INSERT INTO `USER` VALUES ('124', 'd', '4');
INSERT INTO `USER` VALUES ('125', 'e', '5');
INSERT INTO `USER` VALUES ('126', 'a', '1');
INSERT INTO `USER` VALUES ('127', 'b', '2');
INSERT INTO `USER` VALUES ('128', 'c', '3');
INSERT INTO `USER` VALUES ('129', 'd', '4');
INSERT INTO `USER` VALUES ('130', 'e', '5');
INSERT INTO `USER` VALUES ('131', 'a', '1');
INSERT INTO `USER` VALUES ('132', 'b', '2');
INSERT INTO `USER` VALUES ('133', 'c', '3');
INSERT INTO `USER` VALUES ('134', 'd', '4');
INSERT INTO `USER` VALUES ('135', 'e', '5');
INSERT INTO `USER` VALUES ('136', 'a', '1');
INSERT INTO `USER` VALUES ('137', 'b', '2');
INSERT INTO `USER` VALUES ('138', 'c', '3');
INSERT INTO `USER` VALUES ('139', 'd', '4');
INSERT INTO `USER` VALUES ('140', 'e', '5');
INSERT INTO `USER` VALUES ('141', 'a', '1');
INSERT INTO `USER` VALUES ('142', 'b', '2');
INSERT INTO `USER` VALUES ('143', 'c', '3');
INSERT INTO `USER` VALUES ('144', 'd', '4');
INSERT INTO `USER` VALUES ('145', 'e', '5');
INSERT INTO `USER` VALUES ('146', 'a', '1');
INSERT INTO `USER` VALUES ('147', 'b', '2');
INSERT INTO `USER` VALUES ('148', 'c', '3');
INSERT INTO `USER` VALUES ('149', 'd', '4');
INSERT INTO `USER` VALUES ('150', 'e', '5');
INSERT INTO `USER` VALUES ('151', 'a', '1');
INSERT INTO `USER` VALUES ('152', 'b', '2');
INSERT INTO `USER` VALUES ('153', 'c', '3');
INSERT INTO `USER` VALUES ('154', 'd', '4');
INSERT INTO `USER` VALUES ('155', 'e', '5');
