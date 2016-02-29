package com.laomu.note.module.login;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.lib.AccessTokenKeeper;
import com.laomu.note.lib.weibo.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.register.mobile.SelectCountryActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginAct extends ActionBarActivity {

    ImageView icon_weibo;
    ImageView icon_qq;

    private Oauth2AccessToken mAccessToken;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private TextView mTokenText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        icon_weibo = (ImageView) findViewById(R.id.iv_icon_weibo);

        icon_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weiboSSo();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);

//		返回键是否显示出来
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void weiboSSo() {
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        mSsoHandler.authorizeClientSso(new AuthListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class AuthListener implements WeiboAuthListener {
        AuthListener() {
        }

        public void onComplete(Bundle values) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            String phoneNum = mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                updateTokenView(false);
                AccessTokenKeeper.writeAccessToken(LoginAct.this, mAccessToken);
                Toast.makeText(LoginAct.this, R.string.weibosdk_demo_toast_auth_success, 0).show();
                return;
            }
            String code = values.getString(SelectCountryActivity.EXTRA_COUNTRY_CODE);
            String message = getString(R.string.weibosdk_demo_toast_auth_failed);
            if (!TextUtils.isEmpty(code)) {
                message = new StringBuilder(String.valueOf(message)).append("\nObtained the code: ").append(code).toString();
            }
        }

        public void onCancel() {
            Toast.makeText(LoginAct.this, R.string.weibosdk_demo_toast_auth_canceled, 1).show();
        }

        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginAct.this, "Auth exception : " + e.getMessage(), 1).show();
        }
    }

    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(this.mAccessToken.getExpiresTime()));
        String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
        this.mTokenText.setText(String.format(format, new Object[]{this.mAccessToken.getToken(), date}));
        String message = String.format(format, new Object[]{this.mAccessToken.getToken(), date});
        if (hasExisted) {
            message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
        }
        this.mTokenText.setText(message);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mSsoHandler != null) {
            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
