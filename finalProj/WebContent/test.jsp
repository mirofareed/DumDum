<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.example.DAO.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
/* The whole thing */
.custom-menu {
	display: none;
	z-index: 10000000000000000000000000000;
	position: absolute;
	overflow: hidden;
	border: 1px solid #CCC;
	white-space: nowrap;
	font-family: sans-serif;
	background: #FFF;
	color: #333;
	border-radius: 5px;
	padding: 0;
}

/* Each of the items in the list */
.custom-menu li {
	padding: 8px 12px;
	cursor: pointer;
	list-style-type: none;
}

.custom-menu li:hover {
	background-color: #DEF;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>

		<%
			LinkedHashMap<Integer, String> files = UserRegDAO.getfiles(Integer
					.parseInt(request.getParameter("folder_id")));
			session.setAttribute("folderidme",
					Integer.parseInt(request.getParameter("folder_id")));
			for (int i = 0; i < files.values().size(); i++) {
				if (i % 3 == 0) {
		%>
		<tr>
			<%
				}
			%>
			<td><a href=""> <img src="images/myfile.png" class="fileme"
					file-id="<%=files.keySet().toArray()[i]%>"><span>
						<p style="color: blue"><%=files.values().toArray()[i]%></p>
				</span></a></td>

			<%
				if ((i + 1) % 3 == 0) {
			%>
		</tr>
		<%
			}
			}
		%>
		<%
			if(request.getParameter("perm")!=null){
				if(request.getParameter("perm").equals("2")){
		%>
		<form method="post" action="upload" name="upform"
			enctype="multipart/form-data">
			<table>
				<input type="file" name="uploadfile" size="50">
				<input type="hidden" name="todo" value="upload">
				<input type="submit" name="Submit"
					style="color: black; width: 107px; margin-top: -80px; background: white url(./assets/css/images/22.ico) no-repeat scroll 5px center;"
					value="Upload File">
				<span></span>
				<br>
				<br>
			</table>
		</form>
		<%
			}
			}
		%>
		</tr>
	</table>
	<ul class='custom-menu'>
		<%
			if(request.getParameter("perm")!=null){
			if(request.getParameter("perm").equals("2")){
		%>


		<li data-action="first">Open</li>
		<li data-action="second">delete</li>
		<li data-action="third" data-toggle="modal" data-target="#update">rename</li>
		<li data-action="fourth">download</li>

		<%
			} else{
		%>
		<li data-action="first">Open</li>
		<li data-action="fourth">download</li>
		<%
			}}
			else{
		%>

		<li data-action="first">Open</li>
		<li data-action="second">delete</li>
		<li data-action="third" data-toggle="modal" data-target="#update">rename</li>
		<li data-action="fourth">download</li>

		<%
			}
		%>
	</ul>
	<!-- Scripts -->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/skel.min.js"></script>
	<script src="assets/js/skel-viewport.min.js"></script>
	<script src="assets/js/util.js"></script>
	<script src="assets/js/control.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js">
		
	</script>
</body>
</html>