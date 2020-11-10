<?php
if (!is_dir('images/')) {
    mkdir('images/', 0777, true);
}
include 'config.php';

$fname=$_POST["fname"];
$email=$_POST["email"];
$lname=$_POST["lname"];
$dob=$_POST["dob"];
$pass=$_POST["pwd"];


$result = array("success" => $_FILES["file"]["name"]);
$file_path = basename( $_FILES['file']['name']);
$picimg_url='images/'.$file_path;
if(move_uploaded_file($_FILES['file']['tmp_name'], 'images/'.$file_path)) {
    $result = array("success" => "File successfully uploaded");
} else{
    $result = array("success" => "error uploading file");
}

$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json = "SELECT * from user_table where email=$email;";
            $result = mysqli_query($con,$query_json);
            $row = mysqli_fetch_array($result);
            if(!$row)
            {

            $picimg_url="http://paytracker.ca/PayTracker/".$picimg_url;
$query_dis="insert into user_table(firstname,lastname,email,dob,pass,profile_pic) values($fname,$lname,$email,$dob,$pass,'$picimg_url');";
//echo $query_dis;

$retval_dis = mysqli_query($con,$query_dis);

mysqli_close($con);

echo '{"message":"User Registered successfully.","status":"true"}';
}else{
   echo '{"message":"Username is already exist.","status":"false"}';
}
?>