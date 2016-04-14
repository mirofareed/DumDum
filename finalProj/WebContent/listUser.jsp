<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>

<%@ page import="com.example.DAO.DBUser"%>
<%@ page import="com.example.form.User"%>
<%@ page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.sql.StatementEvent"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.mysql.jdbc.Driver"%>
<%@page import="java.sql.DriverManager"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jstl/sql_rt" prefix="sql"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Show All Users</title>
</head>
<body>
	<table border=1>
		<thead>
			<tr>
				<th>User Id</th>
				<th>User Name</th>
				<th>phone</th>
				<th>Email</th>
				<th>status</th>


				<th colspan=2>Action</th>
			</tr>
		</thead>
		<tbody>


			<%
				if (request.getAttribute("users") != null) {
					System.out.println("notNull");

				} else {
					System.out.println("from list user : Null");
				}
			%>

			<%
				List<User> lu = new DBUser().getAllUsers();
				for (int i = 0; i < lu.size(); i++) {
			%>

			<tr>

				<td><%=lu.get(i).getUserid()%></td>
				<td><%=lu.get(i).getUserName() %></td>
				<td><%=lu.get(i).getphone()%></td>
				<td><%=lu.get(i).getEmail()%></td>
				<td><%=lu.get(i).getstatus() %></td>
				<td><% session.setAttribute("action","delete"); %>
				<a href="insert?action=delete&userid=<%=lu.get(i).getUserid()%>">Delete</a></td>
				<td><% session.setAttribute("action2","edit"); %>
				<a href="UpdateData.jsp?action2=edit&userid=<%=lu.get(i).getUserid()%>">edit</a>	
				</td>
			</tr>
		
			<%
				}
			%>
		</tbody>
	</table>
	
	
	
    
  
</body>
</html>