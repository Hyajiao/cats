package com.kingyee.cats.enums;

import com.kingyee.common.model.IEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：上传文件的类型枚举类
 * @author ph
 * @CreateTime 2017-4-5
 */
public enum UploadFileTypeEnum implements IEnum {
	/** 调研报告:<b style="font:bold;"> 0 </b> */
    SURVEY_REPORT("survey_report");

	private String text;

    UploadFileTypeEnum(String text) {
		this.text = text;
	}

	@Override
	public String text() {
		return this.text;
	}

	@Override
	public String value() {
		return this.ordinal() + "";
	}
	
	@Override
	public String toString() {
		return this.ordinal() + "(" + this.text + ")";
	}

	/**
	 * 根据value值获取对应类型的英文名称
	 * @param ordinal
	 * @return
	 */
	public static String getTextByOrdinal(String ordinal) {
		for(UploadFileTypeEnum model : UploadFileTypeEnum.values()) {
			if(model.value().equals(ordinal)) {
				return model.text();
			}
		}
		return "";
	}

    /**
     * 根据value值获取对应类型的英文名称
     * @return
     */
    public static List<String> textList() {
        List<String> list = new ArrayList<String>();
        for(UploadFileTypeEnum model : UploadFileTypeEnum.values()) {
            list.add(model.text());
        }
        return list;
    }

    /**
     * 根据text值获取对应类型
     * @return
     */
    public static UploadFileTypeEnum getEnumByText(String text) {
        List<String> list = new ArrayList<String>();
        for(UploadFileTypeEnum model : UploadFileTypeEnum.values()) {
            if(model.text().equals(text)){
                return model;
            }
        }
        return null;
    }

}
