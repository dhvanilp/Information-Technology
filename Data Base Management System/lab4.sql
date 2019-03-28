-- -- question 1
-- SELECT patie.name as "PATIENT NAME",physi.name as "PHYSICIAN NAME"
--     from PATIENT patie
--     join PHYSICIAN physi on physi.employeeid=patie.pcp
--     where patie.pcp not in(SELECT head from DEPARTMENT);
-- -- question 2
-- SELECT pt.name, ph.name from PATIENT pt, PHYSICIAN ph
-- WHERE pt.pcp = ph.employeeid
-- and 2 <= (SELECT count(a.appointmentid) from APPOINTMENT a, NURSE n where 
--  a.prepnurse = n.employeeid and n.registered = 1 and a.patient=pt.ssn);

-- -- question 3
-- SELECT p.name, ph.name from UNDERGOES u join PATIENT p on p.ssn=u.patient join PHYSICIAN ph 
-- on p.pcp=ph.employeeid join PROCEDURES mp on u.procedures=mp.code where mp.cost >= 5000;


-- question 4
SELECT p.name as physician,
p.position as position, 
pr.name as proc, u.date, 
pt.name as patient, t.certificationexpires  
FROM PHYSICIAN p, UNDERGOES u, PATIENT pt, PROCEDURES pr, TRAINED_IN t
WHERE u.patient = pt.ssn and u.procedures = pr.code and u.physician = p.employeeid  
and pr.code = t.treatment and p.employeeid = t.physician and u.date > t.certificationexpires;

-- -- question 5. 
-- select p.name from PHYSICIAN p
-- where employeeid in (select  physician from UNDERGOES u 
-- where not exists (select * from TRAINED_IN where treatment = procedures and physician=u.physician));
