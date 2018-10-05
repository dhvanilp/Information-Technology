<!DOCTYPE html>
<html>
<head>
	<title>Bill</title>
</head>
<body>

	Hello <?php echo $_POST["fname"]; ?><?php echo " "; ?><?php echo  $_POST["lname"]; ?><br>

	Number of tickets ordered - <?php echo $_POST["num"]; ?> 
	<br>

	T-shirt Size - <?php echo $_POST["size"]; ?>
	<br>

	<?php $num = $_POST["num"];
	$amo = $num * 120; 
	echo "Your bill amount is $amo" ?>
	<br>

	A confirmation email has been sent to <?php echo $_POST["email"]; ?> 
</body>
</html>