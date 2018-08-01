package com.kingyee.cats.enums;

import com.kingyee.common.model.IEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：会议状态枚举类 （0：未审核；1：审核通过；2：未通过审核；）
 * @author ph
 * @CreateTime 2017-4-5
 */
public enum AuthenticationStatusEnum implements IEnum {

    /** 未审核:<b style="font:bold;"> 0 </b> */
    NOT_AUTHENTICATION("未审核"),
    /** 审核通过:<b style="font:bold;"> 0 </b> */
    APPROVE("审核通过"),
    /** 未通过审核:<b style="font:bold;"> 0 </b> */
    NOT_APPROVE("未通过审核");

	private String text;

    AuthenticationStatusEnum(String text) {
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
		for(AuthenticationStatusEnum model : AuthenticationStatusEnum.values()) {
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
        for(AuthenticationStatusEnum model : AuthenticationStatusEnum.values()) {
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
        for(AuthenticationStatusEnum model : AuthenticationStatusEnum.values()) {
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
        for(AuthenticationStatusEnum model : AuthenticationStatusEnum.values()) {
            map.put(model.value(), model.text());
        }
        return map;
    }

}
