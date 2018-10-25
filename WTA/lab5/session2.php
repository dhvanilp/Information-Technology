<?php
session_start();
?>
<!DOCTYPE html>
<html>
<body>

<?php
session_unset(); 
echo "All session variables are deleted<br>";
 
session_destroy(); 
echo "Session destroyed";
?>

</body>
</html>