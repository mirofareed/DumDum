package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.form.*;

public class DBUser {

	// private Connection connection = DB.getConnection();
	//
	// public DBUser() {
	// connection = DB.getConnection();
	// }

	public void addUser(User user) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			PreparedStatement preparedStatement = con
					.prepareStatement("insert into user_data(User_name,Phone,Password,Confirm_Password,Email,Status) values (?, ?, ?, ?, ?,?)");
			// Parameters start with 1
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getphone());
			preparedStatement.setString(3, user.getpassword());
			preparedStatement.setString(4, user.getconfirm_password());
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setString(6, user.getstatus());
			preparedStatement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteUser(int userId) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			PreparedStatement preparedStatement = con
					.prepareStatement("delete from user_data where id=?");
			// Parameters start with 1
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String updateUser(User user) {
		System.out.println(user.toString());
		JSONObject js = new JSONObject();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			PreparedStatement preparedStatement = con
					.prepareStatement("update user_data set User_name=?, Phone=?,Password=?,Confirm_Password=?,Email=?,Status=? where id=?");
			// Parameters start with 1
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getphone());
			preparedStatement.setString(3, user.getpassword());
			preparedStatement.setString(4, user.getconfirm_password());
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setString(6, user.getstatus());
			preparedStatement.setInt(7, user.getUserid());
			preparedStatement.executeUpdate();
			js.put("result", new Boolean(true));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			js.put("result", new Boolean(false));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return js.toString();
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select * from user_data");
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getInt("id"));
				user.setUsername(rs.getString("User_name"));
				user.setphone(rs.getString("Phone"));
				user.setpassword(rs.getString("Password"));
				user.setconfirm_password(rs.getString("Confirm_Password"));
				user.setEmail(rs.getString("Email"));
				user.setstatus(rs.getString("Status"));

				users.add(user);
				// System.out.print(users);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}

	public User getUserById(int userid) {
		User user = new User();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			PreparedStatement preparedStatement = con
					.prepareStatement("select * from user_data where id=?");
			preparedStatement.setInt(1, userid);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				user.setUserid(rs.getInt("id"));
				user.setUsername(rs.getString("User_name"));
				user.setEmail(rs.getString("Email"));
				user.setstatus(rs.getString("Status"));
				user.setpassword(rs.getString("Password"));
				user.setconfirm_password(rs.getString("Confirm_Password"));
				user.setphone(rs.getString("Phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}
}