let table = [['', '', ''], ['', '', ''], ['', '', '']];

// Initialize a 3x3 table to store the game board

let putZero = (position) => {
    // Function to place 'o' in the specified position
    table[position[0]][position[1]] = 'o'; // Set the specified position in the table to 'o'
    $("tr").each((index, element) => {
        // Iterate over each 'tr' element
        if (index != position[0]) {
            // Skip the current iteration if the index doesn't match the specified position's row
            return true;
        }
        $(element).find("td").each((index1, element1) => {
            // Iterate over each 'td' element within the current 'tr' element
            if (index1 != position[1]) {
                // Skip the current iteration if the index doesn't match the specified position's column
                return true;
            }
            $(element1).html('o'); // Set the content of the current 'td' element to 'o'
            return false;
        })
        return false;
    });
}

let computerMove = () => {
    // Function for the computer's move
    $.ajax({
        url: 'computerMove.php', // Send a request to the 'computerMove.php' file
        type: 'POST', // Use the HTTP POST method
        data: JSON.stringify({ table: table }), // Send the table data as JSON in the request body
        success: function (response) {
            // Handle the response from the server
            if (response[response.length - 1] === "r") {
                // If the last character of the response is 'r', indicating a draw
                setTimeout(() => {
                    alert("Remiza") // Display an alert indicating a draw
                }, 1);
            } else if (response[response.length - 1] === "o") {
                // If the last character of the response is 'o', indicating a loss
                setTimeout(() => {
                    alert("Ai pierdut") // Display an alert indicating a loss
                }, 1);
            } else if (response[response.length - 1] === "x") {
                // If the last character of the response is 'x', indicating a win
                setTimeout(() => {
                    alert("Ai castigat") // Display an alert indicating a win
                }, 1);
                return;
            }
            putZero(response); // Place 'o' in the position specified by the response
        }
    });
}

$(document).ready(() => {
    // Execute the following code when the document is ready

    if (Math.floor(Math.random() * 2) === 0) {
        // Randomly determine if the computer should make the first move
        computerMove();
    }

    $('td').each((index, td) => {
        // Iterate over each 'td' element
        $(td).click(() => {
            // Attach a click event handler to the 'td' element
            if ($(td).html() !== '') {
                // If the 'td' element already contains content, i.e., it is not empty
                return; // Exit the function
            }
            table[Math.floor(index / 3)][index % 3] = 'x';
            // Set the corresponding position in the table to 'x' based on the clicked 'td' element
            $(td).html('x');
            // Set the content of the clicked 'td' element to '

            computerMove();
        });
    });
});