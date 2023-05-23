<?php
	include 'db_connection.php';

    $con = OpenCon();
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}

	$pageNr = $_GET["page"];
	$pageLimit = 3;
	$result = mysqli_query($con, "SELECT * FROM client limit " .$pageNr ."," . $pageLimit .";");

	echo "<tr><th>Nume</th><th>Prenume</th><th>Telefon</th><th>Email</th></tr>";
	while($row = mysqli_fetch_array($result)){
		echo "<tr><td>" .$row["nume"] ."</td><td>" .$row["prenume"] ."</td><td>".$row["telefon"] ."</td><td>" .$row["email"] . "</td></tr>";
	}
	CloseCon($con);
?> 