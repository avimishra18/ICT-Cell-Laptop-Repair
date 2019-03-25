<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $complaintID = $_POST['complaintID'];
   	$rollnumber = $_POST['rollnumber'];
    $status = $_POST['status'];
  	$date = date('Y-m-d h:i:s');
    
    require_once 'connect.php';
  
  	if($status=='1')
    	$sql = "UPDATE complaint_details SET status='$status',repaireddate='$date' WHERE rollnumber='$rollnumber' AND complaintID=$complaintID ";
  	else
      	$sql = "UPDATE complaint_details SET status='$status' WHERE rollnumber='$rollnumber' AND complaintID=$complaintID ";

    if(mysqli_query($conn, $sql)) {

      	 if(mysqli_affected_rows($conn)){

          		$result["success"] = "1";
         	 	$result["message"] = "success";

         		echo json_encode($result);
         		mysqli_close($conn);
         }
    }
	else{
		  $result["success"] = "0";
	      $result["message"] = "error!";
 		  echo json_encode($result);

 		  mysqli_close($conn);
    }
}
?>