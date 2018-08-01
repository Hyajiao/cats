package com.kingyee.cats.enums;

import com.kingyee.common.model.IEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：调研通知发送枚举类 （0：未发送；1：发送中；2：发送完成；3：发送失败）
 * @author ph
 * @CreateTime 2017-4-5
 */
public enum SendNoticeStatusEnum implements IEnum {

    /** 未发送:<b style="font:bold;"> 0 </b> */
    NOT_SEND("未发送"),
    /** 发送中:<b style="font:bold;"> 1 </b> */
    SENDING("发送中"),
    /** 发送完成:<b style="font:bold;"> 2 </b> */
    SEND_FINISH("发送完成"),
    /** 发送失败:<b style="font:bold;"> 3 </b> */
    SEND_FAIL("发送失败");

	private String text;

    SendNoticeStatusEnum(String text) {
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
		for(SendNoticeStatusEnum model : SendNoticeStatusEnum.values()) {
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
        for(SendNoticeStatusEnum model : SendNoticeStatusEnum.values()) {
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
        for(SendNoticeStatusEnum model : SendNoticeStatusEnum.values()) {
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
        for(SendNoticeStatusEnum model : SendNoticeStatusEnum.values()) {
            map.put(model.value(), model.text());
        }
        return map;
    }

}
