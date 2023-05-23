<?php
    function OpenCon()
    {
    $con = mysqli_connect("localhost", "root", "","produse");
    
    return $con;
    }
    
    function CloseCon($conn)
    {
    $conn -> close();
    }
   
?>