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

    if (
        isset($_POST['id_user']) &&
        isset($_POST['old_password']) &&
        isset($_POST['new_password']) &&
        isset($_POST['repeat_password'])
    ) {
        $id_user = $_POST['id_user'];
        $old_password = $_POST['old_password'];
        $new_password = $_POST['new_password'];
        $repeat_password = $_POST['repeat_password'];

        if ($new_password != $repeat_password) {
            http_response_code(400);
            echo json_encode([
                "status"  => "failed",
                "message" => "Password baru dan konfirmasi password tidak cocok."
            ]);
            die();
        }

        $sql_get_pass = "SELECT password FROM users WHERE id_user = ?";
        $stmt_get_pass = $c->prepare($sql_get_pass);
        $stmt_get_pass->bind_param("i", $id_user);
        $stmt_get_pass->execute();
        $result = $stmt_get_pass->get_result();

        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            $stored_password = $row['password'];

            if ($old_password == $stored_password) {
                $sql_update = "UPDATE users SET password = ? WHERE id_user = ?";
                $stmt_update = $c->prepare($sql_update);
                $stmt_update->bind_param("si", $new_password, $id_user);

                if ($stmt_update->execute()) {
                    echo json_encode([
                        "status"  => "success",
                        "message" => "Password berhasil diubah."
                    ]);
                } else {

                    echo json_encode([
                        "status"  => "error",
                        "message" => "Gagal memperbarui password di database."
                    ]);
                }
                $stmt_update->close();
            } else {

                echo json_encode([
                    "status"  => "failed",
                    "message" => "Password lama yang Anda masukkan salah."
                ]);
            }
        } else {

            echo json_encode([
                "status"  => "failed",
                "message" => "User tidak ditemukan."
            ]);
        }
        $stmt_get_pass->close();
    } else {

        echo json_encode([
            "status"  => "failed",
            "message" => "Semua field harus diisi."
        ]);
    }
} else {

    echo json_encode([
        "status"  => "error",
        "message" => "Metode request tidak diizinkan."
    ]);
}

$c->close();
