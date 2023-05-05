package com.okeeah.reggie.controller;

import com.okeeah.reggie.util.UUIDUtil;
import com.okeeah.reggie.vo.R;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: Avery
 * @TIME: 2022/5/9 4:35 PM
 * @Description:
 * @since: JDK 1.8
 * @version: 1.0
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.upload.root.path}")
    private String rootPath;

//   菜品图片文件上传: 后端接收multipartFile类型的文件 重命名处理并持久化保存到服务器项目目录中
//   注意文件参数名需和前端保持一致
    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //        生成随机不重复文件名
        //        截取源文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUIDUtil.randomName()+suffix;
        try {
            file.transferTo(new File(rootPath,filename));
        } catch (IOException e) {
            return R.error("上传文件失败!");
        }

        return R.success(filename);

    }

    //   菜品图片回显预览 imgSrc
    @GetMapping("/files")
    public void files(HttpServletResponse response, String name) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(rootPath, name));
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(inputStream,outputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
