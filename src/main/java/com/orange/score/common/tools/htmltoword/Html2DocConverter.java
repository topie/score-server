package com.orange.score.common.tools.htmltoword;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 将html文件转换成word文件
 *
 * @author 王文路
 * @date 2015-7-23
 */
public class Html2DocConverter {

	private String inputPath;	// 输入文件路径，以.html结尾
	private String outputPath;	// 输出文件路径，以.doc结尾

	public Html2DocConverter(String inputPath, String outputPath) {
		super();
		this.inputPath = inputPath;
		this.outputPath = outputPath;
	}


	public boolean writeWordFile() throws Exception {

		InputStream is = null;
		FileOutputStream fos = null;

		// 1 找不到源文件, 则返回false
		File inputFile = new File(this.inputPath);
		if (!inputFile.exists()) {
			return false;
		}

		File outputFile = new File(this.outputPath);
		// 2 如果目标路径不存在 则新建该路径
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}

		try {

			// 3 将html文件内容写入doc文件
			is = new FileInputStream(inputFile);
			POIFSFileSystem poifs = new POIFSFileSystem();
			DirectoryEntry directory = poifs.getRoot();
			directory.createDocument(
					"WordDocument", is);

			fos = new FileOutputStream(this.outputPath);
			poifs.writeFilesystem(fos);

			System.out.println("转换word文件完成!");

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null)
				fos.close();
			if (is != null)
				is.close();
		}

		return false;
	}

	public static void main(String[] args) throws Exception {

        new Html2DocConverter("/tmp/approve_3.html" , "/tmp/temp5.doc").writeWordFile();

	}
}
