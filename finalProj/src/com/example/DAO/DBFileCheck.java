package com.example.DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class DBFileCheck extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String res = "false";
		response.setContentType("text/html");
		String str = request.getParameter("flag");
		UserRegDAO urd = new UserRegDAO();
		PrintWriter out = response.getWriter();
		System.out.println("//*********************    " + str);
		// System.out.println((str != null));
		// System.out.println(str.equals("get_folders"));
		if (str != null) {
			if (str.equals("delete")) {
				try {
					FileDAO.deleteFile(Integer.parseInt(request
							.getParameter("fileid")));
					System.out.println("finished");
					response.sendRedirect("success.jsp");
					// out.println(res);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (str.equals("mobdelete")) {
				try {
					String flag = FileDAO.deletemobFile(
							request.getParameter("userName"),
							request.getParameter("fileName"),
							request.getParameter("type"));
					JSONObject js = new JSONObject();
					js.put("result", flag);
					out.println(js.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (str.equals("mobrename")) {
				try {
					String flag = FileDAO.renamemobFile(
							request.getParameter("userName"),
							request.getParameter("fileName"),
							request.getParameter("type"),
							request.getParameter("newFileName"));
					System.out.println(flag);
					JSONObject js = new JSONObject();
					js.put("result", flag);
					out.println(js.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (str.equals("update")) {
				res = FileDAO.updateFile(
						Integer.parseInt(request.getParameter("fileid")),
						request.getParameter("newname"));
				System.out.println(res);
				response.sendRedirect("success.jsp");
				// out.println(res);
			}
			if (str.equals("add_folder")) {
				File f = new File("/home/miro/final/uploaded/"
						+ request.getSession().getAttribute("userName") + "/"
						+ request.getParameter("foldname") + "/");
				if (!f.exists())
					f.mkdir();
				UserRegDAO.insertfolder(
						request.getParameter("foldname"),
						null,
						"/home/miro/final/uploaded/"
								+ request.getParameter("userName"),
						request.getParameter("userName"),
						"/home/miro/final/uploaded");
				response.sendRedirect("success.jsp");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
