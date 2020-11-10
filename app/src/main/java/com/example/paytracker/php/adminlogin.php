<?php
// Initializing variables using GET method
$uname=$_GET["uname"];
$pwd=$_GET["pwd"];
    // Details of who can access the web call
            include 'config.php';
            // connection to database
            $con=mysqli_connect($hostname, $username, $password,$dbname);
            mysqli_query ($con,"set character_set_results='utf8'");
            //Query which lists the data from admin table where email and pwd exists in database and are same
            $query_json = "SELECT * from admin_table where email='$uname' and password='$pwd';";
            $result = mysqli_query($con,$query_json);

            $row = mysqli_fetch_array($result);
            if(!$row)
            {
                echo '{"message":"Invalid username / password","status":"false"}';
            }else{


                echo '{"message":"Admin Login successful","status":"true"}';

            }
?>