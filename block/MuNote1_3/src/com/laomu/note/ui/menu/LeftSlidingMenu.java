/**
 *
 */
package com.laomu.note.ui.menu;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laomu.note.MainActivity;
import com.laomu.note.R;
import com.laomu.note.common.account.AccountManager;
import com.laomu.note.module.login.LoginAct;
import com.laomu.note.module.login.Oauth2AccessTokenWrapper;
import com.laomu.note.ui.act.CitySelectionActivity;
import com.laomu.note.ui.act.MapNoteActivity;
import com.laomu.note.ui.act.help.HelpAct;
import com.laomu.note.ui.act.setting.NoteSettingAct;
import com.laomu.note.ui.base.NoteBaseFragment;
import com.laomu.note.ui.imp.SlidingMenuShowLis;
import com.umeng.fb.FeedbackAgent;

/**
 * @author luoyuan.myp
 *         <p/>
 *         2014-4-7
 */
@SuppressLint("ValidFragment")
public class LeftSlidingMenu extends NoteBaseFragment implements OnClickListener {
    private TextView tv_personal_clock;
    private TextView tv_map_mark;
    private TextView tv_setting;
    private TextView tv_feedback;

    private int FLAG_FOR_CITY_LIST = 1;
    private int FLAG_FOR_MAP_VIEW = 2;

    public static String LEFT_MENU_TAG = "LeftSlidingMenu";
    private SlidingMenuShowLis mSMShowLis;
    private ImageView iv_lmenu_logo;
    private TextView tv_user_name;
    private MainActivity mMainActivity;
    public LeftSlidingMenu(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    public LeftSlidingMenu() {
    }

    @Override
    public void setArguments(Bundle args) {
        // TODO Auto-generated method stub
        super.setArguments(args);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.left_menu_frame, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        findviews(view);
        initUI();
    }

    private void initUI() {

    }

    private void findviews(View view) {
        tv_personal_clock = (TextView) view.findViewById(R.id.tv_personal_clock);
        tv_map_mark = (TextView) view.findViewById(R.id.tv_map_mark);
        tv_setting = (TextView) view.findViewById(R.id.tv_setting);
        tv_feedback = (TextView) view.findViewById(R.id.tv_help_feedback);
        iv_lmenu_logo = (ImageView) view.findViewById(R.id.iv_lmenu_logo);

        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);


        tv_setting.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
        tv_personal_clock.setOnClickListener(this);
        iv_lmenu_logo.setOnClickListener(this);
        tv_map_mark.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_personal_clock:
                intoPerSonalClock();
                break;
//            case R.id.tv_info:
//                intoCityList();
//                break;
            case R.id.tv_map_mark:
                intoMapView();
                break;
            case R.id.tv_setting:
                intoSetting();
                break;
            case R.id.tv_help_feedback:
                intoFeedback();
                break;
            case R.id.iv_lmenu_logo:
                intoLoginAct();
                break;

            default:
                break;
        }
    }

    private void intoLoginAct() {
        Intent intent = new Intent(getActivity(), LoginAct.class);
        getActivity().startActivityForResult(intent,mMainActivity.REQ_CODE_LOGIN,null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(data.getBooleanExtra("loginresult",false)){
//            //login success
//
//            Oauth2AccessTokenWrapper account = AccountManager.getInstance().getOauthAccount();
//
//            tv_user_name.setText(account.getNickName());
//
//        }
    }

    private void intoFeedback() {
        Intent intent = new Intent(getActivity(), HelpAct.class);
        getActivity().startActivity(intent);
    }

    private void intoSetting() {
        Intent intent = new Intent(getActivity(), NoteSettingAct.class);
        getActivity().startActivity(intent);
    }

    private void intoPerSonalClock() {
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager != null) {

            Intent AlarmClockIntent = new Intent(Intent.ACTION_MAIN).addCategory(
                    Intent.CATEGORY_LAUNCHER).setComponent(
                    new ComponentName("com.android.deskclock", "com.android.deskclock.DeskClock"));

            ResolveInfo resolved = packageManager.resolveActivity(AlarmClockIntent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            if (resolved != null) {
                startActivity(AlarmClockIntent);
                return;
            } else {
                // required activity can not be located!

                Intent intent = new Intent();
                ComponentName comp = new ComponentName("com.android.settings",
                        "com.android.settings.Settings");
                intent.setComponent(comp);
                intent.setAction("android.intent.action.VIEW");
                startActivity(intent);
            }
        }

    }

    private void intoCityList() {
        Intent intent = new Intent(getActivity(), CitySelectionActivity.class);
        startActivityForResult(intent, FLAG_FOR_CITY_LIST);
    }

    private void intoMapView() {
        Intent intent = new Intent(getActivity(), MapNoteActivity.class);
        startActivityForResult(intent, FLAG_FOR_MAP_VIEW);
    }

    public void updateView(Oauth2AccessTokenWrapper oauthAccount) {
        if(tv_user_name != null){
            tv_user_name.setText(oauthAccount.getNickName() + "");
        }
    }
}
