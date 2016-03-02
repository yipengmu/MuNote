package com.laomu.note.common.account;

import com.laomu.note.module.login.Oauth2AccessTokenWrapper;

/**
 * Created by ${yipengmu} on 16/3/2.
 */
public class AccountManager {
    Oauth2AccessTokenWrapper oauthAccount;
    private AccountManager(){}
    private static AccountManager ins = null;
    public static AccountManager getInstance (){
        if(ins == null){
            ins = new AccountManager();
        }

        return ins;
    }

    public Oauth2AccessTokenWrapper getOauthAccount() {
        return oauthAccount;
    }

    public void setOauthAccount(Oauth2AccessTokenWrapper oauthAccount) {
        this.oauthAccount = oauthAccount;
    }
}
