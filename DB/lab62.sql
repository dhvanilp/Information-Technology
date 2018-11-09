-- DROP DATABASE lab6;
-- CREATE DATABASE lab6;
-- use lab6;
-- create table student_marks(s_id int primary key,name varchar(30),sub1 int,sub2 int,sub3 int,sub4 int,sub5 int,total int,per_marks decimal(10,2),grade varchar(20));
-- insert into student_marks(s_id,name) values(1,'Steven King'),(2,'Neena Kochhar'),(3,'Lex De Haan'),(4,'Alexander Hunold');

-- select * from student_marks;

-- delimiter //
-- create trigger display_mark before update on student_marks for each row
--         begin
--         set new.total=new.sub1+new.sub2+new.sub3+new.sub4+new.sub5;
--         set new.per_marks=new.total/5;
--         if new.per_marks>=90 then
--         set new.grade='EXCELLENT';
--         elseif new.per_marks>=75 and new.per_marks<90 then
--         set new.grade='VERY GOOD';
--         elseif new.per_marks>=60 and new.per_marks<75 then
--         set new.grade='GOOD';
--         elseif new.per_marks>=40 and new.per_marks<60 then
--         set new.per_marks='AVERAGE';
--         else set new.grade='NOT PROMOTED';
--         end if;
--     end//
-- delimiter;

-- update student_marks set sub1=54,sub2=69,sub3=89,sub4=87,sub5=59 where s_id=1;

-- select * from student_marks;










-- CREATE TABLE blog (
--         id int,
--         title varchar(20),
--         content varchar(20),
--         deleted int,
--         PRIMARY KEY (id)
--         );
-- CREATE TABLE audit( blog_id int,
--         changetype enum('NEW','EDIT','DELETE') NOT NULL,
--         changetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--         foreign key(blog_id) references blog(id)
--     ) ;

-- create trigger blog_insert after insert on blog for each row
--      begin
--         if new.deleted then
--         set @change='DELETE';
--         else
--         set @change='NEW';
--         end if;
--         insert into audit(blog_id,changetype) values(new.id,@change);
--     end;

-- select * from blog;
-- insert into blog values(1,'education','Indian education system',0);
-- select * from blog;
-- select * from audit;
-- update blog set deleted=1 where id=1;
-- select * from audit;

-- update blog set deleted=1 where id=2;

-- delete from blog where id=2;

-- create trigger blog_update after update on blog for each row
--     begin
--         if new.deleted then
--         set @change='DELETE';
--         else
--         set @change='EDIT';
--         end if;
--         insert into audit(blog_id,changetype) values(new.id,@change);
--     end;


-- update blog set content="Indian agriculture" where id=1;
-- select * from audit;
-- select * from blog;
-- insert into blog values(2,'education','Indian education system',0);
-- update blog set content="Indian education" where id=2;
-- select * from blog;
-- select * from audit;


-- create view showStudents as select name, grade from Highschooler
-- where ID not in (select ID1 from Highschooler H1, Friend, Highschooler H2 where H1.ID = Friend.ID1
-- and Friend.ID2 = H2.ID and H1.grade <> H2.grade) order by grade, name;

CREATE PROCEDURE getEmployee(INOUT id INT, OUT count_pr INT) 
begin set count_pr = (select count(Pno) from WORKS_ON where ssn=id); end; $$

-- create function ufn_get_salary_level(sal INT) returns varchar(10)
-- begin declare lvl varchar(10); if sal < 30000 then set lvl = 'Low'; 
-- elseif (sal >=30000 and sal <= 50000) then set lvl = 'Average'; 
-- elseif sal >= 50000 then set lvl = 'High'; end if; return (lvl); end $$