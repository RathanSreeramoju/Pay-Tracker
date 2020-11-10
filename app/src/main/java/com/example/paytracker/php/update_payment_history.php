<?php
// Details of who can access the webcall
include 'config.php';
// Initializing variables using GET method
$uname=$_GET["uname"];
$work_date=$_GET["work_date"];
$start_time=$_GET["start_time"];
$end_time=$_GET["end_time"];
$payment=$_GET["payment"];
$payment_deduct=$_GET["payment_deduct"];
$company_name=$_GET["company_name"];
$tax=$_GET["tax"];
$sal_hour=$_GET["sal_hour"];
$break_time=$_GET["break_time"];
$total_hours=$_GET["total_hours"];
$id=$_GET["id"];

// connection to database
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
//Query to update the work data
$query_dis="update tb_payment_history set start_time='$start_time',end_time='$end_time',total_hours='$total_hours',payment='$payment',payment_deduct='$payment_deduct',
company_name='$company_name',tax='$tax',sal_hour='$sal_hour',break_time='$break_time' where id='$id' ";
//echo $query_dis;
$retval_dis = mysqli_query($con,$query_dis);
mysqli_close($con);
//Displays the message and status
echo '{"message":"User data is Updated successfully.","status":"true"}';

?>