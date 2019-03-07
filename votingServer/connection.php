<?php

class connector{

	
	function conn(){
		$host = "localhost";
		$user = "root";
		$password = "";
		$db = "voting";
		return new mysqli($host,$user,$password,$db);
		
	}


}



?>