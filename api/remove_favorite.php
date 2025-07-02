<?php

header('Content-Type: application/json');
// error_reporting(0);
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


        $sql = "DELETE FROM `user_favorites` WHERE id_user = ? AND id_location = ?";
        $stmt = $c->prepare($sql);

        $stmt->bind_param("ii", $id_user, $id_location);
        $stmt->execute();

        if ($stmt->affected_rows > 0) {
            echo json_encode([
                "status"  => "success",
                "message" => "Berhasil dihapus dari favorit."
            ]);
        } else {
            echo json_encode([
                "status"  => "failed",
                "message" => "Favorit tidak ditemukan."
            ]);
        }
        $stmt->close();
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
