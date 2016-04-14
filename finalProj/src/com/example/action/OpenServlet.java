package com.example.action;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.DAO.UserRegDAO;

public class OpenServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("fileid"));
		System.out.println("//=============================   " + id);
		String fileName = UserRegDAO.geturl(id);
		File f = new File(fileName);
		System.out.println(fileName);
		// f.setReadOnly();
		int length = 0;
		ServletOutputStream op = resp.getOutputStream();
		ServletContext context = getServletConfig().getServletContext();
		String mimetype = context.getMimeType(fileName);
		resp.setHeader("Content-Disposition", "inline:filename=" + fileName);
		resp.setContentType((mimetype != null) ? mimetype
				: "application/octet-stream");
		resp.setContentLength((int) f.length());
		resp.setHeader("Content-Disposition", "attachment:filename=" + fileName);
		// complete file downloading using buffering
		byte[] bbuf = new byte[1024];
		DataInputStream in = new DataInputStream(new FileInputStream(f));

		while ((in != null) && ((length = in.read(bbuf)) != -1)) {
			op.write(bbuf, 0, length);
		}
		in.close();
		op.flush();
		op.close();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
