<?php
// Details of who can access the webcall
include 'config.php';
// connection to database
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
//Query lists tha data from tb_payment_history
$query_json ="SELECT * from tb_payment_history";
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('id' => $row['id'],'work_date' => $row['work_date'],'payment' => $row['payment']);
}
//displays output in json format
echo json_encode($rows);
//Json End
mysqli_close($con);
?>