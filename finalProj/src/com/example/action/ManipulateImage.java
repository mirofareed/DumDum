package com.example.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.apache.commons.codec.binary.Base64;

import com.example.DAO.FileDAO;
import com.example.DAO.UserRegDAO;

public class ManipulateImage {

	// Decode String into an Image
	synchronized public static void convertStringtoImage(
			String encodedImageStr, String fileName, String filePath,
			String type, String user_name, String fileDate) {
		System.out.println("MAN IMAGE " + fileName + "   " + filePath + "     "
				+ type + "   " + user_name);
		if (!UserRegDAO.checkfileExist(fileName, user_name)) {
			System.out.println("//=========================      "
					+ UserRegDAO.geturl(fileName));
			File fold = new File(UserRegDAO.geturl(fileName));
			FileDAO.deleteFile(fileName);
			fold.delete();
		}

		UserRegDAO urd = new UserRegDAO();
		Path ss = Paths.get(filePath);
		Path parent = ss.getParent();
		String par = parent.toString();
		// HttpSession session = req.getSession();
		try {
			// Decode String using Base64 Class
			byte[] imageByteArray = Base64.decodeBase64(encodedImageStr);
			// Write Image into File system - Make sure you update the path
			String destPath;
			System.out.println(user_name);
			File f = new File("/home/miro/final/uploaded/" + user_name + "/");
			if (!f.exists())
				f.mkdirs();
			if (fileName.indexOf(".") != -1)
				destPath = f.getPath() + "/" + fileName;
			else
				destPath = f.getPath() + "/" + fileName + ".zip";

			FileOutputStream imageOutFile = new FileOutputStream(destPath);
			imageOutFile.write(imageByteArray);
			imageOutFile.close();

			if (fileName.indexOf(".") == -1) {
				ZipFile zf = new ZipFile(destPath);
				ZipFileUtil.unzipFileIntoDirectory(zf, f);
				// unzip(destPath,"./finalProj/uploaded/"+fileName);
			}
			if (type.equalsIgnoreCase("Directory")) {
				UserRegDAO.insertfolder(fileName, filePath, f.getPath(),
						user_name, par);
				File file = new File(f.getPath() + new File(filePath).getName());
				if (!file.exists())
					file.mkdir();
				UserRegDAO.walkin(file, filePath, user_name);
			} else if (type.equals("File")) {
				String ext = fileName.substring(fileName.indexOf(".") + 1);
				UserRegDAO.insertuploadData(fileName, par, filePath, ext,
						destPath, user_name);
			}
			System.out.println("file Successfully Stored");
		} catch (FileNotFoundException fnfe) {
			System.out.println("file Path not found" + fnfe);
		} catch (IOException ioe) {
			System.out.println("Exception while converting the file " + ioe);
		}
	}

	@SuppressWarnings("unused")
	private static void extractFile(ZipInputStream zipIn, String filePath)
			throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(filePath));
		byte[] bytesIn = new byte[4096];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

}