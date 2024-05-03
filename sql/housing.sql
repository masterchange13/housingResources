/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : localhost:3306
 Source Schema         : community

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 27/04/2024 23:53:45
*/

DROP DATABASE IF EXISTS housing;
CREATE DATABASE housing;
USE housing;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type` int(11) NOT NULL COMMENT '1 ',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '小区名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `idn` bigint(20) NOT NULL COMMENT '编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '小区' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (8888, 1, '12号小区', '2023-12-18 09:32:02', '2023-12-19 21:09:07', 1, 1, 3);
INSERT INTO `category` VALUES (22501, 1, '1号小区', '2021-05-27 09:16:58', '2023-09-06 10:54:45', 1, 1, 3);
INSERT INTO `category` VALUES (22502, 1, '2号小区', '2021-05-27 09:17:07', '2023-12-18 12:11:06', 1, 1, 14);
INSERT INTO `category` VALUES (22503, 1, '3号小区', '2021-05-27 09:17:28', '2021-07-09 14:37:13', 1, 1, 5);
INSERT INTO `category` VALUES (22504, 1, '4号小区', '2021-07-09 11:36:15', '2021-07-09 14:39:15', 1, 1, 6);
INSERT INTO `category` VALUES (22505, 1, '5号小区', '2023-09-06 20:43:04', '2023-09-06 20:47:42', 1, 1, 7);
INSERT INTO `category` VALUES (22506, 1, '6号小区', '2023-12-10 16:08:02', '2023-12-10 16:08:02', 1, 1, 8);
INSERT INTO `category` VALUES (22507, 1, '7号小区', '2023-12-17 19:25:11', '2023-12-17 19:25:14', 1, 1, 9);
INSERT INTO `category` VALUES (22508, 1, '8号小区', '2023-12-17 19:28:49', '2023-12-17 19:28:47', 1, 1, 10);
INSERT INTO `category` VALUES (1737097959035609090, 1, '大撒大撒', '2023-12-19 21:10:02', '2023-12-19 21:10:02', 1, 1, 55555);
INSERT INTO `category` VALUES (1742348441524178945, 1, 'dddddd', '2024-01-03 08:53:34', '2024-01-03 08:53:34', 1736411167747706881, 1736411167747706881, 55);

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '房屋号',
  `category_id` bigint(20) NOT NULL COMMENT '小区id',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '房租',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '图片',
  `description` varchar(400) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '0 停售 1 起售',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_dish_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '房屋' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of house
-- ----------------------------
INSERT INTO `house` VALUES (1397850392090947585, '1-3', 22503, 8800.00, '123412341234', '2.png', '三室两厅两卫', 1, '2021-05-27 09:41:19', 1, 0, '2023-12-16 22:02:16', 0);
INSERT INTO `house` VALUES (1397850851245600769, '1-4', 22503, 12800.00, '123412341234', '3.png', '三室两厅两卫', 1, '2021-05-27 09:43:08', 1, 0, '2023-12-16 22:02:21', 0);
INSERT INTO `house` VALUES (1397851099502260226, '1-5', 22503, 11800.00, '23412341234', '4.png', '三室两厅两卫', 1, '2021-05-27 09:44:08', 1, 0, '2023-12-16 22:02:29', 0);
INSERT INTO `house` VALUES (1397851370462687234, '1-6', 22504, 13800.00, '1246812345678', '5.png', '三室两厅两卫', 1, '2021-05-27 09:45:12', 1, 0, '2023-12-16 22:02:32', 0);
INSERT INTO `house` VALUES (1397851668262465537, '2-1', 22506, 16800.00, '1234567812345678', '6.png', '三室两厅两卫', 1, '2021-05-27 09:46:23', 1, 0, '2023-12-16 22:02:34', 0);
INSERT INTO `house` VALUES (1397852391150759938, '2-2', 22504, 8800.00, '2346812468', '7.png', '三室两厅两卫', 1, '2021-05-27 09:49:16', 1, 0, '2023-12-16 22:02:37', 0);
INSERT INTO `house` VALUES (1397853183287013378, '2-3', 22508, 19800.00, '123456787654321', '8.png', '三室两厅两卫', 1, '2021-05-27 09:52:24', 1, 0, '2023-12-16 22:02:40', 0);
INSERT INTO `house` VALUES (1397853709101740034, '2-4', 22504, 9800.00, '1234321234321', '9.png', '三室两厅两卫', 1, '2021-05-27 09:54:30', 1, 0, '2023-12-16 22:02:45', 0);
INSERT INTO `house` VALUES (1397853890262118402, '2-5', 22502, 3800.00, '1234212321234', '1.png', '三室两厅两卫', 1, '2021-05-27 09:55:13', 1, 0, '2023-12-16 22:02:48', 0);
INSERT INTO `house` VALUES (1397854652581064706, '2-6', 22503, 14800.00, '2345312·345321', '2.png', '三室两厅两卫', 1, '2023-12-19 21:13:19', 1, 0, '2023-12-16 22:02:51', 0);
INSERT INTO `house` VALUES (1397854865672679425, '3-1', 22506, 2000.00, '23456431·23456', '3.png', '三室两厅两卫', 1, '2021-05-27 09:59:06', 1, 0, '2023-12-16 22:02:53', 0);
INSERT INTO `house` VALUES (1397860242057375745, '3-2', 22505, 12800.00, '123456786543213456', '4.png', '三室两厅两卫', 1, '2021-05-27 10:20:27', 1, 0, '2023-12-16 22:02:56', 0);
INSERT INTO `house` VALUES (1397860578738352129, '3-3', 22506, 6600.00, '12345678654', '5.png', '三室两厅两卫', 1, '2021-05-27 10:21:48', 1, 0, '2023-12-16 22:02:58', 0);
INSERT INTO `house` VALUES (1397860792492666881, '3-4', 22502, 38800.00, '213456432123456', '6.png', '三室两厅两卫', 1, '2021-05-27 10:22:39', 1, 0, '2023-12-16 22:03:01', 0);
INSERT INTO `house` VALUES (1397860963880316929, '3-5', 22504, 10800.00, '1234563212345', '7.png', '三室两厅两卫', 1, '2021-05-27 10:23:19', 1, 0, '2023-12-16 22:03:03', 0);
INSERT INTO `house` VALUES (1397861683434139649, '3-6', 22503, 38800.00, '1234567876543213456', '8.png', '三室两厅两卫', 1, '2021-05-27 10:26:11', 1, 0, '2023-12-16 22:03:06', 0);
INSERT INTO `house` VALUES (1397862198033297410, '4-1', 22502, 49800.00, '123456786532455', '9.png', '三室两厅两卫', 1, '2021-05-27 10:28:14', 1, 0, '2023-12-16 22:03:09', 0);
INSERT INTO `house` VALUES (1397862477831122945, '4-2', 22501, 108800.00, '1234567865432', '1.png', '三室两厅两卫', 1, '2021-05-27 10:29:20', 1, 0, '2023-12-16 22:03:11', 0);
INSERT INTO `house` VALUES (1413342036832100354, '14-2', 22503, 500000.00, '', '2.png', '三室两厅两卫', 1, '2023-12-18 09:38:25', 1, 0, '2023-12-17 19:07:49', 0);
INSERT INTO `house` VALUES (1413385247889891330, '4-5', 22504, 180000.00, '', '4.png', '三室两厅两卫', 0, '2024-04-24 20:53:24', 1736411167747706881, 0, '2024-04-24 20:53:23', 0);
INSERT INTO `house` VALUES (1699439942500769793, '6-1', 22502, 800000.00, '', '5.png', '三室两厅两卫', 1, '2023-09-06 23:10:31', 1, 0, '2023-12-16 22:03:21', 0);
INSERT INTO `house` VALUES (1736002331211665410, '22-2', 22504, 222200.00, '', '6.png', '阿松大', 0, '2024-04-25 15:12:45', 1736411167747706881, 0, '2024-04-25 15:12:45', 1);
INSERT INTO `house` VALUES (1736016132266926082, '1-8', 22503, 100000.00, '', '2.png', '阿松大', 0, '2024-04-25 15:16:11', 1742601341036847106, 0, '2024-04-25 15:16:10', 1);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` bigint(255) NOT NULL COMMENT '订单号',
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单名称（房屋号）',
  `price` decimal(20, 2) NOT NULL COMMENT '租金',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '支付状态',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (442091583, '1-8', 100000.00, 1);
