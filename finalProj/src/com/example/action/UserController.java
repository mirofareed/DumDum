package com.example.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.DAO.DBUser;
import com.example.DAO.UserRegDAO;
import com.example.form.User;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String INSERT = "success.jsp";
	private static String LIST_USER = "listUser.jsp";
	private static String UPDATE_DATA = "UpadteData.jsp";

	private DBUser dao;

	public UserController() {
		super();
		dao = new DBUser();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		String action = (String) request.getSession().getAttribute("action");
		// String action2 = (String)
		// request.getSession().getAttribute("action2");

		// System.out.println("OFFFFFFFFFFFFFFFFFFFF    "+action);

		if (action.equalsIgnoreCase("delete")) {

			int userid = Integer.parseInt(request.getParameter("userid"));
			dao.deleteUser(userid);
			System.out.println(userid);
			forward = LIST_USER;

			// request.setAttribute("users", dao.getAllUsers());
		} else if (action.equalsIgnoreCase("edit")) {
			forward = UPDATE_DATA;
			int userid = Integer.parseInt(request.getParameter("userid"));
			User user = dao.getUserById(userid);
			request.setAttribute("user", user);
		} else if (action.equalsIgnoreCase("listUser")) {
			forward = LIST_USER;
			request.setAttribute("users", dao.getAllUsers());
		} else {
			forward = INSERT;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserRegDAO urd = new UserRegDAO();
		DBUser db = new DBUser();
		String action = (String) session.getAttribute("action");
		System.out.println(session.getAttribute("action"));
		User user = new User();
		user.setUsername(request.getParameter("userName"));
		user.setstatus(request.getParameter("status"));
		user.setpassword(request.getParameter("password"));
		user.setconfirm_password(request.getParameter("confirm_password"));
		user.setphone(request.getParameter("phone"));
		user.setEmail(request.getParameter("email"));

		String result = new String();
		if (action.equals("insert")) {
			try {
				result = urd.insertData(user.getUserName(), user.getpassword(),
						user.getphone(), user.getEmail(),
						user.getconfirm_password(), user.getstatus());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equals("edit")) {
			user.setUserid(Integer.parseInt(request.getParameter("userid")));
			result = db.updateUser(user);
		}
		System.out.println(result);
		System.out.println("here stop ");

		response.sendRedirect(LIST_USER);

	}
}