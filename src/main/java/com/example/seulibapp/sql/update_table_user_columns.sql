DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;

/*Data for the table `user` */

insert  into `user`(`uid`,`user_name`,`password`,`email`,`user_type`) values (3,'bb','123',NULL,NULL),(7,'L','123','123@qq.com','普通用户'),(8,'123','213','213','3'),(11,'','',NULL,NULL),(12,'111','222',NULL,NULL),(13,'333','333',NULL,NULL),(14,'666','666',NULL,NULL),(15,'777','777',NULL,NULL),(16,'applepie','123456','1260164428@qq.com','普通用户'),(17,'t1','123456','t1@qq.com',NULL),(18,'t2','123456','t2@qq.com',NULL),(19,'t3','123456','t3@qq.com',NULL),(20,'t4','123456','t4@qq.com','admin');
