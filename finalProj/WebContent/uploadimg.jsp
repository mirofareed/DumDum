<%@page import="com.example.action.ManipulateImage"%>
<%@page import="com.example.DAO.*"%>
<%@page import="java.io.File"%>
<%@page import="java.util.*"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String imgEncodedStr = request.getParameter("file");
	String fileName = request.getParameter("fileName");
	System.out.println("File name: " + fileName);
	if (imgEncodedStr != null) {
		if (request.getParameter("fileDate") != null) {
			
			System.out.println((UserRegDAO.checkPath(
					request.getParameter("userName"),
					request.getParameter("filePath"))));
			if (!UserRegDAO.checkPath(request.getParameter("userName"),
					request.getParameter("filePath"))) {
				JSONObject js = new JSONObject();
				js.put("result", "Failure");
				out.println(js.toString());
				return;
			}
			 
			String furl = UserRegDAO.geturl(fileName);
			File f = new File(furl);
			Date d = new Date(f.lastModified());
			Date d1 = new Date(request.getParameter("fileDate"));
			System.out.println(d + "    *****     " + d1);
			System.out
					.println("//===================   " + d.after(d1));
			if (d.after(d1)) {
				JSONObject js = new JSONObject();
				js.put("result", "download");
				out.println(js.toString());
			} else {
				ManipulateImage.convertStringtoImage(imgEncodedStr,
						fileName, request.getParameter("filePath"),
						request.getParameter("type"),
						request.getParameter("userName"),
						request.getParameter("fileDate"));
				System.out.println("Inside if");
				JSONObject js = new JSONObject();
				js.put("result", "uploaded");
				out.println(js.toString());
			}
		} else {
			ManipulateImage.convertStringtoImage(imgEncodedStr,
					fileName, request.getParameter("filePath"),
					request.getParameter("type"),
					request.getParameter("userName"),
					request.getParameter("fileDate"));
			System.out.println("Inside if");
			JSONObject js = new JSONObject();
			js.put("result", "uploaded");
			out.println(js.toString());
		}
	} else {
		System.out.println("Inside else");
		out.print("File is empty");
	}
%>