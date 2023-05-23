<?php

$name = $_GET["q"];

if (is_file($name)) {
    echo "<div onclick=loadDirectory('../..')>..</div>";
    echo strip_tags(file_get_contents($name), "<html>"); // read the content and remove html tags
    exit();
}

$files = scandir($name); // get an array of all files and directories within the specified directory
foreach($files as $file) {
    echo "<li><div onclick=loadDirectory('" . $_GET["q"] . "/" . $file . "')>" . $file . "</div></li>";
}

?>