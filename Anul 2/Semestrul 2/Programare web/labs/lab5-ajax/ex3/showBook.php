<?php
	include 'db_connection.php';

    $con = OpenCon();
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}

	$idBook = $_GET["id"];
	$result = mysqli_query($con, "SELECT titlu, autor, gen FROM carte WHERE id=" .$idBook);
	
	$row = mysqli_fetch_array($result);
	echo "<label>Titlu: </label>";
	echo "<input type='text' name='titlu' value = '" .$row["titlu"] . "'>";
	echo "<br>";
	echo "<br>";
	echo "<label>Autor: </label>";
	echo "<input type='text' name='autor' value = '" .$row["autor"] . "'>";
	echo "<br>";
	echo "<br>";
	echo "<label>Gen: </label>";
	echo "<input type='text' name='gen' value = '" .$row["gen"] . "'>";
	echo "<br>";
	echo "<br>";
	echo "<input id='myBtn' type='submit' value ='Save' disabled>";
	CloseCon($con);
?> 