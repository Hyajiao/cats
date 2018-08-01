package com.kingyee.cats.enums;

import com.kingyee.common.model.IEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：调研通知发送范围枚举类 （0：全部；1：仅未发送；）
 * @author ph
 * @CreateTime 2017-4-5
 */
public enum SendScopeEnum implements IEnum {

    /** 全部:<b style="font:bold;"> 0 </b> */
    ALL("全部"),
    /** 仅未发送:<b style="font:bold;"> 1 </b> */
    NOT_SEND("仅未发送");

	private String text;

    SendScopeEnum(String text) {
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
		for(SendScopeEnum model : SendScopeEnum.values()) {
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
        for(SendScopeEnum model : SendScopeEnum.values()) {
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
        for(SendScopeEnum model : SendScopeEnum.values()) {
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
        for(SendScopeEnum model : SendScopeEnum.values()) {
            map.put(model.value(), model.text());
        }
        return map;
    }

}
