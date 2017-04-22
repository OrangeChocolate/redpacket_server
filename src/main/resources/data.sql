--drop table if exists `admin_user`;
--CREATE TABLE `admin_user` (
--  `id` bigint(20) NOT NULL AUTO_INCREMENT,
--  `password` varchar(255) NOT NULL,
--  `username` varchar(255) NOT NULL,
--  PRIMARY KEY (`id`)
--);
--
--drop table if exists `admin_user_role`;
--CREATE TABLE `admin_user_role` (
--  `id` bigint(20) NOT NULL AUTO_INCREMENT,
--  `role` varchar(255) NOT NULL,
--  PRIMARY KEY (`id`)
--);
--
--drop table if exists `admin_user_role_join`;
--CREATE TABLE `admin_user_role_join` (
--  `admin_user_id` bigint(20) NOT NULL,
--  `admin_user_role_id` bigint(20) NOT NULL,
--  KEY `fk_admin_user_id` (`admin_user_role_id`),
--  KEY `fk_admin_user_role_id` (`admin_user_id`),
--  CONSTRAINT `fk_admin_user_id` FOREIGN KEY (`admin_user_id`) REFERENCES `admin_user` (`id`),
--  CONSTRAINT `fk_admin_user_role_id` FOREIGN KEY (`admin_user_role_id`) REFERENCES `admin_user_role` (`id`)
--)

insert into ADMIN_USER(ID, PASSWORD, USERNAME) values(1, '$2a$10$msRdCsQU1WaNm.iU8Y3uL.95rcGpA96Aoi/BpwDgMTLQCP24v57s2', 'liudonghua');
insert into ADMIN_USER_ROLE(ID, ROLE) values(1, 'ADMIN');
insert into ADMIN_USER_ROLE(ID, ROLE) values(2, 'USER');
insert into ADMIN_USER_ROLE_JOIN(ADMIN_USER_ID, ADMIN_USER_ROLE_ID) values(1, 1);
insert into ADMIN_USER_ROLE_JOIN(ADMIN_USER_ID, ADMIN_USER_ROLE_ID) values(1, 2);



insert into city(`id`,`name`) values ("110000","北京");
insert into city(`id`,`name`) values ("120000","天津");
insert into city(`id`,`name`) values ("130000","河北");
insert into city(`id`,`name`) values ("130100","石家庄");
insert into city(`id`,`name`) values ("130200","唐山");
insert into city(`id`,`name`) values ("130300","秦皇岛");
insert into city(`id`,`name`) values ("130400","邯郸");
insert into city(`id`,`name`) values ("130500","邢台");
insert into city(`id`,`name`) values ("130600","保定");
insert into city(`id`,`name`) values ("130700","张家口");
insert into city(`id`,`name`) values ("130800","承德");
insert into city(`id`,`name`) values ("130900","沧州");
insert into city(`id`,`name`) values ("131000","廊坊");
insert into city(`id`,`name`) values ("131100","衡水");
insert into city(`id`,`name`) values ("140000","山西");
insert into city(`id`,`name`) values ("140100","太原");
insert into city(`id`,`name`) values ("140200","大同");
insert into city(`id`,`name`) values ("140300","阳泉");
insert into city(`id`,`name`) values ("140400","长治");
insert into city(`id`,`name`) values ("140500","晋城");
insert into city(`id`,`name`) values ("140600","朔州");
insert into city(`id`,`name`) values ("140700","晋中");

INSERT INTO `product` VALUES (1,5,'好吃的香油','香油'),(2,3,'非常棒的面包','面包');
INSERT INTO `product_city` VALUES (1,110000),(1,120000),(2,130100),(2,130300),(2,130600);
--INSERT INTO `product_detail` VALUES (1,1,'^A'),(1,2,'\0'),(2,1,'^A'),(2,2,'^A'),(3,1,'^A'),(3,2,'^A'),(4,1,'\0'),(5,1,'\0');
INSERT INTO `wechat_user` VALUES (1,'昆明','昆明','中国','http://headimgurl','中文',0,0,'iman','111','云南','男'),(2,'北京','北京','中国','http://image','中文',0,0,'believejava','222','北京','男');
INSERT INTO `red_packet` VALUES (1,123,'2017-04-22 15:45:53',1),(2,234,'2017-04-21 15:46:10',1),(3,345,'2017-04-13 15:46:27',2);
