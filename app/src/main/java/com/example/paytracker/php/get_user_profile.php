<?php

include 'config.php';
$uname=$_GET["uname"];
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json ="SELECT * from user_table where email='$uname'" ;
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('firstname' => $row['firstname'],'lastname' => $row['lastname'],'dob' => $row['dob'],'pass' => $row['pass'],'profile_pic' => $row['profile_pic'],'email' => $row['email']);
}

echo json_encode($rows);
//Json End
mysqli_close($con);
?>