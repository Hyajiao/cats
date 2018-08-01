package com.kingyee.cats.service;

import com.kingyee.cats.common.security.UserModel;
import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.cats.dao.UserDao;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.db.CmWechatUser;
import com.kingyee.cats.enums.YesNoEnum;
import com.kingyee.common.IBaseService;
import com.kingyee.common.cache.CacheConst;
import com.kingyee.common.cache.CacheFacade;
import com.kingyee.common.spring.mvc.WebUtil;
import com.kingyee.common.util.EmojiFilter;
import com.kingyee.common.util.PropertyConst;
import com.kingyee.common.util.http.HttpClientUtil;
import com.kingyee.common.util.http.HttpUtil;
import com.kingyee.medlive.util.AccountUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 用户管理
 * 
 * @author fanyongqian
 * 2016年10月31日
 */
@Service
public class UserService implements IBaseService {
	
    @Autowired
    private UserDao dao;
    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    private CacheFacade cache;

    /**
     * 根据用户id取得用户信息
     * @param userId 用户id
     * @return
     */
    public UserModel getUserModel(Long userId){
        UserModel userModel = new UserModel();
        CmUser user = getUserById(userId);
        CmWechatUser wechatUser = getWechatUserByUserId(userId);

        userModel.setUser(user);
        userModel.setWechatUser(wechatUser);
        return userModel;
    }


    /**
     * 根据用户id取得用户信息
     * @param userId 用户id
     * @return
     */
    public CmUser getUserById(Long userId){
        if(userId == null){
            return null;
        }else{
            String key = CacheConst.KEY_CM_USER_BY_USER_ID_ + userId;
            CmUser user = cache.getValue(key);
            if(user == null){
                user = dao.get(CmUser.class, userId);
                cache.setValue(key, user);
            }
            return user;
        }

    }

    /**
     * 根据用户openid取得微信的用户信息
     * @param openid openid
     * @return
     */
    public CmWechatUser getWechatUserByOpenid(String openid){
        String key = CacheConst.KEY_CM_WECHAT_BY_OPEN_ID_ + openid;
        CmWechatUser wechatUser = cache.getValue(key);
        if(wechatUser == null){
            wechatUser = dao.getWechatUserByOpenid(openid);
            cache.setValue(key, wechatUser);
        }
        return wechatUser;
    }


    /**
     * 根据用户id取得微信的用户信息
     * @param userId cmUser表的主键
     * @return
     */
    public CmWechatUser getWechatUserByUserId(Long userId){
        String key = CacheConst.KEY_CM_WECHAT_BY_USER_ID_ + userId;
        CmWechatUser wechatUser = cache.getValue(key);
        if(wechatUser == null){
            wechatUser = dao.getWechatUserByUserId(userId);
            if(wechatUser != null){
                cache.setValue(key, wechatUser);
            }

        }
        return wechatUser;
    }


    /**
     * 根据cmWechatUser表的id取得微信的用户信息
     * @param wechatUserId cmWechatUser表的主键
     * @return
     */
    public CmWechatUser getWechatUserByWechatUserId(Long wechatUserId){
        String key = CacheConst.KEY_CM_WECHAT_BY_WECAHT_ID_ + wechatUserId;
        CmWechatUser wechatUser = cache.getValue(key);
        if(wechatUser == null){
            wechatUser = dao.getWechatUserByWechatId(wechatUserId);
            if(wechatUser != null){
                cache.setValue(key, wechatUser);
            }

        }
        return wechatUser;
    }


    /**
     * 根据用户手机号取得用户信息
     * @param telNo 手机号码
     * @return
     */
    public CmUser getUserByTelNo(String telNo){
        return dao.getUserByTelNo(telNo);
    }

    /**
     * 新增或者更新用户信息
     * @param wxMpUser
     */
    public CmWechatUser saveOrUpdateWechatUser(WxMpUser wxMpUser) throws IOException, WxErrorException {
        String openid = wxMpUser.getOpenId();
        CmWechatUser wechatUser = dao.getWechatUserByOpenid(openid);
        if(wechatUser == null){
            // 新增
            wechatUser = saveWechatUser(wxMpUser);
        }else{
            wechatUser = updateWechatUser(wxMpUser, wechatUser);
        }
        return wechatUser;
    }

