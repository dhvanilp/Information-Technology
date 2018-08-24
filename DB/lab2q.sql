-- 1
SELECT Fname,Minit,Lname,Bday,Address from EMPLOYEE,DEPARTMENT where EMPLOYEE.Dno=DEPARTMENT.Dnumber and DEPARTMENT.Dname='Administration';

SELECT Fname,Minit,Lname,Bday,Address from EMPLOYEE INNER JOIN DEPARTMENT ON EMPLOYEE.Dno=DEPARTMENT.Dnumber and DEPARTMENT.Dname='Administration';

-- 2
SELECT SUM(EMPLOYEE.salary),MIN(EMPLOYEE.salary),MAX(EMPLOYEE.salary),AVG(EMPLOYEE.salary),COUNT(EMPLOYEE.salary) 
from EMPLOYEE,DEPARTMENT where EMPLOYEE.Dno=DEPARTMENT.Dnumber and DEPARTMENT.Dname='Research';

SELECT SUM(EMPLOYEE.salary),MIN(EMPLOYEE.salary),MAX(EMPLOYEE.salary),AVG(EMPLOYEE.salary),COUNT(EMPLOYEE.salary) 
from EMPLOYEE  INNER JOIN DEPARTMENT ON EMPLOYEE.Dno=DEPARTMENT.Dnumber and DEPARTMENT.Dname='Research';

-- 3
SELECT COUNT(EMPLOYEE.Fname) from EMPLOYEE INNER JOIN DEPARTMENT on EMPLOYEE.Dno=DEPARTMENT.Dnumber and DEPARTMENT.Dname='Administration';

-- 4
SELECT Pname,Pno,COUNT(Essn) AS number_of_employees from WORKS_ON,PROJECT WHERE PROJECT.Pnumber=WORKS_ON.Pno group by Pname,Pno;

-- 5