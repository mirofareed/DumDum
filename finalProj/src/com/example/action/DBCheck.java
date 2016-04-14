package com.example.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.DAO.UserRegDAO;

@SuppressWarnings("serial")
public class DBCheck extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		String res = "false";
		response.setContentType("text/html");
		String str = request.getParameter("flag");
		UserRegDAO urd = new UserRegDAO();
		PrintWriter out = response.getWriter();
		System.out.println("//*********************    " + str);
		// System.out.println((str != null));
		// System.out.println(str.equals("get_folders"));
		if (str != null) {
			if (str.equals("upload")) {
				InputStream in = request.getInputStream();
				BufferedReader r = new BufferedReader(new InputStreamReader(in));
				PrintWriter pw = new PrintWriter("/home/miro/me");
				BufferedWriter buf = new BufferedWriter(pw);

				while (r.readLine() != null) {
					buf.append(r.readLine());
				}
				String s = buf.toString();
				// savefile(f, dir);
				System.out.println("done");
				out.println(res);
			}
			if (str.equals("reg")) {
				try {
					res = urd.insertData(request.getParameter("userName"),
							request.getParameter("password"),
							request.getParameter("phone"),
							request.getParameter("email"),
							request.getParameter("confirm"));
					System.out.println(res);
					out.println(res);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (str.equals("login")) {
				res = urd.checkUser(request.getParameter("userName"),
						request.getParameter("password"));
				System.out.println(res);
				out.println(res);
			}
			if (str.equals("get_folders")) {
				System.out.println(request.getParameter("userName"));
				// res = urd.checkfolders(request.getParameter("userName"));
				try {
					JSONArray jar = new JSONArray();
					Set<Integer> fold_id = UserRegDAO.checkuserfolder(
							request.getParameter("userName")).keySet();
					ArrayList<String> fold_name = new ArrayList<String>();
					fold_name.addAll(UserRegDAO.checkuserfolder(
							request.getParameter("userName")).values());
					int i = 0;
					for (; i < fold_name.size(); i++) {
						JSONObject jso = new JSONObject();
						jso.put("name", fold_name.get(i));
						jso.put("status", new Boolean(true));
						jso.put("type", "Directory");
						ArrayList<String> files = new ArrayList<String>();
						files.addAll(UserRegDAO.getfiles(
								(Integer) fold_id.toArray()[i]).values());

						jso.put("content", files);
						jar.put(i, jso);
					}
					Set<Integer> file_id = UserRegDAO.checkuser_files(
							request.getParameter("userName")).keySet();
					ArrayList<String> file_name = new ArrayList<String>();
					file_name.addAll(UserRegDAO.checkuser_files(
							request.getParameter("userName")).values());
					// System.out.println(file_id + "  \n   " + file_name);
					int k = i;
					for (int j = 0; j < file_name.size(); j++) {
						JSONObject jso = new JSONObject();
						jso.put("name", file_name.get(j));
						jso.put("status", new Boolean(true));
						jso.put("type", "File");
						ArrayList<String> files = new ArrayList<String>();
						jso.put("content", files);
						jar.put(k, jso);
						k++;
					}
					out.println(jar.toString());
					// System.out.println(jar.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String res = "false";
		response.setContentType("text/html");
		String str = request.getParameter("flag");
		UserRegDAO urd = new UserRegDAO();
		PrintWriter out = response.getWriter();
		System.out.println("//*********************    " + str);
		// System.out.println((str != null));
		// System.out.println(str.equals("get_folders"));
		if (str != null) {
			if (str.equals("upload")) {
				InputStream in = request.getInputStream();
				BufferedReader r = new BufferedReader(new InputStreamReader(in));
				PrintWriter pw = new PrintWriter("/home/miro/me");
				BufferedWriter buf = new BufferedWriter(pw);

				while (r.readLine() != null) {
					buf.append(r.readLine());
				}
				String s = buf.toString();
				// savefile(f, dir);
				System.out.println("done");
				out.println(res);
			}
			if (str.equals("reg")) {
				try {
					res = urd.insertData(request.getParameter("userName"),
							request.getParameter("password"),
							request.getParameter("phone"),
							request.getParameter("email"),
							request.getParameter("confirm"));
					System.out.println(res);
					out.println(res);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (str.equals("login")) {
				res = urd.checkUser(request.getParameter("userName"),
						request.getParameter("password"));
				System.out.println(res);
				out.println(res);
			}
			if (str.equals("get_folders")) {
				System.out.println(request.getParameter("userName"));
				// res = urd.checkfolders(request.getParameter("userName"));
				try {
					JSONArray jar = new JSONArray();
					Set<Integer> fold_id = UserRegDAO.checkuserfolder(
							request.getParameter("userName")).keySet();
					ArrayList<String> fold_name = (ArrayList<String>) UserRegDAO
							.checkuserfolder(request.getParameter("userName"))
							.values();
					System.out.println(fold_id + "  \n   " + fold_name);
					for (int i = 0; i < fold_name.size(); i++) {
						JSONObject jso = new JSONObject();
						jso.put("name", fold_name.get(i));
						jso.put("type", "Directory");
						jso.put("status", new Boolean(true));
						ArrayList<String> files = (ArrayList<String>) UserRegDAO
								.getfiles((Integer) fold_id.toArray()[i])
								.values();
						jso.put("content", files);
						jar.put(i, jso);
					}
					System.out.println("ggfyvgrdhg   " + jar.toString());
					out.println(jar.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void savefile(String f, String dir) {
		URL url;
		try {
			url = new URL(f);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream("/home/miro/downloaded");

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
