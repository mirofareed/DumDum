package com.example.action;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.DAO.UserRegDAO;

@SuppressWarnings("serial")
public class Uploadservlet extends HttpServlet {
	void copyByte(byte[] fromBytes, byte[] toBytes, int start, int len) {
		for (int i = start; i < (start + len); i++) {
			toBytes[i - start] = fromBytes[i];
		}
	}

	@Override
	synchronized protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String savePath = "", filepath = "", filename = "";
		String contentType = "", fileData = "", strLocalFileName = "";
		int startPos = 0;
		int endPos = 0;
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		// copy specified number of bytes from main data buffer to temp data
		// buffer

		int BOF = 0, EOF = 0;
		contentType = request.getContentType();
		out.println("<br>Content type is :: " + contentType);
		if ((contentType != null)
				&& (contentType.indexOf("multipart/form-data") >= 0)) {
			DataInputStream in = new DataInputStream(request.getInputStream());
			DataInputStream in1 = in;
			int formDataLength = request.getContentLength();
			byte dataBytes[] = new byte[formDataLength];
			int byteRead = 0;
			int totalBytesRead = 0;
			while (totalBytesRead < formDataLength) {
				byteRead = in1.read(dataBytes, totalBytesRead, formDataLength);
				totalBytesRead += byteRead;
			}
			out.println("<br>totalBytesRead : " + totalBytesRead
					+ " :formDataLength = " + formDataLength);
			// String file = new String(dataBytes);
			// out.println("<br>FileContents:<br>////////////////////////////////////<br>"
			// + file + "<br>////////////////////////////////<br>");

			byte[] line = new byte[128];
			if (totalBytesRead < 3) {
				return; // exit if file length is not sufficiently large
			}

			String boundary = "";
			String s = "";
			int count = 0;
			int pos = 0;

			// loop for extracting boundry of file
			// could also be extracted from request.getContentType()
			do {
				copyByte(dataBytes, line, count, 1); // read 1 byte at a time
				count += 1;
				s = new String(line, 0, 1);
				fileData = fileData + s;
				pos = fileData
						.indexOf("Content-Disposition: form-data; name=\""); // set
																				// the
																				// file
																				// name
				if (pos != -1)
					endPos = pos;
			} while (pos == -1);
			boundary = fileData.substring(startPos, endPos);

			// loop for extracting filename
			startPos = endPos;
			do {
				copyByte(dataBytes, line, count, 1); // read 1 byte at a time
				count += 1;
				s = new String(line, 0, 1);
				fileData = fileData + s;
				pos = fileData.indexOf("filename=\"", startPos); // set the file
																	// name
				if (pos != -1)
					startPos = pos;
			} while (pos == -1);
			do {
				copyByte(dataBytes, line, count, 1); // read 1 byte at a time
				count += 1;
				s = new String(line, 0, 1);
				fileData = fileData + s;
				pos = fileData.indexOf("Content-Type: ", startPos);
				if (pos != -1)
					endPos = pos;
			} while (pos == -1);
			filename = fileData.substring(startPos + 10, endPos - 3); // to
																		// eliminate
																		// "
																		// from
																		// start
																		// & end
			strLocalFileName = filename;
			int index = filename.lastIndexOf("\\");
			if (index != -1)
				filename = filename.substring(index + 1);
			else
				filename = filename;

			// loop for extracting ContentType
			boolean blnNewlnFlag = false;
			startPos = endPos; // added length of "Content-Type: "
			do {
				copyByte(dataBytes, line, count, 1); // read 1 byte at a time
				count += 1;
				s = new String(line, 0, 1);
				fileData = fileData + s;
				pos = fileData.indexOf("\n", startPos);
				if (pos != -1) {
					if (blnNewlnFlag == true)
						endPos = pos;
					else {
						blnNewlnFlag = true;
						pos = -1;
					}
				}
			} while (pos == -1);
			contentType = fileData.substring(startPos + 14, endPos);

			// loop for extracting actual file data (any type of file)
			startPos = count + 1;
			do {
				copyByte(dataBytes, line, count, 1); // read 1 byte at a time
				count += 1;
				s = new String(line, 0, 1);
				fileData = fileData + s;
				pos = fileData.indexOf(boundary, startPos); // check for end of
															// file data i.e
															// boundry value
			} while (pos == -1);
			endPos = count - boundary.length();
			// file data extracted
			UserRegDAO urg = new UserRegDAO();
			out.println("<br><br>0. Local File Name = " + strLocalFileName);
			out.println("<br><br>1. filename = " + filename);
			out.println("<br>2. contentType = " + contentType);
			out.println("<br>3. startPos = " + startPos);
			out.println("<br>4. endPos = " + endPos);
			out.println("<br>5. boundary = " + boundary);

			String path = request.getContextPath();
			File f = new File("/home/miro/final/uploaded/"
					+ session.getAttribute("userName"));
			if (!f.exists())
				f.mkdirs();
			// create destination path & save file there
			String appPath = getServletContext().getRealPath("/");
			out.println("<br>appPath : " + appPath);
			System.out.println("HEEEEEEEEEEEEERE   "
					+ session.getAttribute("folderidme"));
			String destFolder = f.getPath() + "/";
			if (request.getParameter("subfolder") == null) {
				UserRegDAO.insertuploadData(filename,
						(String) session.getAttribute("userName"), null,
						contentType, destFolder + filename,
						(String) session.getAttribute("userName"));
				filename = destFolder + filename;
			} else {
				System.out.println("HERE IN ELSE ");
				UserRegDAO.insertfiles(
						filename,
						(String) session.getAttribute("userName")
								+ "/"
								+ UserRegDAO.getFolderName((Integer) session
										.getAttribute("folderidme")),
						null,
						contentType,
						destFolder
								+ UserRegDAO.getFolderName((Integer) session
										.getAttribute("folderidme")));
				UserRegDAO.insertfolder_file(UserRegDAO.getFileID(filename),
						(Integer) session.getAttribute("folderidme"));
				filename = destFolder
						+ UserRegDAO.getFolderName((Integer) session
								.getAttribute("folderidme")) + "/" + filename;
			}

			FileOutputStream fileOut = new FileOutputStream(filename);
			fileOut.write(dataBytes, startPos, (endPos - startPos));
			fileOut.flush();
			fileOut.close();
			System.out.println("<br>File saved as >> " + filename);
			out.println("<br>File saved as >> " + filename);
			response.sendRedirect("success.jsp");
		} else {
			out.println("Error in uploading ");
		}

	}

	@Override
	synchronized protected void doPost(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
