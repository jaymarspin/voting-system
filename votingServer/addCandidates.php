<?php

if(isset($_GET['name'])){
	$name = $_GET['name'];
	$position = $_GET['position'];
	$election_id = $_GET['electionId'];
	$img = $_GET['img'];
	$course = $_GET['course'];
	$platform = $_GET['platform'];
	

	include("connection.php");
	$con = new connector();
	$con = $con->conn();
	$q = "INSERT INTO candidates(name,course,platform,position,election_id) VALUES('$name','$course','$platform','$position',$election_id)";
	$accept= $con->query($q);
	if($accept){
		$q = "SELECT id,name FROM candidates WHERE name = '$name'";
		$accept = $con->query($q);
		$response = 0;
		while ($row = mysqli_fetch_array($accept)) {
			$response = intval($row['id']);	
		}
		echo "success!-".$response;
	}
}else{
	echo "so bad";
}




?>