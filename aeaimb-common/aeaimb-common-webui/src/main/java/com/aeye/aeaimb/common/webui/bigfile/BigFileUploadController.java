package com.aeye.aeaimb.common.webui.bigfile;

import com.aeye.aeaimb.common.core.exception.SystemMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/upload/file")
@Slf4j
public class BigFileUploadController {

    @Autowired(required = false)
    private BigFileStorage bigFileStorage;

    /**
     * 获取文件元数据，判断文件是否可以秒传
     * @param originFileName
     * @param fileSize
     * @param md5
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/fileMetaData")
    public FileMetaData fileMetaData(String originFileName,Long fileSize,String md5) throws URISyntaxException, MalformedURLException {
        String baseName = FilenameUtils.getBaseName(originFileName);
        String extension = FilenameUtils.getExtension(originFileName);

        String finalFileName = bigFileStorage.rename(baseName, fileSize);
        if(StringUtils.isNotEmpty(extension)){
            finalFileName += ("."+extension);
        }

        URI relativePath = bigFileStorage.relativePath(finalFileName);
        URI absoluteURI = bigFileStorage.absolutePath(relativePath);

        FileMetaData fileMetaData = new FileMetaData(originFileName, finalFileName, fileSize, relativePath.toString(), absoluteURI.toString());
        if(StringUtils.isNotBlank(md5)) {
            fileMetaData.setMd5(md5);
            String similarFile = bigFileStorage.checkSimilarFile(fileSize, md5);
            fileMetaData.setSimilarFile(similarFile);
            bigFileStorage.createSimilarLink(new URI(similarFile), relativePath);
        }
        return fileMetaData;
    }

    /**
     * 获取当前文件已经上传的大小，用于断点续传
     * @return
     */
    @GetMapping("/filePosition")
    public long filePosition(String relativePath) throws IOException, URISyntaxException {
        return bigFileStorage.filePosition(relativePath);
    }

    /**
     * 上传分段
     * @param multipartFile
     * @return
     */
    @PostMapping("/uploadPart")
    public long uploadPart(@RequestParam("file") MultipartFile multipartFile, String relativePath) throws IOException, URISyntaxException {
        bigFileStorage.uploadPart(multipartFile,relativePath);
        return bigFileStorage.filePosition(relativePath);
    }

    /**
     * 检查文件是否完整
     * @param relativePath
     * @param fileSize
     * @param md5
     * @return
     */
    @GetMapping("/validateFile")
    public void validateFile(String relativePath,Long fileSize,String md5) throws IOException, URISyntaxException {
        long filePosition = bigFileStorage.filePosition(relativePath);
        if(filePosition != fileSize){
            log.error("大文件上传失败，文件大小不完整 "+filePosition+" != "+fileSize);
            throw SystemMessage.FILE_INCOMPLETE.exception();
        }
        String targetMd5 = bigFileStorage.md5(relativePath);
        if(StringUtils.isNotBlank(targetMd5) && StringUtils.isNotBlank(md5) && !targetMd5.equals(md5)){
            log.error("大文件上传失败，文件损坏 "+targetMd5+" != "+md5);
            throw SystemMessage.FILE_CORRUPTED.exception();
        }
    }
}
