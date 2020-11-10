<?php
// Details of who can access the web call
include 'config.php';
// Initializing variables using GET method
$companyname=$_GET["companyname"];
$jobtitle=$_GET["jobtitle"];
$salaryperhour=$_GET["salaryperhour"];
$pid=$_GET["pid"];
$uemail=$_GET["uemail"];

// connection to database
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
//Main query used to insert job profile
$query_dis="insert into job_table(companyname,jobtitle,salaryperhour,pid,uemail) values('$companyname','$jobtitle',$salaryperhour,$pid,'$uemail');";
//echo $query_dis;
$retval_dis = mysqli_query($con,$query_dis);
mysqli_close($con);
//Displays the message and status ad true
echo '{"message":"Job Profile is added successfully.","status":"true"}';

?>