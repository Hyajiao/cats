package com.kingyee.cats.enums;

import com.kingyee.common.model.IEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：是否枚举类
 * @author ph
 * @CreateTime 2018-02-02
 */
public enum YesNoEnum implements IEnum {
	/** 否:<b style="font:bold;"> 0 </b> */
	NO("no"),
    /** 是:<b style="font:bold;"> 0 </b> */
    YES("yes");

	private String text;

    YesNoEnum(String text) {
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
		for(YesNoEnum model : YesNoEnum.values()) {
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
        for(YesNoEnum model : YesNoEnum.values()) {
            list.add(model.text());
        }
        return list;
    }

}
