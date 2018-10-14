-- select database
USE CS144;

-- drop existing tables
DROP TABLE IF EXISTS Posts;

-- create table Posts
CREATE TABLE Posts(
  username	 VARCHAR(40),
  postid	 INTEGER,
  title		 VARCHAR(100),
  body		 TEXT,
  modified	 TIMESTAMP DEFAULT '2000-01-01 00:00:00',
  created	 TIMESTAMP DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY(username, postid)
);

<<<<<<< HEAD
Insert into Posts Values('Camille',0,'My Favorite Post','This is a post','2000-01-01 00:00:00','2000-01-01 00:00:00');
Insert into Posts Values('John',1,'My First Post','This is a post too','2000-01-01 00:00:00','2000-01-01 00:00:00');
=======

Insert into Posts Values('Camille',0,'My Favorite Post','This is a post','2000-01-01 00:00:00','2000-01-01 00:00:00');
Insert into Posts Values('John',1,'My First Post','This is a post too','2000-01-01 00:00:00','2000-01-01 00:00:00');
>>>>>>> 19b6c8ce64f6d76e83989ac98a8d4597483dcc7d
