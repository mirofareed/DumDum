<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.DAO.*"%>
<%@ page import="com.example.form.User"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-html" prefix="html"%>
<!DOCTYPE HTML>
<!--
        Astral by HTML5 UP
        html5up.net | @n33co
        Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>Dum Dum</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link type="text/css"
	href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="assets/css/main.css" />

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<noscript>
	<link rel="stylesheet" href="assets/css/noscript.css" />
</noscript>
<head>
<title>LIGHTBOX EXAMPLE</title>
<style>
.black_overlay {
	display: none;
	position: relative;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 80);
}

.white_content {
	display: none;
	position: absolute;
	top: 25%;
	left: 25%;
	width: 50%;
	height: 50%;
	padding: 16px;
	border: 16px solid #3399CC;
	background-color: white;
	z-index: 1002;
	overflow: auto;
	border-radius: 25px;
	width: 500px;
	height: 400px;
}

h3 {
	color: blue;
	margin-left: 65px;
}

.popover-title {
	background-color: rgba(0, 0, 255, 0.3);
}

.modal-body {
	width: 400px;
	height: 700px;
	padding-left: 70px;
}

.modal-content {
	width: 430px;
	padding-left: 0px;
	margin-left: 60px;
	height: 660px;
}

.modal-title {
	margin-left: 155px;
	color: blue;
}

#add {
	position: absolute;
	height: 50px;
	width: 96px
}

#delete {
	position: absolute;
	height: 79px;
	right: 0px;
	top: 200px;
	width: 95px;
}

#rename {
	position: absolute;
	height: 80px;
	width: 94px;
	right: 0px;
	top: 279px;
}

#close {
	position: absolute;
	right: 22px;
	top: 20px;
}

#file {
	width: 386px;
}

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

