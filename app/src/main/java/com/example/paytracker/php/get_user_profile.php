<?php
// Details of who can access the webcall
include 'config.php';
// Initializing variables using GET method
$uname=$_GET["uname"];
// connection to database
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
//Query lists the data of user_table based on email
$query_json ="SELECT * from user_table where email='$uname'" ;
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('firstname' => $row['firstname'],'lastname' => $row['lastname'],'dob' => $row['dob'],'pass' => $row['pass'],'profile_pic' => $row['profile_pic'],'email' => $row['email']);
}
//displays output in json format
echo json_encode($rows);
//Json End
mysqli_close($con);
?>