package freestar.vip.common.common.widget.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import freestar.vip.common.R;

/**
 * 描述：
 * 作者：一颗浪星
 * 日期：2017/9/30 0030
 * github：
 */
@SuppressWarnings({"unchecked", "unused"})
public abstract class BaseRecyclerAdapter<Data> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> implements View.OnLongClickListener, View.OnClickListener, AdapterCallback<Data> {

    private final List<Data> mData;
    private AdapterListener<Data> mListener;
    private Context mContext;

    /**
     * 构造函数模块
     */
    public BaseRecyclerAdapter() {
        this(null);
    }

    public BaseRecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    public BaseRecyclerAdapter(List<Data> data, AdapterListener<Data> listener) {
        mData = data;
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mData.get(position));
    }

    /**
     * 得到布局的类型
     *
     * @param position 坐标
     * @param data     当前的数据
     * @return XML文件的ID，用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    @Override
    public BaseViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        // 得到 LayoutInflater 用于把 XML 初始化为 View
        // 把 XML id为 viewType 的文件初始化为一个 root View
        View root = LayoutInflater.from(mContext).inflate(viewType, parent, false);

        // 通过子类必须实现的方法，得到一个 ViewHolder
        BaseViewHolder<Data> holder = onCreateViewHolder(root, viewType);

        // 设置 View 的 Tag 为 ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);
        // 设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        // 进行界面注解绑定
        holder.unbinder = ButterKnife.bind(holder, root);
        // 绑定 callback
        holder.callback = this;
        return holder;
    }

    /**
     * 得到一个新的 BaseViewHolder
     *
     * @param root     根布局
     * @param viewType 布局类型，其实就是XML的ID
     * @return ViewHolder
     */
    protected abstract BaseViewHolder<Data> onCreateViewHolder(View root, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        // 得到需要绑定的数据
        Data data = mData.get(position);
        // 触发Holder的绑定方法
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 返回整个集合
     *
     * @return List<Data>
     */
    public List<Data> getItems() {
        return mData;
    }

    /**
     * 插入一条数据并通知插入
     *
     * @param data Data
     */
    public void add(Data data) {
        mData.add(data);
        notifyItemInserted(mData.size() - 1);
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList Data
     */
    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mData.size();
            Collections.addAll(mData, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList Data
     */
    public void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mData.size();
            mData.addAll(dataList);
            notifyItemRangeInserted(startPos, dataList.size());
        }
    }

    /**
     * 删除操作
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换为一个新的集合，其中包括了清空
     *
     * @param dataList 一个新的集合
     */
    public void replace(Collection<Data> dataList) {
        mData.clear();
        if (dataList == null || dataList.size() == 0)
            return;
        mData.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public boolean onLongClick(View v) {
        BaseViewHolder holder = (BaseViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (mListener != null) {
            // 得到 ViewHolder 当前对应的适配器中的坐标
            int pos = holder.getAdapterPosition();
            // 回调方法
            mListener.onItemLongClick(holder, mData.get(pos));
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        BaseViewHolder holder = (BaseViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (mListener != null) {
            // 得到ViewHolder当前对应的适配器中的坐标
            int pos = holder.getAdapterPosition();
            // 回掉方法
            this.mListener.onItemClick(holder, mData.get(pos));
        }
    }

    @Override
    public void update(Data data, BaseViewHolder<Data> holder) {
        // 得到当前 ViewHolder 的坐标
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {
            // 进行数据的移除和更新
            mData.remove(pos);
            mData.add(pos, data);
            // 通知这个坐标下的数据有更新
            notifyItemChanged(pos);
        }
    }

    public static abstract class BaseViewHolder<Data> extends RecyclerView.ViewHolder {
        private Unbinder unbinder;
        private Data mData;
        private AdapterCallback<Data> callback;

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

        public void updateData(Data data) {
            if (callback != null) {
                callback.update(data, this);
            }
        }
    }

    /**
     * 设置适配器的监听
     *
     * @param adapterListener AdapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        mListener = adapterListener;
    }

    /**
     * 我们的自定义监听器
     *
     * @param <Data> 范型
     */
    public interface AdapterListener<Data> {
        // 当Cell点击的时候触发
        void onItemClick(BaseRecyclerAdapter.BaseViewHolder holder, Data data);

        // 当Cell长按时触发
        void onItemLongClick(BaseRecyclerAdapter.BaseViewHolder holder, Data data);
    }

    /**
     * 对回调接口做一次实现AdapterListener
     *
     * @param <Data>
     */
    public static class AdapterListenerImpl<Data> implements AdapterListener<Data> {

        @Override
        public void onItemClick(BaseViewHolder holder, Data data) {

        }

        @Override
        public void onItemLongClick(BaseViewHolder holder, Data data) {

        }
    }
}
