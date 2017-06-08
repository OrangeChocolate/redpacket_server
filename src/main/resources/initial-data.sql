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

INSERT INTO `city` (`id`, `name`) VALUES (110000,'北京市'),(120000,'天津市'),(130000,'河北市'),(130100,'石家庄市'),(130200,'唐山市'),(130300,'秦皇岛市'),(130400,'邯郸市'),(130500,'邢台市'),(130600,'保定市'),(130700,'张家口市'),(130800,'承德市'),(130900,'沧州市'),(131000,'廊坊市'),(131100,'衡水市'),(140000,'山西市'),(140100,'太原市'),(140200,'大同市'),(140300,'阳泉市'),(140400,'长治市'),(140500,'晋城市'),(140600,'朔州市'),(140700,'晋中市');

INSERT INTO `product` (`id`, `amount`, `average_amount`, `description`, `name`, `random_redpacket`) VALUES (1,5,100,'好吃的香油','香油',0),(2,3,100,'非常棒的面包','面包',0);

INSERT INTO `product_city` (`product_id`, `city_id`) VALUES (1,110000),(1,120000),(2,130100),(2,130300),(2,130600);

INSERT INTO `product_detail` (`product_detail_num`, `product_id`, `enable`, `product_name`, `scanned`) VALUES (1,1,1,'香油',0),(1,2,0,'面包',0),(2,1,1,'香油',0),(2,2,1,'面包',0),(3,1,1,'香油',0),(3,2,1,'面包',0),(4,1,0,'香油',0),(5,1,0,'香油',0);

INSERT INTO `wechat_user` (`id`, `actual_city`, `city`, `country`, `create_date`, `head_img_url`, `language`, `latitude`, `longitude`, `nickname`, `open_id`, `province`, `sex`, `subscribe`, `update_date`) VALUES (1, '昆明市', '昆明', '中国', '2017-5-28 09:19:00', 'http://wx.qlogo.cn/mmopen/xygvUDr4WZjBv76PIrGmT25NttTTQP7icWAGRpOd6reQvmq2oSIzbkbxKXVCdYBNPNdEQGF4XHvLFXwZKo82U07WbFWTC7hys/0', 'zh_CN', 24.835258, 102.846436, 'iman¹²³', 'oseIo1mUcV1BTM550N3H0QmxeSWg', '云南', '男', 1, '2017-5-28 09:19:30');
INSERT INTO `wechat_user` (`id`, `actual_city`, `city`, `country`, `create_date`, `head_img_url`, `language`, `latitude`, `longitude`, `nickname`, `open_id`, `province`, `sex`, `subscribe`, `update_date`) VALUES (2, '昆明市', '昆明', '中国', '2017-6-4 00:42:07', 'http://wx.qlogo.cn/mmopen/GGf9ZHrqZN72u81x3yOxQiaCuUQvuyPhb22rnenLFkFghwKpz0dw4vvLh2QhLCJXv9KA1ITCTmdwe2r6qg9iaGoqc2lr7y3Xiat/0', 'zh_CN', 0, 0, 'iman¹²³', 'oEWEwwS3FsTqqWghRp7VbRU0QSk0', '云南', '男', 1, '2017-6-4 00:42:07');

INSERT INTO `red_packet` (`id`, `amount`, `create_date_time`, `wechat_nickname`, `wechat_user_id`, `wechat_open_id`, `product_detail_id`) VALUES (1,123,'2017-04-22 15:45:53','iman¹²³',1,'oseIo1mUcV1BTM550N3H0QmxeSWg',1),(2,234,'2017-04-21 15:46:10','iman¹²³',1,'oseIo1mUcV1BTM550N3H0QmxeSWg',2);

INSERT INTO `option` (`enable`, `name`, `value`, `description`) VALUES (1, 'redpacket_act_name', '微信红包啦！', '活动名称');
INSERT INTO `option` (`enable`, `name`, `value`, `description`) VALUES (1, 'redpacket_wishing', '感谢您购买我们的产品', '红包祝福语');
INSERT INTO `option` (`enable`, `name`, `value`, `description`) VALUES (1, 'redpacket_remark', '谢谢！', '红包备注信息');
INSERT INTO `option` (`enable`, `name`, `value`, `description`) VALUES (1, 'wechat_send_name', '众信香油', '红包发送者名称');
INSERT INTO `option` (`enable`, `name`, `value`, `description`) VALUES (0, 'force_city_check', 'false', '是否强制检查用户所在城市与产品可售城市匹配');
INSERT INTO `option` (`enable`, `name`, `value`, `description`) VALUES (1, 'max_redpackets_user_total', '5', '用户领取红包总上限');
INSERT INTO `option` (`enable`, `name`, `value`, `description`) VALUES (1, 'max_redpackets_user_daily', '2', '用户领取红包每天上限');

INSERT INTO `wechat_auto_reply` (`content`, `keyword`, `priority`, `type`) VALUES ('hi,too', 'hi', 0, 'text');
INSERT INTO `wechat_auto_reply` (`content`, `keyword`, `priority`, `type`) VALUES ('{\r\n  \"title\": \"title\",\r\n  \"description\": \"description\",\r\n  \"thumbMediaId\": \"thumbMediaId\",\r\n  \"musicUrl\": \"musicUrl\",\r\n  \"hqMusicUrl\": \"hqMusicUrl\"\r\n}', 'music', 0, 'music');
INSERT INTO `wechat_auto_reply` (`content`, `keyword`, `priority`, `type`) VALUES ('{\r\n  \"mediaId\": \"5eFogdEzZBzr5xGmt6tMuJ743XOJ2b1MoUQy0rqLQMk\"}', 'image', 0, 'image');

