<?php
// Details of who can access the web call
            include 'config.php';

// Initializing variables using GET method
$emailid=$_GET["emailid"];
$pwd="";
               $con=mysqli_connect($hostname, $username, $password,$dbname);
            // Query lists the data based on email id from user_table
            $query_json = "SELECT * from user_table where email='$emailid';";

          //  echo $query_json;
            $query_pwd = "SELECT pass from user_table where email='$emailid';";
            $result_pwd = mysqli_query($con,$query_pwd);
            $result = mysqli_query($con,$query_json);
            while($row = mysqli_fetch_assoc($result_pwd)) {
                $pwd=$row['pass'];
                echo $pwd;
            }

            $row = mysqli_fetch_array($result);
            if(!$row)
            {
                echo '{"message":"Sorry Invalid Email/Username","status":"false"}';
            }else{

 $to = $emailid;

         $subject = "Password Request";

         $message = "Your password is <b>$pwd</b>";


         $header = "From:rathansreeramoju@gmail.com \r\n";

         $header .= "MIME-Version: 1.0\r\n";
         $header .= "Content-type: text/html\r\n";

         $retval = mail ($to,$subject,$message,$header);

      //  echo $retval;
                echo '{"message":"Password sent to your register email successfully.","status":"true"}';
            }
?>