<?php
	include 'db_connection.php';

    $con = OpenCon();
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}
	$titlu = $_POST["titlu"];
	$autor = $_POST["autor"];
	$gen = $_POST["gen"];
	$id = $_POST["id"];
	$sql =  "UPDATE carte SET titlu = '" .$titlu ."', autor = '" .$autor ."', gen = '" .$gen ."' WHERE id = '".$id ."';";
	if (mysqli_query($con, $sql)) {
		echo "Record updated successfully";
	} else {
		echo "Error updating record: " . mysqli_error($con);
	}
	
	CloseCon($con);
?> 