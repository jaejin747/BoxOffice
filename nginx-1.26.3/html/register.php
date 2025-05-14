<?php 
    $con = mysqli_connect("localhost", "root", "cal6238!", "userdb");
    mysqli_query($con, 'SET NAMES utf8');

    $userSeat = isset($_POST["seat"]) ? $_POST["seat"] : "";
    $userPassword = isset($_POST["password"]) ? $_POST["password"] : "";
    $userName = isset($_POST["name"]) ? $_POST["name"] : "";
    $userPhone = isset($_POST["phone"]) ? $_POST["phone"] : "";

    $response = array();

    // 필수 입력 체크
    if ($userSeat === "" || $userPassword === "" || $userName === "" || $userPhone === "") {
        $response["success"] = false;
        $response["message"] = "모든 항목을 입력해주세요.";
        echo json_encode($response);
        exit();
    }

    // INSERT 실행
    $statement = mysqli_prepare($con, "INSERT INTO userinfo (seat, password, name, phone) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssss", $userSeat, $userPassword, $userName, $userPhone);

    $execResult = mysqli_stmt_execute($statement);

    if ($execResult) {
        $response["success"] = true;
    } else {
        $response["success"] = false;
        $response["message"] = "회원가입에 실패했습니다: " . mysqli_error($con);
    }

    echo json_encode($response);
?>
