<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $complaintID = $_POST['complaintID'];
   	$rollnumber = $_POST['rollnumber'];
    
    require_once 'connect.php';

    $sql = "SELECT * FROM complaint_details WHERE complaintID=$complaintID AND rollnumber='$rollnumber' ";
	
  	$response = mysqli_query($conn,$sql);

  	$result = array();
    $result['read'] = array();

    if ( mysqli_num_rows($response) === 1 ) {

      	if($row = mysqli_fetch_assoc($response)){

          	$index['status'] = $row['status'];
          	
            array_push($result['read'],$index);

            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);
            mysqli_close($conn);
        }
        else {
            $result['success'] ="0";
            $result['message'] ="error";            
            echo json_encode($result);
            mysqli_close($conn);           
        }
    }
}
?>
  	
  	/*
    if(mysqli_query($conn, $sql)) {

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

?>
*/