<?php
include 'config.php';

$companyname=$_GET["companyname"];
$jobtitle=$_GET["jobtitle"];
$salaryperhour=$_GET["salaryperhour"];
$pid=$_GET["pid"];
$uemail=$_GET["uemail"];
$jid=$_GET["jid"];

$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");

$query_dis="update job_table set companyname='$companyname',jobtitle='$jobtitle',salaryperhour='$salaryperhour',pid='$pid' where jid='$jid' ";
//echo $query_dis;
$retval_dis = mysqli_query($con,$query_dis);
mysqli_close($con);
echo '{"message":"Job Profile is Updated successfully.","status":"true"}';

?>