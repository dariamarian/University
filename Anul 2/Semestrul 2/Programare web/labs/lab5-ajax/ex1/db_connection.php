<?php
    function OpenCon()
    {
    $con = mysqli_connect("localhost", "root", "","trenuri");
    
    return $con;
    }
    
    function CloseCon($conn)
    {
    $conn -> close();
    }
   
?>