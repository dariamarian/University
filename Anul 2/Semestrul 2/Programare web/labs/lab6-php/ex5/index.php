<?php
session_start();

if (isset($_SESSION[session_id()])) {
    header("Location: profile.php");
    exit;
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST['username'];
    $password = $_POST['password'];

    if (empty($username)) {
        $error = "Username is required.";
    } elseif (!preg_match('/^[a-zA-Z0-9_]+$/', $username)) {
        $error = "Username can only contain letters, numbers, and underscores.";
    }

    if (empty($password)) {
        $error = "Password is required.";
    } elseif (strlen($password) < 3) {
        $error = "Password must be at least 3 characters long.";
    }
    if (!isset($error)) {
        $connection = new PDO("sqlite:../database.db");

        $statement = $connection->prepare("SELECT * FROM users WHERE username = :username AND password = :password");
        $statement->bindParam(':username', $username);
        $statement->bindParam(':password', $password);
        $statement->execute();
        $users = $statement->fetchAll();
        if (sizeof($users) == 0) {
            $error = "incorrect username or pass";
        } else {
            session_id($users[0]['id']);
            $_SESSION[session_id()] = 1;
            header("Location: profile.php");
           exit;
        }
    }
    
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>log in user</title>
</head>
<body>
    <h2>log in user</h2>

    <?php if (isset($error)) { ?>
        <p><?php echo $error; ?></p>
    <?php } ?>

    <form method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
        <label for="username">username:</label>
        <input type="text" name="username" id="username" required>
        <br>
        <label for="password">password:</label>
        <input type="password" name="password" id="password" required>
        <br>
        <input type="submit" value="log in">
    </form>
</body>
</html>