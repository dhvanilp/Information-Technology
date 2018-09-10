-- question 1
SELECT patie.name as "PATIENT NAME",physi.name as "PHYSICIAN NAME"
    from PATIENT patie
    join PHYSICIAN physi on physi.employeeid=patie.pcp
    where patie.pcp not in(SELECT head from DEPARTMENT);
-- question 2


