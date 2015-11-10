/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.6.27 : Database - yiding
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`yiding` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `yiding`;

/*Table structure for table `base_adminrank` */

DROP TABLE IF EXISTS `base_adminrank`;

CREATE TABLE `base_adminrank` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `info` varchar(50) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `RANK` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_attachmentinfo` */

DROP TABLE IF EXISTS `base_attachmentinfo`;

CREATE TABLE `base_attachmentinfo` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `create_userid` varchar(36) DEFAULT NULL,
  `filename` varchar(20) DEFAULT NULL,
  `filetype` varchar(5) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `store_type` int(11) DEFAULT NULL,
  `store_url` varchar(50) DEFAULT NULL,
  `messages_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6qb3rwkgttvmsc5g7f9e15c04` (`messages_id`),
  CONSTRAINT `FK_6qb3rwkgttvmsc5g7f9e15c04` FOREIGN KEY (`messages_id`) REFERENCES `base_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_authority` */

DROP TABLE IF EXISTS `base_authority`;

CREATE TABLE `base_authority` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `authinfo` varchar(30) DEFAULT NULL,
  `authtype` varchar(20) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_backupdata` */

DROP TABLE IF EXISTS `base_backupdata`;

CREATE TABLE `base_backupdata` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `bakname` varchar(30) DEFAULT NULL,
  `creattime` datetime DEFAULT NULL,
  `filename` varchar(30) DEFAULT NULL,
  `filepath` varchar(100) DEFAULT NULL,
  `filesize` int(11) DEFAULT NULL,
  `ignortables` varchar(255) DEFAULT NULL,
  `success` bit(1) DEFAULT NULL,
  `userid` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_codetype` */

DROP TABLE IF EXISTS `base_codetype`;

CREATE TABLE `base_codetype` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `editable` bit(1) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `type` varchar(25) NOT NULL,
  `valuetype` varchar(20) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_o5rf42c7r8fwj4rl6v71c91p3` (`parent_id`),
  CONSTRAINT `FK_o5rf42c7r8fwj4rl6v71c91p3` FOREIGN KEY (`parent_id`) REFERENCES `base_codetype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_codevalue` */

DROP TABLE IF EXISTS `base_codevalue`;

CREATE TABLE `base_codevalue` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `cnvalue` varchar(30) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `enable` bit(1) DEFAULT NULL,
  `stringvalue` varchar(50) DEFAULT NULL,
  `codetype_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_reifl05b59u48s5suxooxovxs` (`codetype_id`),
  CONSTRAINT `FK_reifl05b59u48s5suxooxovxs` FOREIGN KEY (`codetype_id`) REFERENCES `base_codetype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_groupinfo` */

DROP TABLE IF EXISTS `base_groupinfo`;

CREATE TABLE `base_groupinfo` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `groupinfo` varchar(50) DEFAULT NULL,
  `groupname` varchar(50) DEFAULT NULL,
  `grouptype` int(11) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_topg4r1vgsu9d9y6g88ws6aus` (`parent_id`),
  CONSTRAINT `FK_topg4r1vgsu9d9y6g88ws6aus` FOREIGN KEY (`parent_id`) REFERENCES `base_groupinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_loginfo` */

DROP TABLE IF EXISTS `base_loginfo`;

CREATE TABLE `base_loginfo` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `level` varchar(10) DEFAULT NULL,
  `logtime` datetime DEFAULT NULL,
  `logger` varchar(100) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `thread` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_menu` */

DROP TABLE IF EXISTS `base_menu`;

CREATE TABLE `base_menu` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `authority` varchar(255) DEFAULT NULL,
  `htmlId` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `defaultvalue` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ddlv56m37t03gswtnyhplqi5h` (`parent_id`),
  CONSTRAINT `FK_ddlv56m37t03gswtnyhplqi5h` FOREIGN KEY (`parent_id`) REFERENCES `base_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_message` */

DROP TABLE IF EXISTS `base_message`;

CREATE TABLE `base_message` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `recevie_userids` varchar(100) DEFAULT NULL,
  `recevie_usernames` varchar(200) DEFAULT NULL,
  `sendDate` datetime DEFAULT NULL,
  `senddelete` bit(1) DEFAULT NULL,
  `success` bit(1) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `send_userid` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_312bbgmw0rh006res40f7o7eo` (`send_userid`),
  CONSTRAINT `FK_312bbgmw0rh006res40f7o7eo` FOREIGN KEY (`send_userid`) REFERENCES `base_userbasic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_operation` */

