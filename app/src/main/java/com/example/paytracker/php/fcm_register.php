<?php
// Details of who can access the web call
include 'config.php';
// Initializing variables using GET method
$email=$_GET["email"];
$fcm_id=$_GET["fcm_id"];

$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
//Query to list the data for given username
$query_json = "SELECT * from user_table where uname='$uname';";
            $result = mysqli_query($con,$query_json);

            $row = mysqli_fetch_array($result);
            if(!$row)
            {
// Query to insert data to usertable
$query_dis="insert into user_table (uname, fcm_id) values('$uname', '$fcm_id');";
$retval_dis = mysqli_query($con,$query_dis);
            }else{
             $query_dis="update user_table set fcm_id='$fcm_id' where uname='$uname';";
$retval_dis = mysqli_query($con,$query_dis);
            }

mysqli_close($con);

echo '{"message":"User Registered successfully.","status":"true"}';

?>