package com.example.action;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.example.DAO.UserRegDAO;

public class RegistrationServlet extends HttpServlet {
	private static Pattern pattern;
	private static Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String email;
	private String phone;
	private String confirm;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		userName = req.getParameter("userName");
		password = req.getParameter("password");
		confirm = req.getParameter("confirm");
		email = req.getParameter("email");
		phone = req.getParameter("phone");
		System.out.println(userName + "    " + password + "  " + confirm
				+ "   " + email + "   " + phone);
		UserRegDAO dao = new UserRegDAO();
		String check;
		if (validate()) {
			try {
				check = dao.insertData(userName, password, phone, email,
						confirm, "creator");
				System.out.println("registration   " + check);
				JSONObject js = new JSONObject(check);
				boolean checkusr = (boolean) js.get("result");
				resp.sendRedirect("login.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			resp.sendRedirect("/Registration");

		}
	}

	public static boolean validate(String hex) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}

	public boolean validate() {
		boolean result = true;
		if (email == null || email.length() < 1) {
			result = false;
		}
		if (!validate(email)) {
			result = false;
		}
		if (phone == null || phone.length() < 1) {
			result = false;

		}
		if (userName == null || userName.length() < 1) {
			result = false;

		}
		if (password == null || password.length() < 1) {
			result = false;
		}
		if (confirm == null || confirm.length() < 1) {
			result = false;
		}
		if (password.equals(confirm)) {
			result = false;
		}
		return result;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
