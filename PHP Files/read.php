<?php 
    require_once 'connect.php';

    //creating a query
    $stmt = $conn->prepare("SELECT complaintID,rollnumber,model,serialnumber,issue,status,complaintdate,repaireddate FROM complaint_details");
 
    //executing the query 
    $stmt->execute();
 
    //binding results to the query 
    $stmt->bind_result($complaintID,$rollnumber,$model,$serialnumber,$issue,$status,$complaintdate,$repaireddate);
 
 $products = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 $temp['complaintID'] = $complaintID; 
 $temp['rollnumber'] = $rollnumber; 
 $temp['model'] = $model; 
 $temp['serialnumber'] = $serialnumber; 
 $temp['issue'] = $issue;
 $temp['status'] = $status;
 $temp['complaintdate'] = $complaintdate;
 $temp['repaireddate'] = $repaireddate;
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);