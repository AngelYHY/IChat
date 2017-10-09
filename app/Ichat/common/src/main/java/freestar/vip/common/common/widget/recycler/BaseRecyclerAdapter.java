package freestar.vip.common.common.widget.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 描述：
 * 作者：一颗浪星
 * 日期：2017/9/30 0030
 * github：
 */

public class BaseRecyclerAdapter<Data> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {

    private final List<Data> mData;

    /**
     * 构造函数模块
     */
    public BaseRecyclerAdapter() {
        this(null);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        // 得到需要绑定的数据
        Data data = mData.get(position);
        // 触发Holder的绑定方法
        holder.bind(data);
    }

    public BaseRecyclerAdapter(List<Data> data) {
        mData = data;
    }



    @Override
    public int getItemCount() {
        return 0;
    }

    public static abstract class BaseViewHolder<Data> extends RecyclerView.ViewHolder{

        private Data mData;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data 绑定的数据
         */
        void bind(Data data) {
            mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候回调；必须复写
         *
         * @param data 绑定的数据
         */
        protected abstract void onBind(Data data);

    }
}
