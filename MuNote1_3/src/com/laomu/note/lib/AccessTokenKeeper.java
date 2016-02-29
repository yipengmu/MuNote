package com.laomu.note.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.register.mobile.LetterIndexBar;

/**
 * Created by ${yipengmu} on 16/2/29.
 */
public class AccessTokenKeeper {
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_UID = "uid";
    private static final String PREFERENCES_NAME = "com_weibo_sdk_android";

    public static void writeAccessToken(Context context, Oauth2AccessToken token) {
        if (context != null && token != null) {
            SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_NAME, AccessibilityNodeInfoCompat.ACTION_PASTE).edit();
            editor.putString(KEY_UID, token.getUid());
            editor.putString(KEY_ACCESS_TOKEN, token.getToken());
            editor.putString(KEY_REFRESH_TOKEN, token.getRefreshToken());
            editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
            editor.commit();
        }
    }

    public static Oauth2AccessToken readAccessToken(Context context) {
        if (context == null) {
            return null;
        }
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, AccessibilityNodeInfoCompat.ACTION_PASTE);
        token.setUid(pref.getString(KEY_UID, LetterIndexBar.SEARCH_ICON_LETTER));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN, LetterIndexBar.SEARCH_ICON_LETTER));
        token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, LetterIndexBar.SEARCH_ICON_LETTER));
        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
        return token;
    }

    public static void clear(Context context) {
        if (context != null) {
            SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_NAME, AccessibilityNodeInfoCompat.ACTION_PASTE).edit();
            editor.clear();
            editor.commit();
        }
    }
}
