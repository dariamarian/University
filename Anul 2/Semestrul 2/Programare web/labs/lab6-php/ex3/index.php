<?php
session_start();

if (isset($_SESSION['id_prof'])) {
    header("Location: note.php");
    exit;
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST['username'];
    $password = $_POST['password'];

    $connection = new PDO("sqlite:../database.db");

    $statement = $connection->prepare("SELECT * FROM profesori WHERE username = :username AND password = :password");
    $statement->bindParam(':username', $username);
    $statement->bindParam(':password', $password);
    $statement->execute();
    $profesori = $statement->fetchAll();
    if (sizeof($profesori) == 0) {
        $error = "incorrect username or pass";
    }else {
        $_SESSION['id_prof'] = 1;
        header("Location: note.php");
        exit;
    }
}
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>log in profesor</title>
</head>
<body>
    <h2>log in profesor</h2>

    <?php if (isset($error)) { ?>
        <p><?php echo $error; ?></p>
    <?php } ?>

    <form method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
        <label for="username">Username:</label>
        <input type="text" name="username" id="username" required>
        <br>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>
        <br>
        <input type="submit" value="Log in">
    </form>
</body>
</html>