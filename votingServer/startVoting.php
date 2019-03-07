<?php
if(isset($_GET['id'])){

	include("connection.php");
	$id = $_GET['id'];
	$date = $_GET['date'];
	$conn = new connector();
	$conn = $conn->conn();
	$q =  "SELECT * FROM elections WHERE id = $id";
	$accept = $conn->query($q);
	
	while ($row = mysqli_fetch_array($accept)) {
		if($row['concluded'] == ""){
			$end = $row['dateE'];
			$start = $row['dateS'];
			$d1 = date("y-m-d", strtotime($date));
			$d2 = date("m/d/Y", strtotime($end));
			$d3 = date("m/d/Y", strtotime($start));
			$d1 = strtotime($d1);
			$d2 = strtotime($d2);
			$d3 = strtotime($d3);
			
			if($d1 > $d2 || $d1 < $d3){
				echo "Date Error";
			}else{
				$q = "UPDATE elections SET concluded = '".$date."' WHERE id = $id";
				$accept2 = $conn->query($q);
				if($accept2){
					echo "success!";
				}
			}
		}else{
			echo "Election is already going or have concluded";
		}
		
	}
}else{
	
}


?>