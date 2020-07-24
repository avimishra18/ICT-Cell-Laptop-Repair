<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){
	
	$rollnumber = $_POST['rollnumber'];
	$model = $_POST['model'];
	$serialnumber = $_POST['serialnumber'];
	$issue = $_POST['issue'];
	
	require_once 'connect.php';
	
	$sql = "INSERT INTO complaint_details(rollnumber,model,serialnumber,issue) VALUES ('$rollnumber','$model','$serialnumber','$issue')";
			
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