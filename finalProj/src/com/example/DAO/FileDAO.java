package com.example.DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FileDAO {
	public static String deletemobFile(String userName, String fileName,
			String type) {
		int id = 0;
		String url = new String();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			ResultSet res2 = st
					.executeQuery("select server_url from files where file_name='"
							+ fileName + "';");

			while (res2.next()) {
				url = res2.getString("server_url");
			}
			File file = new File(url);
			file.delete();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int flag = deleteDBFile(userName, fileName, type);

		if (flag != 0)
			return "true";

		else
			return "false";

	}

	public static int deleteDBFile(String userName, String fileName, String type) {
		int flag = 0;
		if (type.equals("File")) {
			int id = 0;

			try {
				Class.forName("org.sqlite.JDBC");
				Connection con = DriverManager
						.getConnection("jdbc:sqlite:mydb");
				Statement st = con.createStatement();
				ResultSet res = st
						.executeQuery("select id from files where file_name='"
								+ fileName + "';");

				while (res.next()) {
					id = res.getInt("id");
				}
				PreparedStatement preparedStatement = con
						.prepareStatement("delete from files where id=?");
				// Parameters start with 1
				preparedStatement.setInt(1, id);
				flag = preparedStatement.executeUpdate();
				PreparedStatement preparedStatement2 = con
						.prepareStatement("delete from user_files where file_id=?");
				// Parameters start with 1
				preparedStatement2.setInt(1, id);
				flag = preparedStatement2.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} else {

			int id = 0;
			ResultSet res;
			try {
				Class.forName("org.sqlite.JDBC");
				Connection con = DriverManager
						.getConnection("jdbc:sqlite:mydb");
				Statement st = con.createStatement();
				res = st.executeQuery("select id from folders where folder_name='"
						+ fileName + "';");
				while (res.next()) {
					id = res.getInt("id");
				}
				PreparedStatement preparedStatement = con
						.prepareStatement("delete from folders where id=?");
				// Parameters start with 1
				preparedStatement.setInt(1, id);
				flag = preparedStatement.executeUpdate();
				System.out.println("FIRST FLAG   " + flag);
				PreparedStatement preparedStatement2 = con
						.prepareStatement("delete from user_folders where folder_ID=?");
				// Parameters start with 1
				preparedStatement2.setInt(1, id);
				flag = preparedStatement2.executeUpdate();
				System.out.println("SECOND FLAG    " + flag);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return flag;

	}

	// ===========================================================================
	public static String renamemobFile(String userName, String fileName,
			String type, String newFileName) {
		System.out.println("/=========================     " + type);
		String flag = renameDBFile(userName, fileName, type, newFileName);

		if (flag.equals("true"))
			return "true";

		else
			return "false";

	}

	public static String renameDBFile(String userName, String fileName,
			String type, String newFileName) {
		System.out.println(type);
		if (type.equals("Directory")) {
			int id = 0;
			try {
				Class.forName("org.sqlite.JDBC");
				Connection con = DriverManager
						.getConnection("jdbc:sqlite:mydb");
				Statement st = con.createStatement();
				ResultSet res = st
						.executeQuery("select id from folders where folder_name='"
								+ fileName + "';");

				while (res.next()) {
					id = res.getInt("id");
				}
				System.out.println("rename folder id =    " + id);
				PreparedStatement preparedStatement = con
						.prepareStatement("update folders set folder_name=? where id=?");
				// Parameters start with 1
				preparedStatement.setString(1, newFileName);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
				// js.put("result", new Boolean(true));
				return "true";
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// return js.toString();

		} else {
			int id = 0;
			try {
				Class.forName("org.sqlite.JDBC");
				Connection con = DriverManager
						.getConnection("jdbc:sqlite:mydb");
				Statement st = con.createStatement();
				ResultSet res = st
						.executeQuery("select id from files where file_name='"
								+ fileName + "';");

				while (res.next()) {
					id = res.getInt("id");
				}
				ResultSet res1 = st
						.executeQuery("select mobile_url from files where id='"
								+ id + "';");
				String mb_url = null;
				while (res1.next()) {
					mb_url = res.getString("mobile_url");
				}
				ResultSet res2 = st
						.executeQuery("select server_url from files where id='"
								+ id + "';");
				String srv_url = null;
				while (res1.next()) {
					srv_url = res.getString("server_url");
				}
				File fil = new File(mb_url);
				String parent = fil.getParent();
				File fil2 = new File(srv_url);
				String par = fil.getParent();

				System.out.println(parent);
				PreparedStatement preparedStatement = con
						.prepareStatement("update files set file_name=?,mobile_url=?,server_url=? where id=?");
				// Parameters start with 1
				preparedStatement.setString(1, newFileName);
				preparedStatement.setString(2, parent + "/" + newFileName);
				preparedStatement.setString(3, par + "/" + newFileName);
				preparedStatement.setInt(4, id);
				preparedStatement.executeUpdate();
				// js.put("result", new Boolean(true));
				return "true";
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// return js.toString();
			// return "false";
		}
		return "false";
	}

	public static void deleteFile(String name) {
		try {
			int id = 0;
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			ResultSet res = st
					.executeQuery("select id from files where file_name='"
							+ name + "';");

			while (res.next()) {
				id = res.getInt("id");
			}
			PreparedStatement preparedStatement = con
					.prepareStatement("delete from files where id=?");
			// Parameters start with 1
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			PreparedStatement preparedStatement2 = con
					.prepareStatement("delete from user_files where file_id=?");
			// Parameters start with 1
			preparedStatement2.setInt(1, id);
			preparedStatement2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile(int id) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			PreparedStatement preparedStatement = con
					.prepareStatement("delete from files where id=?");
			// Parameters start with 1
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			PreparedStatement preparedStatement2 = con
					.prepareStatement("delete from user_files where file_id=?");
			// Parameters start with 1
			preparedStatement2.setInt(1, id);
			preparedStatement2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static String updateFile(int id, String name) {
		// System.out.println(user.toString());
		// JSONObject js = new JSONObject();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			PreparedStatement preparedStatement = con
					.prepareStatement("update files set file_name=? where id=?");
			// Parameters start with 1
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			// js.put("result", new Boolean(true));
			return "success";
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return js.toString();
		return "failed";
	}

}
