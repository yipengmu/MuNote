package com.laomu.note.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.common.account.AccountManager;
import com.laomu.note.lib.weibo.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class LoginAct extends ActionBarActivity {

    ImageView icon_weibo;
    ImageView icon_qq;

    private Oauth2AccessTokenWrapper mAccessToken;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;


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
                Toast.makeText(getApplicationContext(), "正在跳转到微博", Toast.LENGTH_SHORT).show();
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
            mAccessToken = new Oauth2AccessTokenWrapper().parseAccessTokenProxy(values);
            AccountManager.getInstance().setOauthAccount(mAccessToken);

        }

        public void onCancel() {
            Toast.makeText(LoginAct.this, R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_SHORT).show();
        }

        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginAct.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mSsoHandler != null) {
            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

        if(mAccessToken.getAccessToken().isSessionValid()){
            Toast.makeText(LoginAct.this, "登录成功",Toast.LENGTH_SHORT).show();
        }

        LoginAct.this.setResult(RESULT_OK, data.putExtra("loginresult",true)); /* 关闭activity */
        LoginAct.this.finish();
    }
}
