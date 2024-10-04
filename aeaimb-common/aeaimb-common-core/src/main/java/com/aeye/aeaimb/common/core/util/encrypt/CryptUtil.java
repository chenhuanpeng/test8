package com.aeye.aeaimb.common.core.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class CryptUtil {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法
    private final static Charset UTF8 = StandardCharsets.UTF_8;
    public final static String SECRET_KEY = "aeye20231201";
    public final static String PRE_KEY = "aeye_";

    public static byte[] hmac256(byte[] key, String msg) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
        mac.init(secretKeySpec);
        return mac.doFinal(msg.getBytes(UTF8));
    }

    public static String sha256Hex(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] d = md.digest(s.getBytes(UTF8));
        return DatatypeConverter.printHexBinary(d).toLowerCase();
    }

	public static String genAppKey(String orgCode) throws Exception {
		return sha256Hex(PRE_KEY+orgCode);
	}

	public static String genAppSecret(String orgCode) throws Exception {
		return sha256Hex(orgCode+SECRET_KEY);
	}



    public static void main(String[] args) throws Exception {


		String secretKey = sha256Hex("0000111111112333333333333333333123123"+SECRET_KEY);
		System.out.println(secretKey);


//        //对bizContent进行加密
//        String bizContent = "{\"fileType\":1, \"fileContentHash\":\"93a6ea87e40932557b2af8ce9ceecfc97d337e33ae07ff7efcfec2f8860d6552\"}";
//
//        long timestamp = new Date().getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
//        String date = sdf.format(timestamp);
//        //随机数
//        Map<String, String> paraMap = new HashMap<>();
//        paraMap.put("token", bizContent);
//        paraMap.put("timestamp", bizContent);
//        paraMap.put("bizContent", bizContent);
//        String sortParam = sortParameters(paraMap);
//        System.out.println("原文:"+sortParam);
//        String signature = sha256Hex(sortParam);
//        System.out.println("签名:"+signature);
//
//        System.out.println("-----------------------");
//
//        System.out.println("-----------------------");
//
//        timestamp = new Date().getTime();
//        date = sdf.format(timestamp);
//        Map<String, String> resParaMap = new HashMap<>();
//        resParaMap.put("code", "100000");
//        resParaMap.put("msg", "成功");
//        resParaMap.put("data", "{\n" +
//                "    \"code\": \"100000\",\n" +
//                "    \"msg\": \"请求成功\",\n" +
//                "    \"data\": \"http://www.baidu.com/authorization?signtask=&timeStamp=&token=63abe93940184773b66a6c5582d536d9\"\n" +
//                "}");
//        String resSortParam = sortParameters(resParaMap);
//        System.out.println("响应原文:"+resSortParam);
//        String resSignature = sha256Hex(resSortParam);
//        System.out.println("响应签名:"+resSignature);
//
//
//        String contactCode = "aaaaaaaaaaaaaaaddddxxxxxxx666666666666666666666666666666666666666666";
//
//        String encrypt = encrypt(contactCode, SECRET_KEY);
//        System.out.println("加密值:"+encrypt);
//
//        String decrypt = decrypt(encrypt, SECRET_KEY);
//        System.out.println("解密值:"+decrypt);
    }


    /**
     *
     * @param sortParam 排序后得参数字符串
     * @param timestamp 时间戳
     * @param secretKey secreKey
     * @return
     * @throws Exception
     */
    public static String sign(String sortParam,
                              String timestamp, String secretKey) throws Exception{
        //将排序后字符串转为sha256Hex
        String signText = sha256Hex(sortParam);
        // ************* 计算签名 *************
        byte[] secretSigning = hmac256((secretKey).getBytes(UTF8), timestamp);
        //计算后得到签名
        String signature = DatatypeConverter
                .printHexBinary(hmac256(secretSigning, signText)).toLowerCase();
        return signature;
    }

    /**

     * AES 加密操作

     *

     * @param content 待加密内容

     * @param secretKey 加密密码

     * @return 返回Base64转码后的加密数据

     */
    public static String encrypt(String content, String secretKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(secretKey.getBytes());
        kgen.init(128, random);
        SecretKey secretKeya = kgen.generateKey();
        byte[] enCodeFormat = secretKeya.getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
        byte[] byteContent = content.getBytes(UTF8);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);// 初始化为加密模式的密码器
        byte[] result = cipher.doFinal(byteContent);// 加密
        return Base64.getEncoder().encodeToString(result);//通过Base64转码返回
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param secretKey
     * @return
     */
    public static String decrypt(String content, String secretKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(secretKey.getBytes());
        kgen.init(128, random);
        SecretKey secretKeya = kgen.generateKey();
        byte[] enCodeFormat = secretKeya.getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
        //实例化
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        //执行操作
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
        return new String(result, UTF8);
    }


    public static String sortParameters(Map<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        SortedMap<String,String> paramMap = new TreeMap<String,String>(parameters);
        int index=0;
        for(String key:paramMap.keySet()){
            sb.append(key).append("=").append(paramMap.get(key));
            index++;
            if(index != parameters.size()){
                sb.append("&");
            }
        }
        return sb.toString();
    }
}

