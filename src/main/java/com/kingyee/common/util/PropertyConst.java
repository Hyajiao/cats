package com.kingyee.common.util;


public class PropertyConst {
	

	/**系统环境，开发(dev)OR测试(test)OR正式(product)*/
	public static String ENVIRONMENT = PropertyUtil.getPropertyValue("environment");
    /** 文件上传的根路径*/
    public static String UPLOAD_PATH = PropertyUtil.getPropertyValue("upload.path");
    /** 图片上传路径*/
    public static String PIC_PATH = PropertyUtil.getPropertyValue("pic.path");
    /** 微信头像路径*/
    public static String HEADIMG_PATH = PropertyUtil.getPropertyValue("headimg.path");
    /** 二维码路径*/
    public static String QRCODE_PATH = PropertyUtil.getPropertyValue("qrcode.path");
    /** 项目的域名*/
    public static String DOMAIN_URL = PropertyUtil.getPropertyValue("domain.url");
    /** 医脉通网址*/
    public static String MEDLIVE_URL = PropertyUtil.getPropertyValue("medlive.url");
    /**医脉通用户网址*/
    public static String MYMEDLIVE_URL = PropertyUtil.getPropertyValue("mymedlive.url");
    /** 项目网址*/
    public static String MEDLIVE_GET_USER_INFO_URL = PropertyUtil.getPropertyValue("medlive.get.user.info.url");
    /** 模板消息-发送调研问卷*/
    public static String TEMPLATE_SURVEY_NOTICE = PropertyUtil.getPropertyValue("template_survey_notice");
    /** 模板消息-余额变动提醒*/
    public static String TEMPLATE_BALANCE_CHANGE = PropertyUtil.getPropertyValue("template_balance_change");
    /** 短信模块-推送调研问卷的短信模板*/
    public static String SMS_TEMPLATE_SURVEY_NOTICE = PropertyUtil.getPropertyValue("sms_template_survey_notice");
    /** 短信模板-收款的短信模板*/
    public static String SMS_TEMPLATE_GATHERING = PropertyUtil.getPropertyValue("sms_template_gathering");
    /** 短信模板-提现的短信模板*/
    public static String SMS_TEMPLATE_WITHDRAW = PropertyUtil.getPropertyValue("sms_template_withdraw");

    /**发送邮件配置 */
    public static String MAIL_SMTP = PropertyUtil.getPropertyValue("mail.smtp");
    public static String MAIL_FROMADDRESS = PropertyUtil.getPropertyValue("mail.username");
    public static String MAIL_PASSWORD = PropertyUtil.getPropertyValue("mail.password");
    public static String MAIL_SWTICH = PropertyUtil.getPropertyValue("mail.swtich");

    /**视频转换工具路径*/
    public static String FFMPEG_PATH = PropertyUtil.getPropertyValue("ffmpeg.path");

    public static String CACHE_TYPE = PropertyUtil.getPropertyValue("cache_type");

    /** 系统管理员*/
    public static String ROLE_SYS_ADMIN = PropertyUtil.getPropertyValue("role_sys_admin");


	/** 资讯文章检索接口*/
	public static String NEWS_URL = PropertyUtil.getPropertyValue("news.url");
	/** 资讯文章检索接口Token*/
	public static String NEWS_TOKEN = PropertyUtil.getPropertyValue("news.token");
	/** 资讯统计接口*/
	public static String NEWS_ANALYZE = PropertyUtil.getPropertyValue("news.analyze");

	/** 文献检索接口*/
	public static String PAPERS_URL = PropertyUtil.getPropertyValue("papers.url");
	/** 文献统计接口*/
	public static String PAPERS_ANALYZE = PropertyUtil.getPropertyValue("papers.analyze");

	/**调研秘钥*/
	public static String SURVEY_TOKEN = PropertyUtil.getPropertyValue("survey.token");
}
