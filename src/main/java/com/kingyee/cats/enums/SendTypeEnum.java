package com.kingyee.cats.enums;

import com.kingyee.common.model.IEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：调研通知发送方式枚举类 （0：微信；1：短信；）
 * @author ph
 * @CreateTime 2017-4-5
 */
public enum SendTypeEnum implements IEnum {

    /** 微信:<b style="font:bold;"> 0 </b> */
    WECHAT("微信"),
    /** 短信:<b style="font:bold;"> 1 </b> */
    SMS("短信");

	private String text;

    SendTypeEnum(String text) {
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
		for(SendTypeEnum model : SendTypeEnum.values()) {
			if(model.value().equals(ordinal)) {
				return model.text();
			}
		}
		return "";
	}

    /**
     * 根据value值获取对应类型的英文名称
     * @param ordinal
     * @return
     */
    public static String getTextByOrdinal(Integer ordinal) {
        for(SendTypeEnum model : SendTypeEnum.values()) {
            if(ordinal.equals(model.ordinal())) {
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
        for(SendTypeEnum model : SendTypeEnum.values()) {
            list.add(model.text());
        }
        return list;
    }

    /**
     * 取得此枚举的map
     * @return
     */
    public static Map<String, String> textMap() {
        Map<String, String> map = new HashMap();
        for(SendTypeEnum model : SendTypeEnum.values()) {
            map.put(model.value(), model.text());
        }
        return map;
    }

}
