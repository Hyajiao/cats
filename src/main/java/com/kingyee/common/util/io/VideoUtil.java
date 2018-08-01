package com.kingyee.common.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoUtil {

    private final static Logger logger = LoggerFactory.getLogger(VideoUtil.class);

    /**
     * 判断一个文件后缀是否是音频文件
     * @param ext
     * @return
     */
    public static boolean isVideo(String ext){
        if(ext.equalsIgnoreCase("MOV")  || ext.equalsIgnoreCase("mp4")){
            return true;
        }else{
            return false;
        }
    }
}
