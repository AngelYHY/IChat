package freestar.vip.common.common.widget.recycler;

/**
 * 描述：
 * 作者：一颗浪星
 * 日期：2017/10/10
 * github：
 */

public interface AdapterCallback<Data> {
    void update(Data data, BaseRecyclerAdapter.BaseViewHolder<Data> holder);
}
