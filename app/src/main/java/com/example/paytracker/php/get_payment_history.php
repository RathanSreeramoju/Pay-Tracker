<?php
// Initializing variables using GET method
$start_date = $_GET['start_date'];
$end_date = $_GET['end_date'];
$uname =$_GET['uname'];
// Details of who can access the web call
include 'config.php';
// connection to database
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
//Query lists the data of work between the given dates
$query_json ="SELECT * from tb_payment_history where uname='$uname' and work_date BETWEEN CAST('$start_date' AS DATE) AND CAST('$end_date' AS DATE)";
//json format array
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('uname' => $row['uname'],'payment' => $row['payment'],'payment_deduct' => $row['payment_deduct'],'start_time' => $row['start_time'],'end_time' => $row['end_time'],'work_date' => $row['work_date'],'total_hours' => $row['total_hours'],'company_name' => $row['company_name'],'tax' => $row['tax'],'sal_hour' => $row['sal_hour'],'break_time' => $row['break_time']);
}
//displays output in json format
echo json_encode($rows);
//Json End
mysqli_close($con);
?>
