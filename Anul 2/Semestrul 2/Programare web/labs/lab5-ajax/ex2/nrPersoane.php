<?php
	include 'db_connection.php';

    $con = OpenCon();
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}

	$pageNr = $_GET["page"];
	$pageLimit = 3;
	$result = mysqli_query($con, "SELECT * FROM client limit " .$pageNr ."," . $pageLimit .";");
	
	echo $result->num_rows;
	
	CloseCon($con);
?> 