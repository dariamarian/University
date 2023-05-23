<?php

    include 'db_connection.php';
    $conn = OpenCon();
    if (!$conn) {
        die("Connection failed to database");
    }
    $sql = "SELECT DISTINCT plecare FROM tren ORDER BY plecare ASC";
    $result = $conn->query($sql);
    while ($row = mysqli_fetch_assoc($result)) {
        echo '<li>' . $row['plecare'] . '</li>';
    }
    CloseCon($conn);

?>