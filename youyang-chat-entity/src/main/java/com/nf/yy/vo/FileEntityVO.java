package com.nf.yy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 文件信息
 * @author smile
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileEntityVO {

    /** 文件名称 */
    private String fileName;
    /** 文件地址 */
    private String filePath;
    /** 文件大小 */
    private long fileSize;
    /** 文件类型 */
    private String fileType;

}
