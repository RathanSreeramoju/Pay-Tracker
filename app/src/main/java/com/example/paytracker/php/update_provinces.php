<?php

include 'config.php';

$id=$_GET["id"];
$provinces_name=$_GET["provinces_name"];
$tax=$_GET["tax"];

$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_dis="update tb_pt_taxes set tax_percentage='$tax' where pid=$id;";

$retval_dis = mysqli_query($con,$query_dis);

mysqli_close($con);

echo '{"message":"Data is updated successfully.","status":"true"}';
?>