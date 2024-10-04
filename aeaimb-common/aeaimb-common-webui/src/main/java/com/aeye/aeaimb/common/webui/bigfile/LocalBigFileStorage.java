package com.aeye.aeaimb.common.webui.bigfile;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LocalBigFileStorage implements BigFileStorage {

    /**
     * 上传的基准路径，默认取当前路径
     */
    @Value("${sanri.webui.upload.basePath:/tmp/}")
    private String basePath;
    @Value("${spring.application.name:default}")
    private String appName;

    @Override
    public String rename(String originFileName, Long fileSize) {
        return System.currentTimeMillis()+"";
    }

    /**
     * 文件路径为 basePath/appName/yyyyMMdd/finalFileName
     * @param finalFileName
     * @return
     */
    static final String yyyyMMdd = "yyyyMMdd";
    @Override
    public URI relativePath(String finalFileName) throws URISyntaxException {
//        String yyyyMMddDate = DateFormatUtils.format(System.currentTimeMillis(), yyyyMMdd);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(yyyyMMdd);
        String yyyyMMddDate = LocalDate.now().format(dateTimeFormatter);
        return new URI(appName+"/"+ yyyyMMddDate+"/"+ finalFileName);
    }

    @Override
    public URI absolutePath(URI relativePath) throws  URISyntaxException {
        return new URI("file","",basePath,"").resolve(relativePath);
    }

    @Override
    public String checkSimilarFile(long fileSize, String md5) {
        return null;
    }

    @Override
    public void createSimilarLink(URI sourcePath, URI targetPath) {

    }

    @Override
    public long filePosition(String relativePath) throws URISyntaxException, IOException {
        File absoluteFile = storageFile(relativePath);
        return absoluteFile.length();
    }

    @Override
    public void uploadPart(MultipartFile multipartFile, String relativePath) throws IOException, URISyntaxException {
        File absoluteFile = storageFile(relativePath);

        FileOutputStream fileOutputStream = new FileOutputStream(absoluteFile,true);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        IOUtils.copy(multipartFile.getInputStream(),bufferedOutputStream);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    private File storageFile(String relativePath) throws URISyntaxException, IOException {
        URI absolutePath = absolutePath(new URI(relativePath));
        File absoluteFile = new File(absolutePath);
        File parentFile = absoluteFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        if (!absoluteFile.exists()) {
            absoluteFile.createNewFile();
        }
        return absoluteFile;
    }

    @Override
    public String md5(String relativePath) throws IOException, URISyntaxException {
        File absoluteFile = storageFile(relativePath);

        //大文件 md5 使用流来分段计算
        FileInputStream fileInputStream = new FileInputStream(absoluteFile);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte [] buffer = new byte[8192];
            int length = -1;
            while((length = fileInputStream.read(buffer)) != -1){
                messageDigest.update(buffer,0,length);
            }
            return new String(Hex.encodeHex(messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }
}
