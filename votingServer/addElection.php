<?php



if(isset($_GET['name'])){
	$name = $_GET['name'];
	$year = $_GET['year'];
	$dateS = $_GET['dateS'];
	$dateE = $_GET['dateE'];
	$date = strtotime(date("m/d/Y"));
	$started = date("m/d/Y",strtotime($dateS));
	$end = date("m/d/Y",strtotime($dateE));
	$started = strtotime($started);
	
	$end = strtotime($end);
	if($started < $date || $end < $date){
		echo "date error!";
		}else{
			if($started > $end){
				echo "date error!";
			}else{
				include("connection.php");

	$con = new connector();
	$con = $con->conn();
	$year = intval($year);

	$names = explode("*_*", $name);
	$tmp = "";
	$num = 0;

	while ($num < sizeof($names)) {
	
		if($num < (sizeof($names)) - 1){
			$tmp = $tmp."".strval($names[$num])." ";
		}else{
			$tmp = $tmp."".strval($names[$num]);
		}
		
		$num += 1;
	}
	$tmp = trim($tmp);
	$tmp = stripcslashes($tmp);
	$q = "INSERT INTO elections(name,year,dateS,dateE) VALUES('".$tmp."',".$year.",'".$dateS."','".$dateE."')";
	$con = $con->query($q);
	if($con){
		$q = "SELECT id,name FROM elections WHERE name = '".$tmp."'";
		$conn = new connector();
		$conn = $conn->conn();
		$rower = mysqli_query($conn, $q);
		$response = 0;
		while ($row = mysqli_fetch_array($rower)) {
			$response = $row['id'];

		}
		echo "success!-".$response;
		
	}else{
		echo "bad!";
	}
	}

			}
		
	


}


?>