    /**
     * 新增用户微信信息
     * @param wxMpUser
     */
    public CmWechatUser saveWechatUser(WxMpUser wxMpUser) throws IOException {
        String openid = wxMpUser.getOpenId();

        // 新增
        CmWechatUser wechatUser = new CmWechatUser();
        if(StringUtils.isNotEmpty(wxMpUser.getHeadImgUrl())){
            //用户没有头像
            String headImgPath = PropertyConst.HEADIMG_PATH + openid + ".jpeg";
            String path = WebUtil.getRealPath(PropertyConst.HEADIMG_PATH);
            HttpClientUtil.downloadPicture(wxMpUser.getHeadImgUrl(), path,openid + ".jpeg");
            wechatUser.setCwuHeadimgurl(wxMpUser.getHeadImgUrl());
            wechatUser.setCwuHeadimg(headImgPath);
        }
        wechatUser.setCwuSubscribe(wxMpUser.getSubscribe()?1:0);
        wechatUser.setCwuOpenid(wxMpUser.getOpenId());
        wechatUser.setCwuNickname(EmojiFilter.filterEmoji(wxMpUser.getNickname()));
        wechatUser.setCwuSex(wxMpUser.getSex());
        wechatUser.setCwuCity(wxMpUser.getCity());
        wechatUser.setCwuCountry(wxMpUser.getCountry());
        wechatUser.setCwuProvince(wxMpUser.getProvince());
        wechatUser.setCwuLanguage(wxMpUser.getLanguage());
        wechatUser.setCwuSubscribeTime(wxMpUser.getSubscribeTime());
        wechatUser.setCwuUnionid(wxMpUser.getUnionId());
        wechatUser.setCwuRemark(wxMpUser.getRemark());
        if(wxMpUser.getGroupId() != null){
            wechatUser.setCwuGroupid(wxMpUser.getGroupId().toString());
        }
        if(wxMpUser.getTagIds() != null && wxMpUser.getTagIds().length > 0){
            wechatUser.setCwuTagidList(StringUtils.join(wxMpUser.getTagIds(), ","));
        }

        wechatUser.setCwuScore(0);
        wechatUser.setCwuIsValid(1);
        long now = System.currentTimeMillis();
        wechatUser.setCwuCreateTime(now);
        long wechatUserId = dao.save(wechatUser);

        wechatUser.setCwuId(wechatUserId);

        // 缓存
        UserUtil.cacheWechatUser(cache, wechatUser);


        return wechatUser;
    }

    /**
     * 更新用户信息
     * @param wxMpUser
     */
    public CmWechatUser updateWechatUser(WxMpUser wxMpUser, CmWechatUser wechatUser) throws IOException, WxErrorException {
        String openid = wxMpUser.getOpenId();
        if(StringUtils.isNotEmpty(wxMpUser.getHeadImgUrl())){
            //用户没有头像
            String headImgPath = PropertyConst.HEADIMG_PATH + openid + ".jpeg";
            String path = WebUtil.getRealPath(PropertyConst.HEADIMG_PATH);
            HttpClientUtil.downloadPicture(wxMpUser.getHeadImgUrl(), path,openid + ".jpeg");
            wechatUser.setCwuHeadimgurl(wxMpUser.getHeadImgUrl());
            wechatUser.setCwuHeadimg(headImgPath);
        }

        // 更新用户的微信信息
        wechatUser.setCwuSubscribe(wxMpUser.getSubscribe()?1:0);
        wechatUser.setCwuNickname(EmojiFilter.filterEmoji(wxMpUser.getNickname()));
        wechatUser.setCwuSex(wxMpUser.getSex());
        wechatUser.setCwuCity(wxMpUser.getCity());
        wechatUser.setCwuCountry(wxMpUser.getCountry());
        wechatUser.setCwuProvince(wxMpUser.getProvince());
        wechatUser.setCwuLanguage(wxMpUser.getLanguage());
        wechatUser.setCwuSubscribeTime(wxMpUser.getSubscribeTime());
        wechatUser.setCwuRemark(wxMpUser.getRemark());
        if(wxMpUser.getGroupId() != null){
            wechatUser.setCwuGroupid(wxMpUser.getGroupId().toString());
        }
        if(wxMpUser.getTagIds() != null && wxMpUser.getTagIds().length > 0){
            wechatUser.setCwuTagidList(StringUtils.join(wxMpUser.getTagIds(), ","));
        }
        wechatUser.setCwuIsValid(1);
        wechatUser.setCwuUpdateTime(System.currentTimeMillis());

        dao.update(wechatUser);

        // 缓存
        UserUtil.cacheWechatUser(cache, wechatUser);

        // 更新用户信息
        if(wechatUser.getCwuCuId() != null){
            CmUser user = dao.get(CmUser.class, wechatUser.getCwuCuId());
            user.setCuNickName(wechatUser.getCwuNickname());
            user.setCuHeadimg(wechatUser.getCwuHeadimg());
            user.setCuIsValid(1);
            dao.update(user);

            // 缓存
            UserUtil.cacheUser(cache, user);
        }

        return wechatUser;
    }

