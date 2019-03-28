CREATE DATABASE lab6;

use lab6;

CREATE TABLE STUDENT_MARKS(STUDENT_ID int(3),
                            NAME varchar(100),
                            SUB1 int(3),
                            SUB2 int(3),
                            SUB3 int(3),
                            SUB4 int(3),
                            SUB5 int(3),
                            TOTAL int(3),
                            PER_MARKS float(6,2),
                            GARDE varchar(20));

INSERT INTO STUDENT_MARKS values(1,"Steven King",0,0,0,0,0,0,0,NULL);
INSERT INTO STUDENT_MARKS values(2,"Nenna Kochhar",0,0,0,0,0,0,0,NULL);
INSERT INTO STUDENT_MARKS values(3,"Lex de Haan",0,0,0,0,0,0,0,NULL);
INSERT INTO STUDENT_MARKS values(4,"Alexandar Hunold",0,0,0,0,0,0,0,NULL);


CREATE TABLE blog (
id int,
title varchar(20),
content varchar(20),
deleted int,
PRIMARY KEY (id)
);


CREATE TABLE audit( blog_id int,
changetype enum('NEW','EDIT','DELETE') NOT NULL,
changetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
foreign key(blog_id) references blog(id)
) ;