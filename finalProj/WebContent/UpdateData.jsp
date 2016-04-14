<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.example.DAO.DBUser"%>
<%@ page import="com.example.form.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link type="text/css"
	href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="assets/css/main.css" />

<title>Insert title here</title>
</head>
<body>

	<%
		int userid = Integer.parseInt(request.getParameter("userid"));
		System.out.println(userid);
		User usr = new User();
		DBUser dao = new DBUser();
		usr = dao.getUserById(userid);
		System.out.print(userid);
	%>

	<div class="modal-body">
		<form method="POST" action="insert" id="myguestform">
			<%
				session.setAttribute("action", "edit");
			%>

			User ID : <input type="text" readonly="readonly" type="hidden"
				name="userid" value="<%=userid%>" /> <br /> User Name : <input
				type="text" name="userName" value="<%=usr.getUserName()%>" /> <br />
			<br /> phone : <input type="text" name="phone"
				value="<%=usr.getphone()%>" /> <br /> Email : <input type="text"
				name="email" value="<%=usr.getEmail()%>" /> <br /> password : <input
				type="text" name="password" value="<%=usr.getpassword()%>" /> <br />
			Confirm Password : <input type="text" name="confirm_password"
				value="<%=usr.getconfirm_password()%>" /> <br /> Status : <input
				type="text" name="status" value="<%=usr.getstatus()%>" /> <br /> <input
				type="submit" value="Submit" />
		</form>
</body>
</html>