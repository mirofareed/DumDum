package com.example.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class URLAction {
	

  public static List<String> addFiles(List<String> files, File dir) {
        if (files == null) {
            files = new ArrayList<String>();
        }

        if (!dir.isDirectory()) {
//			files.add(dir.getName());
            return files;
        }

        for (File file : dir.listFiles()) {
            files.add(file.getName());
            addFiles(files, file);
        }
        return files;
    }
//	public staticF
}
