<?php
// error_reporting(0);
header('Content-Type: application/json');

// Koneksi ke database
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


    if (isset($_POST['id_user']) && isset($_POST['id_location'])) {
        $id_user = $_POST['id_user'];
        $id_location = $_POST['id_location'];

        $sql_check = "SELECT * FROM user_favorites WHERE id_user = ? AND id_location = ?";
        $stmt_check = $c->prepare($sql_check);
        $stmt_check->bind_param("ii", $id_user, $id_location);
        $stmt_check->execute();
        $result = $stmt_check->get_result();

        if ($result->num_rows > 0) {
            echo json_encode([
                "status"  => "failed",
                "message" => "Lokasi ini sudah ada di daftar favorit Anda."
            ]);
        } else {
            $sql_insert = "INSERT INTO user_favorites(id_user, id_location) VALUES (?, ?)";
            $stmt_insert = $c->prepare($sql_insert);
            $stmt_insert->bind_param("ii", $id_user, $id_location);

            if ($stmt_insert->execute()) {
                echo json_encode([
                    "status"  => "success",
                    "message" => "Berhasil ditambahkan ke favorit."
                ]);
            } else {
                echo json_encode([
                    "status"  => "error",
                    "message" => "Gagal menyimpan data."
                ]);
            }
            $stmt_insert->close();
        }
        $stmt_check->close();
    } else {
        echo json_encode([
            "status"  => "failed",
            "message" => "Parameter id_user dan id_location dibutuhkan."
        ]);
    }
} else {
    echo json_encode([
        "status"  => "error",
        "message" => "Metode request tidak diizinkan."
    ]);
}

$c->close();
