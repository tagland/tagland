-- source with: 
--    mysql -uroot -pcooper < createTagDB.sql

DROP DATABASE IF EXISTS Tagland;

CREATE DATABASE Tagland;

USE Tagland;

CREATE TABLE Tags
	(
	TagID VARCHAR(24) NOT NULL,
	Tag VARCHAR(1024),
	Path VARCHAR(1024), 
	Date VARCHAR(30) NULL, 
	Content TEXT, 
	KeywordCol TEXT, 
	
	PRIMARY KEY(TagID), 
	INDEX Path(Path) 
	);

INSERT INTO Tags (TagID, Tag, Path, Content)  VALUES('1', 'Using iLok Browser App', '/computers/configuration/vendors/ilok/notes/', 'To use ilok on the Mac G5, use Safari, not Firefox 3(I have, and does not let you talk to ilok device) or Firefox 4(they say it is bad).');

select * from Tags;












GRANT ALL PRIVILEGES ON Tapes.* to laramie@localhost IDENTIFIED BY 'cooper';

-- Done!

