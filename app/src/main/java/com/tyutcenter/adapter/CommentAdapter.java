package com.tyutcenter.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tyutcenter.R;
import com.tyutcenter.model.Floor;
import com.tyutcenter.views.FloorView;

/**
 * 2018/11/15
 * Created by SharkChao
 * 827623353@qq.com
 * https://github.com/sharkchao
 */
public class CommentAdapter extends BaseQuickAdapter<Floor,BaseViewHolder> {

    public CommentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Floor item) {
        FloorView floorView = helper.getView(R.id.floor_view);
        floorView.initFloor(item.getComments());
    }
}
