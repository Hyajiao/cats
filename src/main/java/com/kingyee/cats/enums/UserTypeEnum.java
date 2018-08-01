package com.kingyee.cats.enums;

import com.kingyee.common.model.IEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：用户类型枚举类
 * @author ph
 * @CreateTime 2017-4-5
 */
public enum UserTypeEnum implements IEnum {
	/** 讲者:<b style="font:bold;"> 0 </b> */
    SPEAKER("speaker"),
    /** 听者:<b style="font:bold;"> 0 </b> */
    LISTENER("listener");

	private String text;

    UserTypeEnum(String text) {
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
		for(UserTypeEnum model : UserTypeEnum.values()) {
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
        for(UserTypeEnum model : UserTypeEnum.values()) {
            list.add(model.text());
        }
        return list;
    }

}
