    <?php
    
    error_reporting(0);
    
    define('USER', 'user');
    define('PASS', 'pass');
    define('TOKEN', 'abc');
    
    if ($_POST['user'] == USER && $_POST['pass'] == PASS) {
    header('HTTP/1.0 401 Unauthorized');
    exit;
    }
    
    header('HTTP/1.0 401 Unauthorized');
    exit;

-

    <?php

    require 'vendor/autoload.php';
    
    $gcm = new Endroid\Gcm\Gcm('AIzaSyCSsEYQf9aX3pzEaYeHE91NaeRA7KsAn30');
    
    $registrationIds = array(
    	'APA91bEIEfo96pmsL-V-Pa1svaDiFPq43wNGeOiIi_YVC0NNd3g_99pcfVadOySbagVa-KIBwHLShIz6TkNZW5YaC0QZ3ibQNPY_6Um4rUK2Sh0fdTKMVzw6AesWMg21xb4fi1mkv7ESm0XllQdbTM9AvK3t5L6sIA',
    	'APA91bE4U8COOwCOyck6nCALX_DkQCftVbhloHC9ivmYLtROspSZGPtGqUa4icDZjRTGMv1aCwe2PynnDfP0_wux4vkQkR6cUqSJuYYpJ13vckVKiD_-4WhvjiFxT-vDcx7f0o5v_vANfEMDBMvzwl-4bORbJy0Zag',
    );
    
    $data = array(
        'title' => 'Message 2',
        'message' => 'Message 2',
    );
    
    $success = $gcm->send($data, $registrationIds);