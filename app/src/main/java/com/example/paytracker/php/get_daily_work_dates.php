<?php
$uname=$_GET["uname"];
include 'config.php';
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json ="SELECT work_date FROM tb_payment_history where uname='$uname' GROUP BY work_date;";
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('work_date' => $row['work_date']);
}

echo json_encode($rows);
//Json End
mysqli_close($con);
?>