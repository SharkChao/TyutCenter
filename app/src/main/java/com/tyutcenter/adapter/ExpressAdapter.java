package com.tyutcenter.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tyutcenter.R;
import com.tyutcenter.model.Message;
import com.tyutcenter.utils.CommonUtil;

import java.util.List;

public class ExpressAdapter extends BaseMultiItemQuickAdapter<Message,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpressAdapter(List<Message> data) {
        super(data);
        addItemType(Message.TYPE_IMG0, R.layout.fragment_express_type0);
        addItemType(Message.TYPE_IMG1, R.layout.fragment_express_type1);
        addItemType(Message.TYPE_IMG2, R.layout.fragment_express_type1);
        addItemType(Message.TYPE_IMG3, R.layout.fragment_express_type3);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        switch (helper.getItemViewType()){
            case Message.TYPE_IMG0:
                helper.setText(R.id.tvTitle, CommonUtil.isStrEmpty(item.getMsg_title())?"":item.getMsg_title());
                helper.setText(R.id.tvFrom,CommonUtil.isStrEmpty(item.getPublish_person()+"")?"":"管理员");
                helper.setText(R.id.tvDate,CommonUtil.isStrEmpty(item.getMsg_date())?"":item.getMsg_date());
                break;
            case Message.TYPE_IMG1:
                helper.setText(R.id.tvTitle, CommonUtil.isStrEmpty(item.getMsg_title())?"":item.getMsg_title());
                helper.setText(R.id.tvFrom,CommonUtil.isStrEmpty(item.getPublish_person()+"")?"":"管理员");
                helper.setText(R.id.tvDate,CommonUtil.isStrEmpty(item.getMsg_date())?"":item.getMsg_date());
                ImageView ivImg1 = helper.getView(R.id.ivImg1);
                Glide.with(helper.getConvertView().getContext()).load(item.getImages()[0]).placeholder(R.mipmap.iv_default_news).error(R.mipmap.iv_default_news).into(ivImg1);
                break;
            case Message.TYPE_IMG2:
                helper.setText(R.id.tvTitle, CommonUtil.isStrEmpty(item.getMsg_title())?"":item.getMsg_title());
                helper.setText(R.id.tvFrom,CommonUtil.isStrEmpty(item.getPublish_person()+"")?"":"管理员");
                helper.setText(R.id.tvDate,CommonUtil.isStrEmpty(item.getMsg_date())?"":item.getMsg_date());
                ImageView ivImg2 = helper.getView(R.id.ivImg1);
                Glide.with(helper.getConvertView().getContext()).load(item.getImages()[0]).placeholder(R.mipmap.iv_default_news).error(R.mipmap.iv_default_news).into(ivImg2);
                break;
            case Message.TYPE_IMG3:
                helper.setText(R.id.tvTitle, CommonUtil.isStrEmpty(item.getMsg_title())?"":item.getMsg_title());
                helper.setText(R.id.tvFrom,CommonUtil.isStrEmpty(item.getPublish_person()+"")?"":"管理员");
                helper.setText(R.id.tvDate,CommonUtil.isStrEmpty(item.getMsg_date())?"":item.getMsg_date());
                ImageView ivImg4 = helper.getView(R.id.ivImg1);
                ImageView ivImg5 = helper.getView(R.id.ivImg2);
                ImageView ivImg6 = helper.getView(R.id.ivImg3);
                Glide.with(helper.getConvertView().getContext()).load(item.getImages()[0]).placeholder(R.mipmap.iv_default_news).error(R.mipmap.iv_default_news).into(ivImg4);
                Glide.with(helper.getConvertView().getContext()).load(item.getImages()[1]).placeholder(R.mipmap.iv_default_news).error(R.mipmap.iv_default_news).into(ivImg5);
                Glide.with(helper.getConvertView().getContext()).load(item.getImages()[2]).placeholder(R.mipmap.iv_default_news).error(R.mipmap.iv_default_news).into(ivImg6);
                break;
        }
    }


}
