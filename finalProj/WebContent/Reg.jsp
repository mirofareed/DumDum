

<%@page import="org.apache.jasper.tagplugins.jstl.core.Out"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-html" prefix="html"%>
<%@page import=" java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="com.example.DAO.UserRegDAO"%>

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
<style>
.error {
	color: red;
	font-size: 10px;
}
</style>
</head>
<body>
	<%
		String usernameError = "";
		String emailError = "";
		String phoneError = "";
		String passwordError = "";
		String confirmPasswordError = "";
		boolean flag = false;
		if (request.getMethod() == "POST") {
			String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

			String uname = request.getParameter("userName");
			String upass = request.getParameter("password");
			String uconfirm = request.getParameter("confirm");
			String umail = request.getParameter("email");
			String uphone = request.getParameter("phone");
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(umail);
			if (uname.equals("")) {

				usernameError = "This field is required";
				flag = true;
			}

			if (umail.equals("")) {
				emailError = "This field is required";
				flag = true;
			}

			if (uphone.equals("")) {
				phoneError = "This field is required";
				flag = true;
			}

			if (upass.equals("")) {
				passwordError = "This field is required";
				flag = true;
			}
			if (uconfirm.equals("")) {
				confirmPasswordError = "This field is required";
				flag = true;
			} else if (!upass.equals(uconfirm)) {
				confirmPasswordError = "Passwords not matched";
				flag = true;
			}
			if (emailError.equals("") && !matcher.matches()) {
				emailError = "This Field id not an email";
				flag = true;
			}
			if (emailError.equals("") && !(UserRegDAO.checkemail(umail))) {
				emailError = "This Email is already exist";
				flag = true;
			}
			System.out.println(!uname.equals("") + "   "
					+ !UserRegDAO.checkUserexsit(uname));
			if (!uname.equals("") && !UserRegDAO.checkUserexsit(uname)) {
				usernameError = "User is Already Exist";
				flag = true;
			}
			if (!flag) {
				UserRegDAO urd = new UserRegDAO();
				String check = urd.insertData(uname, upass, uphone, umail,
						uconfirm, "creator");
				response.sendRedirect("login.jsp");
			}
		}
	%>
	<!-- Wrapper-->
	<div id="wrapperl">

		<!-- Nav -->

		<nav id="nav">
			<a href="login.jsp" class="fa fa-user " style="color: blue"><span>Sign
					Up</span></a> <a class="fa fa-user-plus active" style="color: blue"><span>test</span></a>
			<!--<a href="#reg" class="fa fa-users"><span>Login</span></a>-->
		</nav>

		<!-- Main -->
		<div id="main">

			<!-- Me -->

			<!-- Work -->

			<article id="work" class="panel">
				<!--<section>-->
				<div class="panelcontainer">
					<form method="post">
						<table style="color: white">
							<tr>
								<td><span id="usr" style="text-align: center">UserName
										*:</span></td>
								<td><input type="text" name="userName" /> <span
									class="error"> <%
 	out.println(usernameError);
 %>
								</span></td>
							</tr>
							<tr>
								<td>Email *:</td>
								<td><input type="text" name="email" /> <span class="error">
										<%
											out.println(emailError);
										%>
								</span></td>
							</tr>
							<tr>
								<td>Phone :</td>
								<td><input type="text" name="phone" /><span class="error">
										<%
											out.println(phoneError);
										%>
								</span></td>
							</tr>
							<tr>
								<td>Password *:</td>
								<td><input type="password" name="password" /> <span
									class="error"> <%
 	out.println(passwordError);
 %>
								</span></td>
							</tr>
							<tr>
								<td>Confirm Password *:</td>
								<td><input type="password" name="confirm" /> <span
									class="error"> <%
 	out.println(confirmPasswordError);
 %>
								</span></td>
							</tr>
						</table>
						<input type="submit" value="Sign me Up" />

					</form>
				</div>
				<!--</section>-->
			</article>
		</div>
	</div>
	<%
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		String uname = request.getParameter("userName");
		String upass = request.getParameter("password");
		String uconfirm = request.getParameter("confirm");
		String umail = request.getParameter("email");
		String uphone = request.getParameter("phone");
	%>
	<!-- Scripts -->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/skel.min.js"></script>
	<script src="assets/js/skel-viewport.min.js"></script>
	<script src="assets/js/util.js"></script>
	<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
	<script src="assets/js/main.js"></script>

</body>
</html>
