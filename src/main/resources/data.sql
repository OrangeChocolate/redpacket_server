-- The correct order is following
--`admin_user` 
--`admin_user_role` 
--`admin_user_role_join` 
--`city` 
--`product` 
--`product_city` 
--`product_detail` 
--`wechat_user` 
--`red_packet` 

INSERT INTO `admin_user` (`id`, `password`, `username`) VALUES (1,'$2a$10$msRdCsQU1WaNm.iU8Y3uL.95rcGpA96Aoi/BpwDgMTLQCP24v57s2','liudonghua');
INSERT INTO `admin_user_role` (`id`, `role`) VALUES (1,'ADMIN'),(2,'USER');
INSERT INTO `admin_user_role_join` (`admin_user_id`, `admin_user_role_id`) VALUES (1,1),(1,2);
INSERT INTO `city` (`id`, `name`) VALUES (110000,'北京'),(120000,'天津'),(130000,'河北'),(130100,'石家庄'),(130200,'唐山'),(130300,'秦皇岛'),(130400,'邯郸'),(130500,'邢台'),(130600,'保定'),(130700,'张家口'),(130800,'承德'),(130900,'沧州'),(131000,'廊坊'),(131100,'衡水'),(140000,'山西'),(140100,'太原'),(140200,'大同'),(140300,'阳泉'),(140400,'长治'),(140500,'晋城'),(140600,'朔州'),(140700,'晋中');
INSERT INTO `product` (`id`, `amount`, `average_amount`, `description`, `name`, `random_redpacket`) VALUES (1,5,0,'好吃的香油','香油','\0'),(2,3,0,'非常棒的面包','面包','\0');
INSERT INTO `product_city` (`product_id`, `city_id`) VALUES (1,110000),(1,120000),(2,130100),(2,130300),(2,130600);
INSERT INTO `product_detail` (`product_detail_id`, `product_id`, `enable`, `product_name`, `scanned`) VALUES (1,1,1,'香油',0),(1,2,0,'面包',0),(2,1,1,'香油',0),(2,2,1,'面包',0),(3,1,1,'香油',0),(3,2,1,'面包',0),(4,1,0,'香油',0),(5,1,0,'香油',0);
INSERT INTO `wechat_user` (`id`, `actual_city`, `city`, `country`, `headimgurl`, `language`, `latitude`, `longitude`, `nickname`, `open_id`, `province`, `sex`) VALUES (1,'昆明','昆明','中国','http://headimgurl','中文',0,0,'iman','111','云南','男'),(2,'北京','北京','中国','http://image','中文',0,0,'believejava','222','北京','男');
INSERT INTO `red_packet` (`id`, `amount`, `create_date_time`, `product_id`, `product_name`, `wechat_nickname`, `wechat_user_id`, `product_detail_id`) VALUES (1,123,'2017-04-22 15:45:53',1,'香油','iman',1, 1),(2,234,'2017-04-21 15:46:10',1,'香油','iman',1, 2),(3,345,'2017-04-13 15:46:27',2,'面包','believejava',2, 1);
