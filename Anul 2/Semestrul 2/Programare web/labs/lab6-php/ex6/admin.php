<?php
session_start();

$connection = new PDO("sqlite:../database.db");

function approve($idComentariu) {
    global $connection;

    $query = $connection->prepare("UPDATE comentarii SET aprobat = 1 WHERE id_comentariu = ?");
    $query->execute([$idComentariu]);
}

if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST["id_com_to_approve"])) {
    approve($_POST["id_com_to_approve"]);
}

function delete($idComentariu) {
    global $connection;

    $query = $connection->prepare("DELETE FROM comentarii WHERE id_comentariu = ?");
    $query->execute([$idComentariu]);
}

if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST["id_com_to_delete"])) {
    delete($_POST["id_com_to_delete"]);
}

if (isset($_GET['logout'])) {
    session_destroy(); 
    header("Location: index.php");
    exit;
}

?>

<!DOCTYPE html>
<html>
<head>
    <title>admin page</title>
</head>
<body>
    <h2>admin</h2>

    <form method="GET" action="<?php echo $_SERVER['PHP_SELF']; ?>">
        <input type="hidden" name="logout" value="1">
        <input type="submit" value="log out">
    </form>

    <?php if ($_SESSION["rol"] === "admin"): ?>
        <h2>manage comments</h2>
        <?php
        $query = $connection->prepare("SELECT * FROM comentarii WHERE aprobat = 0");
        $query->execute();
        $unapprovedComms = $query->fetchAll(PDO::FETCH_ASSOC);
        ?>

        <?php if ($unapprovedComms): ?>
            <ul>
                <?php foreach ($unapprovedComms as $com): ?>
                    <li>
                        <strong><?php echo htmlspecialchars($com["nume"]); ?>:</strong>
                        <?php echo htmlspecialchars($com["comentariu"]); ?>

                        <form method="POST" action="">
                            <input type="hidden" name="id_com_to_approve" value="<?php echo $com['id_comentariu']; ?>">
                            <button type="submit" name="approve">approve</button>
                        </form>

                        <form method="POST" action="">
                            <input type="hidden" name="id_com_to_delete" value="<?php echo $com['id_comentariu']; ?>">
                            <button type="submit" name="delete">delete</button>
                        </form>
                    </li>
                <?php endforeach; ?>
            </ul>
        <?php else: ?>
            <p>no comments to manage yet</p>
        <?php endif; ?>
    <?php endif; ?>
</body>
</html>
