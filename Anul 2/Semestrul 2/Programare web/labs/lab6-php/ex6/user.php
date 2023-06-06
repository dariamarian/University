<?php
session_start();

$connection = new PDO("sqlite:../database.db");

if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST["comentariu"])) {
    $nume = $_POST["nume"];
    $comentariu = $_POST["comentariu"];

    $query = $connection->prepare("INSERT INTO comentarii (nume, comentariu, aprobat) VALUES (?, ?, 0)");
    $query->execute([$nume, $comentariu]);
}

$query = $connection->prepare("SELECT * FROM comentarii WHERE aprobat = 1");
$query->execute();
$coms = $query->fetchAll(PDO::FETCH_ASSOC);

if (isset($_GET['logout'])) {
    session_destroy();
    header("Location: index.php");
    exit;
}

?>

<!DOCTYPE html>
<html>
<head>
    <title>user page</title>
</head>
<body>
    <h1>user</h1>

    <?php if ($coms): ?>
        <h3>comments:</h3>
        <ul>
            <?php foreach ($coms as $comentariu): ?>
                <li>
                    <strong><?php echo htmlspecialchars($comentariu["nume"]); ?>:</strong>
                    <?php echo htmlspecialchars($comentariu["comentariu"]); ?>
                </li>
            <?php endforeach; ?>
        </ul>
    <?php else: ?>
        <p>no comments yet</p>
    <?php endif; ?>

    <h3>add comment:</h3>
    <form method="POST" action="">
        <label for="nume">name:</label>
        <input type="text" name="nume" id="nume" required>

        <br>

        <label for="comentariu">comment:</label>
        <textarea name="comentariu" id="comentariu" rows="4" required></textarea>

        <br>

        <button type="submit">post</button>
    </form>

    <form method="GET" action="<?php echo $_SERVER['PHP_SELF']; ?>">
        <input type="hidden" name="logout" value="1">
        <input type="submit" value="log out">
    </form>
</body>
</html>
