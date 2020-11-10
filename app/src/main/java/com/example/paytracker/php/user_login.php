<?php
// Initializing variables using GET method
$uname=$_GET["uname"];
$pwd=$_GET["pwd"];
            // Details of who can access the webcall
            include 'config.php';
            // connection to database
            $con=mysqli_connect($hostname, $username, $password,$dbname);
            mysqli_query ($con,"set character_set_results='utf8'");
            //Query lists the data from user_table for given email and pwd
            $query_json = "SELECT * from user_table where email='$uname' and pass='$pwd';";
            $result = mysqli_query($con,$query_json);

            $row = mysqli_fetch_array($result);
            if(!$row)
            {
                echo '{"message":"Invalid username / password","status":"false"}';
            }else{


                echo '{"message":"Login successful","status":"true"}';

            }
?>