package com.kingyee.cats.common.security;

import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.db.CmWechatUser;

import java.io.Serializable;

public class UserModel implements Serializable{

	CmWechatUser wechatUser;
	CmUser user;

    public CmWechatUser getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(CmWechatUser wechatUser) {
        this.wechatUser = wechatUser;
    }

    public CmUser getUser() {
        return user;
    }

    public void setUser(CmUser user) {
        this.user = user;
    }
}
