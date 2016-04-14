package com.example.DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegDAO {
	public static boolean checkPath(String username, String path) {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection con;
			con = DriverManager.getConnection("jdbc:sqlite:mydb");
			JSONObject js = new JSONObject();
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select id from user_data where User_name='"
							+ username + "';");
			int uid = 0;
			while (rs.next()) {
				uid = rs.getInt("id");
			}
			if (uid != 0) {
				ResultSet rs1 = st
						.executeQuery("select id from files where mobile_url='"
								+ path + "';");
				int uid1 = 0;
				while (rs1.next()) {
					uid1 = rs.getInt("id");
				}
				if (uid1 != 0) {
					ResultSet rs2 = st
							.executeQuery("select * from user_files where file_id='"
									+ uid1 + "' and user_id='" + uid + "';");
					boolean uname = false;
					while (rs2.next()) {
						uname = true;
					}
					if (uname)
						return true;
				} else
					return false;
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean checkemail(String email) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			JSONObject js = new JSONObject();
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select id from user_data where Email='"
							+ email + "';");
			boolean check = true;
			while (rs.next()) {
				check = false;
			}
			con.close();

			return check;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static boolean checkUserexsit(String user_name) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			JSONObject js = new JSONObject();
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from user_data where User_name='"
							+ user_name + "';");
			boolean check = true;
			while (rs.next()) {
				check = false;
			}
			con.close();
			return check;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static String getFolderName(int id) {
		ResultSet rs = null;
		String name = new String();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			rs = st.executeQuery("select folder_name from folders where id='"
					+ id + "';");
			while (rs.next()) {
				name = rs.getString("folder_name");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}

	public static String getMail(String username) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			JSONObject js = new JSONObject();
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select Email from user_data where User_name='"
							+ username + "';");
			String mail = new String();
			while (rs.next()) {
				mail = rs.getString("Email");
			}
			con.close();
			return mail;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getFileName(int id) {
		ResultSet rs = null;
		String name = new String();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			rs = st.executeQuery("select file_name from files where id='" + id
					+ "';");
			while (rs.next()) {
				name = rs.getString("file_name");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}

	public static ArrayList<String> getsubfolders(int folderid) {
		ArrayList<String> fold = new ArrayList<String>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			// st.execute("insert into user_folders values('miro fareed','videos')");
			ResultSet rs = st
					.executeQuery("select server_url from folders where id= '"
							+ folderid + "';");
			String surl = new String();
			while (rs.next()) {
				surl = rs.getString("server_url");
			}
			// ArrayList<String> fname = new ArrayList<String>();
			ResultSet rf = st
					.executeQuery("select folder_name from folders where parent= '"
							+ surl + "';");

			while (rf.next()) {
				fold.add(rf.getString("folder_name"));
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fold;

	}

	public static String insertfiles(String name, String parent,
			String mob_url, String type, String server_url) {

		JSONObject js = new JSONObject();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			System.out.println("jdbc connection");
			int valu = st
					.executeUpdate("INSERT INTO files(file_name,parent,mobile_url,type,server_url) "
							+ "VALUES('"
							+ name
							+ "','"
							+ parent
							+ "','"
							+ mob_url
							+ "','"
							+ type
							+ "','"
							+ server_url
							+ "');");
			con.close();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
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

	public static String geturl(int id) {
		String file_id = new String();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			ResultSet res = st
					.executeQuery("select server_url from files where id='"
							+ id + "';");

			while (res.next()) {
				file_id = res.getString("server_url");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return file_id;
	}

	public static String geturl(String name) {
		String file_id = new String();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select id from files where file_name='"
							+ name + "';");
			int id = 0;
			while (rs.next()) {
				id = rs.getInt("id");
			}
			ResultSet res = st
					.executeQuery("select server_url from files where id='"
							+ id + "';");

			while (res.next()) {
				file_id = res.getString("server_url");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return file_id;
	}

	public static String getmob_url(String name) {
		String file_id = new String();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select id from files where file_name='"
							+ name + "';");
			int id = 0;
			while (rs.next()) {
				id = rs.getInt("id");
			}
			ResultSet res = st
					.executeQuery("select mobile_url from files where id='"
							+ id + "';");

			while (res.next()) {
				file_id = res.getString("mobile_url");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return file_id;
	}

	public static void walkin(File server_dir, String mob_dir, String user_name) {
		// String pattern = ".mp3";

		File listFile[] = server_dir.listFiles();
		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				String ext = listFile[i].getName().substring(
						listFile[i].getName().indexOf(".") + 1);
				insertfiles(listFile[i].getName(), listFile[i].getParentFile()
						.getName(), mob_dir + "/" + listFile[i].getName(), ext,
						server_dir.getPath() + "/" + listFile[i].getName());
				insertfolder_file(getFileID(listFile[i].getName()),
						getFolderID(listFile[i].getParentFile().getName()));
			}
		}
	}

	public static int getFolderID(String name) {
		ResultSet rs = null;
		int id = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			rs = st.executeQuery("select id from folders where folder_name='"
					+ name + "';");
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static int getFileID(String name) {
		ResultSet rs = null;
		int id = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			rs = st.executeQuery("select id from files where file_name='"
					+ name + "';");
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static String insertfolder_file(int file_id, int folder_id) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			System.out.println("jdbc connection");
			int valu = st
					.executeUpdate("INSERT INTO folder_files(folder_id,file_id) "
							+ "VALUES('" + folder_id + "','" + file_id + "');");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "fail";

	}

	public static String insertfolder(String name, String mob_url,
			String server_url, String user_name, String parent) {
		JSONObject js = new JSONObject();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			System.out.println("jdbc connection");
			int valu = st
					.executeUpdate("INSERT INTO folders(folder_name,parent,mobile_url,server_url) "
							+ "VALUES('"
							+ name
							+ "','"
							+ parent
							+ "','"
							+ mob_url + "','" + server_url + "');");
			ResultSet rs = st
					.executeQuery("select id from user_data where User_name='"
							+ user_name + "';");
			int usr_id = 0;
			while (rs.next()) {
				usr_id = rs.getInt("id");
			}
			ResultSet res = st
					.executeQuery("select id from folders where folder_name='"
							+ name + "';");
			int file_id = 0;
			while (res.next()) {
				file_id = res.getInt("id");
			}
			System.out.println(file_id);
			int valu2 = st
					.executeUpdate("INSERT INTO user_folders(folder_ID,user_ID) "
							+ "VALUES('" + file_id + "','" + usr_id + "');");
			System.out.println("I GOT THE DB");
			js.put("result", new Boolean(true));

			con.close();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return js.toString();

	}

	public static String insertfold(String name, String mob_url,
			String server_url, String user_name, String parent) {
		JSONObject js = new JSONObject();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			System.out.println("jdbc connection");
			int valu = st
					.executeUpdate("INSERT INTO folders(folder_name,parent,mobile_url,server_url) "
							+ "VALUES('"
							+ name
							+ "','"
							+ parent
							+ "','"
							+ mob_url + "','" + server_url + "');");
			con.close();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return js.toString();

	}

	public static boolean checkfileExist(String file_name, String user_name) {
		ArrayList<String> filename = new ArrayList<String>();
		filename.addAll(checkuser_files(user_name).values());
		for (int i = 0; i < filename.size(); i++) {
			if (file_name.equals(filename.get(i)))

				return false;
		}
		return true;
	}

	public static LinkedHashMap<Integer, String> checkuser_files(
			String user_name) {
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			// st.execute("insert into user_folders values('miro fareed','videos')");

			ResultSet ruser = st
					.executeQuery("select id from user_data where User_name='"
							+ user_name + "';");
			int usr = 0;
			while (ruser.next()) {
				usr = ruser.getInt("id");
			}

			ResultSet rs = st
					.executeQuery("select file_id from user_files where user_id= '"
							+ usr + "';");
			ArrayList<Integer> fid = new ArrayList<Integer>();
			while (rs.next()) {
				int id = rs.getInt("file_id");
				fid.add(id);

			}
			// ArrayList<String> fname = new ArrayList<String>();
			for (int i = 0; i < fid.size(); i++) {
				ResultSet rf = st
						.executeQuery("select file_name from files where id= '"
								+ fid.get(i) + "';");
				while (rf.next()) {
					String back = rf.getString("file_name");
					result.put(fid.get(i), back);
				}
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static boolean checkuser(String user_name, String password) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			JSONObject js = new JSONObject();
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from user_data where User_name='"
							+ user_name + "' and Password ='" + password + "';");
			boolean check = false;
			while (rs.next()) {
				check = true;
			}
			con.close();
			return check;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public String checkUser(String user_name, String password) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			JSONObject js = new JSONObject();
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from user_data where User_name='"
							+ user_name + "' and Password ='" + password + "';");
			boolean check = false;
			while (rs.next()) {
				check = true;
			}
			js.put("result", new Boolean(check));
			con.close();
			return js.toString();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject js = new JSONObject();
		try {
			js.put("result", new Boolean(false));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static boolean checkFBFUser(String user_name, String id) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			JSONObject js = new JSONObject();
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from user_facebook where id ='"
							+ id + "';");
			boolean check = false;
			while (rs.next()) {
				check = true;
			}
			js.put("result", new Boolean(check));
			con.close();
			return check;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject js = new JSONObject();
		try {
			js.put("result", new Boolean(false));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String checkfolders(String user_name) {
		LinkedHashMap<Integer, String> result = checkuserfolder(user_name);
		JSONObject js = new JSONObject();
		if (result.values().size() > 0) {
			try {
				js.put("status", new Boolean(true));
				js.put("user_folders", result.values());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			try {
				js.put("status", new Boolean(false));
				js.put("user_folders", result.values());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return js.toString();
	}

	public static LinkedHashMap<Integer, String> getfiles(int folderid) {
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			// st.execute("insert into user_folders values('miro fareed','videos')");
			ResultSet rs = st
					.executeQuery("select file_id from folder_files where folder_id= '"
							+ folderid + "';");
			ArrayList<Integer> fid = new ArrayList<Integer>();
			while (rs.next()) {
				int id = rs.getInt("file_id");
				fid.add(id);

			}
			// ArrayList<String> fname = new ArrayList<String>();
			for (int i = 0; i < fid.size(); i++) {
				ResultSet rf = st
						.executeQuery("select file_name from files where id= '"
								+ fid.get(i) + "';");
				while (rf.next()) {
					String back = rf.getString("file_name");
					result.put(fid.get(i), back);
				}
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public static LinkedHashMap<Integer, String> getmainfiles(int userid) {
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			// st.execute("insert into user_folders values('miro fareed','videos')");
			ResultSet rs = st
					.executeQuery("select file_id from user_files where user_id= '"
							+ userid + "';");
			ArrayList<Integer> fid = new ArrayList<Integer>();
			while (rs.next()) {
				int id = rs.getInt("file_id");
				fid.add(id);

			}
			// ArrayList<String> fname = new ArrayList<String>();
			for (int i = 0; i < fid.size(); i++) {
				ResultSet rf = st
						.executeQuery("select file_name from files where id= '"
								+ fid.get(i) + "';");
				while (rf.next()) {
					String back = rf.getString("file_name");
					result.put(fid.get(i), back);
				}
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public static ArrayList<Integer> getfoldersID(String UserName) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			// st.execute("insert into user_folders values('miro fareed','videos')");
			ResultSet ruser = st
					.executeQuery("select id from user_data where User_name='"
							+ UserName + "';");
			int usr = 0;
			while (ruser.next()) {
				usr = ruser.getInt("id");
			}
			ResultSet rs = st
					.executeQuery("select folder_ID from user_folders where user_ID= '"
							+ usr + "';");
			ArrayList<Integer> fid = new ArrayList<Integer>();
			while (rs.next()) {
				int id = rs.getInt("folder_ID");
				fid.add(id);

			}
			con.close();
			return fid;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static LinkedHashMap<Integer, String> checkuserfolder(
			String user_name) {
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			// st.execute("insert into user_folders values('miro fareed','videos')");

			ResultSet ruser = st
					.executeQuery("select id from user_data where User_name='"
							+ user_name + "';");
			int usr = 0;
			while (ruser.next()) {
				usr = ruser.getInt("id");
			}

			ResultSet rs = st
					.executeQuery("select folder_ID from user_folders where user_ID= '"
							+ usr + "';");
			ArrayList<Integer> fid = new ArrayList<Integer>();
			while (rs.next()) {
				int id = rs.getInt("folder_ID");
				fid.add(id);

			}

			// ArrayList<String> fname = new ArrayList<String>();
			for (int i = 0; i < fid.size(); i++) {
				ResultSet rf = st
						.executeQuery("select folder_name from folders where id= '"
								+ fid.get(i) + "';");
				while (rf.next()) {
					String back = rf.getString("folder_name");
					result.put(fid.get(i), back);
				}
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static String insertuploadData(String name, String parent,
			String mob_url, String type, String server_url, String user_name) {
		JSONObject js = new JSONObject();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
			Statement st = con.createStatement();
			System.out.println("jdbc connection");
			int valu = st
					.executeUpdate("INSERT INTO files(file_name,parent,mobile_url,type,server_url) "
							+ "VALUES('"
							+ name
							+ "','"
							+ parent
							+ "','"
							+ mob_url
							+ "','"
							+ type
							+ "','"
							+ server_url
							+ "');");
			ResultSet rs = st
					.executeQuery("select id from user_data where User_name='"
							+ user_name + "';");
			int usr_id = 0;
			while (rs.next()) {
				usr_id = rs.getInt("id");
			}
			ResultSet res = st
					.executeQuery("select id from files where file_name='"
							+ name + "';");
			int file_id = 0;
			while (res.next()) {
				file_id = res.getInt("id");
			}

			int valu2 = st
					.executeUpdate("INSERT INTO user_files(file_id,user_id) "
							+ "VALUES('" + file_id + "','" + usr_id + "');");
			System.out.println("I GOT THE DB");
			js.put("result", new Boolean(true));

			con.close();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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

	public String insertData(String userName, String password, String phone,
			String email, String confirm) throws Exception {
		JSONObject js = new JSONObject();
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");

		try {
			Statement st = con.createStatement();
			System.out.println("jdbc connection");
			int valu = st
					.executeUpdate("INSERT INTO user_data(User_name,Phone,Email,Password,Confirm_Password,Status) "
							+ "VALUES('"
							+ userName
							+ "','"
							+ phone
							+ "','"
							+ email
							+ "','"
							+ password
							+ "','"
							+ confirm
							+ "','" + "creator" + "');");
			System.out.println("I GOT THE DB");
			js.put("result", new Boolean(true));
			con.close();
			return js.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		js.put("result", new Boolean(false));
		return js.toString();
	}

	public static boolean insertFBData(String userName, String id)
			throws Exception {
		JSONObject js = new JSONObject();
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");

		try {
			Statement st = con.createStatement();
			System.out.println("jdbc connection");
			int valu = st
					.executeUpdate("INSERT INTO user_facebook(user_name,id) "
							+ "VALUES('" + userName + "','" + id + "');");
			System.out.println("I GOT THE DB");

			con.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// js.put("result", new Boolean(false));
		return false;
	}

	public String insertData(String userName, String password, String phone,
			String email, String confirm, String status) throws Exception {

		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mydb");
		JSONObject js = new JSONObject();
		try {
			try {
				Statement st = con.createStatement();
				System.out.println("jdbc connection");
				int valu = st
						.executeUpdate("INSERT INTO user_data(User_name,Phone,Email,Password,Confirm_Password,Status) "
								+ "VALUES('"
								+ userName
								+ "','"
								+ phone
								+ "','"
								+ email
								+ "','"
								+ password
								+ "','"
								+ confirm
								+ "','" + status + "');");
				System.out.println("I GOT THE DB");

				js.put("result", new Boolean(true));
				return js.toString();
			} catch (SQLException ex) {
				System.out.println("SQL statement is not executed!" + ex);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		js.put("result", new Boolean(false));
		return js.toString();
	}
}