DROP TABLE IF EXISTS `base_operation`;

CREATE TABLE `base_operation` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `creattime` datetime DEFAULT NULL,
  `info` varchar(200) DEFAULT NULL,
  `ip` varchar(30) DEFAULT NULL,
  `model` varchar(30) DEFAULT NULL,
  `operation` varchar(50) DEFAULT NULL,
  `parameter` varchar(50) DEFAULT NULL,
  `success` bit(1) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_posion` */

DROP TABLE IF EXISTS `base_posion`;

CREATE TABLE `base_posion` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `info` varchar(80) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `adminrank_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m7ar2eb34v3km6l9xeioevfl` (`adminrank_id`),
  CONSTRAINT `FK_m7ar2eb34v3km6l9xeioevfl` FOREIGN KEY (`adminrank_id`) REFERENCES `base_adminrank` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_recevie_message` */

DROP TABLE IF EXISTS `base_recevie_message`;

CREATE TABLE `base_recevie_message` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `hasread` bit(1) DEFAULT NULL,
  `messages_id` varchar(36) DEFAULT NULL,
  `recevie_userid` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rp9qpm6ybwrqmoxktm24imr8g` (`messages_id`),
  KEY `FK_mvlfnbaos13hq4xs4qn762s88` (`recevie_userid`),
  CONSTRAINT `FK_mvlfnbaos13hq4xs4qn762s88` FOREIGN KEY (`recevie_userid`) REFERENCES `base_userbasic` (`id`),
  CONSTRAINT `FK_rp9qpm6ybwrqmoxktm24imr8g` FOREIGN KEY (`messages_id`) REFERENCES `base_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_report` */

DROP TABLE IF EXISTS `base_report`;

CREATE TABLE `base_report` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `fileurl` varchar(50) DEFAULT NULL,
  `groups` varchar(100) DEFAULT NULL,
  `show_page_number` bit(1) DEFAULT NULL,
  `sqlstring` varchar(500) DEFAULT NULL,
  `sqltype` varchar(10) DEFAULT NULL,
  `subtotals` varchar(50) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `templatepath` varchar(100) DEFAULT NULL,
  `templatesrc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_report_column` */

DROP TABLE IF EXISTS `base_report_column`;

CREATE TABLE `base_report_column` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `patter` varchar(30) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `report_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6luxyjfs4imir954whldjkerv` (`report_id`),
  CONSTRAINT `FK_6luxyjfs4imir954whldjkerv` FOREIGN KEY (`report_id`) REFERENCES `base_report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_report_parameter` */

DROP TABLE IF EXISTS `base_report_parameter`;

CREATE TABLE `base_report_parameter` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `default_value` varchar(20) DEFAULT NULL,
  `fromtype` int(11) DEFAULT NULL,
  `inorout` int(11) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `resoucename` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `value` varchar(20) DEFAULT NULL,
  `report_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_41j0u7efn2gfcrkr9wqjt8m6l` (`report_id`),
  CONSTRAINT `FK_41j0u7efn2gfcrkr9wqjt8m6l` FOREIGN KEY (`report_id`) REFERENCES `base_report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_roles_authority` */

DROP TABLE IF EXISTS `base_roles_authority`;

CREATE TABLE `base_roles_authority` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `authority_id` varchar(36) DEFAULT NULL,
  `rolesinfo_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b60r0b70se65kean36ed201xp` (`authority_id`),
  KEY `FK_phwer3q7hiju6t3tv1yw2yn5i` (`rolesinfo_id`),
  CONSTRAINT `FK_b60r0b70se65kean36ed201xp` FOREIGN KEY (`authority_id`) REFERENCES `base_authority` (`id`),
  CONSTRAINT `FK_phwer3q7hiju6t3tv1yw2yn5i` FOREIGN KEY (`rolesinfo_id`) REFERENCES `base_rolesinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_roles_authority_rules` */

DROP TABLE IF EXISTS `base_roles_authority_rules`;

CREATE TABLE `base_roles_authority_rules` (
  `roles_auth_id` varchar(36) NOT NULL,
  `rules_id` varchar(36) NOT NULL,
  PRIMARY KEY (`roles_auth_id`,`rules_id`),
  KEY `FK_9x31e3ohjk64uh5ljgc9kj3w8` (`rules_id`),
  CONSTRAINT `FK_9x31e3ohjk64uh5ljgc9kj3w8` FOREIGN KEY (`rules_id`) REFERENCES `base_rulesinfo` (`id`),
  CONSTRAINT `FK_ckj913ntka9y8bs7hkyc3c7eo` FOREIGN KEY (`roles_auth_id`) REFERENCES `base_roles_authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_rolesinfo` */

DROP TABLE IF EXISTS `base_rolesinfo`;

CREATE TABLE `base_rolesinfo` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `rolename` varchar(50) DEFAULT NULL,
  `rolesinfo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_rulesinfo` */

DROP TABLE IF EXISTS `base_rulesinfo`;

CREATE TABLE `base_rulesinfo` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `expression` varchar(255) DEFAULT NULL,
  `info` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `value` varchar(30) DEFAULT NULL,
  `value_from` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_statistic` */

