package com.example.form;

public class User {

	private int userid;
	private String userName;
	private String email;
	private String status;
	private String password;
	private String confirm_password;
	private String phone;

	public void setUsername(String u) {
		userName = u;
	}

	public String getUserName() {
		return userName;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getstatus() {
		return status;
	}

	public void setstatus(String status) {
		this.status = status;
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getconfirm_password() {
		return confirm_password;
	}

	public void setconfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	public String getphone() {
		return phone;
	}

	public void setphone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", UserName=" + userName + ",status="
				+ status + ",password=" + password + ",confirm_password="
				+ confirm_password + ",phone=" + phone + ",email=" + email
				+ "]";
	}

}