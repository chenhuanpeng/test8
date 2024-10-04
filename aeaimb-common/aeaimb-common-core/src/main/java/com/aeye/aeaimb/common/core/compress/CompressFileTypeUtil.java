package com.aeye.aeaimb.common.core.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CompressFileTypeUtil {

	public enum FileType {
		// 未知
		UNKNOWN,
		// 压缩文件
		ZIP, RAR, _7Z, TAR, GZ, TAR_GZ, BZ2, TAR_BZ2,
		// 位图文件
		BMP, PNG, JPG, JPEG,
		// 矢量图文件
		SVG,
		// 影音文件
		AVI, MP4, MP3, AAR, OGG, WAV, WAVE
	}

	/**
	 * 获取文件真实类型
	 *
	 * @param file 要获取类型的文件。
	 * @return 文件类型枚举。
	 */
	public static FileType computeFileType(File file) throws IOException {
		try(FileInputStream inputStream = new FileInputStream(file);){
			byte[] head = new byte[4];
			if (-1 == inputStream.read(head)) {
				return FileType.UNKNOWN;
			}

			return magicFileType(head);

		}
	}

	/**
	 * 根据文件头四字节判断文件类型
	 * @param head
	 * @return
	 */
	public static FileType magicFileType(byte[] head){
		int headHex = 0;
		for (byte b : head) {
			headHex <<= 8;
			headHex |= b;
		}

		switch (headHex) {
			case 0x504B0304:
				return FileType.ZIP;
			case 0x776f7264:
				return FileType.TAR;
			case -0x51:
				return FileType._7Z;
			case 0x425a6839:
				return FileType.BZ2;
			case -0x74f7f8:
				return FileType.GZ;
			case 0x52617221:
				return FileType.RAR;
			default:
				return FileType.UNKNOWN;
		}
	}
}
