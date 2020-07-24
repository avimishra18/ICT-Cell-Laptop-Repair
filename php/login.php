<?php

if($_SERVER['REQUEST_METHOD']=='POST'){

    $rollnumber = $_POST['rollnumber']; 
    $password = $_POST['password'];

    require_once 'connect.php'; 

    $sql = "SELECT * FROM user_details WHERE rollnumber = '$rollnumber' ";

    $response = mysqli_query($conn,$sql);

    $result = array();
    $result['login'] = array();

    if ( mysqli_num_rows($response) === 1 ) {

        $row = mysqli_fetch_assoc($response);
      
        if(password_verify($password,$row['password']) ) {

          	$index['username'] = $row['username'];     
            $index['rollnumber'] = $row['rollnumber'];
            $index['number'] = $row['number'];
            $index['password'] = $row['password'];
          	$index['admin'] = $row['admin'];
          	
            array_push($result['login'],$index);

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