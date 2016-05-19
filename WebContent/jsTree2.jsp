<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="themes/default/style.min.css" />
	<script src="scripts/jquery-1.12.0.js"></script>
	<script src="scripts/jstree.js"></script>
	
	<script>
	  $(function () {
		    $(".search-input").keyup(function() {

		        var searchString = $(this).val();
		        console.log(searchString);
		        $('#jstree').jstree('search', searchString);
		    });
		    // 6 create an instance when the DOM is ready
		    $('#jstree').jstree({
		    	"search": {

		            "case_insensitive": true,
		            "show_only_matches" : true,
					"show_only_matches_children":true

		        },		        
		        'types': {
	            	'icon' :  '/images/folder.png'	                    
	            },
		        "plugins": ["search","types"]
			});
		    // 7 bind to events triggered on the tree
		    $('#jstree').on("changed.jstree", function (e, data) {
		      console.log(data.selected);
		    });
		    // 8 interact with the tree - either way is OK
		    $('button').on('click', function () {
// 		      $('#jstree').jstree(true).select_node('child_node_1');
// 		      $('#jstree').jstree('select_node', 'child_node_1');
// 		      $.jstree.reference('#jstree').select_node('child_node_1');
		    });
		  });
	</script>
<title>jsTree</title>
</head>
<body>
	<div>
	    <input class="search-input form-control"></input>
	</div>
	<div id="jstree">
    <!-- in this example the tree is populated from inline HTML -->
    <ul>
      <li>Root node 1
        <ul>
          <li id="child_node_1">Child node 1</li>
          <li>Child node 2
          	<ul>
          		<li>Filho</li>
          	</ul>
          </li>
        </ul>
      </li>
      <li>Root node 2</li>
    </ul>
  </div>
  <button>Demonstração</button>
</body>
</html>