package com.example.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.example.DAO.UserRegDAO;
import com.example.form.LoginForm;

public class UserLoginAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("HEY IAM HERE ");
		HttpSession ses = request.getSession(true);
		LoginForm logForm = (LoginForm) form;
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirm");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		System.out.println(userName + "    " + password + "  " + confirm
				+ "   " + email + "   " + phone);
		UserRegDAO dao = new UserRegDAO();
		String check = dao.insertData(userName, password, phone, email,
				confirm, "creator");
		System.out.println("registration   " + check);
		JSONObject js = new JSONObject(check);
		boolean checkusr = (boolean) js.get("result");
		if (checkusr) {
			return mapping.findForward("sucess");
		} else {
			return mapping.findForward("fail");
		}
	}
}
