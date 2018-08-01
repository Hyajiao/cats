package com.kingyee.cats.enums;

import com.kingyee.common.model.IEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：账单类型枚举类
 * @author ph
 * @CreateTime 2017-4-5
 */
public enum BillTypeEnum implements IEnum {
	/** 调研:<b style="font:bold;"> 0 </b> */
	SUVERY("调研"),
    /** 提现:<b style="font:bold;"> 1 </b> */
    WITHDRAW("提现");

	private String text;

    BillTypeEnum(String text) {
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
		for(BillTypeEnum model : BillTypeEnum.values()) {
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
        for(BillTypeEnum model : BillTypeEnum.values()) {
            list.add(model.text());
        }
        return list;
    }

}
