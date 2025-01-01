/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.35 : Database - bookstore
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bookstore` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `bookstore`;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `bid` int NOT NULL AUTO_INCREMENT,
  `bname` varchar(255) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `printer` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `store` int DEFAULT NULL,
  `sales` int DEFAULT '0',
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb3;

/*Data for the table `book` */

insert  into `book`(`bid`,`bname`,`detail`,`price`,`author`,`printer`,`date`,`type`,`image`,`store`,`sales`) values (76,'小王子','梅子涵主编，国际水准手绘插画，独一无二创意栏目：揭秘大师长成记和名著诞生记，分享同名影视动画衍生剧作，打造有趣有料、有颜有品的特色大师精选童话。','11','(法) 圣-埃克苏佩里','辽宁少年儿童出版社','2017-04-01','童话','/img/小王子.jpg',98,0),(77,'外科风云','正午阳光影业继《琅琊榜》《欢乐颂》后，又一热播医疗职场励志剧同名小说。直击中国医患矛盾的情感极致力作，温暖而明亮的职场励志小说。老干部靳东演绎的中国医疗精英。','78','朱朱','浙江文艺出版社','2017-05-01','治愈','/img/外科风云.png',2,0),(78,'从你的全世界路过','2015央视年度好书，近二十年华语小说销量奇迹，超40亿阅读，每个故事都在变成电影，每一分钟，都有人看到自己。张嘉佳献给你的心动故事！','19','张嘉佳 ','湖南文艺出版社','2013-07-01','治愈','/img/从你的全世界路过.jpg',99,0),(79,'追风筝的人','中文版，快乐大本营高圆圆感动推荐，奥巴马送给女儿的新年礼物。为你，千千万万遍！','17','[美]卡勒德·胡赛尼（Khaled Hosseini）','上海人民出版社','2006-05-01','治愈','/img/追风筝的人.jpg',99,0),(80,'解忧杂货店','《白夜行》后东野圭吾备受欢迎作品：不是推理小说，却更扣人心弦','27','(日)东野圭吾','南海出版公司','2014-05-01','小说','/img/解忧杂货店.jpg',99,0),(81,'丝绸之路：一部全新的世界史','关心国家战略，一带一路必读书','92','彼得.弗兰科潘 (Peter Frankopan)','浙江大学出版社','2016-08-17','历史','/img/丝绸之路.jpg',10,0),(82,'全球通史','从史前史到21世纪','87','斯塔夫里阿诺斯','北京大学出版社','2006-10-01','历史','/img/全球通史.jpg',50,0),(83,'月亮和六便士','(译文经典.精）','30','毛姆','上海译文出版社','2015-03-10','小说','/img/月亮和六便士.jpg',5,0),(84,'苏菲的世界','热售榜前50','23','乔斯坦.贾德','作家出版社','2007-07-07','小说','/img/苏菲的世界.jpg',77,0),(85,'史记','(精装全三册)','75','司马迁','岳麓书社','2011-07-01','历史','/img/史记.jpg',39,0),(86,'阳明心学的力量','阳明心学在中国的当代商业实践,阳明教育研究院创始人白立新、奥康集团董事长王振滔、北京大学社会科学学部副主任文东茅教授等诸多企业家、教育家学习阳明心学、致良知的生动记录','35','北京知行合一阳明教育研究院','机械工业出版社','2017-04-01','管理学','/img/阳明心学的力量.jpg',33,0),(87,'钢铁是怎样炼成的','俄中直译全译本','29','奥斯特洛夫斯基 ','西安交通大学出版社','2016-11-11','小说','/img/钢铁是怎样炼成的.jpg',79,0),(88,'斯坦福极简经济学','（当当全国独家精装升级版）','37','[美]泰勒','湖南人民出版社','2016-08-01','经济','/img/斯坦福极简经济学.jpg',78,0),(89,'鲁迅全集','（全20卷，纪念鲁迅先生逝世80周年！）','370','鲁迅','北京日报出版社（原同心出版社）','2014-10-15','文学','/img/鲁迅全集.jpg',99,0),(90,'百年孤独','加西亚马尔克斯代表作','38','加西亚·马尔克斯','南海出版公司','2011-06-27','小说','/img/百年孤独.jpg',68,0),(91,'海底两万里','（中小学新课标必读名著）','32','儒勒.凡尔纳 ','国际文化出版公司','2017-01-17','小说','/img/海底两万里.jpg',99,0),(92,'三毛：撒哈拉的故事','满100减50','17','三毛','北京十月文艺出版社','2013-05-15','文学','/img/三毛：撒哈拉的故事.jpg',99,0),(93,'了不起的盖茨比','【作家榜推荐】','26','菲茨杰拉德','浙江文艺出版社','2017-03-15','小说','/img/了不起的盖茨比.jpg',44,0),(94,'天才在左疯子在右','（完整版）','28','高铭','北京联合出版公司','2016-01-20','心理','/img/天才在左疯子在右.jpg',88,0),(95,'悲惨世界','销量遥遥领先','67','[法]维克多·雨果','作家出版社','2016-06-14','小说','/img/悲惨世界.jpg',3,0),(96,'必然','尼古拉·尼葛洛庞帝互联网启蒙读物《数字化生存》20周年再版','50','凯文·凯利','电子工业出版社','2016-01-16','社会科学','/img/必然.jpg',99,0),(97,'艺术的疗效','治愈了赵薇的书！——赵薇在来往上推荐的治愈系图书。','116','（英）德波顿','广西美术出版社','2014-07-12','艺术','/img/艺术的疗效.jpg',99,0),(98,'胜者思维','危机领导战略，思维制胜之道，决策核心思考。','35','金一南','北京联合出版公司','2017-05-10','政治理论','/img/胜者思维.jpg',99,0),(99,'学习关键词','社科值得看的好书','38','人民日报海外版“学习小组”','人民出版社','2016-11-21','政治理论','/img/学习关键词.jpg',99,0),(100,'与孤独为友','如何获得内心世界的轻松和愉悦','29','和田秀树','北京联合出版公司','2017-04-12','心理','/img/与孤独为友.jpg',49,0),(101,'社会心理学','（第11版）','111','（美） 戴维·迈尔斯','人民邮电出版社','2016-01-14','心理','/img/社会心理学.jpg',48,0),(102,'晚学盲言','（全两册，第二版）','87','钱穆','生活.读书.新知三联书店','2014-03-11','社会科学','/img/晚学盲言.jpg',99,0),(103,'江村经济','销售榜前茅','29','费孝通','北京大学出版社','2016-07-08','社会科学','/img/江村经济.jpg',99,0),(104,'摄影入门','拍出美照超简单','38','乔旭亮','化学工业出版社','2016-06-06','艺术','/img/摄影入门.jpg',99,0),(105,'精通Python网络爬虫','资深专家，以实战为导向，讲透Python网络爬虫各项核心技术和主流框架，深度讲解网络爬虫的抓取技术与反爬攻关技巧','61','韦玮','机械工业出版社','2017-04-04','计算机','/img/精通Python网络爬虫.jpg',58,0),(106,'Android高级进阶','满50减10','84','顾浩鑫','电子工业出版社','2016-09-18','计算机','/img/Android高级进阶.jpg',17,0),(107,'Python大战机器学习','数据科学家的第一个小目标','61','华校专，王正林','电子工业出版社','2017-02-16','计算机','/img/Python大战机器学习.jpg',88,0);

/*Table structure for table `book_order` */

DROP TABLE IF EXISTS `book_order`;

CREATE TABLE `book_order` (
  `oid` int NOT NULL AUTO_INCREMENT,
  `date` varchar(255) DEFAULT NULL,
  `oname` varchar(255) DEFAULT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `uid` int DEFAULT NULL,
  `bid` int DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;

/*Data for the table `book_order` */

insert  into `book_order`(`oid`,`date`,`oname`,`adress`,`status`,`uid`,`bid`) values (3,'2017-05-27 22:43:37','L','我家超市','已发货',7,10),(4,'2017-06-02 14:45:09','L','广药生活区','已签收',7,12),(5,'2017-06-02 14:57:25','L','王子家','未发货',7,14),(6,'2017-06-02 14:59:40','L','家','未发货',7,16),(8,'2017-06-02 15:06:35','L','???','未发货',7,18),(13,'2017-06-03 02:08:16','L','我的家','未发货',7,10),(14,'2017-06-03 02:18:02','L','你家','未发货',7,12),(15,'2017-06-05 14:30:41','L','WWWWW','未发货',7,79);

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `bid` int DEFAULT NULL,
  `uname` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

/*Data for the table `comment` */

insert  into `comment`(`cid`,`bid`,`uname`,`date`,`content`) values (1,10,'L','2017-06-01 02:03','不错'),(2,12,'L','2017-06-01 02:10','一般般');

/*Table structure for table `order_detail` */

DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  `book_num` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb3;

/*Data for the table `order_detail` */

insert  into `order_detail`(`id`,`order_id`,`book_id`,`book_num`,`status`) values (17,3,10,1,'已评价'),(19,3,12,2,'已评价'),(26,5,14,9,'待评价'),(31,8,16,2,'待评价'),(33,4,22,1,'待评价'),(34,6,21,1,'待评价'),(59,13,10,2,'待评价'),(60,14,10,1,'待评价'),(61,15,14,1,'待评价');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `credit` int DEFAULT '30',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

/*Data for the table `user` */

insert  into `user`(`uid`,`user_name`,`password`,`gender`,`phone`,`email`,`adress`,`user_type`,`credit`) values (3,'bb','123',NULL,NULL,NULL,NULL,NULL,30),(7,'L','123','男','12345678900','123@qq.com','我家',NULL,30),(8,'123','213','男','321','213','3',NULL,30),(11,'','',NULL,NULL,NULL,NULL,NULL,30),(12,'111','222',NULL,NULL,NULL,NULL,NULL,30),(13,'333','333',NULL,NULL,NULL,NULL,NULL,30),(14,'666','666',NULL,NULL,NULL,NULL,NULL,30),(15,'777','777',NULL,NULL,NULL,NULL,NULL,30),(16,'t4','123456',NULL,NULL,'t4@qq.com',NULL,'admin',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
