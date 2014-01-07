<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src="../assets/js/jquery-1.10.2.min.js"></script>
<script src="../assets/js/jquery.jqGrid.min.js"></script>
<script src="../assets/js/grid.locale-en.js"></script>

<link rel="stylesheet" type="text/css" media="screen"
	href="../assets/css/styles.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="../assets/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="../assets/css/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Categories</title>
</head>
<body>
	<h1>Categories</h1>
	<table id="list">
		<tr>
			<td></td>
		</tr>
	</table>
	<div id="pager"></div>
	<a class="button" id="add">Add</a>
	<a class="button" id="edit">Edit</a>
	<a class="button" id="delete">Delete</a>
	<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
	<script>
		$(function() {
			$("#list").jqGrid(
					{
						url : "getJson",
						datatype : "json",
						mtype : "GET",
						jsonReader: {
							root: "result",
							total: "total"
						},
						colNames : [ "Id", "Name", "Description"],
						colModel : [ {
							name : "id",
							width : 55,
							editable:false,
							editoptions:{readonly:true,size:10}
						}, {
							name : "name",
							width : 150,
							editable : true,
							editoptions:{size:10}
						}, {
							name : "description",
							width : 200,
							editable : true,
							editoptions:{size:10}
						}],
						pager : "#pager",
						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						sortname : "id",
						sortorder : "desc",
						viewrecords : true,
						gridview : true,
						autoencode : true,
						caption : "Categories List",
						editurl:"edit"
					});
			$("#add").click(function(){
				jQuery("#list").jqGrid('editGridRow',"new",{height:280,reloadAfterSubmit:false});
			});
			$('#edit').on("click", function() {
				var gr = jQuery("#list").jqGrid('getGridParam','selrow');
				if( gr != null ) jQuery("#list").jqGrid('editGridRow',gr,{height:280,reloadAfterSubmit:false});
				else alert("Please Select Row");
			});
			$("#delete").click(function(){
				var gr = jQuery("#list").jqGrid('getGridParam','selrow');
				if( gr != null ) jQuery("#list").jqGrid('delGridRow',gr,{reloadAfterSubmit:true});
				else alert("Please Select Row to delete!");
			});
		});
	</script>
</body>
</html>