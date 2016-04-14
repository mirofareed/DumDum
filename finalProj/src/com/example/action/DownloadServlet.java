package com.example.action;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.example.DAO.UserRegDAO;

@SuppressWarnings("serial")
public class DownloadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> Array_src = new ArrayList<String>();
		List<String> Array_mob = new ArrayList<String>();
		List<String> Array_name_from_android = new ArrayList<String>();
		// Array_name_from_android.add("as");
		// Array_name_from_android.add("HTML");

		System.out.println("heey");
		response.setContentType("text/html");
		File dir = new File("/home/miro/final/uploaded/tmp");
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int j = 0; j < children.length; j++) {
				new File(dir, children[j]).delete();
			}
		}
		String str_size = request.getParameter("arraysize");
		int size = Integer.parseInt(str_size);
		System.out.println("i=" + size);
		for (int i = 0; i < size; i++) {
			String str = request.getParameter("filename" + i);
			Array_name_from_android.add(str);
			System.out.println("Array_name_from_android .."
					+ Array_name_from_android.get(i));
		}
		System.out.println(UserRegDAO.geturl(Array_name_from_android.get(0)));
		// Array_src.add("D:/DumDumServer/uploaded/miro/ITSC_interview.txt");
		// get passes from database name from Array name and username=""
		for (int i = 0; i < Array_name_from_android.size(); i++) {
			Array_src.add(UserRegDAO.geturl(Array_name_from_android.get(i)));
			System.out.print("accccccccccc"
					+ Array_src.get(0).toString()
					+ Array_src.size()
					+ "kkkk"
					+ UserRegDAO.geturl(Array_name_from_android.get(i)
							.toString()));

			Array_mob
					.add(UserRegDAO.getmob_url(Array_name_from_android.get(i)));
		}

		System.out.print("zxxxx" + Array_src.get(0).toString());

		for (int i = 0; i < Array_src.size(); i++) {
			File trgDir = new File("/home/miro/final/uploaded/tmp/"
					+ Array_name_from_android.get(i).toString());
			File srcDir = new File(Array_src.get(i).toString());
			System.out.print("accccccccccc" + Array_src.get(0).toString());
			if (srcDir.isDirectory()) {
				System.out.print("gg" + srcDir);
				FileUtils.copyDirectory(srcDir, trgDir);
			} else {
				copyFile(srcDir, trgDir);
			}
		}

		File directoryToZip = new File("/home/miro/final/uploaded/tmp");

		List<File> fileList = new ArrayList<File>();
		System.out.println("---Getting references to all files in: "
				+ directoryToZip.getCanonicalPath());
		getAllFiles(directoryToZip, fileList);
		System.out.println("---Creating zip file");
		writeZipFile(directoryToZip, fileList);
		System.out.println("---Done");

		System.out.println("heey");
		response.setContentType("text/html");
		// String str_size=request.getParameter("arraysize");
		// int size=Integer.parseInt(str_size);
		// for( int i=0;i<size;i++)
		// {
		//
		// String str=request.getParameter("filename"+i);
		// Array_name_from_android.add(str);
		//
		// }
		String str = request.getParameter("filename");

		System.out.println("fileName" + str);

		PrintWriter out = response.getWriter();
		String filename = "asmaa.zip";
		// String ext = FilenameUtils.getExtension("/path/to/file/foo.txt");
		String filepath = "/home/miro/final/uploaded/";
		response.setContentType("APPLICATION/OCTET-STREAM");
		System.out.println("hsj" + Array_mob.toString());
		response.setHeader("Mobile_URL", Array_mob.toString());

		FileInputStream fileInputStream = new FileInputStream(filepath
				+ filename);

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		// File dir = new File("D:/DumDumServer\\uploaded/tmp");
		// if (dir.isDirectory()) {
		// String[] children = dir.list();
		// for (int j = 0; j < children.length; j++) {
		// new File(dir, children[j]).delete();
		// }
		// }
		fileInputStream.close();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	public static void getAllFiles(File dir, List<File> fileList) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				fileList.add(file);
				if (file.isDirectory()) {
					System.out.println("directory:" + file.getCanonicalPath());
					getAllFiles(file, fileList);
				} else {
					System.out.println("     file:" + file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeZipFile(File directoryToZip, List<File> fileList) {

		try {
			FileOutputStream fos = new FileOutputStream(
					"/home/miro/final/uploaded/asmaa" + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory()) { // we only zip files, not directories
					addToZip(directoryToZip, file, zos);
				}
			}

			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addToZip(File directoryToZip, File file,
			ZipOutputStream zos) throws FileNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(file);

		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		String zipFilePath = file.getCanonicalPath().substring(
				directoryToZip.getCanonicalPath().length() + 1,
				file.getCanonicalPath().length());
		System.out.println("Writing '" + zipFilePath + "' to zip file");
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

	public static void copyFile(File oldLocation, File newLocation)
			throws IOException {
		if (oldLocation.exists()) {
			BufferedInputStream reader = new BufferedInputStream(
					new FileInputStream(oldLocation));
			BufferedOutputStream writer = new BufferedOutputStream(
					new FileOutputStream(newLocation, false));
			try {
				byte[] buff = new byte[8192];
				int numChars;
				while ((numChars = reader.read(buff, 0, buff.length)) != -1) {
					writer.write(buff, 0, numChars);
				}
			} catch (IOException ex) {
				throw new IOException("IOException when transferring "
						+ oldLocation.getPath() + " to "
						+ newLocation.getPath());
			} finally {
				try {
					if (reader != null) {
						writer.close();
						reader.close();
					}
				} catch (IOException ex) {
					// Log.e(TAG, "Error closing files when transferring " +
					// oldLocation.getPath() + " to " + newLocation.getPath() );
				}
			}
		} else {
			throw new IOException(
					"Old location does not exist when transferring "
							+ oldLocation.getPath() + " to "
							+ newLocation.getPath());
		}
	}

}