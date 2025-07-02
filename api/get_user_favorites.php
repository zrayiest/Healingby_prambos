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

if ($_SERVER['REQUEST_METHOD'] == 'GET') {


    if (isset($_GET['id_user'])) {
        $id_user = $_GET['id_user'];


        $sql = "SELECT l.* FROM locations l
                INNER JOIN user_favorites uf ON l.id_location = uf.id_location
                WHERE uf.id_user = ?";

        $stmt = $c->prepare($sql);
        $stmt->bind_param("i", $id_user);
        $stmt->execute();
        $result = $stmt->get_result();

        $favorites = [];
        while ($row = $result->fetch_assoc()) {
            $favorites[] = $row;
        }


        echo json_encode([
            "status" => "success",
            "data"   => $favorites
        ]);

        $stmt->close();
    } else {
        echo json_encode([
            "status"  => "failed",
            "message" => "Parameter id_user dibutuhkan."
        ]);
    }
} else {
    echo json_encode([
        "status"  => "error",
        "message" => "Metode request tidak diizinkan. Gunakan GET."
    ]);
}

$c->close();
