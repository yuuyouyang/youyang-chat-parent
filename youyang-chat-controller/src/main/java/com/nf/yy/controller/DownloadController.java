package com.nf.yy.controller;

import com.nf.yy.util.FileUtils;
import com.nf.yy.util.VerifyCodeUtils;
import com.nf.yy.vo.FileEntityVO;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author smile
 */
@RestController
public class DownloadController {

    /**
     * 生成获取验证码图片
     */
    @GetMapping("/verification/code")
    public void getVerificationCode(HttpSession httpSession, HttpServletResponse response) throws IOException {
        String randomText = VerifyCodeUtils.generateVerifyCode(4, VerifyCodeUtils.VERIFY_CODES);
        httpSession.setAttribute("verifyCode", randomText);
        response.setContentType("image/png");
        // 防止浏览器缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        VerifyCodeUtils.outputImage(112, 50, response.getOutputStream(), randomText);
    }

    /**
     * 支持文件下载操作
     */
    @GetMapping("/download")
    public void download(String filename, HttpServletResponse response) throws IOException {
        if (FileUtils.existsFile(filename)) {
            String filePath = FileUtils.ABSOLUTE_FILE_PATH + filename;
            FileInputStream fileInputStream = new FileInputStream(filePath);
            response.setHeader("content-disposition", "attachment;filename=" + filename);
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
            outputStream.close();
            fileInputStream.close();
        } else {
            System.out.println("文件 " + FileUtils.ABSOLUTE_FILE_PATH + filename + " 不存在！");
        }
    }

    /**
     * 获取缩略图
     */
    @GetMapping("/thumbnail")
    public void thumbnail(String filename, ServletOutputStream outputStream) throws Exception {
        if (FileUtils.existsFile(filename)) {
            String filePath = FileUtils.ABSOLUTE_FILE_PATH + filename;
            if (filename.contains("/group") || filename.contains("/head")) {
                Thumbnails.of(filePath).size(40, 40).outputQuality(1f).outputFormat("gif").toOutputStream(outputStream);
            } else {
                Thumbnails.of(filePath).size(240, 240).outputQuality(1f).outputFormat("gif").toOutputStream(outputStream);
            }
        } else {
            System.out.println("文件 " + FileUtils.ABSOLUTE_FILE_PATH + filename + " 不存在！");
        }
    }

    /**
     * 支持文件上传操作
     */
    @PostMapping("/upload")
    public List<FileEntityVO> upload(MultipartFile[] files) throws Exception {
        List<FileEntityVO> realFilePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String saveFileName = FileUtils.createFileName(file.getOriginalFilename());
            String realFilePath = "/" + FileUtils.createRealFilePath(saveFileName);
            realFilePaths.add(new FileEntityVO(saveFileName, realFilePath, file.getSize(), file.getContentType()));
            if (file.getContentType().contains("image")) {
                BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
                if (bufferedImage.getWidth() > 1000 || bufferedImage.getHeight() > 1000) {
                    String suffix = file.getContentType().substring(6);
                    Thumbnails.of(file.getInputStream()).size(1000, 1000).outputFormat(suffix)
                            .outputQuality(1f).toFile(FileUtils.ABSOLUTE_FILE_PATH + realFilePath);
                    continue;
                }
            }
            file.transferTo(new File(FileUtils.ABSOLUTE_FILE_PATH + realFilePath));
        }
        return realFilePaths;
    }

}