DROP TABLE IF EXISTS `base_statistic`;

CREATE TABLE `base_statistic` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `defaultvalue` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_systemconfig` */

DROP TABLE IF EXISTS `base_systemconfig`;

CREATE TABLE `base_systemconfig` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `privilage` int(11) DEFAULT NULL,
  `propertyname` varchar(50) DEFAULT NULL,
  `propertyvalue` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_user_roles` */

DROP TABLE IF EXISTS `base_user_roles`;

CREATE TABLE `base_user_roles` (
  `user_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_j7p0fb27vqoa5n27uvsp0p00e` (`role_id`),
  CONSTRAINT `FK_4q855lxvsmqv1qnl8tn8idc7d` FOREIGN KEY (`user_id`) REFERENCES `base_userbasic` (`id`),
  CONSTRAINT `FK_j7p0fb27vqoa5n27uvsp0p00e` FOREIGN KEY (`role_id`) REFERENCES `base_rolesinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_userbasic` */

DROP TABLE IF EXISTS `base_userbasic`;

CREATE TABLE `base_userbasic` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `online` bit(1) DEFAULT NULL,
  `loginname` varchar(50) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `group_id` varchar(36) DEFAULT NULL,
  `position_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bi4vtpnw0llebjuucdsq1sb3q` (`group_id`),
  KEY `FK_co3h2vf6alo6wgc33ky6io43d` (`position_id`),
  CONSTRAINT `FK_bi4vtpnw0llebjuucdsq1sb3q` FOREIGN KEY (`group_id`) REFERENCES `base_groupinfo` (`id`),
  CONSTRAINT `FK_co3h2vf6alo6wgc33ky6io43d` FOREIGN KEY (`position_id`) REFERENCES `base_posion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `base_userinfo` */

DROP TABLE IF EXISTS `base_userinfo`;

CREATE TABLE `base_userinfo` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `lastlogin_ip` varchar(20) DEFAULT NULL,
  `lastlogin_time` datetime DEFAULT NULL,
  `logintotal` int(11) DEFAULT NULL,
  `mobilephone` varchar(20) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `photo` varchar(50) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `totaltime` int(11) DEFAULT NULL,
  `userbasec_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m42rb7a26lne95ix98guhl8s2` (`userbasec_id`),
  CONSTRAINT `FK_m42rb7a26lne95ix98guhl8s2` FOREIGN KEY (`userbasec_id`) REFERENCES `base_userbasic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cms_article` */

DROP TABLE IF EXISTS `cms_article`;

CREATE TABLE `cms_article` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `allowComment` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `copyfrom` varchar(255) DEFAULT NULL,
  `desciption` varchar(255) DEFAULT NULL,
  `hits` int(11) DEFAULT NULL,
  `inputDate` datetime DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `outline` varchar(255) DEFAULT NULL,
  `posid` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `templates` varchar(255) DEFAULT NULL,
  `thumb` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `category_id` varchar(36) DEFAULT NULL,
  `site_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `lobContent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2t7t521ow9c08uqbd9p11fsq4` (`category_id`),
  KEY `FK_dhlwo889mu6md0kuui51npttr` (`site_id`),
  KEY `FK_p9jh9l0c468m5fxtcp1bcv3a6` (`user_id`),
  CONSTRAINT `FK_2t7t521ow9c08uqbd9p11fsq4` FOREIGN KEY (`category_id`) REFERENCES `cms_category` (`id`),
  CONSTRAINT `FK_dhlwo889mu6md0kuui51npttr` FOREIGN KEY (`site_id`) REFERENCES `cms_site` (`id`),
  CONSTRAINT `FK_p9jh9l0c468m5fxtcp1bcv3a6` FOREIGN KEY (`user_id`) REFERENCES `base_userbasic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cms_articledata` */

DROP TABLE IF EXISTS `cms_articledata`;

CREATE TABLE `cms_articledata` (
  `id` varchar(36) NOT NULL,
  `lobContent` longtext,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_tgver71blm2djvcdgrlblw3wi` FOREIGN KEY (`id`) REFERENCES `cms_article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cms_articletag` */

DROP TABLE IF EXISTS `cms_articletag`;

CREATE TABLE `cms_articletag` (
  `article_id` varchar(36) NOT NULL,
  `tags_id` varchar(36) NOT NULL,
  KEY `FK_fwldg50r5xdtfw498iuwh2jsv` (`tags_id`),
  KEY `FK_i4ggs212anspxpkxaqypp2ig1` (`article_id`),
  CONSTRAINT `FK_fwldg50r5xdtfw498iuwh2jsv` FOREIGN KEY (`tags_id`) REFERENCES `cms_tags` (`id`),
  CONSTRAINT `FK_i4ggs212anspxpkxaqypp2ig1` FOREIGN KEY (`article_id`) REFERENCES `cms_article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cms_category` */

DROP TABLE IF EXISTS `cms_category`;

CREATE TABLE `cms_category` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `allowComment` varchar(255) DEFAULT NULL,
  `catSize` int(11) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `desciption` varchar(255) DEFAULT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `inList` varchar(255) DEFAULT NULL,
  `inMenu` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `showModes` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `templates` varchar(255) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `site_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_l6w7jqgssuvoxd8lsf5x2quxu` (`parent_id`),
  KEY `FK_c7eo3wdkq7e1bng47nmhqhut1` (`site_id`),
  CONSTRAINT `FK_c7eo3wdkq7e1bng47nmhqhut1` FOREIGN KEY (`site_id`) REFERENCES `cms_site` (`id`),
  CONSTRAINT `FK_l6w7jqgssuvoxd8lsf5x2quxu` FOREIGN KEY (`parent_id`) REFERENCES `cms_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cms_comment` */

DROP TABLE IF EXISTS `cms_comment`;

CREATE TABLE `cms_comment` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `auditDate` datetime DEFAULT NULL,
  `auditUser` tinyblob,
  `content` varchar(255) DEFAULT NULL,
  `contentId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `article_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_n527ht479dh7p14cv7ucbnhx5` (`article_id`),
  CONSTRAINT `FK_n527ht479dh7p14cv7ucbnhx5` FOREIGN KEY (`article_id`) REFERENCES `cms_article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cms_site` */

DROP TABLE IF EXISTS `cms_site`;

CREATE TABLE `cms_site` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `copyright` varchar(255) DEFAULT NULL,
  `delFlag` int(11) DEFAULT NULL,
  `desciption` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `theme` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cms_tags` */

DROP TABLE IF EXISTS `cms_tags`;

CREATE TABLE `cms_tags` (
  `id` varchar(36) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `publishCount` int(11) DEFAULT NULL,
  `refCount` int(11) DEFAULT NULL,
  `siteId` varchar(255) DEFAULT NULL,
  `tagName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dhtfiles` */

DROP TABLE IF EXISTS `dhtfiles`;

CREATE TABLE `dhtfiles` (
  `singlefilelength` bigint(20) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `info_hash` char(40) NOT NULL,
  KEY `info_hash` (`info_hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dhtinfo` */

DROP TABLE IF EXISTS `dhtinfo`;

CREATE TABLE `dhtinfo` (
  `infohash` char(40) NOT NULL,
  `peer_ipport` varchar(30) DEFAULT NULL,
  `lastrequesttime` datetime DEFAULT CURRENT_TIMESTAMP,
  `dhtstate` smallint(1) DEFAULT '0',
  `crawcount` int(11) DEFAULT '1',
  `hitcount` int(11) DEFAULT '0',
  `tag` varchar(100) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `filelength` bigint(20) DEFAULT NULL,
  `creattime` datetime DEFAULT NULL,
  `singerfile` tinyint(1) DEFAULT NULL,
  `isindex` tinyint(1) DEFAULT '0',
  `validstate` smallint(1) DEFAULT '1',
  `successcount` int(11) DEFAULT '0',
  PRIMARY KEY (`infohash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
