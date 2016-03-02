package com.laomu.note.module.login;

import android.os.Bundle;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by ${yipengmu} on 16/3/2.
 */
public class Oauth2AccessTokenWrapper extends Oauth2AccessToken {

    public String nickName;
    public Oauth2AccessToken inner;

    public Oauth2AccessTokenWrapper parseAccessTokenProxy(Bundle values){

        Oauth2AccessTokenWrapper myTo = new Oauth2AccessTokenWrapper();
        myTo.inner = Oauth2AccessToken.parseAccessToken(values);
        nickName = values.getString("com.sina.weibo.intent.extra.NICK_NAME");
        return myTo;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        nickName = nickName;
    }
}
