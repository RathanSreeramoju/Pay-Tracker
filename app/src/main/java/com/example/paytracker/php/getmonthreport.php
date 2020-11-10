<?php

include 'config.php';

$email=$_POST['email'];
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json ="SELECT * from tb_payment_history where uname='$email' LIMIT 7";
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('id' => $row['id'],'work_date' => $row['work_date'],'payment' => $row['payment']);
}

echo json_encode($rows);
//Json End
mysqli_close($con);
?>
