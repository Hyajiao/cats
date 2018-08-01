package com.kingyee.common.util.collection;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * List相关的工具类
 * @author ph
 */
public class ListUtil {

    /**
     * 字符串转成list
     * 结果：[1,2,3]
     * @author ph
     *
     */
    public static List<String> strToList(String str){
        return strToList(str, ",");
    }

    /**
     * 字符串转成list
     * 结果：[1,2,3]
     *
     */
    public static List<String> strToList(String str, String separate){
        List<String> list = new ArrayList<String>();
        if(StringUtils.isNotEmpty(str)){
            //,1,2,3, 截成  1,2,3
            String s = str.substring(1, str.length()-1);
            if(s.indexOf(separate) >= 0){
                String[] arr = s.split(separate);
                for(int i = 0; i < arr.length; i++){
                    list.add(arr[i].trim());
                }
            }else{
                list.add(s.trim());
            }
        }
        return list;
    }

    /**
     * list 转成 字符串
     * 结果,1,2,3,
     *
     */
    public static String listToStr(List<String> list){
        return listToStr(list, ",");
    }

    /**
     * list 转成 字符串
     * 结果,1,2,3,
     *
     */
    public static String listToStr(List<String> list, String separate){
        String str = "";
        if(list != null && list.size() > 0){
            str = separate;
            for(String s:list){
                str = str + s + separate;
            }
        }
        return str;
    }
}
