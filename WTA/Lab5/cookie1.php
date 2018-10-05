<?php
$cookie_name = "owner";
$cookie_value = "Swapnil Chavan";
setcookie($cookie_name, $cookie_value, time() + (86400 * 30), "/"); // 86400 = 1 day
?>
<html>
<body>

<?php
if(!isset($_COOKIE[$cookie_name])) {
    echo "Cookie named '" . $cookie_name . "' is not set!";
} else {
    echo "Cookie '" . $cookie_name . "' is set!<br>";
    echo "Value is: " . $_COOKIE[$cookie_name];
}
?>

<br>

<form method="POST" id="fm">
<button type="submit" onclick="return true;" name="btn">Delete Cookies</button>
</form>
<?php
	if(isset($_POST['btn']))
	{
		echo "
		<script type=\"text/javascript\">
			var e= document.getElementById('fm');
			e.action='cookie2.php';
			e.submit();
		</script>
		";
	}	

?>
</body>
</html>