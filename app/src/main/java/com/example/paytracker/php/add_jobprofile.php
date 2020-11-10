<?php
include 'config.php';

$companyname=$_GET["companyname"];
$jobtitle=$_GET["jobtitle"];
$salaryperhour=$_GET["salaryperhour"];
$pid=$_GET["pid"];
$uemail=$_GET["uemail"];


$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");

$query_dis="insert into job_table(companyname,jobtitle,salaryperhour,pid,uemail) values('$companyname','$jobtitle',$salaryperhour,$pid,'$uemail');";
//echo $query_dis;
$retval_dis = mysqli_query($con,$query_dis);
mysqli_close($con);
echo '{"message":"Job Profile is added successfully.","status":"true"}';

?>