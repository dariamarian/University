<?php
    function OpenCon()
    {
    $con = mysqli_connect("localhost", "root", "","clienti");
    
    return $con;
    }
    
    function CloseCon($conn)
    {
    $conn -> close();
    }
   
?>