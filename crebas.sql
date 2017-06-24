/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : crebas

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2017-06-23 09:53:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admininfo
-- ----------------------------
DROP TABLE IF EXISTS `admininfo`;
CREATE TABLE `admininfo` (
  `admin_id` int(11) NOT NULL,
  `admin_name` varchar(255) DEFAULT NULL,
  `admin_login_pwd` varchar(255) DEFAULT NULL,
  `admin_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admininfo
-- ----------------------------
INSERT INTO `admininfo` VALUES ('101', '刘', '101', '12232');

-- ----------------------------
-- Table structure for attendanceinfo
-- ----------------------------
DROP TABLE IF EXISTS `attendanceinfo`;
CREATE TABLE `attendanceinfo` (
  `attendance_id` int(11) NOT NULL,
  `class_id` int(11) DEFAULT NULL,
  `record_time` varchar(110) DEFAULT NULL,
  PRIMARY KEY (`attendance_id`),
  KEY `FK_Relationship_5` (`class_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attendanceinfo
-- ----------------------------

-- ----------------------------
-- Table structure for attendancerecord
-- ----------------------------
DROP TABLE IF EXISTS `attendancerecord`;
CREATE TABLE `attendancerecord` (
  `attendanceRecord_id` int(11) NOT NULL,
  `student_id` int(11) DEFAULT NULL,
  `attendance_id` int(11) DEFAULT NULL,
  `attendance_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`attendanceRecord_id`),
  KEY `FK_Relationship_3` (`student_id`),
  KEY `FK_Relationship_7` (`attendance_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attendancerecord
-- ----------------------------
INSERT INTO `attendancerecord` VALUES ('13111', '2015214038', '123', '迟到');

-- ----------------------------
-- Table structure for classinfo
-- ----------------------------
DROP TABLE IF EXISTS `classinfo`;
CREATE TABLE `classinfo` (
  `class_id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `course_name` varchar(100) DEFAULT NULL,
  `class_start_week` int(11) DEFAULT NULL,
  `class_end_week` int(11) DEFAULT NULL,
  `class_time` varchar(100) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`class_id`),
  KEY `FK_Relationship_6` (`teacher_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classinfo
-- ----------------------------
INSERT INTO `classinfo` VALUES ('13111505', '1', '电子竞技', '1', '20', '68', '100');
INSERT INTO `classinfo` VALUES ('13111506', '2', '经济学', '2', '22', '98', '101');

-- ----------------------------
-- Table structure for classstudentinfo
-- ----------------------------
DROP TABLE IF EXISTS `classstudentinfo`;
CREATE TABLE `classstudentinfo` (
  `classStu_id` int(11) NOT NULL,
  `class_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`classStu_id`),
  KEY `FK_Relationship_2` (`student_id`),
  KEY `FK_Relationship_8` (`class_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classstudentinfo
-- ----------------------------
INSERT INTO `classstudentinfo` VALUES ('2015214038', '101', '2015214038');

-- ----------------------------
-- Table structure for collegeinfo
-- ----------------------------
DROP TABLE IF EXISTS `collegeinfo`;
CREATE TABLE `collegeinfo` (
  `college_name` varchar(100) NOT NULL,
  `college_address` varchar(100) DEFAULT NULL,
  `college_contact` varchar(100) DEFAULT NULL,
  `college_tel` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`college_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collegeinfo
-- ----------------------------
INSERT INTO `collegeinfo` VALUES ('重庆邮电大学', '重邮', '学习', '1123');
INSERT INTO `collegeinfo` VALUES ('重庆医科大学', '重医', '学习', '123');
INSERT INTO `collegeinfo` VALUES ('重庆工商大学', '22', '222', '2222');

-- ----------------------------
-- Table structure for facultyinfo
-- ----------------------------
DROP TABLE IF EXISTS `facultyinfo`;
CREATE TABLE `facultyinfo` (
  `faculty_name` varchar(100) NOT NULL,
  `faculty_address` varchar(100) DEFAULT NULL,
  `faculty_contact` varchar(100) DEFAULT NULL,
  `faculty_tel` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`faculty_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of facultyinfo
-- ----------------------------
INSERT INTO `facultyinfo` VALUES ('前端', '软件工程学院', 'ADC', '110');
INSERT INTO `facultyinfo` VALUES ('后端', '计算机学院', 'nin', '200');
INSERT INTO `facultyinfo` VALUES ('终端', '传媒艺术学院', 'ADCV', '300');

-- ----------------------------
-- Table structure for studentinfo
-- ----------------------------
DROP TABLE IF EXISTS `studentinfo`;
CREATE TABLE `studentinfo` (
  `student_id` int(11) NOT NULL,
  `student_name` varchar(100) DEFAULT NULL,
  `student_login_pwd` varchar(100) DEFAULT NULL,
  `student_college` varchar(100) DEFAULT NULL,
  `student_major` varchar(100) DEFAULT NULL,
  `student_email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  KEY `FK_Relationship_9` (`student_college`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of studentinfo
-- ----------------------------
INSERT INTO `studentinfo` VALUES ('2015214038', '黄卿怡', '123', '重庆邮电大学', '前端', '297947067');
INSERT INTO `studentinfo` VALUES ('123456', '小红', '123', '重庆邮电大学', '艺术', '664646');
INSERT INTO `studentinfo` VALUES ('1234567', '小篮', '123', '重庆医科大学', '经济', '232323');

-- ----------------------------
-- Table structure for teacherinfo
-- ----------------------------
DROP TABLE IF EXISTS `teacherinfo`;
CREATE TABLE `teacherinfo` (
  `teacher_id` int(11) NOT NULL,
  `teacher_name` varchar(100) DEFAULT NULL,
  `teacher_login_pwd` varchar(100) DEFAULT NULL,
  `college_name` varchar(100) DEFAULT NULL,
  `faculty_name` varchar(100) DEFAULT NULL,
  `teacher_email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`teacher_id`),
  KEY `FK_Relationship_1` (`college_name`),
  KEY `FK_Relationship_4` (`faculty_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacherinfo
-- ----------------------------
INSERT INTO `teacherinfo` VALUES ('100', '刘俊', '123', '重庆邮电大学', '前端', '297947');
INSERT INTO `teacherinfo` VALUES ('101', '张老师', '123', '重庆医科大学', '后端', '62546');
INSERT INTO `teacherinfo` VALUES ('102', '李老师', '123', '重庆邮电大学', '前端', '2435235');
INSERT INTO `teacherinfo` VALUES ('103', '罗老师', '123', '重庆工商大学', '后端', '2232323');
