<?php

header('Content-Type: application/json');
// error_reporting(0);

try {
    $c = new mysqli("localhost", "root", "", "healing_yuk");
} catch (Exception $e) {

    echo json_encode([
        "status"  => "error",
        "message" => "Gagal terhubung ke database: " . $e->getMessage()
    ]);
    die();
}


if ($_SERVER['REQUEST_METHOD'] == 'GET') {


    if (isset($_GET['id_user']) && isset($_GET['id_location'])) {
        $id_user = $_GET['id_user'];
        $id_location = $_GET['id_location'];


        $sql = "SELECT * FROM user_favorites WHERE id_user = ? AND id_location = ? LIMIT 1";
        $stmt = $c->prepare($sql);
        $stmt->bind_param("ii", $id_user, $id_location);
        $stmt->execute();
        $result = $stmt->get_result();


        if ($result->num_rows > 0) {

            echo json_encode([
                "status"  => "success",
                "is_favorite" => true
            ]);
        } else {
            echo json_encode([
                "status"  => "success",
                "is_favorite" => false
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
        "message" => "Metode request tidak diizinkan. Gunakan GET."
    ]);
}

$c->close();
