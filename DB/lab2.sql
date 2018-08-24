drop database DHP;
create database DHP;
USE DHP;

CREATE TABLE IF NOT EXISTS EMPLOYEE(
    Fname varchar(20) NOT NULL,
    Minit varchar(2),
    Lname varchar(20),
    Ssn int(10) PRIMARY KEY,
    Bday date NOT NULL,
    Address varchar(100) NOT NULL,
    Sex char NOT NULL,
    Salary int(10),
    Super_ssn int(15),
    Dno int(2) NOT NULL
);


INSERT INTO EMPLOYEE values('John','B','Smith',123456789,'1965-01-09','731 Fondren,Houston,TX','M',30000,333445555,5);
INSERT INTO EMPLOYEE values('Franklin','T','Wong',333445555,'1955-12-08','638 Voss,Houston,TX','M',40000,888665555,5);
INSERT INTO EMPLOYEE values('Alicia','J','Zelaya',999887777,'1968-01-19','3321 Castle,Spring,TX','F',25000,987654321,4);
INSERT INTO EMPLOYEE values('Jennifer','S','Wallace',987654321,'1941-06-20','291 Berry,Bellaire,TX','F',43000,888665555,4);
INSERT INTO EMPLOYEE values('Ramesh','K','Narayan',666884444,'1962-09-15','975 Fire Oak,Humble,TX','M',38000,333445555,5);
INSERT INTO EMPLOYEE values('Joyce','A','English',453453453,'1972-07-31','5631 Rice,Houston,TX','F',25000,333445555,5);
INSERT INTO EMPLOYEE values('Ahmed','V','Jabber',987987987,'1969-03-29','980 Dallas,Houston,TX','M',25000,987654321,4);
INSERT INTO EMPLOYEE values('James','E','Brog',888665555,'1937-11-10','450 Stone,Houston,TX','M',55000,NULL,1);




CREATE TABLE IF NOT EXISTS DEPARTMENT(
    Dname varchar(20),
    Dnumber int(2) PRIMARY KEY,
    Mgr_ssn int(15) NOT NULL,
    Mgr_start_date date NOT NULL
);

INSERT INTO DEPARTMENT values('Research',5,333445555,'1988-05-22');
INSERT INTO DEPARTMENT values('Administration',4,987654321,'1995-01-01');
INSERT INTO DEPARTMENT values('Headquarters',1,888665555,'1981-06-19');




CREATE TABLE IF NOT EXISTS DEPT_LOCATION(
    Dnumber int(2),
    Dlocation varchar(20),
    FOREIGN KEY(Dnumber) REFERENCES DEPARTMENT(Dnumber)
);

INSERT INTO DEPT_LOCATION values(1,'Houston');
INSERT INTO DEPT_LOCATION values(4,'Stafford');
INSERT INTO DEPT_LOCATION values(5,'Bellaire');
INSERT INTO DEPT_LOCATION values(5,'Sugarland');
INSERT INTO DEPT_LOCATION values(5,'Houston');





CREATE TABLE IF NOT EXISTS PROJECT(
    Pname varchar(20),
    Pnumber int(4) PRIMARY KEY,
    Plocation varchar(20),
    Dnum int(2),
    FOREIGN KEY(Dnum) REFERENCES DEPARTMENT(Dnumber)
);

INSERT INTO PROJECT values('ProductX',1,'Bellaire',5);
INSERT INTO PROJECT values('ProductY',2,'Sugarland',5);
INSERT INTO PROJECT values('ProductZ',3,'Houston',5);
INSERT INTO PROJECT values('Computerization',10,'Stafford',4);
INSERT INTO PROJECT values('Reorganization',20,'Houston',1);
INSERT INTO PROJECT values('Newbenefits',30,'Stafford',4);





CREATE TABLE IF NOT EXISTS WORKS_ON(
    Essn int(10),
    Pno int(4),
    Hours decimal(4,2),
    -- FOREIGN KEY(Essn) REFERENCES EMPLOYEE(Ssn),
    FOREIGN KEY(Pno) REFERENCES PROJECT(Pnumber)
);


INSERT INTO WORKS_ON values(123456789,1,32.5);
INSERT INTO WORKS_ON values(123456789,2,7.5);
INSERT INTO WORKS_ON values(666884444,3,40.0);
INSERT INTO WORKS_ON values(453453453,1,20.0);
INSERT INTO WORKS_ON values(453453453,2,20.0);
INSERT INTO WORKS_ON values(333445555,2,10.0);
INSERT INTO WORKS_ON values(333445555,3,10.0);
INSERT INTO WORKS_ON values(333445555,10,10.0);
INSERT INTO WORKS_ON values(333445555,20,10.0);
INSERT INTO WORKS_ON values(999887777,30,30.0);
INSERT INTO WORKS_ON values(999887777,10,10.0);
INSERT INTO WORKS_ON values(987987987,10,35.0);
INSERT INTO WORKS_ON values(987987987,30,5.0);
INSERT INTO WORKS_ON values(987654321,30,20.0);
INSERT INTO WORKS_ON values(987654321,20,15.0);
INSERT INTO WORKS_ON values(888665555,20,NULL);

CREATE TABLE IF NOT EXISTS DEPENDENT(
    Essn int(10),
    Dependent_name varchar(20),
    Sex char,
    Bdate date,
    Relationship varchar(20),
    FOREIGN KEY(Essn) REFERENCES EMPLOYEE(Ssn)
);


INSERT INTO DEPENDENT values(333445555,'Alice','F','1986-04-05','Daughter');
INSERT INTO DEPENDENT values(333445555,'Theodore','M','1983-10-25','Son');
INSERT INTO DEPENDENT values(333445555,'Joy','F','1958-05-03','Spouse');
INSERT INTO DEPENDENT values(987654321,'Abner','M','1942-02-28','Spouse');
INSERT INTO DEPENDENT values(123456789,'Michael','M','1988-01-04','Son');
INSERT INTO DEPENDENT values(123456789,'Alice','F','1988-12-30','Daughter');
INSERT INTO DEPENDENT values(123456789,'Elizabeth','F','1967-05-05','Spouse');

SELECT 'END REACHED' as '';



