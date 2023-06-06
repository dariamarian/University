<?php
session_start();

if (!isset($_SESSION['id_prof'])) {
    header("Location: index.php");
    exit;
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id_stud = $_POST['id_stud'];
    $materie = $_POST['materie'];
    $nota = $_POST['nota'];


    $connection = new PDO("sqlite:../database.db");

    $statementStudents = $connection->prepare("SELECT * FROM studenti WHERE id = :id_stud");
    $statementStudents->bindParam(':id_stud', $id_stud);
    $statementStudents->execute();

    $statementSubjects = $connection->prepare("SELECT * FROM materii WHERE LOWER(nume) = LOWER(:materie)");
    $statementSubjects->bindParam(':materie', $materie);
    $statementSubjects->execute();

    if ($nota < 1 || $nota > 10) {
        $error = "between 1 and 10";
    }
    else if (sizeof($statementStudents->fetchAll()) == 0) {
        $error = "no student with such id";
    }
    else if (sizeof($statementSubjects->fetchAll()) == 0){
        $error = "no subject with this name";
    }
    else {
        $statement = $connection->prepare("INSERT INTO note (id_student, denumire_materie, nota) VALUES (:id_stud, LOWER(:materie), :nota)");
        $statement->bindParam(':id_stud', $id_stud);
        $statement->bindParam(':materie', $materie);
        $statement->bindParam(':nota', $nota);

        if (!$statement->execute()) {
            $error = "error";
        }
        $success = "success";
    }
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
    <title>Note</title>
</head>
<body>
    <h2>Add grades</h2>

    <?php if (isset($success)) { ?>
        <p><?php echo $success; ?></p>
    <?php } ?>

    <?php if (isset($error)) { ?>
            <p><?php echo $error; ?></p>
        <?php } ?>

    <form method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
        <label for="id_stud">id student:</label>
        <input type="text" name="id_stud" id="id_stud" required>
        <br>
        <label for="materie">subject:</label>
        <input type="text" name="materie" id="materie" required>
        <br>
        <label for="nota">grade:</label>
        <input type="text" name="nota" id="nota" required>
        <br>
        <input type="submit" value="add">
    </form>

    <form method="GET" action="<?php echo $_SERVER['PHP_SELF']; ?>">
            <input type="hidden" name="logout" value="1">
            <input type="submit" value="log out">
        </form>
</body>
</html>
