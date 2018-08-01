package com.kingyee.common.util.io;

import it.sauronsoftware.jave.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AudioUtil {

    private final static Logger logger = LoggerFactory.getLogger(AudioUtil.class);

    /**
     * 获取音频文件的时长
     * @param file
     * @return
     */
    public static long getDuration(File file){
        Encoder encoder = new Encoder();
        try {
            MultimediaInfo multimediaInfo = encoder.getInfo(file);
            long ls = multimediaInfo.getDuration();
            return ls/ 1000;
        } catch (Exception e) {
            logger.error("获取文件时长出错。文件路径：" + file.getAbsolutePath(), e);
            return 0;
        }
    }

    public static void changeToMp3(String sourcePath, String targetPath) {
        File source = new File(sourcePath);
        File target = new File(targetPath);
        AudioAttributes audio = new AudioAttributes();
        Encoder encoder = new Encoder();

        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);

        try {
            encoder.encode(source, target, attrs);
        } catch (IllegalArgumentException e) {
            logger.error("获取文件时长出错。文件路径：" + sourcePath, e);
        } catch (InputFormatException e) {
            logger.error("获取文件时长出错。文件路径：" + sourcePath, e);
        } catch (EncoderException e) {
            logger.error("获取文件时长出错。文件路径：" + sourcePath, e);
        }
    }

    /**
     * 判断一个文件后缀是否是音频文件
     * @param ext
     * @return
     */
    public static boolean isAudio(String ext){
        if(ext.equalsIgnoreCase("mp3") || ext.equalsIgnoreCase("m4a")  || ext.equalsIgnoreCase("amr")){
            return true;
        }else{
            return false;
        }
    }
}
