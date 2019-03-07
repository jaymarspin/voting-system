<?php
if(isset($_GET['fname'])){
	$fname = $_GET['fname'];
	$lname = $_GET['lname'];
	$course = $_GET['course'];
	$year = $_GET['year'];
	$vId = $_GET['vId'];
	$electionId = $_GET['electionId'];

	$fnames = explode("%20", $fname);
	$tmp = "";
	$num = 0;

	while ($num < sizeof($fnames)) {
	
		if($num < (sizeof($fnames)) - 1){
			$tmp = $tmp."".strval($fnames[$num])." ";
		}else{
			$tmp = $tmp."".strval($fnames[$num]);
		}
		
		$num += 1;
	}
	$vId = explode("-", $vId)[1];

	include("connection.php");
	$conn = new connector();
	$conn = $conn->conn();
	$q = "INSERT INTO voters(fname,lname,course,year,election_id,voters_id) VALUES('$tmp','$lname','$course','$year',$electionId,'$vId')";
	$accept = $conn->query($q);
	if($accept){
		$q = "SELECT voters_id,id FROM voters WHERE voters_id = '$vId'";
		$accept = $conn->query($q);
		$response = 0;
		while ($row = mysqli_fetch_array($accept)) {
			$response = $row['id']; 
		}
		echo "success!-".$response;
	}else{
		echo "Failed!";
	}
}

?>