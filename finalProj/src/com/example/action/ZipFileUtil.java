package com.example.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author csterling
 * 
 */
public class ZipFileUtil {

	/**
	 * @param zipFile
	 * @param jiniHomeParentDir
	 */
	public static void unzipFileIntoDirectory(ZipFile zipFile,
			File jiniHomeParentDir) {
		Enumeration files = zipFile.entries();
		File f = null;
		FileOutputStream fos = null;
		ZipEntry ze = null;
		while (files.hasMoreElements()) {
			try {
				ZipEntry entry = (ZipEntry) files.nextElement();
				InputStream eis = zipFile.getInputStream(entry);
				byte[] buffer = new byte[1024];
				int bytesRead = 0;

				f = new File(jiniHomeParentDir.getAbsolutePath()
						+ File.separator + entry.getName());
				System.out.println("LOOOOOOOOOOOL      " + f.getName() + "  "
						+ f.getPath());
				if (entry.isDirectory()) {
					f.mkdir();
					continue;
				} else {
					f.getParentFile().mkdir();
					f.createNewFile();
				}

				fos = new FileOutputStream(f);

				while ((bytesRead = eis.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// ignore
					}
				}
			}
		}
	}
	// public static void main(String[] arg) {
	// ZipFile zf = null;
	// try {
	// zf = new ZipFile("/home/miro/final/uploaded/Facebook.zip");
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// ZipFileUtil.unzipFileIntoDirectory(zf, new File(
	// "/home/miro/final/uploaded/"));
	// }
}