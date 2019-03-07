<?php

include("connection.php");

$conn = new connector();
$conn = $conn->conn();
$id = $_GET['id'];
$q ="SELECT * FROM candidates WHERE election_id = ".$id." ORDER BY id DESC";
$accept = $conn->query($q);
$datas = array();
while ($row = mysqli_fetch_array($accept)) {
	$datas[] = $arrayName = array('id' =>  $row['id'],
								'name' =>	$row['name'],
								'position' => $row['position'],
								'election' => $row['election_id'],
								'courseCandidates' => $row['course'],
								'platform' => $row['platform']
								);
}
$all_data = json_encode($datas);
echo $all_data;



?>

