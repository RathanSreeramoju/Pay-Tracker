<?php
$date = date("Y-m-d");
$email = $_GET["email"];

$msg = $_GET["msg"];


include 'config.php';
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");

$query_json = "INSERT INTO send_notification(uname msg,date) VALUES ('$email',$msg','$date')";
$retval_json = mysqli_query($con,$query_json);

$query_json = "SELECT fcm_id FROM send_notification where uname='$uname'";
$retval_json = mysqli_query($con,$query_json);
$fcm_token="";
while($row = mysqli_fetch_assoc($retval_json)) {
    $fcm_token = $row['fcm_id'];
}

$url = "https://fcm.googleapis.com/fcm/send";

    $token = $fcm_token;
    $serverKey = '';

    $notification = array('Email' =>$email , 'message' => $msg,  'date' => $date, 'sound' => 'default', 'badge' => '1');

   $arrayToSend = array('to' => $token, 'data' => $notification,'priority'=>'high');
    $json = json_encode($arrayToSend);
    $headers = array();
    $headers[] = 'Content-Type: application/json';
    $headers[] = 'Authorization: key='. $serverKey;
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST,"POST");
    curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
    curl_setopt($ch, CURLOPT_HTTPHEADER,$headers);
    //Send the request
    $response = curl_exec($ch);
    //Close request
    if ($response === FALSE) {
    //die('FCM Send Error: ' . curl_error($ch));
    }
    curl_close($ch);
    ?>