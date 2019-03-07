<?php

include("connection.php");


	$con = new connector();
	$con = $con->conn();

	$q = "SELECT * FROM elections ORDER BY ID desc";
	$tmpCon = $con;
	$con = $con->query($q);
	$row_data = array();
	while ($row = mysqli_fetch_array($con)) {

		$d = $row['id'];
		$q2 = "SELECT * FROM voters WHERE election_id = $d";
		$tmp = $tmpCon->query($q2);
		$num = 0;
		$concluded = "";
		while ($row2 = mysqli_fetch_array($tmp)) {
			$num += 1;
		}
		if($row['concluded'] == ""){
			$concluded = "pending ";
		}else{
			$concluded = "On going";
			$now = date("y-m-d");
			$end = $row['concluded'];
			
			$date = date("y-m-d", strtotime($end));
			$now = strtotime($now);
			$date = strtotime($date);
			if($now > $date){
				$concluded = "Concluded at $end";
			}else{

			}

		}

		$row_data[] = $arrayName = array('id' =>  $row['id'],
										'name' => $row['name'],
										'year' => $row['year'],
										'dateS' => $row['dateS'],
										'dateE' => $row['dateE'],
										'voters' => $num,
										'concluded' => $concluded

									);
	}

	$all_data = json_encode($row_data);
	
	echo $all_data;

	




?>