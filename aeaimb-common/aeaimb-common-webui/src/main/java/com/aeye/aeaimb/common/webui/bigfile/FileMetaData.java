package com.aeye.aeaimb.common.webui.bigfile;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class FileMetaData {
    // 原始文件名和最终文件名
    @NonNull
    private String originFileName;
    @NonNull
    private String finalFileName;
    //文件大小
    @NonNull
    private long fileSize;
    // 文件 md5 值，这个是前端计算的值
    private String md5;
    // 相对路径和绝对路径
    @NonNull
    private String relativePath;
    @NonNull
    private String absolutePath;
    // 是否存在相似文件，如果存在相似文件，这里将是一个文件的相对路径; 目标文件可以创建一个链接指向当前文件
    private String similarFile;
}
