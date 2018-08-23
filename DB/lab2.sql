drop database DHP;
create database DHP;
USE DHP;

CREATE TABLE IF NOT EXISTS EMPLOYEE(
    Fname varchar(20) NOT NULL,
    Minit varchar(2),
    Lname varchar(20),
    Ssn int(10) PRIMARY KEY NOT NULL,
    Bday date NOT NULL,
    Address varchar(100) NOT NULL,
    Sex char NOT NULL,
    Salary int(10),
    Super_ssn int(15) UNIQUE,
    Dno int(2) NOT NULL
);

INSERT INTO EMPLOYEE values('John','B','Smith',123456789,'1985-01-09','731 Fondren,Houston,TX','M',300000,333445555,5);
INSERT INTO EMPLOYEE values('Franklin','T','Wong',333445555,'1955-12-08','638 Voss,Houston,TX','M',400000,888665555,5);
INSERT INTO EMPLOYEE values('some','a','lnam',987654321,'1925-11-23','622 boss,Bhama,VX','F',600000,123465555,4);

CREATE TABLE IF NOT EXISTS DEPARTMENT(
    Dname varchar(20),
    Dnumber int(2) PRIMARY KEY NOT NULL,
    Mgr_ssn int(15) NOT NULL,
    Mgr_start_date date NOT NULL
    -- FOREIGN KEY(Mgr_ssn) REFERENCES EMPLOYEE(Super_ssn) //added at the end
);

CREATE TABLE IF NOT EXISTS DEPT_LOCATION(
    Dnumber int(2),
    Dlocation varchar(20),
    FOREIGN KEY(Dnumber) REFERENCES DEPARTMENT(Dnumber)
);

CREATE TABLE IF NOT EXISTS PROJECT(
    Pname varchar(20),
    Pnumber int(4) PRIMARY KEY NOT NULL,
    Plocation varchar(20),
    Dnum int(2),
    FOREIGN KEY(Dnum) REFERENCES DEPARTMENT(Dnumber)
);

CREATE TABLE IF NOT EXISTS WORKS_ON(
    Essn int(10),
    Pno int(4),
    Hours decimal(4,2),
    FOREIGN KEY(Essn) REFERENCES EMPLOYEE(Ssn),
    FOREIGN KEY(Pno) REFERENCES PROJECT(Pnumber)
);

CREATE TABLE IF NOT EXISTS DEPENDENT(
    Essn int(10),
    Dependent_name varchar(20),
    Sex char,
    Bdate date,
    Relationship varchar(20),
    FOREIGN KEY(Essn) REFERENCES EMPLOYEE(Ssn)
);

ALTER TABLE DEPARTMENT ADD FOREIGN KEY(Mgr_ssn) REFERENCES EMPLOYEE(Super_ssn);

desc EMPLOYEE;
desc DEPARTMENT;
desc DEPT_LOCATION;
desc PROJECT;
desc WORKS_ON;
desc DEPENDENT;



