/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.19 : Database - supplychainfinance
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`supplychainfinance` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `supplychainfinance`;

/*Table structure for table `login_user` */

DROP TABLE IF EXISTS `login_user`;

CREATE TABLE `login_user` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(30) DEFAULT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT '1' COMMENT '乐观锁',
  `role` int(11) DEFAULT '1' COMMENT '角色',
  `verify` varchar(45) DEFAULT '未认证' COMMENT '认证状态',
  `info` varchar(50) DEFAULT NULL COMMENT '图片信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `login_user` */

insert  into `login_user`(`id`,`username`,`password`,`email`,`create_time`,`update_time`,`version`,`role`,`verify`,`info`) values 
('007abdd0a5714698b7b6ab45524c8848','Tiangang building materials Co., Ltd','123456','4623264645@qq.com','2021-06-19','2021-06-19',1,3,'未认证',NULL),
('1c0cfc7054e948d3ad628321469419e7','Alibaba Group','123456','623254654465@qq.com','2021-06-19','2021-06-19',1,4,'未认证',NULL),
('2914216d303842bc8a12fe11f831c410','Snowflake building materials company','123456','4623264645@qq.com','2021-06-19','2021-06-19',1,3,'未认证',NULL),
('32eb397288c241aeb4985fd1037e3796','Organic chemistry Limited','123456','98464652665@qq.com','2021-06-19','2021-06-19',1,3,'未认证',NULL),
('38e27229b59444b8acb54dacbf3ef4b1','ICBC','123456','9449167275@qq.com','2021-06-19','2021-06-19',1,2,'未认证',NULL),
('3e1b97d85ff74ae4af4871eab0a8bf2b','Weilai Automobile Co., Ltd','123456','9491616665@qq.com','2021-06-19','2021-06-19',1,3,'未认证',NULL),
('4ba552f18ef04a0b8176e0de04287229','China Construction Bank','123456','879416516275@qq.com','2021-06-19','2021-06-19',1,2,'未认证',NULL),
('4e0f1608c3eb4284b3162f3a083c65b1','HSBC Group','123456','6463294465@qq.com','2021-06-19','2021-06-19',1,4,'未认证',NULL),
('67d7d44c26eb4448a06fa91d9dae1f63','Xiaolonggang development company','123456','4665456465@qq.com','2021-06-19','2021-06-19',1,3,'未认证',NULL),
('71144203dd634e12b809369605c2f23c','Futian cotton Co., Ltd','123456','659416265@qq.com','2021-06-19','2021-06-19',1,3,'未认证',NULL),
('7ed335ffecc54dcf8f4567bf392347b7','Unparalleled Materials Co., Ltd','123456','98032659665@qq.com','2021-06-19','2021-06-19',1,3,'未认证',NULL),
('b2ddf14dca314e2d882bfb3aff990cdf','the Agricultural Bank of China','123456','9712312375@qq.com','2021-06-19','2021-06-19',1,2,'未认证',NULL),
('be7a8e6f468c4cce9e0c392197f45f13','Tencent group','123456','496556465@qq.com','2021-06-19','2021-06-19',1,4,'未认证',NULL),
('c8d6278cc98f4252a9924ea6ea6b4867','Flying real estate company','123456','4665456465@qq.com','2021-06-19','2021-06-19',1,3,'未认证',NULL),
('ca2c5af4cf7645d7812c4207c40bcec7','Supply chain finance block chain architecture group','sanfashi','2963624004@qq.com','2021-05-10','2021-05-10',1,1,'已通过',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
