package com.smartwf.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author WCH
 * @Date: 2019/3/14 16:10
 * @Description: base64工具类
 */
public class Base64Utils {


    /**
     * base64字符串转化成图片
     * 
     * */ 
    public static File generateImage(String imgStr, HttpServletRequest request) throws IOException {
        // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {
            // 图像数据为空
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码
        byte[] b = decoder.decodeBuffer(imgStr);
        for (int i = 0; i < b.length; ++i) {
        	// 调整异常数据
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        // 生成jpeg图片
        String contextpath = request.getSession().getServletContext().getRealPath("/") + "/tempfileDir";
        String fileName = MathUtils.getKey();
        //新生成的图片
        String imgFilePath = contextpath + fileName + ".png";
        OutputStream out = new FileOutputStream(imgFilePath);
        out.write(b);
        out.flush();
        out.close();
        return new File(imgFilePath);
    }


    /**
     * 图片转化成base64字符串
     * 
     * */ 
    public static String getImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }
}
