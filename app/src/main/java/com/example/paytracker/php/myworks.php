<?php
// Initializing variables using GET method
$uname =$_GET['uname'];
// Details of who can access the webcall
include 'config.php';
// connection to database
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
//Query lists the work data from tb_payment_history based on uname
$query_json ="SELECT * from tb_payment_history where uname='$uname' ";

$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('id' => $row['id'],'uname' => $row['uname'],'payment' => $row['payment'],'payment_deduct' => $row['payment_deduct'],'start_time' => $row['start_time'],'end_time' => $row['end_time'],'work_date' => $row['work_date'],'total_hours' => $row['total_hours'],'company_name' => $row['company_name'],'tax' => $row['tax'],'sal_hour' => $row['sal_hour'],'break_time' => $row['break_time']);
}
//displays output in json format
echo json_encode($rows);
//Json End
mysqli_close($con);
?>