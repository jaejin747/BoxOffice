<?php

mysqli_report(MYSQLI_REPORT_OFF);

$mysqli = @new mysqli("localhost", "root", "cal6238!");

if($mysqli->connect_errno){
	echo "MySQL 접속 <font color=\"red\"><b>실패</b></font>";
}
else{
	echo "MySQL 접속 <font color=\"blue\"><b>성공</b></font>";
}

?>