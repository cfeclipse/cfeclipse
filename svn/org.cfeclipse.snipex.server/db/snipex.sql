


CREATE TABLE `lib` (
  `libid` int(11) NOT NULL auto_increment COMMENT 'The ID of the library',
  `name` varchar(64) NOT NULL default '' COMMENT 'The name of the library',
  `description` varchar(255) NOT NULL default '',
  `parent` int(11) default NULL COMMENT 'Optionally, the ID of a parent library',
  `created` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT 'The date the library was created',
  `updated` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT 'The last time the library was updated (can be used to refresh a cache)',
  PRIMARY KEY  (`libid`),
  KEY `parent` (`parent`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COMMENT='Table used to store a library (including the root SnipEx library/category)';




CREATE TABLE `snip` (
  `snipid` int(11) NOT NULL auto_increment COMMENT 'The snippet''s ID',
  `name` varchar(64) NOT NULL default '' COMMENT 'The snippet''s name',
  `useremail` varchar(255) default NULL,
  `parent` int(11) NOT NULL default '0' COMMENT 'The snippet''s parent library ID',
  `help` text NOT NULL COMMENT 'The snippet''s help text',
  `description` varchar(255) NOT NULL default '' COMMENT 'The snippet''s description',
  `starttext` text NOT NULL COMMENT 'The snippet''s start text',
  `endtext` text NOT NULL COMMENT 'The snippet''s end text',
  `author` varchar(64) NOT NULL default '' COMMENT 'The snippet''s author',
  `platforms` varchar(64) NOT NULL default '' COMMENT 'A comma delimited list of platforms this snippet is confirmed to work on (ex:CF6,CF7,BD6)',
  `template` varchar(6) NOT NULL default '' COMMENT 'The file extensions, if any, this snippet is a template for (ex: cfm). ',
  `created` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT 'The date this snippet was created',
  `updated` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT 'The last time the snippet was updated (can be used to refresh a cache)',
  `published` int(1) NOT NULL default '0',
  PRIMARY KEY  (`snipid`),
  KEY `parent` (`parent`)
) ENGINE=MyISAM AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;



