package com.kingyee.medlive.util;


import com.kingyee.common.util.PropertyConst;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.CRC32;

public class AccountUtil {
    public static final String HASH_EXT_KEY_HASHID = "dasfgfsdbz";
    public static final String HASH_EXT_KEY_CHECKID = "hiewrsbzxc";

    /**
     * 取得医脉通号的CRC32 hash 值
     * @param userid
     * @return
     */
    public static String hashUserID(long userid, String salt) {
        final String key = "asdfwrew.USER_SEED";
        final String key2 = "werhhs.USER_SEED2";

        CRC32 crc32 = new CRC32();
        if (StringUtils.isEmpty(salt)) {
            crc32.update(key.getBytes());
        } else {
            crc32.update((salt + key).getBytes());
        }

        long hash = crc32.getValue() - userid;

        crc32 = new CRC32();
        crc32.update((hash + key2).getBytes());
        long hash2 = crc32.getValue();

        String temp = String.valueOf(hash2);
        String k1 = temp.substring(0, 3);
        String k2 = temp.substring(temp.length() - 2);
        return k1 + hash + k2;
    }


    /**
     * 获取强制登陆医脉通cas的链接
     * @param medliveId 医脉通id
     * @param backUrl 登录后回跳url
     * @return 链接
     */
    public static String getForceLoginUrl(Long medliveId, String backUrl) throws UnsupportedEncodingException {
        StringBuffer url = new StringBuffer();
        url.append(PropertyConst.MEDLIVE_URL);
        url.append("/force_login.php?hashid=");
        url.append(AccountUtil.hashUserID(medliveId, AccountUtil.HASH_EXT_KEY_HASHID));
        url.append("&checkid=");
        url.append(AccountUtil.hashUserID(medliveId, AccountUtil.HASH_EXT_KEY_CHECKID));
        url.append("&url=");
        url.append(URLEncoder.encode(backUrl, "UTF-8"));

        return url.toString();
    }

    /**
     * 取得退出登陆后，成功登陆的链接
     * @param backUrl
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getReloginUrl(String backUrl) throws UnsupportedEncodingException {
        String loginUrl = PropertyConst.MEDLIVE_URL + "/auth/login?service=" + URLEncoder.encode(backUrl, "UTF-8");
        String logoutUrl = PropertyConst.MEDLIVE_URL + "/auth/logout?service=" + URLEncoder.encode(loginUrl, "UTF-8");
        return logoutUrl;
    }



    public static void main(String[] args) {
        System.out.println(AccountUtil.hashUserID(659184, HASH_EXT_KEY_HASHID));
        System.out.println(AccountUtil.hashUserID(659184, HASH_EXT_KEY_CHECKID));
    }

}
