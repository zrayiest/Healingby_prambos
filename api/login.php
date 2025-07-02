<?php

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
    // Cek apakah parameter email dan password dikirim
    if (isset($_POST['email']) && isset($_POST['password'])) {

        $email = $_POST['email'];
        $password = $_POST['password'];

        $sql = "SELECT * FROM users WHERE email = ?";
        $stmt = $c->prepare($sql);
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            $user = $result->fetch_assoc();

            // Membandingkan password secara langsung (plain text)
            if ($password == $user['password']) {
                // Login berhasil

                // Hapus password dari data yang dikirim kembali
                unset($user['password']);

                echo json_encode([
                    "status"  => "success",
                    "message" => "Login berhasil!",
                    "data"    => $user // Kirim data user ke aplikasi
                ]);
            } else {
                // Password salah
                echo json_encode([
                    "status"  => "failed",
                    "message" => "Email atau password salah."
                ]);
            }
        } else {
            // Email tidak ditemukan
            echo json_encode([
                "status"  => "failed",
                "message" => "Email atau password salah."
            ]);
        }
        $stmt->close();
    } else {
        echo json_encode([
            "status"  => "failed",
            "message" => "Parameter email dan password dibutuhkan."
        ]);
    }
} else {
    echo json_encode([
        "status"  => "error",
        "message" => "Metode request tidak diizinkan."
    ]);
}

$c->close();
