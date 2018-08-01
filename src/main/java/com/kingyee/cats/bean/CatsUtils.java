package com.kingyee.cats.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author hanyajiao
 * @date 2018/6/25 13:52
 */
public class CatsUtils {

    /**
     * list 集合排序
     * @param ls
     */
    public static void sortList(List<Object[]> ls){
        Collections.sort(ls,new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                if (Long.valueOf(o1[2].toString()) > Long.valueOf(o2[2].toString())) {
                    return -1;
                }
                return 1;
            }
        });
    }
}
