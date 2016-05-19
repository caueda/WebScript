<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="themes/default/style.min.css" />
	<link rel="stylesheet" href="primeui/theme.css" />
	<link rel="stylesheet" href="primeui/primeui-min.css" />
	<script src="scripts/jquery-1.12.0.js"></script>
	<script src="scripts/jquery-ui.js"></script>	
	<script type="text/javascript" src="primeui/primeui-min.js"></script>
	<script type="text/javascript" src="primeui/primeelements.js"></script>
	<script type="text/javascript" src="primeui/x-tag-core.min.js"></script>

	
	<script>
	  $(function () {
		  $('#mb1').puislidemenu();
	  });
	</script>
<title>PRIMEUI MENUS</title>
</head>
<body>
	<ul id="mb1" >
	 <li ><a>Root 1</a>
	    <ul >
	        <li><a>Child 1</a></li>
	        <li><a>Child 2</a></li>
	    </ul >
	 </li >
	 <li><a>Root 2</a></li>
	 <li><a>Root 3</a></li>
	</ul >
</body>
</html>