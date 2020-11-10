<?php
/Permissions given to the folder and if there is no folder named images, then create one with that name
if (!is_dir('images/')) {
    mkdir('images/', 0777, true);
}
// Details of who can access the webcall
include 'config.php';
// Initializing variables using Post method
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
// connection to database
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
//Query lists the data from user_table based on given email
$query_json = "SELECT * from user_table where email=$email;";
            $result = mysqli_query($con,$query_json);
            $row = mysqli_fetch_array($result);
            if($row)
            {

            $picimg_url="http://paytracker.ca/PayTracker/".$picimg_url;
// Query updates the data to user_table .
$query_dis="update user_table set firstname=$fname,lastname=$lname,email=$email,dob=$dob,pass=$pass,profile_pic='$picimg_url' where email=$email";
//echo $query_dis;

$retval_dis = mysqli_query($con,$query_dis);

mysqli_close($con);

echo '{"message":"Updated successfully.","status":"true"}';
}else{
   echo '{"message":"UserName is already exist.","status":"false"}';
}
?>