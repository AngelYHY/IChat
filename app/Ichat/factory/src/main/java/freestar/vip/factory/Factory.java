package freestar.vip.factory;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import freestar.vip.common.common.app.BaseApplication;

/**
 * 描述：
 * 作者：一颗浪星
 * 日期：2017/10/12
 * github：
 */

public class Factory {

    /**
     * Factory 中的初始化
     */
    public static void setup() {
        // 初始化数据库
        FlowManager.init(new FlowConfig.Builder(app())
                .openDatabasesOnInit(true) // 数据库初始化的时候就开始打开
                .build());
    }

    /**
     * 返回全局的Application
     *
     * @return Application
     */
    private static BaseApplication app() {
        return BaseApplication.getInstance();
    }

}
