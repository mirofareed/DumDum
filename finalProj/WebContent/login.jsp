<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-html" prefix="html"%>
<%@page import="com.example.action.FBConnection"%>
<!DOCTYPE HTML>
<!--
        Astral by HTML5 UP
        html5up.net | @n33co
        Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>Dum Dum</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="assets/css/main.css" />
<noscript>
	<link rel="stylesheet" href="assets/css/noscript.css" />
</noscript>
<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
</head>
<body>
	<%
		FBConnection fbConnection = new FBConnection();
	%>
	<!-- Wrapper-->
	<div id="wrapperl">

		<!-- Nav -->

		<nav id="nav">
			<a href="#me" class="fa fa-user active" style="color: blue"><span>Login</span></a>
			<a href="Reg.jsp" class="fa fa-user-plus" style="color: blue"><span>Signup</span></a>
			<!--<a href="#reg" class="fa fa-users"><span>Login</span></a>-->
		</nav>

		<!-- Main -->
		<div id="main">

			<!-- Me -->
			<article id="me" class="panel">
				<header>
					<div class="wrapperl">
						<div class="container">

							<html:form action="/myfiles" method="post">

								<table>
									<tr>

										<input type="text" name="userName" size="50"
											placeholder="username" />
									</tr>
									<tr>

										<input type="password" name="password" size="30"
											placeholder="password" />
									</tr>
									<tr>
										<td><br> <input type="image"
											src="images/login-button.png" alt="Submit" width="20"
											height="70"></td>
										<div></div>
										<td><br> <a href="<%=fbConnection.getFBAuthUrl()%>">
												<img src="./assets/css/images/facebookloginbutton.png" />
										</a></td>
									</tr>
								</table>
							</html:form>


							<ul class="bg-bubbles">
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
							</ul>
						</div>
				</header>

			</article>
		</div>
	</div>

	<!-- Scripts -->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/skel.min.js"></script>
	<script src="assets/js/skel-viewport.min.js"></script>
	<script src="assets/js/util.js"></script>

	<script src="assets/js/main.js"></script>
</body>
</html>