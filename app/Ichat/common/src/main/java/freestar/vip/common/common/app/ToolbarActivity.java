package freestar.vip.common.common.app;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import freestar.vip.common.R;

/**
 * 描述：
 * 作者：一颗浪星
 * 日期：2017/9/30 0030
 * github：
 */

public abstract class ToolbarActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar(((Toolbar) findViewById(R.id.toolbar)));

    }

    /**
     * 初始化 toolbar
     *
     * @param toolbar Toolbar
     */
    private void initToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        initTitleNeedBack();
    }

    private void initTitleNeedBack() {
        // 设置左上角的返回按钮为实际的返回效果
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // NavigationIcon 返回按钮
            //显示 NavigationIcon ,这个方法是 ActionBar 的方法. Toolbar 没有这个方法.
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 是否可点击
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
