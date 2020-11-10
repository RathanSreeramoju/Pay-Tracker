<?php

include 'config.php';
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json ="SELECT * from tb_pt_taxes";
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('id' => $row['pid'],'province_name' => $row['province_name'],'tax_percentage' => $row['tax_percentage']);
}

echo json_encode($rows);
//Json End
mysqli_close($con);
?>