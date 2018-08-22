## This is to move database (not a proper way but will do the work)

- In mysql ``` SHOW VARIABLES LIKE 'datadir';```
- Go to the folder shown (In my case```/var/lib/mysql/```)
- Find the database by name and copy the folder(requires super user)
- Later to fix permissions after copying ```sudo chown mysql:mysql database_name```
