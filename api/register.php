<?php

header('Content-Type: application/json');

try {
    $c = new mysqli("localhost", "root", "", "healing_yuk");
} catch (Exception $e) {
    echo json_encode([
        "status"  => "error",
        "message" => "Gagal terhubung ke database: " . $e->getMessage()
    ]);
    die();
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];


    $sqlEmail = "SELECT email FROM users WHERE email = ?";
    $stmtEmail = $c->prepare($sqlEmail);
    $stmtEmail->bind_param("s", $email);
    $stmtEmail->execute();
    $result = $stmtEmail->get_result();


    if ($result->num_rows > 0) {
        echo json_encode([
            "status"  => "failed",
            "message" => "Email sudah terdaftar. Gunakan email lain."
        ]);
    } else {
        $sqlInsert = "INSERT INTO `users`(`email`, `name`, `password`) VALUES (?,?,?)";
        $stmtInsert = $c->prepare($sqlInsert);
        $stmtInsert->bind_param("sss", $email, $name, $password);

        if ($stmtInsert->execute()) {
            echo json_encode([
                "status"  => "success",
                "message" => "Akun berhasil dibuat."
            ]);
        } else {
            echo json_encode([
                "status"  => "error",
                "message" => "Gagal menyimpan data ke database."
            ]);
        }
        $stmtInsert->close();
    }

    $stmtEmail->close();
    $c->close();
} else {
    echo json_encode([
        "status"  => "error",
        "message" => "Metode request tidak diizinkan."
    ]);
}
