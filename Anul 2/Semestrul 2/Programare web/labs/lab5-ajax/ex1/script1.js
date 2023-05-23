showPlecari();
showSosiri();

// function showPlecari(){
//     var request = new XMLHttpRequest();
//     request.onreadystatechange = function(){
//         if(request.readyState == 4){ 
//             if(request.status == 200){
//                 depList = document.getElementById('departureStations').innerHTML = request.responseText;
//             }
//             else
//                 alert('Error aici: ' + request.statusText);
//         }
//     };
//     request.open("GET", "get-departure-stations.php", false);
//     request.send('');
// }

function showPlecari() {
    var request = new XMLHttpRequest();

    request.onreadystatechange = function() {
        if (request.readyState == 4) { 
            if (request.status == 200) {
                document.getElementById('departureStations').innerHTML = request.responseText;
            } else {
                alert('Error: ' + request.status);
            }
        }
    };

    request.open("POST", "get-departure-stations.php", false);

    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.send();

}

function showSosiri(){
    var departures = document.getElementById('departureStations').getElementsByTagName('li');
    for(let i=0; i < departures.length; i++){
        departures[i].onclick = function() {
            var request = new XMLHttpRequest();
            request.onreadystatechange = function(){
                if(request.readyState == 4){
                    if(request.status == 200){
                        document.getElementById('arrivalStations').innerHTML = request.responseText;
                    }
                    else
                        alert('Error: ' + request.status);
                }
            }
            request.open('GET', 'get-arrival-stations.php?departure=' + this.innerHTML, true);
            request.send('');
        };
    }
}