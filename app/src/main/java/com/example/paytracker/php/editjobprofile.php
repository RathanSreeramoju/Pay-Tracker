<?php
// Details of who can access the web call
include 'config.php';
// Initializing variables using GET method
$companyname=$_GET["companyname"];
$jobtitle=$_GET["jobtitle"];
$salaryperhour=$_GET["salaryperhour"];
$pid=$_GET["pid"];
$uemail=$_GET["uemail"];
$jid=$_GET["jid"];

$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
//Query to update the job profile
$query_dis="update job_table set companyname='$companyname',jobtitle='$jobtitle',salaryperhour='$salaryperhour',pid='$pid' where jid='$jid' ";
//echo $query_dis;
$retval_dis = mysqli_query($con,$query_dis);
mysqli_close($con);
//Displays the message and status
echo '{"message":"Job Profile is Updated successfully.","status":"true"}';

?>