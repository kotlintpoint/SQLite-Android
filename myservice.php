<?php
	$host="localhost";
	$username="root";
	$password="";
	$db_name="tops";
	
	$mysqli = new mysqli($host, $username, $password, $db_name);
	
	if(mysqli_connect_errno()) 
	{  
		echo "Error: Could not connect to database.";  
		exit;
	}
	
	
	if(isset($_REQUEST["selectempall"]))
	{
		//echo '{"id":"1","name":"Rahul","mobile":"7894561230","email":"rahul@gmail.com"}';
		$sql = "select * from employee";
		$result = mysqli_query($mysqli, $sql) or die("Error in Selecting " . mysqli_error($mysqli));
 	
		while($row =mysqli_fetch_assoc($result))
		{
			$emparray[] = $row;
		}
		echo json_encode($emparray);
	}
	
	if(isset($_REQUEST["insertemp"]))
	{
		$name=$_REQUEST['name'];
		$mobile=$_REQUEST['mobile'];
		$email=$_REQUEST['email'];
		
		$sql="insert into employee (name, mobile, email) values ('$name', '$mobile', '$email')";		
		$count=mysqli_query($mysqli, $sql);
		if($count>0)
			echo "Success";
		else
			echo "Fail";
	}
	//http://buddybaba.com/nthsrtm/myservice.php?assignmentupload&filename=test
	
?>