    /**
     * 更新用户信息
     */
    public void updateWechatUser(CmWechatUser wechatUser) throws IOException {
        wechatUser.setCwuIsValid(1);
        wechatUser.setCwuUpdateTime(System.currentTimeMillis());

        dao.update(wechatUser);

        // 缓存
        UserUtil.cacheWechatUser(cache, wechatUser);
    }


    /**
     * 保存医生用户注册信息
     * @param user
     */
    public CmUser saveRegUser(CmUser user) {
//        user.setCuUserName(user.getCuTelNo());
        user.setCuIsValid(YesNoEnum.YES.ordinal());

        if(user.getCuId() == null){
            user.setCuCreateTime(System.currentTimeMillis());
            Long userId = dao.save(user);
            user.setCuId(userId);
        }else{
            user.setCuUpdateTime(System.currentTimeMillis());
            dao.update(user);
        }

        WebUtil.getOrCreateSession().setAttribute(UserUtil.USER_SESSION_NAME, user.getCuId());
        UserUtil.cacheUser(cache, user);
        return user;
    }

    /**
     * 更新
     *
     * @param user
     */
    public void updateCmUser(CmUser user) {
        dao.update(user);

        // 缓存
        UserUtil.cacheUser(cache, user);
    }

    /**
     * 更新
     *
     * @param wechatUser
     */
    public void updateCmWechatUser(CmWechatUser wechatUser) {
        dao.update(wechatUser);

        // 缓存
        UserUtil.cacheWechatUser(cache, wechatUser);
    }


    /**
     * 删除缓存
     *
     */
    public void removeCache() {
        // 缓存
        UserUtil.removeCache(cache);
    }


    /**
     * 根据医脉通id 拉取用户信息
     * @param user
     * @param medliveId
     * @return
     * @throws Exception
     */
    public CmUser saveDoctorByMedliveId(CmUser user, Long medliveId) throws Exception{
        // 为了在取到id的同时,也取得用户昵称。(在保存检索历史记录中用到)
        HttpClient httpClient = HttpUtil.getHttpClient();
        String url = PropertyConst.MEDLIVE_GET_USER_INFO_URL;
        url = url + "?hashid=" + AccountUtil.hashUserID(Long.valueOf(medliveId), AccountUtil.HASH_EXT_KEY_HASHID);
        url = url + "&checkid=" + AccountUtil.hashUserID(Long.valueOf(medliveId), AccountUtil.HASH_EXT_KEY_CHECKID);
        HttpGet httpGetUserPrfileUrl = new HttpGet(url);
        HttpResponse clientResponse = httpClient.execute(httpGetUserPrfileUrl);
        int statusCode = clientResponse.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            HttpEntity entity = clientResponse.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            JSONObject obj = new JSONObject(result);

            if(obj.getString("success_msg").equals("success")){
                JSONObject data = obj.getJSONObject("data");
                user = buildMedliveUser(user, data);
            }
        }

        return user;
    }


    public CmUser buildMedliveUser(CmUser user, JSONObject data) throws JSONException {

        user.setCuMedliveId(data.getLong("user_id"));
        user.setCuRealName(getValueFromJson(data, "name"));
        user.setCuTelNo(getValueFromJson(data, "mobile"));
        user.setCuProvince(getValueFromJson(data, "province"));
        user.setCuCity(getValueFromJson(data, "city"));
        if(data.has("profession_ext") && !data.getString("carclass2").equals("null")){
            user.setCuProfessional(data.getString("carclass2"));
        }
        if(data.has("profession_ext")){
            JSONArray array = data.getJSONArray("profession_ext");
            if(array.length() == 2){
                user.setCuDept(array.getJSONObject(array.length() - 1).getString("name"));
            }
            if(array.length() == 3){
                // 如果第二级是内科或者外科的话，去第三级，否则去第二级
                String profession2 = array.getJSONObject(1).getString("name");
                if(profession2.equals("内科") || profession2.equals("外科")){
                    user.setCuDept(array.getJSONObject(array.length() - 1).getString("name"));
                }else{
                    user.setCuDept(profession2);
                }
            }
        }

        if(data.has("profession_ext")){
            JSONArray array = data.getJSONArray("company_ext");
            if(array.length() > 0){
                user.setCuHospital(array.getJSONObject(array.length() - 1).getString("name"));
            }
        }

        // 头像
        user.setCuMedliveHeadImg(getValueFromJson(data, "thumb"));
        return user;
    }

    private String getValueFromJson(JSONObject data, String key) throws JSONException {
        if(data.has(key)){
            return data.getString(key);
        }else{
            return null;
        }
    }

    public CmUser getUserByMedliveId(Long medliveId){
        return dao.getUserByMedliveId(medliveId);
    }
}
