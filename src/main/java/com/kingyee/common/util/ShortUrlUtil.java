package com.kingyee.common.util;

import com.kingyee.common.util.encrypt.EncryptUtil;

import java.io.UnsupportedEncodingException;
import java.util.Stack;

/**
 *
 * 此工具类，提供了两种生成短连接的方案。
 *
 * 方案一：将自增id转成短链接，无碰撞风险
 * 同时短连接可逆回自增id,
 * 相邻自增id无规律，防破解。
 * 基于Feistel的特性转为Base62。
 *
 * 方案二：将url的hash后，再按位进行运算，有碰撞的风险
 * https://teakki.com/p/57df78131201d4c1629ba365
 *
 * @author ph
 * @create 2018-06-20
 **/
public class ShortUrlUtil {

    /**
     * 调整字符顺序可增加混淆
     */
    private static char[] charSet =
            "0WqPQRI7yCDE31VONvSXnxY2bcdJK8zoLMZa9AmBjklpTUu45FGrst6Hwefghi".toCharArray();


    /**
     * 方案一
     * @param id
     * @return
     */
    public static String getShortUrl(Long id){
        //转码测试
        Long pid = permutedId(id);
        return convertDecimalToBase62(pid, 0);
    }

    /**
     * Feistel密码结构：如果permutedId(a)=b，那么必然会有permutedId(b)=a
     *
     * @param id
     * @return
     */
    private static Long permutedId(Long id) {
        Long l1 = (id >> 16) & 65535;
        Long r1 = id & 65535;

        for (int i = 0; i < 2; i++) {
            Long l2 = r1;
            Long r2 = l1 ^ (int) (roundFunction(r1) * 65535);
            l1 = l2;
            r1 = r2;
        }
        return ((r1 << 16) + l1);
    }


    private static Double roundFunction(Long val) {
        return ((131239 * val + 15534) % 714025) / 714025.0;
    }

    /**
     * 将10进制转化为62进制
     *
     * @param number
     * @param length 转化成的62进制长度，不足length长度的话高位补0，否则不改变什么
     * @return
     */
    private static String convertDecimalToBase62(long number, int length) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(charSet[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        int resultLength = result.length();
        StringBuilder temp0 = new StringBuilder();
        for (int i = 0; i < length - resultLength; i++) {
            temp0.append('0');
        }
        return temp0.toString() + result.toString();
    }

    /**
     * 将62进制转换成10进制数
     *
     * @param str
     * @return
     */
    private static String convertBase62ToDecimal(String str) {
        int multiple = 1;
        long result = 0;
        Character c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(str.length() - i - 1);
            result += getCharValue(c) * multiple;
            multiple = multiple * 62;
        }
        return result + "";
    }

    /**
     * 获取字符下标
     *
     * @param c
     * @return
     */
    private static int getCharValue(Character c) {
        for (int i = 0; i < charSet.length; i++) {
            if (c == charSet[i]) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 方案二
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String[] getShortUrl(String url) throws UnsupportedEncodingException {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "kingyee";
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = EncryptUtil.getMD5Value(key + url);
        String hex = sMD5EncryptResult;

        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {

            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += charSet[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }


    public static void main(String[] args) {
        //转码测试
        System.out.println(getShortUrl(1L));

//    //相邻转码无关性测试
//    for (int i = 10000; i < 40000; i++) {
//        Long newId = permutedId(Long.valueOf(i));
//        Long de = permutedId(newId);
//        String code = ShortCodeKit.convertDecimalToBase62(newId, 8);
//        String preCode = convertBase62ToDecimal(code);
//        System.out.println(String.format("原ID=%d, 一次permuted的ID=%d, 二次permuted后的ID=%d, Base62加密后=%s,解密=%d", i, newId, de, code, permutedId(Long.parseLong(preCode))));
//    }
    }
}