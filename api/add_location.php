<?php

header('Content-Type: application/json');


try {
    $c = new mysqli("localhost", "root", "", "healing_yuk");
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode([
        "status"  => "error",
        "message" => "Gagal terhubung ke database: " . $e->getMessage()
    ]);
    die();
}

// Hanya proses jika metode request adalah POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {


    if (
        isset($_POST['name']) &&
        isset($_POST['image_url']) &&
        isset($_POST['short_description']) &&
        isset($_POST['category']) &&
        isset($_POST['full_description']) &&
        isset($_POST['address']) &&
        isset($_POST['operating_hours'])
    ) {

        $name = $_POST['name'];
        $url = $_POST['image_url'];
        $shortDesc = $_POST['short_description'];
        $category = $_POST['category'];
        $fullDesc = $_POST['full_description'];
        $address = $_POST['address'];
        $hour = $_POST['operating_hours'];


        $sql_insert = "INSERT INTO `locations`(`name`, `image_url`, `short_description`, `category`, `full_description`, `address`, `operating_hours`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        $stmt_insert = $c->prepare($sql_insert);
        $stmt_insert->bind_param("sssssss", $name, $url, $shortDesc, $category, $fullDesc, $address, $hour);

        if ($stmt_insert->execute()) {

            echo json_encode([
                "status"  => "success",
                "message" => "Lokasi baru berhasil ditambahkan."
            ]);
        } else {

            echo json_encode([
                "status"  => "error",
                "message" => "Gagal menyimpan data ke database."
            ]);
        }
        $stmt_insert->close();
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
