package com.kingyee.cats.enums;

import com.kingyee.common.model.IEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：系统用户类型枚举类
 * @author ph
 * @CreateTime 2017-4-5
 */
public enum SysUserTypeChEnum implements IEnum {
    /** 占位:<b style="font:bold;"> 0 </b> */
    BLANK("占位"),
	/** 系统用户:<b style="font:bold;"> 1 </b> */
    SYSTEM_USER("管理员"),
    /** 企业用户:<b style="font:bold;"> 2 </b> */
    ENTERPRISE_USER("企业账号"),
	/** 财务:<b style="font:bold;"> 3 </b> */
	FINANCE("财务");

	private String text;

    SysUserTypeChEnum(String text) {
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
		for(SysUserTypeChEnum model : SysUserTypeChEnum.values()) {
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
        for(SysUserTypeChEnum model : SysUserTypeChEnum.values()) {
            list.add(model.text());
        }
        return list;
    }

}
