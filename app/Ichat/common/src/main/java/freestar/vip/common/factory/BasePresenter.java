package freestar.vip.common.factory;

/**
 * 描述：
 * 作者：一颗浪星
 * 日期：2017/10/10
 * github：
 */

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.Presenter {

    private T mView;

    public BasePresenter(T view) {
        setView(view);
    }

    /**
     * 设置一个 View，子类可以复写
     */
    @SuppressWarnings("unchecked")
    protected void setView(T view) {
        mView = view;
        mView.setPresenter(this);
    }

    /**
     * 给子类使用的获取View的操作
     * 不允许复写
     *
     * @return View
     */
    protected final T getView() {
        return mView;
    }

    @Override
    public void start() {
        // 开始的时候进行 Loading 调用
        if (mView != null) {
            mView.showLoading();
        }
    }

    @Override
    public void destroy() {
        mView = null;
    }
}
