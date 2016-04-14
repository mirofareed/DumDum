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
import com.example.form.RegistrationForm;

public class UserRegAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("HEY IAM HERE ");
		HttpSession ses = request.getSession(true);

		RegistrationForm registerForm = (RegistrationForm) form;
		String userName = registerForm.getUserName();
		String password = registerForm.getPassword();
		UserRegDAO dao = new UserRegDAO();
		String check = dao.checkUser(userName, password);
		System.out.println("wejcynui   " + check);
		JSONObject js = new JSONObject(check);
		boolean checkusr = (boolean) js.get("result");
		if (checkusr) {
			// ses.putValue("folder", dao.checkfolders(userName));
			ses.setAttribute("userName", userName);
			return mapping.findForward("done");
		} else {
			return mapping.findForward("error");
		}

	}
}