package com.laomu.note.module.login;

import android.os.Bundle;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by ${yipengmu} on 16/3/2.
 */
public class Oauth2AccessTokenWrapper {
    public String nickName;
    public Oauth2AccessToken accessToken;

    public Oauth2AccessTokenWrapper parseAccessTokenProxy(Bundle values){
        accessToken = Oauth2AccessToken.parseAccessToken(values);
        nickName = values.getString("com.sina.weibo.intent.extra.NICK_NAME");
        return this;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        nickName = nickName;
    }

    public Oauth2AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(Oauth2AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
