<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){
	
	$rollnumber = $_POST['rollnumber'];
	$username = $_POST['username'];
	$password = $_POST['password'];
	$number = $_POST['number'];	
	
	$password = password_hash($password, PASSWORD_DEFAULT);
	require_once 'connect.php';
	
	$sql = "INSERT INTO user_details(rollnumber,username,password,number) VALUES ('$rollnumber','$username','$password','$number')";
			
	if(mysqli_query($conn,$sql)){
		$result["success"]= "1";
		$result["message"]= "success";
		
		echo json_encode($result);
		mysqli_close($conn);
	}
	else{
		$result["success"]= "0";
		$result["message"]= "error";
		
		echo json_encode($result);
		mysqli_close($conn);
	}
		
}
?>