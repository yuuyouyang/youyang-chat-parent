package com.nf.yy.util;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Calendar;
import java.util.UUID;

/**
 * 文件操作
 *
 * @author smile
 */
public class FileUtils {

    /**
     * 操作目录
     */
    public static final String ABSOLUTE_FILE_PATH = "D:/yuyouyang";

    /**
     * 复制一个文件到指定地址
     */
    public static Boolean copy(String srcPath, String destPath) throws IOException {
        String abs_srcPath = FileUtils.ABSOLUTE_FILE_PATH + srcPath;
        String abs_destPath = FileUtils.ABSOLUTE_FILE_PATH + destPath;
        FileUtils.copyFile(abs_srcPath, abs_destPath);
        return existsFile(destPath);
    }

    /**
     * 判断文件或目录是否存在
     */
    public static Boolean existsFile(String path) {
        String abs_Path = FileUtils.ABSOLUTE_FILE_PATH + path;
        File tempFile = new File(abs_Path);
        return tempFile.exists();
    }

    /**
     * 将文件从指定位置的复制到另一个位置
     */
    private static void copyFile(String srcPath, String destPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(srcPath);
        FileOutputStream fileOutputStream = new FileOutputStream(destPath);
        IOUtils.write(IOUtils.toByteArray(fileInputStream), fileOutputStream);
    }

    /**
     * 如果文件路径所对应的文件存在，并且是一个文件，则删除
     */
    public static void deleteFile(String path) {
        String abs_Path = FileUtils.ABSOLUTE_FILE_PATH + path;
        File file = new File(abs_Path);
        System.out.println(abs_Path);
        if (file.exists() && file.isFile() && file.delete()) {
            System.out.println("文件" + path + "删除成功！");
        } else {
            System.out.println("文件" + path + "删除失败！");
        }
    }

    /**
     * 根据UUID与文件后缀生成随机文件名
     */
    public static String createFileName(String filename) {
        return UUID.randomUUID() + filename.substring(filename.indexOf('.'));
    }

    /**
     * 根据基本路径和文件名称生成真实文件路径，-- 基本路径/年/月/fileName
     */
    public static String createRealFilePath(String filename) {
        Calendar today = Calendar.getInstance();
        String year = String.valueOf(today.get(Calendar.YEAR));
        String month = String.valueOf(today.get(Calendar.MONTH) + 1);
        String upPath = year + "/" + month + "/";
        File file = new File(FileUtils.ABSOLUTE_FILE_PATH + "/" + upPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return upPath + filename;
    }

    /**
     * 读取文件中所有字符保存到字符串
     */
    public static String readFile(String filePathAndName) throws IOException {
        String abs_Path = FileUtils.ABSOLUTE_FILE_PATH + filePathAndName;
        FileInputStream fileInputStream = new FileInputStream(abs_Path);
        String readToString = IOUtils.toString(fileInputStream, "utf-8");
        fileInputStream.close();
        return readToString;
    }

}
