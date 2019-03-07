<?php
include("connection.php");
$conn = new connector();
$conn = $conn->conn();


class funcCollection
{
	
	function __construct()
	{
		
	}
	function sort($q,$conn){
		$accept = $conn->query($q);
		$num = 0;
		while ($row = mysqli_fetch_array($accept)) {
			
		}
	}


	function count($q,$conn){
		$accept = $conn->query($q);
		$num = 0;
		while ($row = mysqli_fetch_array($accept)) {
			$num += 1;
		}
		return $num;
	}
}



if(isset($_GET['id'])){
	$id = $_GET['id'];
	$q = "SELECT * FROM candidates WHERE election_id =".$id;
	$accept = $conn->query($q);
$positions = array();
$positions = ["President","Vice President","Auditor","Secretary","Treasurer","Sargeant at Arms"];
$preVotes = 0;
	$classic = new funcCollection();
	$all_data = array();
	$count = 0;
	$vals = array();
	while ($row = mysqli_fetch_array($accept)) {
		$q2 = "SELECT * FROM voted WHERE election_id = $id && candidate_id = ".intval($row['id']);
					 $votes = $classic->count($q2,$conn);

					 


					 $allVotes = $classic->count("SELECT * FROM voted WHERE election_id = $id && candidate_id = ".intval($row['id'])."",$conn);
					 $classic->sort("SELECT * FROM voted WHERE election_id = $id && candidate_id = ".intval($row['id'])."",$conn);

					 $all_data[] = $arrayName = array('position' => trim($row['position']),
					 									'name' => $row['name'],
					 									'noVotes' => $votes,
					 									'persent' => $allVotes



					);

			$count += 1;
			
		
	}
	
	echo json_encode($all_data);
	

}



?>