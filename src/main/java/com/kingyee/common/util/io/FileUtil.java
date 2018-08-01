package com.kingyee.common.util.io;

import javax.servlet.ServletContext;
import java.io.*;

public class FileUtil {

    // 获取文件后缀
    public static String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }

    /**
     * 将指定路径的文件，写到输入流对象中
     *
     * @param path
     *            文件路径
     * @param servletContext servletContext对象
     * @return 输入流对象
     */
    public static InputStream writeInputStream(String path,
                                               ServletContext servletContext) {
        InputStream downlaodStream = null;
        try {
            // 取得输入文件流
            InputStream is = servletContext.getResourceAsStream(path);
            // 输出文件流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 缓冲区大小
            byte[] buffer = new byte[1024];
            int readLength = 0;
            while ((readLength = is.read(buffer)) != -1) {
                baos.write(buffer, 0, readLength);
            }
            is.close();
            baos.close();
            byte[] ba = baos.toByteArray();
            downlaodStream = new ByteArrayInputStream(ba);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return downlaodStream;
    }


    /**
     * 格式化输入文件名【防止输入文件名为乱码】
     *
     * @param fileName
     *            文件名
     * @return 转换后文件名
     */
    public static String formatFileName(String fileName) {
        try {
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
