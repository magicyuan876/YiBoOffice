package com.yibo.office.controller;

import com.yibo.office.service.YiBoOffice2PdfService;
import kong.unirest.Unirest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/fileServer")
public class FileInfoRestController {

    @Autowired
    private YiBoOffice2PdfService yiBoOffice2PdfService;

    public ResponseEntity<byte[]> convertOfficeByUrl(String url) throws IOException {

        File result = Unirest.get(url)
                .asFile("/disk/location/file.zip")
                .getBody();
        yiBoOffice2PdfService.office2pdf(result.getAbsolutePath(), result.getName());

        String fileName = URLEncoder.encode(result.getName(), "utf-8");
        HttpHeaders httpHeaders = new HttpHeaders();
        // 通知浏览器以下载文件方式打开
        ContentDisposition contentDisposition =
                ContentDisposition.builder("attachment").filename(fileName).build();
        httpHeaders.setContentDisposition(contentDisposition);
        // application/octet_stream设置MIME为任意二进制数据
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 使用apache commons-io 里边的 FileUtils工具类
        //return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(image.getLocation())),
        //        httpHeaders, HttpStatus.OK);
        // 使用spring自带的工具类也可以 FileCopyUtils
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(result),
                httpHeaders, HttpStatus.OK);


    }
}
