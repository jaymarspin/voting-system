<?php
if(isset($_GET['role'])){
	$role = $_GET['role'];
	$id = $_GET['id'];
	include("connection.php");
	$conn = new connector();
	$conn = $conn->conn();
	if($role == "admin"){

	}else{
		$q = "SELECT * FROM voters WHERE voters_id = '$id'";
		$accept = $conn->query($q);
		$accept3 = $conn->query($q);
		if($accept->num_rows > 0){
			$voted = false;
			while ($row = mysqli_fetch_array($accept3)) {
				if($row['voted'] == true){
					$voted = true;
				}
			}


			if($voted == false){
					$pass = false;
				$notyet = false;
				while ($row = mysqli_fetch_array($accept)) {
					$q2 = "SELECT * FROM elections WHERE id = ".$row['election_id']."";
					$accept2 = $conn->query($q2);
					
					while ($row2 = mysqli_fetch_array($accept2)) {
						if($row2['concluded'] == ""){
							$notyet = true;
						}else{
							$now = date("y-m-d");
						$end = $row2['concluded'];
						
						$date = date("y-m-d", strtotime($end));
						$now = strtotime($now);
						$date = strtotime($date);

						if($now < $date){
							$pass = true;
						}
						}
						
					}
				}	


				if($pass == true){
					$fullName = "";
					$id = "";
					$d = -1;
					$accept = $conn->query($q);
					while ($row = mysqli_fetch_array($accept)) {
						
					$fullName = ucfirst($row['fname'])." ".ucfirst($row['lname']);	
					$id = $row['election_id']; 
					$d = $row['id'];
					}
					echo "success---".$fullName."---".$id."---".$d;
				}else{
					if($notyet == true){
						echo "The Voting has not been authorize to begin";
					}else{
						echo "sorry election for you have concluded already!";
					}
					
				}
			}else{
				echo "sorry you already have voted, Thank oyu";
			}




			

			
		}else{
			echo "No account found!";
		}
		

	}
}
else{
	echo "error occured";
}


?>