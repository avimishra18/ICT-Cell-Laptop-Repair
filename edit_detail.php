<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $rollnumber = $_POST['rollnumber'];
  	$username = $_POST['username'];
    $number = $_POST['number'];
    $password = $_POST['password'];

    $password = password_hash($password, PASSWORD_DEFAULT);    
    require_once 'connect.php';

    $sql = "UPDATE user_details SET username='$username', number='$number', password='$password' WHERE rollnumber='$rollnumber' ";

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


