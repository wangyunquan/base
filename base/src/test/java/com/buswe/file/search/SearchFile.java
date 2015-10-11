package com.buswe.file.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {

	public static void main(String[] args) {
		String fileName = "094416";

		File filePath = new File("F:\\百度云\\相片视频");
		List<File> myfile = new ArrayList<File>();
		// 开始遍历
		listDirectory(filePath, myfile);

		System.out.println("目录下包含 " + myfile.size() + "个文件：");
		for (File file : myfile) {
//			if (file.getName().contains(fileName))
//			{
//			System.out.println(" 找到文件：" + file.getName()+"  "+file.getAbsolutePath());
//			}
			if(!file.isDirectory())
			{
				if(file.length()==0)
				{
		
					System.out.println(file.getName());
					file.delete();
				}
			}
		}
	}

	public static void listDirectory(File path, List<File> myfile) {

		if (path.isFile()) {
			myfile.add(path);
		} else {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				listDirectory(files[i], myfile);
			}
		}
	}
}
