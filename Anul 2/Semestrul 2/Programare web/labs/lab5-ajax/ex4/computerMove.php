<?php
$data = json_decode(file_get_contents("php://input"), true);
// Retrieve the JSON data sent in the request body and decode it into an associative array
$table = $data['table'];
// Retrieve the 'table' data from the decoded JSON and assign it to the variable '$table'

function isWinning($table, $player) {
    // Function to check if a player has won
    for ($i = 0; $i < 3; $i++) {
        if ($table[$i][0] == $player && $table[$i][0] == $table[$i][1] && $table[$i][1] == $table[$i][2]) {
            // Check rows for a winning combination
            return true;
        }
    }
    for ($i = 0; $i < 3; $i++) {
        if ($table[0][$i] == $player && $table[0][$i] == $table[1][$i] && $table[1][$i] == $table[2][$i]) {
            // Check columns for a winning combination
            return true;
        }
    }
    if ($table[0][0] == $player && $table[0][0] == $table[1][1] && $table[1][1] == $table[2][2]) {
        // Check the diagonal from top-left to bottom-right for a winning combination
        return true;
    }
    if ($table[0][2] == $player && $table[0][2] == $table[1][1] && $table[1][1] == $table[2][0]) {
        // Check the diagonal from top-right to bottom-left for a winning combination
        return true;
    }
    return false;
}

$freeCells = 0;

foreach ($table as $row) {
    foreach ($row as $cell) {
        if ($cell == '') {
            $freeCells++;
        }
    }
}
// Count the number of free cells in the game board

if (isWinning($table, "x")) {
    echo "x";
    exit();
}
// If 'x' has won, echo "x" and exit the script

if ($freeCells == 0) {
    echo "r";
    exit();
}
// If there are no more free cells, echo "r" (representing a draw) and exit the script

$row = rand(0, 2);
$column = rand(0, 2);

while ($table[$row][$column] != '') {
    $row = rand(0, 2);
    $column = rand(0, 2);
}
// Randomly select an empty cell on the game board

$table[$row][$column] = "o";
echo $row . $column;
// Set the selected cell to "o" and echo the row and column of the selected cell

if (isWinning($table, "o")) {
    echo "o";
    exit();
}
// If 'o' has won, echo "o" and exit the script

if ($freeCells == 1) {
    echo "r";
    exit();
}
// If there is only one free cell remaining, echo "r" (representing a draw) and exit the script
?>