window.onload = () => {
    loadDirectory("C:/xampp/htdocs")
}

// let loadDirectory = (directory) => {
//     let xmlhttp = new XMLHttpRequest();
//     xmlhttp.onreadystatechange = function () {
//         if (this.readyState === 4 && this.status === 200) {
//             document.getElementById("fileList").innerHTML = this.responseText;
//         }
//     };
//     xmlhttp.open("GET", "loadFiles.php?q=" + directory, true);
//     xmlhttp.send();
// }

let loadDirectory = (directory) => {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("fileList").innerHTML = this.responseText;
        }
    };
    xmlhttp.open("POST", "loadFiles.php?q=" + directory, true);
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlhttp.send();
}