/* The whole thing */
.foldermenu {
	display: none;
	z-index: 1000;
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
.foldermenu li {
	padding: 8px 12px;
	cursor: pointer;
	list-style-type: none;
}

.foldermenu li:hover {
	background-color: #DEF;
}
</style>
</head>

</head>
<header> </header>
<body>

	<div>
		<img src="./assets/css/images/1.png">
	</div>
	<!-- Wrapper-->
	<div id="wrapperl">

		<!-- Nav -->

		<nav id="nav">
			<a href="#work" class="icon fa-folder active" style="color: blue"><span>My
					Folders</span></a> <a href="#contact" class="icon fa-envelope"
				style="color: blue"><span>Contact</span></a>
		</nav>

		<!-- Main -->
		<div id="main">

			<!-- Work -->
			<article id="work" class="panel">

				<header>
					<table>
						<tr>
							<td><h2><%=session.getAttribute("userName")%></h2></td>
							<td><span></span></td>
							<td></td>
							<td style="float: right;"><input type="button"
								value="Add File"
								style="width: 90px; font-size: 12px; border-radius: 25px; background-color: white; color: blue; height: 40;"
								data-toggle="modal" data-target="#newfile"> <input
								type="button" value="Create Folder"
								style="width: 90px; font-size: 12px; border-radius: 25px; background-color: white; color: blue; height: 40;"
								data-toggle="modal" data-target="#newfolder"></td>
						</tr>
					</table>
				</header>
				<section>
					<div class="row">
						<table>
							<tr>
								<%
									String usr = (String) session.getAttribute("userName");
																																																																																																																																																																																																																																																																																																																																																																																																																																																																												 LinkedHashMap<Integer,String> result =UserRegDAO.checkuserfolder(usr);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																											int no = result.values().size();
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																System.out.println("HERE   " + no);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																for (int i = 0; i < no; i++) {
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																int idme =(Integer) result.keySet().toArray()[i];
								%>
								<input type="hidden" name="hidid" value=<%=idme%>>
								<td><a style="position: relative" class="testme"
									href="test.jsp?folder_id=<%=idme%>" folder-id=<%=idme%>> <img
										src="images/folder.png" width="97" height="97"> <input
										type="hidden" name="folder" id="idfold"
										value="<%=(Integer) result.keySet().toArray()[i]%>"> <span>
											<div style="position: absolute; left: 80px; bottom: 0px;"><%=result.values().toArray()[i]%></div>
									</span>
								</a></td>
								<%
									}
								%>
								<%
									LinkedHashMap<Integer,String> res =UserRegDAO.checkuser_files(usr);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				int num = res.values().size();
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				System.out.println("HERE   " + num);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				for (int i = 0; i < num; i++) {
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																					int idfe =(Integer) res.keySet().toArray()[i];
								%>
								<td><a style="position: relative" class="fileme"
									file-id="<%=res.keySet().toArray()[i]%>"> <img
										src="images/myfile.png"><span>
											<div
												style="position: absolute; width: 120px; word-wrap: break-word;"><%=res.values().toArray()[i]%></div>
									</span>
								</a></td>
								<%
									}
								%>
								<div id="light" class="white_content">
									<br> <br>
									<form method="post" action="upload?subfolder=true>"
										name="upform" enctype="multipart/form-data">

										<input id="file" type="file" name="uploadfile" size="150"
											style="margin-left: -2px; margin-top: -50px;"> <input
											type="hidden" id="todo" value="<script> fold</script>"
											name="myfolder2"> <input type="submit" name="Submit"
											style="border-radius: 25px; height: 40; margin-left: 30px; width: 100px;"
											value="Add File">

									</form>
									<br> <br>
									<div></div>
									<div id="getmyfiles"></div>

									<a href="javascript:void(0)"
										onclick="document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">
										<span id="close" class="glyphicon glyphicon-remove"></span>
									</a>
								</div>
								<div id="fade" class="black_overlay"></div>




							</tr>
						</table>
					</div>
				</section>
			</article>

			<!-- Contact -->
			<article id="contact" class="panel">
				<header>
					<h2>Contact Me</h2>
				</header>
				<form action="success.jsp">
					<input type="text" value="Add Comment"
						style="height: 171px; width: 668px"> <input type="submit"
						value="send" style="margin-left: 250px">
				</form>
				<p style="float: left; color: black">Send Mail:
					dumdumnewproject@gmail.com</p>
			</article>

		</div>



	</div>

	<div class="modal fade" id="newfile" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="height: 300px;">
			<div class="modal-content" style="height: 300px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add Folder</h4>
				</div>
				<div class="modal-body">
					<form method="post" action="upload" name="upform"
						enctype="multipart/form-data">
						<table>
							<input type="file" name="uploadfile" size="80">
							<input type="hidden" name="todo" value="upload">
							<input type="submit" name="Submit"
								style="color: black; width: 107px; margin-top: -80px; background: white url(./assets/css/images/22.ico) no-repeat scroll 5px center;"
								value="Upload File">
							<span></span>
							<br>
							<br>
						</table>
					</form>
				</div>

			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="height: 300px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add</h4>
				</div>





				<div class="modal-body">
					<form method="POST" action="insert" id="myguestform">
						<%
							session.setAttribute("action", "insert");
						%>
						User ID : <input type="text" readonly="readonly" type="hidden"
							name="userId" value="" /> <br /> First Name : <input
							type="text" name="firstName" value="" /> <br /> Last Name : <input
							type="text" name="lastName" value="" /> <br /> phone : <input
							type="text" name="phone" value="" /> <br /> <input
							type="submit" value="Submit" />
					</form>


				</div>


			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="update" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="height: 300px;">
			<div class="modal-content" style="height: 300px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Rename file</h4>
				</div>
				<div class="modal-body">
					<form action="DBFile?flag=update" method="post">
						file name:<input type="text" name="newname"> <input
							type="hidden" name="fileid" id="renamepop">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save
							changes</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="newfolder" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="height: 300px;">
			<div class="modal-content" style="height: 300px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add Folder</h4>
				</div>
				<div class="modal-body">
					<form
						action="DBFile?flag=add_folder&userName=<%=session.getAttribute("userName")%>"
						method="post">
						folder name:<input type="text" name="foldname">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save
							changes</button>
					</form>
				</div>

			</div>
		</div>
	</div>

	<ul class='custom-menu'>
		<li data-action="first">Open</li>
		<li data-action="second">delete</li>
		<li data-action="third" data-toggle="modal" data-target="#update">rename</li>
		<li data-action="fourth">download</li>
	</ul>

	<ul class='foldermenu'>
		<li data-action="once" data-toggle="modal" data-target="#guestinvite">AddGuest</li>
	</ul>

	<div class="modal fade" id="guestinvite" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="height: 300px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Invite Guest</h4>
				</div>
				<div class="modal-body">
					<form action="Mail.jsp" method="post">
						Email:<input type="text" name="guest"> <input
							type="hidden" name="fileid" id="inviteguest"> <select
							name="item">
							<option value=1>read-only</option>
							<option value=2>read/write</option>
						</select>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">invite</button>
					</form>
				</div>

			</div>
		</div>
	</div>
	<!-- Scripts -->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/skel.min.js"></script>
	<script src="assets/js/skel-viewport.min.js"></script>
	<script src="assets/js/util.js"></script>
	<script src="assets/js/main.js"></script>
	<script src="assets/js/control.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js">
		
	</script>
	<script>
		$('[data-toggle="popover"]').popover({
			html : 'true',
			placement : 'right'
		});
		$('#myModal').on('shown.bs.modal', function() {
			$('#myInput').focus();
		});
	</script>
</body>
</html>