INSERT INTO `orders` VALUES (801150893, '4-5', 180000.00, 0);
INSERT INTO `orders` VALUES (1246700070, '22-2', 222200.00, 0);
INSERT INTO `orders` VALUES (2084574268, '22-2', 222200.00, 1);

-- ----------------------------
-- Table structure for parking
-- ----------------------------
DROP TABLE IF EXISTS `parking`;
CREATE TABLE `parking`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `category_id` bigint(20) NOT NULL COMMENT '小区id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '车位名称',
  `price` decimal(10, 2) NOT NULL COMMENT '车位租金',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态 0:停用 1:启用',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编码',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_setmeal_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '车位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parking
-- ----------------------------
INSERT INTO `parking` VALUES (1735998028753326082, 22501, '3334', 500000.00, 1, '', '阿萨大大', '10.png', '2023-12-16 20:19:18', '2023-12-18 09:40:41', 1, 1, 0);
INSERT INTO `parking` VALUES (1737098938585948161, 22507, '2222', 500000.00, 1, '', '好车位', '10.png', '2023-12-19 21:13:55', '2023-12-19 21:13:55', 1, 1, 0);
INSERT INTO `parking` VALUES (1737102196394110977, 22506, '255585', 8777700.00, 1, '', '沥青', '10.png', '2023-12-19 21:26:52', '2023-12-19 21:26:52', 1, 1, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NULL DEFAULT 0 COMMENT '创建人',
  `update_user` bigint(20) NULL DEFAULT 0 COMMENT '修改人',
  `purview` bigint(20) NOT NULL COMMENT '权限：房东/租客',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '住户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'ddr', '123456', '18762147581', '1', '320811211308131510', 1, '2024-01-04 08:22:30', '2024-01-04 08:22:33', 0, 0, 1);
INSERT INTO `user` VALUES (1736411167747706881, '大大', '123456', '18761259365', '0', '320811211308131510', 1, '2023-12-17 23:40:58', '2023-12-18 11:49:28', 1, 1, 1);
INSERT INTO `user` VALUES (1737103974758035457, '沙阳洋', '123456', '18762159367', '1', '320811200303031120', 1, '2023-12-19 21:33:56', '2023-12-19 21:33:56', 1, 1, 0);
INSERT INTO `user` VALUES (1742601341036847106, '徐梦圆', '123456', '18762159355', '1', '320811200308031510', 1, '2024-01-04 01:38:30', '2024-04-25 10:29:40', 1736411167747706881, 1736411167747706881, 0);
INSERT INTO `user` VALUES (1742613753106468865, '十大', '123456', '18762159367', '1', '320811200308031510', 1, '2024-01-04 02:27:50', '2024-01-04 02:27:50', NULL, NULL, 0);
INSERT INTO `user` VALUES (1742613899777085442, '十大', '123456', '18762159367', '1', '320811200308031510', 1, '2024-01-04 02:28:25', '2024-01-04 02:28:25', NULL, NULL, 0);
INSERT INTO `user` VALUES (1742614551534153729, '萨达', '123456', '18762159367', '1', '320811200308031510', 1, '2024-01-04 02:31:00', '2024-01-04 02:31:00', NULL, NULL, 0);
INSERT INTO `user` VALUES (1742699001827803137, '沙阳洋顶顶顶', '123456', '18762159367', '1', '320811200308031510', 1, '2024-01-04 08:06:34', '2024-01-04 08:06:34', NULL, NULL, 0);
INSERT INTO `user` VALUES (1742699244577316866, '撒打算', '123456', '18762159367', '1', '320811200308031510', 1, '2024-01-04 08:07:32', '2024-01-04 08:07:32', NULL, NULL, 0);
INSERT INTO `user` VALUES (1742699577118502913, '打算大苏打', '123456', '18762159367', '1', '320811200308031510', 1, '2024-01-04 08:08:52', '2024-01-04 08:08:52', NULL, NULL, 0);
INSERT INTO `user` VALUES (1742727082877587458, 'dsdadddd', '123456', '18711111111', '1', '380211200308031510', 1, '2024-01-04 09:58:10', '2024-01-04 09:58:10', 1736411167747706881, 1736411167747706881, 0);
INSERT INTO `user` VALUES (1783393487352115201, 'shayyy', 'yangyang2003', '18762159367', '1', '320811200308031510', 1, '2024-04-25 15:11:56', '2024-04-25 15:11:56', 1736411167747706881, 1736411167747706881, 0);

SET FOREIGN_KEY_CHECKS = 1;
