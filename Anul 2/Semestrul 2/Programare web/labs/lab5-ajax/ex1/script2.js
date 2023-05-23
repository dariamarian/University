$(document).ready(function() {
    showPlecari();
    showSosiri();
});

// function showPlecari() {
//     $.ajax({
//         url: "get-departure-stations.php",
//         method: "GET",
//         async: false, // synchronous request - need to wait for the response
//         success: function(response) {
//             $('#departureStations').html(response);
//         },
//         error: function(xhr) {
//             alert('Error: ' + xhr.status);
//         }
//     });
// }

function showPlecari() {
    $.ajax({
        url: "get-departure-stations.php",
        method: "POST",
        async: false,
        dataType: "html",
        success: function(response) {
            $('#departureStations').html(response);
        },
        error: function(xhr) {
            alert('Error: ' + xhr.status);
        }
    });
}


function showSosiri() {
    $('#departureStations li').click(function(){
        var departure = $(this).html(); 
        $.ajax({
            url: 'get-arrival-stations.php',
            method: 'GET',
            async: true,
            data: 'departure=' + departure,
            success: function(response) {
                $('#arrivalStations').html(response);
            },
            error: function(xhr) {
                alert('Error: ' + xhr.status);
            }
        });
    });
}