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

$sql = "select * from locations";
$stmt = $c->prepare($sql);
$stmt->execute();
$result = $stmt->get_result();
$array = array();

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $array[] = $row;
    }
}
$info = array(
    "status"  => "success",
    "data" => $array
);
echo json_encode($info);
