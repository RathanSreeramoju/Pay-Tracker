<?php

include 'config.php';
$uname=$_GET["uname"];
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json ="SELECT company_name FROM tb_payment_history WHERE uname='$uname' GROUP by company_name;" ;
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('company_name' => $row['company_name']);
}

echo json_encode($rows);
//Json End
mysqli_close($con);
?>
