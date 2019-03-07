<?php
include("connection.php");
$conn = new connector();
$conn = $conn->conn();
$id = $_GET['id'];
$q = "SELECT * FROM voters WHERE election_id = $id ORDER BY id DESC";
$accept = $conn->query($q);
$datas[] = array();
while ($row = mysqli_fetch_array($accept)) {
	$datas[] = $arrayName = array('id' => $row['id'],
									'fname' => $row['fname'],
									'lname' => $row['lname'],
									'course' => $row['course'],
									'yearString' => $row['year'],
									'election_id' => $row['election_id'],
									'votersId' => $row['voters_id']

								);
}
$all_data = json_encode($datas);
echo $all_data;


?>