package com.example.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.DAO.UserRegDAO;

public class MainMenu extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String code = "";

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		code = req.getParameter("code");
		if (code == null || code.equals("")) {
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback.");
		}
		FBConnection fbConnection = new FBConnection();
		String accessToken = fbConnection.getAccessToken(code);

		FBGraph fbGraph = new FBGraph(accessToken);
		String graph = fbGraph.getFBGraph();
		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
		boolean get = UserRegDAO.checkFBFUser(fbProfileData.get("name"),
				fbProfileData.get("id"));
		if (!get) {
			try {
				UserRegDAO.insertFBData(fbProfileData.get("name"),
						fbProfileData.get("id"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		res.sendRedirect("success.jsp");
	}
}