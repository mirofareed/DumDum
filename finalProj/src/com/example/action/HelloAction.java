package com.example.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HelloAction extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type

		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		System.out.println(request.getParameter("folders"));
		out.println("<h1>" + "hey sync its me" + "</h1>");
	}

	public static List<String> stringToList(String input) {
		String[] elements = input.substring(1, input.length() - 1).split(", ");
		List<String> result = new ArrayList<String>(elements.length);
		for (String item : elements) {
			result.add(item);
		}
		return result;
	}

	public static ArrayList<String> getdifferent(
			List<String> Alist, ArrayList<String> Blist) {
		ArrayList<String> finalarr = new ArrayList<String>();
//		ArrayList<String> result = new ArrayList<String>();
//		ArrayList<String> result1 = new ArrayList<String>();
		finalarr.add("Downloads");
		for (int i = 0; i < Alist.size(); i++) {
			if (!Blist.contains(Alist.get(i))) {
				System.out.println("Equals..: " + Alist.get(i));
				finalarr.add(Alist.get(i));
			}
		}
		finalarr.add("Uploads");
		for (int i = 0; i < Blist.size(); i++) {
			if (!Alist.contains(Blist.get(i))) {
				System.out.println("Equals..: " + Blist.get(i));
				finalarr.add(Blist.get(i));
			}

		}
//		finalarr.add(result1);
		return finalarr;
	}

	public static List<String> addFiles(List<String> files, File dir) {
		if (files == null) {
			files = new ArrayList<String>();
		}

		if (!dir.isDirectory()) {
			// files.add(dir.getName());
			return files;
		}

		for (File file : dir.listFiles()) {
			files.add(file.getName());
			addFiles(files, file);
		}
		return files;
	}

	public void downloadFile(String filename, HttpServletResponse res)
			throws FileNotFoundException, IOException {
		ServletOutputStream out = res.getOutputStream();
		String pathName = getServletContext().getRealPath("/" + filename);
		String contentType = getServletContext().getMimeType(pathName);
		if (contentType != null)
			res.setContentType(contentType);
		else
			res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment; filename=\""
				+ pathName + "\"");
		// A FileInputStream is for bytes
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(pathName);
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			int bytesRead;
			while ((bytesRead = fis.read(buf)) != -1)
				out.write(buf, 0, bytesRead);
		} finally {
			if (fis != null)
				fis.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");
		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>" + " me post hey sync its me" + "</h1>");
		String str = "/home/miro/final/Servers/Tomcat v6.0 Server at localhost-config/miro fareed/images";
		// String str
		// ="/home/miro/final/Servers/Tomcat v6.0 Server at localhost (2)-config/images";
		File f = new File(str);
		ArrayList<String> ars = new ArrayList<String>();
		List<String> result = addFiles(ars, f);
		ArrayList<String> arr = (ArrayList<String>) stringToList(request
				.getParameter("folders"));
		ArrayList<String> getdiff = getdifferent(
				 result, arr);
		StringBuilder sb =new StringBuilder();
		for(String s :getdiff){
			sb.append(s);
			sb.append(",");	
		}
		out.println(sb.toString());
//		if (getdiff.get(0).size() != 0) {
//			out.println("true");
//		}
		
		// System.out.println(getdiff.toString());
		// out.println((getdiff.get(0).size() > 0));
		// for (int i = 0; i < arr.size(); i++) {
		// out.println(arr.get(i));
		// }
		// out.println(request.getParameter("folders"));
	}
}
