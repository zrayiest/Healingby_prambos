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


        $sql_user = "SELECT * FROM users WHERE id_user = ? LIMIT 1";
        $stmt_user = $c->prepare($sql_user);
        $stmt_user->bind_param("i", $id_user);
        $stmt_user->execute();
        $result_user = $stmt_user->get_result();

        if ($result_user->num_rows > 0) {

            $user_data = $result_user->fetch_assoc();

            $sql_count = "SELECT COUNT(id_location) as total_favorites FROM user_favorites WHERE id_user = ?";
            $stmt_count = $c->prepare($sql_count);
            $stmt_count->bind_param("i", $id_user);
            $stmt_count->execute();
            $result_count = $stmt_count->get_result();
            $count_data = $result_count->fetch_assoc();


            $user_data['total_favorites'] = (int)$count_data['total_favorites'];


            echo json_encode([
                "status" => "success",
                "data"   => $user_data
            ]);

            $stmt_count->close();
        } else {
            echo json_encode([
                "status"  => "failed",
                "message" => "User tidak ditemukan."
            ]);
        }
        $stmt_user->close();
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
