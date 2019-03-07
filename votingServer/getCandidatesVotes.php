<?php
if(isset($_GET['id'])){
	$id = $_GET['id'];
	include("connection.php");
	$conn = new connector();
	$conn = $conn->conn();
	$q = "SELECT * FROM candidates WHERE election_id = $id";
	$accept = $conn->query($q);
	$data = array();
	while ($row = mysqli_fetch_array($accept)) {
		$data[] = $arrayName = array('name' => $row['name'],
									'position' => $row['position'],
									'id' => $row['id'],
									'courseCandidates' => $row['course'],
								'platform' => $row['platform']
									);
	}
	echo json_encode($data);
}
?>