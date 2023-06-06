<?php

$connection = new PDO("sqlite:../database.db");

$checkboxValue = isset($_POST['legaturi']) ? htmlspecialchars($_POST['legaturi'], ENT_QUOTES, 'UTF-8') : "false";
if ($checkboxValue == "true") {
    withChanges($connection);
} else {
    withoutChanges($connection);
}

function withoutChanges($connection) {
    $sql = "SELECT * FROM trenuri WHERE plecare LIKE :plecare AND sosire LIKE :sosire";

    $stmt = $connection->prepare($sql);
    $stmt->execute(['plecare' => htmlspecialchars($_POST['plecare'], ENT_QUOTES, 'UTF-8') . '%',
                    'sosire' => htmlspecialchars($_POST['sosire'], ENT_QUOTES, 'UTF-8') . '%']);

    $destinations = $stmt->fetchAll();

    foreach ($destinations as $destination) {
        echo "<p style=\"font-size: 20px\">" . htmlspecialchars($destination['plecare'], ENT_QUOTES, 'UTF-8') . " ----- "
         .  htmlspecialchars($destination['sosire'], ENT_QUOTES, 'UTF-8') . "</p>";
    }
}

function withChanges($connection) {
    $sql = "SELECT * FROM trenuri";

    $stmt = $connection->prepare($sql);
    $stmt->execute();

    $trainConnections = $stmt->fetchAll();

    $trainConnectionsGraph = [];
    $visited = [];
    foreach ($trainConnections as $trainConnection) {
        $trainConnectionsGraph[$trainConnection['plecare']][] = $trainConnection['sosire'];
        $visited[$trainConnection['plecare']] = false;
        $visited[$trainConnection['sosire']] = false;
    }

    $route = [];
    findRoutes($trainConnectionsGraph, htmlspecialchars($_POST['plecare'], ENT_QUOTES, 'UTF-8'),
                htmlspecialchars($_POST['sosire'], ENT_QUOTES, 'UTF-8'), $visited, $route);
}

function findRoutes($graph, $start, $end, $visited, $route) {
    $visited[$start] = true;
    $route[] = $start;

    if ($start == $end) {
        echo "<p style=\"font-size: 20px\">" . implode(' ----- ', array_map('htmlspecialchars', $route)) . "</p>";
    } else {
        foreach ($graph[$start] as $nextStation) {
            if (!$visited[$nextStation]) {
                findRoutes($graph, $nextStation, $end, $visited, $route);
            }
        }
    }

    $lastStationIndex = array_search($start, $route);
    unset($visited[$start]);
    array_splice($route, $lastStationIndex);
}

?>