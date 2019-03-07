<?php
if(isset($_GET['id'])){
	$id = intval($_GET['id']);
	$getV = $_GET['votes'];
	$votes = explode("->", $getV);
	$election_id = $_GET['election_id'];
	include("connection.php");
	$conn = new connector();
	$conn = $conn->conn();
	$response = "";
	for($i = 0;$i < sizeof($votes);$i++){
		$q = "INSERT INTO voted(candidate_id,election_id,voters_id) VALUES(".$votes[$i].",$election_id,$id)";
		$accept = $conn->query($q);
		if($accept){
			$response = strval($response."-accept");
			$q2 = "SELECT * FROM candidates WHERE id = ".$votes[$i]."";
			$exe2 = $conn->query($q2);
			$name;
			$position;
			while ($row2 = mysqli_fetch_array($exe2)) {
				$name = $row2['name'];	
				$position = $row2['position'];
				}	
				$q3 = "SELECT * FROM voted WHERE candidate_id = ".$votes[$i]."";
			$exe3 = $conn->query($q3);
			$sum = 0;
			while ($row3 = mysqli_fetch_array($exe3)){
					$sum += 1;
				}
				$q4 = "INSERT INTO winner(election_id,name,position,val) VALUES($election_id,'$name','$position',$sum)";
				$exe4 = $conn->query($q4);

					

		}else{
			$response = strval($response."-failed");
		}

	}
	$q = "UPDATE voters SET voted = true WHERE id = $id";
	$accept = $conn->query($q);
	if($accept){
		echo "Thank you for participating, Have a nice day!";
	}
	

}else{
	echo "bad";
}



?>