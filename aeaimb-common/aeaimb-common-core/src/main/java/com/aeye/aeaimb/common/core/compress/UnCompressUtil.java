package com.aeye.aeaimb.common.core.compress;

import com.github.junrar.Archive;
import com.github.junrar.UnrarCallback;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UnCompressUtil {

	/**
	 * 解压 rar 测试中
	 * @param file
	 * @param targetPath
	 * @throws IOException
	 * @throws RarException
	 */
	public static void unRar(File file, File targetPath) throws IOException, RarException {
		Archive archive = new Archive(file, null);
		FileHeader fileHeader;
		// 创建输出目录
		createDirectory(targetPath, null);
		while( (fileHeader = archive.nextFileHeader()) != null){
			if(fileHeader.isDirectory()){
				createDirectory(targetPath, fileHeader.getFileNameString().trim());
			}else{
				FileOutputStream outputStream = new FileOutputStream(new File(targetPath + File.separator + fileHeader.getFileNameString().trim()));
				archive.extractFile(fileHeader, outputStream);
			}
		}
	}

	/**
	 *  构建目录
	 * @param outputDir 输出目录
	 * @param subDir 子目录
	 */
	private static void createDirectory(File file, String subDir){
		if(!(subDir == null || "".equals(subDir))) {//子目录不为空
			file = new File(file , subDir);
		}
		if(!file.exists()){
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			file.mkdirs();
		}
	}
}
