<?php
    function OpenCon()
    {
    $con = mysqli_connect("localhost", "root", "","carti");
    
    return $con;
    }
    
    function CloseCon($conn)
    {
    $conn -> close();
    }
   
?>