<?php 
$con = mysqli_connect("localhost", "root", "cal6238!", "userdb");
mysqli_query($con, 'SET NAMES utf8');

// POST 데이터 유효성 검사
$userSeat = isset($_POST["seat"]) ? $_POST["seat"] : "";

$response = array();

// 빈 값이면 false 반환
if (empty($userSeat)) {
    $response["success"] = false;
    $response["message"] = "자리번호가 비어 있습니다.";
    echo json_encode($response);
    exit();
}

// 쿼리 실행
$statement = mysqli_prepare($con, "SELECT seat FROM userinfo WHERE seat = ?");
mysqli_stmt_bind_param($statement, "s", $userSeat);
mysqli_stmt_execute($statement);
mysqli_stmt_store_result($statement);

// 결과 판단
if (mysqli_stmt_num_rows($statement) > 0) {
    $response["success"] = false; // 중복된 자리번호
    $response["seat"] = $userSeat;
} else {
    $response["success"] = true;  // 사용 가능
}

echo json_encode($response);
?>
