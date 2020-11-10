<?php
include 'config.php';

$uname=$_GET["uname"];
$work_date=$_GET["work_date"];
$start_time=$_GET["start_time"];
$end_time=$_GET["end_time"];
$payment=$_GET["payment"];
$payment_deduct=$_GET["payment_deduct"];
$total=$_GET["total_hours"];
$company=$_GET["company_name"];
$tax=$_GET["tax"];
$sal=$_GET["sal_hour"];
$btime=$_GET["break_time"];

$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");

$query_dis="insert into tb_payment_history(uname,work_date,start_time,end_time,payment,payment_deduct,total_hours,company_name,tax,sal_hour,break_time)
values('$uname','$work_date','$start_time','$end_time',$payment,$payment_deduct,'$total','$company','$tax','$sal','$btime');";
//echo $query_dis;

$retval_dis = mysqli_query($con,$query_dis);

mysqli_close($con);

echo '{"message":"Data is added successfully.","status":"true"}';

?>