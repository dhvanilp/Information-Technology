<?php
date_default_timezone_set('Asia/Kolkata');

$timestamp = time();
$date_time = date("d-m-Y (D) H:i:s", $timestamp);
echo "Current date and local time on this server is $date_time\n";

$favcolor = rand(1,5);

switch ($favcolor) {
    case 1:
        echo "Hello";
        break;
    case 2:
        echo "Hi";
        break;
    case 3:
        echo "How are you";
        break;
    case 4:
        echo "Good morning";
        break;    
    case 5:
        echo "Good afternoon";
        break;    
    default:
        echo "Good night";
}
$handle = fopen("counter1.txt", "r"); 
if(!$handle){ echo "could not open the file" ; } 
else { $counter = (int ) fread($handle,20); fclose ($handle); 
    $counter++; echo" <strong><div></div> you are visitor no ". $counter . " </strong> " ; 
    $handle = fopen("counter.txt", "w" ); fwrite($handle,$counter) ; 
    fclose ($handle) ; 
}

?>