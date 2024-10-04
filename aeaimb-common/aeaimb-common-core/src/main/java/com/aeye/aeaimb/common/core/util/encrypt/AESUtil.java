package com.aeye.aeaimb.common.core.util.encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtil {
	private static Logger logger = LoggerFactory.getLogger(AESUtil.class);
	public final static String key ="80eab80ac324f01d22ee9770ea19e320";

	private AESUtil() {
	}

	/**
	 * 生成AES密钥
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] initKey() throws NoSuchAlgorithmException{
	  	KeyGenerator keygen =  KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        keygen.init(random);
        byte[] encoded = keygen.generateKey().getEncoded();
        return encoded;
	}

	/**
	 * AES加密(默认EBC加密模式)
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encryptAES(String data) throws Exception{
		byte[] skey = Hex.decodeHex(key.toCharArray());
		SecretKeySpec secretKey = new SecretKeySpec(skey, "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 初始化
        return Hex.encodeHexString(cipher.doFinal(data.getBytes()));
	}

	/**
	 * AES解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptAES(String data) throws Exception{
		byte[] skey = Hex.decodeHex(key.toCharArray());
		byte[] bytes = Hex.decodeHex(data.toCharArray());
		SecretKeySpec secretKey = new SecretKeySpec(skey, "AES");
          Cipher cipher = Cipher.getInstance("AES");// 创建密码器
          cipher.init(Cipher.DECRYPT_MODE, secretKey);// 初始化
          return cipher.doFinal(bytes);
	}


	public static String encrypt(String plainText, String key, String iv) throws Exception {
		// 将密钥和初始化向量从字符串转换为字节数组
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);

		// 创建密钥规格
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		// 创建初始化向量规格
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		// 创建Cipher实例并初始化加密操作
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

		// 执行加密
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

		// 将加密后的字节数组进行Base64编码
		String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

		return encryptedText;
	}


	public static String decrypt(String encryptedText, String key, String iv) throws Exception {
		// 将Base64编码的密文解码为字节数组
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

		// 将密钥和初始化向量从字符串转换为字节数组
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);

		// 创建密钥规格
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		// 创建初始化向量规格
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		// 创建Cipher实例并初始化解密操作
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		// 执行解密
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

		// 将解密后的字节数组转换为字符串
		String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

		return decryptedText;
	}

	public static void main(String[] args) throws DecoderException {
		String hh = "uuuuuuuukkkkkasdfafasuhjhasfkashfkjas";
		try {

//			String encodeHexString = encryptAES(hh);
//			logger.info("value:{},length:{}",encodeHexString,encodeHexString.length());
//
//
//			byte[] decryptAES = decryptAES(encodeHexString);
//			String origin = new String(decryptAES);
//			logger.info("value:{},length:{}",origin,origin.length());

			// 假设这些是你的输入值
			String encryptedText = "jUrP/62CF9TyS+zay4mAype1ymPbtwKeQklc/b1PtQZUEMuTXUub5Rgh2AEAioxLCQcWipj0e72m9gUgRcvF4SVSe5X76OrEIRffWqlHl3bK6WIUb6DJaSu4oiN1W9CO6mCghqDk2xgfxlan3C24b6iB1HIJySakyHUOsZ9Zg/8=";
			String key = "t1mWQEsypukodiFK";
			String iv = "0000000000000000";

			// 调用解密方法
			String decryptedText = decrypt(encryptedText, key, iv);

			String encryptedText1 = encrypt(decryptedText, key, iv);

			// 输出解密后的文本
			System.out.println("解密后的文本: " + decryptedText);
			System.out.println("加密后的文本: " + encryptedText1);



		} catch (Exception e) {
			logger.error("encryptDES Exception",e);
		}
	}

}
