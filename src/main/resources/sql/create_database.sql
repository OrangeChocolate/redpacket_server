CREATE DATABASE redpacket DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

ALTER TABLE product
ADD COLUMN wechat_user_title varchar(255) AFTER update_date,
ADD COLUMN wechat_user_text varchar(255) AFTER wechat_user_title,
ADD COLUMN wechat_share_title varchar(255) AFTER wechat_user_text,
ADD COLUMN wechat_share_link varchar(255) AFTER wechat_share_title,
ADD COLUMN wechat_share_img_url varchar(255) AFTER wechat_share_link;