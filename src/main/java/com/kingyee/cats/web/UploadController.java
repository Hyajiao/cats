package com.kingyee.cats.web;

import com.google.gson.JsonElement;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.spring.mvc.WebUtil;
import com.kingyee.common.util.*;
import com.kingyee.cats.enums.UploadFileTypeEnum;
import com.kingyee.common.util.io.AudioUtil;
import com.kingyee.common.util.io.ImageUtil;
import com.kingyee.common.util.io.VideoUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 上传文件controller
 * 
 * @author peihong
 * 2018年03月03日
 */
@Controller
@RequestMapping(value = "/upload/")
public class UploadController {

	private final static Logger logger = LoggerFactory.getLogger(UploadController.class);

    /**
     * 上传普通文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "uploadFile" }, method = RequestMethod.POST)
    public JsonElement uploadPic(MultipartFile file, String type){
        if(UploadFileTypeEnum.getEnumByText(type) == null){
            return JsonWrapper.newErrorInstance("上传类型不存在。");
        }

        String folderPath = PropertyConst.UPLOAD_PATH + type + "/";
        String path = WebUtil.getRealPath(folderPath);
        String fileName = file.getOriginalFilename();
        //重命名
        String ext = this.getSuffix(fileName);
        fileName = System.currentTimeMillis() + "." + ext;

        //保存
        try {
            File targetFile = new File(path, fileName);
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("上传文件时出错。", e );
            return JsonWrapper.newErrorInstance("上传文件时出错。");
        }
        return JsonWrapper.newDataInstance(folderPath + fileName);
    }

    /**
     * 上传缩略图
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "uploadPic" }, method = RequestMethod.POST)
    public JsonElement uploadPic(MultipartFile file, String type, Integer width){
        if(UploadFileTypeEnum.getEnumByText(type) == null){
            return JsonWrapper.newErrorInstance("上传类型不存在。");
        }

        String folderPath = PropertyConst.UPLOAD_PATH + type + "/";
        String path = WebUtil.getRealPath(folderPath);
        String fileName = file.getOriginalFilename();
        //重命名
        String ext = this.getSuffix(fileName);
        fileName = System.currentTimeMillis() + "." + ext;

        //保存
        try {
            // 是否要压缩
            boolean isCompress = width != null && width > 0 && ("jpg".equalsIgnoreCase(ext) || "jpeg".equalsIgnoreCase(ext) || "png".equalsIgnoreCase(ext));
            File targetFile = null;
            if(isCompress){
                targetFile = new File(path, "original/" + fileName);
            }else{
                targetFile = new File(path, fileName);
            }
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);

            // 判断图片是否需要旋转
            targetFile = ImageUtil.doRotate(targetFile);
            if(isCompress){
                // ImageUtil.resize(targetFile.getAbsolutePath(), path + File.separator + destFileName, 200);
                Thumbnails.of(targetFile.getAbsolutePath()).width(width).toFile(path
                        + File.separator + fileName);
            }
        } catch (Exception e) {
            logger.error("上传图片时出错。", e );
            return JsonWrapper.newErrorInstance("上传图片时出错。");
        }
        return JsonWrapper.newDataInstance(folderPath + fileName);
    }

    /**
     * 上传ppt文件（用于ppt转化为图片工具）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "uploadPpt" }, method = RequestMethod.POST)
    public JsonElement uploadPpt(MultipartFile file, String type){
        if(UploadFileTypeEnum.getEnumByText(type) == null){
            return JsonWrapper.newErrorInstance("上传类型不存在。");
        }

        String fileName = file.getOriginalFilename();
        String ext = this.getSuffix(fileName);
        if(!"ppt".equals(ext) && !"pptx".equals(ext)){
            return JsonWrapper.newErrorInstance("只能上传ppt格式的文件。");
        }

        String folderPath = PropertyConst.UPLOAD_PATH + type + "/";
        String path = WebUtil.getRealPath(folderPath);
        //重命名
        String currentTimeMillis = System.currentTimeMillis() + "";
        fileName = currentTimeMillis + "." + ext;
        // 要保存的图片的路径
        String pptFolderPath = folderPath + "original/" + fileName;

        //保存
        try {
            // 原始文件
            File originalFile = new File(path, "original/" + fileName);
            if(!originalFile.getParentFile().exists()){
                originalFile.getParentFile().mkdirs();
            }
            file.transferTo(originalFile);

            // 需要转换的文件
            File targetFile = new File(path, "ppt/" + fileName);
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            FileUtils.copyFile(originalFile, targetFile);
        } catch (Exception e) {
            logger.error("上传PPT时出错。", e );
            return JsonWrapper.newErrorInstance("上传PPT时出错。");
        }
        return JsonWrapper.newDataInstance(pptFolderPath);
    }


	/**
	 * 上传多媒体文件：音频 视频
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "uploadMultimediaFile" }, method = RequestMethod.POST)
	public JsonElement uploadMultimediaFile(MultipartFile file, String type, Long minDuration){
        if(UploadFileTypeEnum.getEnumByText(type) == null){
            return JsonWrapper.newErrorInstance("上传类型不存在。");
        }
        String folderPath = PropertyConst.UPLOAD_PATH + type + "/";
		String path = WebUtil.getRealPath(folderPath);
		String fileName = file.getOriginalFilename();
		//重命名
		String ext = this.getSuffix(fileName);
		fileName = System.currentTimeMillis() + "";
		File targetFile = new File(path, fileName + "." + ext);
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
		//返回路径
		String returnPath = folderPath + fileName;
		//保存
		try {
		    // 保留原始文件
            File originalFile = new File(path, "original/" + fileName + "." + ext);
            if(!originalFile.getParentFile().exists()){
                originalFile.getParentFile().mkdirs();
            }

            if(AudioUtil.isAudio(ext)){
                // 如果是音频资源
//                if(ext.equals("mp3")){
                    file.transferTo(targetFile);
                    if(minDuration != null){
                        long duration = AudioUtil.getDuration(targetFile);
                        if(duration < minDuration){
                            return JsonWrapper.newErrorInstance("音频时长过小。最小" + TimeUtil.getTimeDesc(minDuration * 1000));
                        }
                    }
//                }else{
//                    file.transferTo(originalFile);
//                    if(minDuration != null){
//                        long duration = AudioUtil.getDuration(originalFile);
//                        if(duration < minDuration){
//                            return JsonWrapper.newErrorInstance("音频时长过小。最小" + TimeUtil.getTimeDesc(minDuration * 1000));
//                        }
//                    }
//                    AudioUtil.changeToMp3(originalFile.getAbsolutePath(), path + fileName + ".mp3");
//                }
                returnPath = returnPath + "." + ext;
            }else if(VideoUtil.isVideo(ext)){
                file.transferTo(originalFile);
                // 原始视频文件
                String videoRealPath = originalFile.getAbsolutePath();
                // 压缩后视频文件
                String compressVideoRealPath = path + "/" + fileName + ".mp4";
                // 压缩转换视频
                String line = PropertyConst.FFMPEG_PATH + " -y -i " + videoRealPath
                        + " -ar 44100 -vcodec libx264 " + compressVideoRealPath;
                boolean ret = runCommand(line);
                if (ret) {
                    returnPath = returnPath + ".mp4";// 视频
                } else {
                    returnPath = folderPath + "original/" + fileName + "." + ext;// 视频
                }
            }

		} catch (Exception e) {
			logger.error("上传多媒体文件时出错。", e);
			return JsonWrapper.newErrorInstance("上传多媒体文件时出错。");
		}
		return JsonWrapper.newDataInstance(returnPath);
	}

    // 获取文件后缀
    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }

    private boolean runCommand(String line) {
		try {
			// 异常处理
			DefaultExecutor executor = new DefaultExecutor();
			CommandLine cmdLine = CommandLine.parse(line);
			int exitValue = executor.execute(cmdLine);

			// 防止抛出异常
			executor.setExitValues(null);

			// 命令执行的超时时间
			ExecuteWatchdog watchdog = new ExecuteWatchdog(600000);
			executor.setWatchdog(watchdog);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
			PumpStreamHandler streamHandler = new PumpStreamHandler(
					outputStream, errorStream);
			executor.setStreamHandler(streamHandler);

			if (StringUtils.isNotBlank(errorStream.toString())) {
				logger.error("====error stream====" + errorStream.toString());
				return false;
			}
			return true;
		} catch (IOException e) {
			logger.error("执行命令失败：" + line, e);
			return false;
		}
	}

}