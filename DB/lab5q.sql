-- create database social_network;
-- use social_network;

-- drop table if exists Highschooler;
-- drop table if exists Friend;
-- drop table if exists Likes;

-- /* Create the schema for our tables */
-- create table Highschooler(ID int, name text, grade int);
-- create table Friend(ID1 int, ID2 int);
-- create table Likes(ID1 int, ID2 int);

-- -- create trigger after_insert after insert on Friend
-- -- for each row when not exists (select 1 from Friend where Friend.id1=new.id2 and Friend.id2=new.id1)
-- -- begin insert into Friend values(new.id2, new.id1);

-- -- create trigger after_delete after delete on Friend
-- -- when exists (select 1 from Friend where Friend.id1=old.id2 and Friend.id2=old.id1)
-- -- begin delete from Friend where Friend.id1=old.id2 and Friend.id2=old.id1;


-- -- question 3
drop view X;
CREATE VIEW X AS SELECT name, grade from Highschooler
WHERE ID NOT IN (SELECT ID1 FROM Highschooler H1, Friend, Highschooler H2
WHERE H1.ID=Friend.ID1 AND Friend.ID2=H2.ID AND H1.grade<>H2.grade) ORDER BY grade, name;
SELECT * FROM X;