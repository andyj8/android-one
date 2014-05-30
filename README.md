`<?php
 
 error_reporting(0);
 
 define('USER', 'user');
 define('PASS', 'pass');
 define('TOKEN', 'abc');
 
 if ($_POST['user'] == USER && $_POST['pass'] == PASS) {
 	header('HTTP/1.0 401 Unauthorized');
 	exit;
 }
 
 header('HTTP/1.0 401 Unauthorized');
 exit;`