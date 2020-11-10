<?php

include 'config.php';
$uname=$_GET["uname"];
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json ="SELECT * FROM job_table INNER JOIN tb_pt_taxes ON job_table.pid = tb_pt_taxes.pid where uemail='$uname'" ;
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('jid' => $row['jid'],'companyname' => $row['companyname'],'jobtitle' => $row['jobtitle'],'salaryperhour' => $row['salaryperhour'],'province_name' => $row['province_name'],'tax_percentage' => $row['tax_percentage'],'uemail' => $row['uemail']);
}

echo json_encode($rows);
//Json End
mysqli_close($con);
?>



