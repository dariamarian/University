$(document).ready(() => {
    loadDirectory("C:/xampp/htdocs")
});

// let loadDirectory = (directory) => {
//     $.ajax({
//         url: "loadFiles.php",
//         type: "GET",
//         data: {q: directory},
//         success: (response) => {
//             $("#fileList").html(response);
//         },
//         error: (xhr, status, error) => {
//             console.log(error);
//         }
//     });
// }

let loadDirectory = (directory) => {
    $.ajax({
        url: "loadFiles.php",
        type: "POST",
        data: { q: directory },
        success: (response) => {
            $("#fileList").html(response);
        },
        error: (xhr, status, error) => {
            console.log(error);
        }
    });
}
