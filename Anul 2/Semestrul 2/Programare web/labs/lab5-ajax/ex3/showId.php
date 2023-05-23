<?php
	include 'db_connection.php';

    $con = OpenCon();
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}

	$result = mysqli_query($con, "SELECT id FROM carte");
	while($row = mysqli_fetch_array($result)){
		echo "<li>" .$row[0] ."</li>";
	}
	CloseCon($con);
?> 