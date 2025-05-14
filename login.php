<?php
    $con = mysqli_connect("localhost", "root", "cal6238!", "userdb");
    mysqli_query($con,'SET NAMES utf8');

    $userSeat = isset($_POST["seat"]) ? $_POST["seat"] : "";
    $userPassword = isset($_POST["password"]) ? $_POST["password"] : "";
    
    $statement = mysqli_prepare($con, "SELECT * FROM userinfo WHERE seat = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $userSeat, $userPassword);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userSeat, $userPassword, $userName, $userPhone);

    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["seat"] = $userSeat;
        $response["password"] = $userPassword;
        $response["name"] = $userName;
        $response["phone"] = $userPhone;        
    }

    echo json_encode($response);

?> 

