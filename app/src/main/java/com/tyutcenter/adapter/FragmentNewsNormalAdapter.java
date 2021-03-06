package com.tyutcenter.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tyutcenter.R;
import com.tyutcenter.model.News;
import com.tyutcenter.utils.CommonUtil;

/**
 * Created by Admin on 2018/2/2.
 */

public class FragmentNewsNormalAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
    public FragmentNewsNormalAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        helper.setText(R.id.tvTitle, CommonUtil.isStrEmpty(item.getTitle())?"未知":item.getTitle());
        helper.setText(R.id.tvTime, CommonUtil.isStrEmpty(item.getTime())?"未知":item.getTime());
        helper.setText(R.id.tvFrom, CommonUtil.isStrEmpty(item.getFrom())?"未知":item.getFrom());
        SimpleDraweeView ivPic = helper.getView(R.id.ivPic);
        ivPic.setImageURI(Uri.parse(item.getPicUrl()));
    }

}
