package com.tyutcenter.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tyutcenter.R;

public class ExpressDetailAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public ExpressDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.ivImg);
        Glide.with(helper.itemView.getContext()).load(item).asBitmap().fitCenter()
                .skipMemoryCache(false).into(imageView);
    }
}
