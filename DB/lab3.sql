DROP DATABASE HOSPITAL;
CREATE DATABASE HOSPITAL;
use HOSPITAL;
CREATE TABLE IF NOT EXISTS PHYSICIAN(
    employeeid int(10) PRIMARY KEY,
    name varchar(20) NOT NULL,
    position varchar(30) NOT NULL,
    ssn int(10)
);

CREATE TABLE IF NOT EXISTS DEPARTMENT(
    departmentid int(3) PRIMARY KEY,
    name varchar(20) NOT NULL,
    head int(10),
    FOREIGN KEY(head) references PHYSICIAN(employeeid)
);


CREATE TABLE IF NOT EXISTS PATIENT(
    ssn int(10) PRIMARY KEY,
    name varchar(20) NOT NULL,
    address varchar(50),
    phone varchar(13),
    insuranceid int(10),
    pcp int(10),
    FOREIGN KEY(pcp) references PHYSICIAN(employeeid)
);


CREATE TABLE IF NOT EXISTS NURSE(
    employeeid int(10) PRIMARY KEY,
    name varchar(20) NOT NULL,
    position varchar(20) NOT NULL,
    registered boolean,
    ssn int(10)
);


CREATE TABLE IF NOT EXISTS APPOINTMENT(
    appointmentid int(10) PRIMARY KEY,
    patient int(10),
    prepnurse int(10),
    physician int(10),
    start_dt_time DATETIME,
    end_dt_time DATETIME,
    examinationroom varchar(10),
    FOREIGN KEY(patient) references PATIENT(ssn),
    FOREIGN KEY(prepnurse) references NURSE(employeeid),
    FOREIGN KEY(physician) references PHYSICIAN(employeeid)

);

CREATE TABLE IF NOT EXISTS BLOCK(
    blockfloor int(4),
    blockcode int(4),
    PRIMARY KEY(blockfloor,blockcode)
);

CREATE TABLE IF NOT EXISTS ROOM(
    roomnumber int(4) PRIMARY KEY,
    roomtype varchar(10),
    blockfloor int(4),
    blockcode int(4),
    unavailable boolean,
    FOREIGN KEY(blockfloor,blockcode) references BLOCK(blockfloor,blockcode);

SELECT 'END REACHED' as '';

INSERT INTO NURSE values(101,'Carla Espinosa','Head Nurse',true,111111110);
INSERT INTO NURSE values(102,'Laverne Roberts','Nurse',true,222222220);
INSERT INTO NURSE values(103,'Paul Flowers','Head Nurse',false,333333330);

INSERT INTO PHYSICIAN values(1,'John Dorian','Staff Internist',111111111);
INSERT INTO PHYSICIAN values(2,'Elliot Reid','Attending Physician',222222222);
INSERT INTO PHYSICIAN values(3,'Christopher Turk','Surgical Attending Physician',333333333);
INSERT INTO PHYSICIAN values(4,'Percival Cox','Senior Attending Physician',444444444);
INSERT INTO PHYSICIAN values(5,'Bob Kelso','Head Chief of Medicine',555555555);
INSERT INTO PHYSICIAN values(6,'Todd Quinlan','Surgical Attending Physician',666666666);
INSERT INTO PHYSICIAN values(7,'John Wen','Surgical Attending Physician',777777777);
INSERT INTO PHYSICIAN values(8,'Keith Dudemeister','MD Resident',888888888);
INSERT INTO PHYSICIAN values(9,'Molly Clock','Attending Psychiatrist',999999999);

INSERT INTO DEPARTMENT values(1,'General Medicine',4);
INSERT INTO DEPARTMENT values(2,'Surgery',7);
INSERT INTO DEPARTMENT values(3,'Psychiatry',9);

INSERT INTO BLOCK values(1,1);
INSERT INTO BLOCK values(1,2);
INSERT INTO BLOCK values(2,1);
INSERT INTO BLOCK values(2,2);
INSERT INTO BLOCK values(3,1);
INSERT INTO BLOCK values(3,2);