package com.aeye.aeaimb.common.webui.validation.custom;

import com.aeye.aeaimb.common.webui.helper.FileCharsetHelper;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
public class TextFileValidator implements ConstraintValidator<TextFile, MultipartFile> {
    private Charset [] charsets ;
    private long charsLimit;

    // 默认最大检查文件字符集和字符数最大文件大小为  4M
    public static long MAX_CHECK_FILE_SIZE = 4194304;

    @Override
    public void initialize(TextFile fileCharset) {
        String[] supports = fileCharset.charsets();
        this.charsets = new Charset [supports.length];
        for (int i = 0; i < supports.length; i++) {
            this.charsets[i] = Charset.forName(supports[i]);
        }
        charsLimit  = fileCharset.charsLimit();
    }

    static final MimeType textMimeType = MimeTypeUtils.parseMimeType("text/*");

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if(multipartFile == null)return true;
        //首先判断是不是文本文件
        MimeType mimeType = MimeTypeUtils.parseMimeType(multipartFile.getContentType());
        if(!textMimeType.isCompatibleWith(mimeType)){
            log.error("非文本字符类型,contentType:"+mimeType);
            return false;
        }
        if(multipartFile.getSize() > MAX_CHECK_FILE_SIZE){
            log.error("超过最大文本文件大小，不做检查，因为比较耗费内存");
            return true;
        }

        try{
            @Cleanup
            InputStream inputStream = multipartFile.getInputStream();

            Charset detectCodepage = FileCharsetHelper.detectCodepage(inputStream);
            boolean contains = ArrayUtils.contains(charsets, detectCodepage);
            if(!contains){
                log.error("文本字符集不匹配，target:{},supports:{}",detectCodepage, StringUtils.join(charsets));
                return false;
            }

            // 判断文本字数是否超过限制
            if(charsLimit != -1) {
                @Cleanup
                InputStream fileInputStream = multipartFile.getInputStream();

                List<String> lines = IOUtils.readLines(fileInputStream, detectCodepage);
                Long count = lines.stream().map(line -> (long) line.trim().length()).reduce(0L, (i, j) -> i + j);
                if (count > charsLimit) {
                    return false;
                }
            }
        } catch (IOException e) {
            log.error("解析文件编码出错:"+e.getMessage(),e);
        }
        return true;
    }
}
