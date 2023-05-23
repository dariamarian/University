<?php
    include 'db_connection.php';
    $conn = OpenCon();
    if (!$conn) {
        die("Connection failed to database");
    } 
    
    if (isset($_REQUEST['departure'])) {
        $departure = $_REQUEST['departure'];

        $sql = "SELECT DISTINCT sosire FROM tren WHERE plecare = '$departure'";
        $result = $conn->query($sql);

        while ($row = mysqli_fetch_assoc($result)) {
            echo '<li>' . $row['sosire'] . '</li>';
        }
    } else {
        echo "No 'departure' parameter provided in the request.";
    }
    CloseCon($conn);
?>
