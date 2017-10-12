package freestar.ichat;

import android.content.Context;

import com.igexin.sdk.PushManager;

import freestar.vip.common.common.app.BaseApplication;
import freestar.vip.factory.Factory;

/**
 * 描述：
 * 作者：一颗浪星
 * 日期：2017/10/12
 * github：
 */

public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        // 调用 Factory 进行初始化
        Factory.setup();

        // 推送进行初始化
        PushManager.getInstance().initialize(this);
    }

    @Override
    protected void showAccountView(Context context) {
        super.showAccountView(context);
        // 登录界面的显示
    }
}
