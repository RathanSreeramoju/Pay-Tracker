<?php
// Details of who can access the web call
include 'config.php';
// connection to database
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
//Query lists the data from tb_pt_taxes
$query_json ="SELECT * from tb_pt_taxes";
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('id' => $row['pid'],'province_name' => $row['province_name'],'tax_percentage' => $row['tax_percentage']);
}
//displays output in json format
echo json_encode($rows);
//Json End
mysqli_close($con);
?>