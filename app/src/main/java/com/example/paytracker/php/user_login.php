<?php
$uname=$_GET["uname"];
$pwd=$_GET["pwd"];

            include 'config.php';

            $con=mysqli_connect($hostname, $username, $password,$dbname);
            mysqli_query ($con,"set character_set_results='utf8'");
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