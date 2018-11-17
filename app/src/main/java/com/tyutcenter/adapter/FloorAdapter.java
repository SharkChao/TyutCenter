package com.tyutcenter.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tyutcenter.R;
import com.tyutcenter.model.Comment;
import com.tyutcenter.utils.CommonUtil;
import com.tyutcenter.utils.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FloorAdapter extends BaseMultiItemQuickAdapter<Comment,BaseViewHolder>{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    public FloorAdapter(List<Comment> data) {
        super(data);
        addItemType(Comment.TYPE_NORMAL, R.layout.view_comment_normal);
        addItemType(Comment.TYPE_HIDE, R.layout.view_comment_hide);
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        switch (helper.getItemViewType()){
            case Comment.TYPE_NORMAL:
                helper.setText(R.id.tvName, CommonUtil.isStrEmpty(item.getNick_name())?"未知":item.getNick_name());
                helper.setText(R.id.tvLocation,CommonUtil.isStrEmpty(item.getLocation())?"未知":item.getLocation());
                helper.setText(R.id.tvPhoneType,CommonUtil.isStrEmpty(item.getPhone_type())?"未知":item.getPhone_type());
                SimpleDateFormat sf=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                try {
                    Date date = sf.parse(CommonUtil.isStrEmpty(item.getDate()) ? "2000年01月01日 01时01分01秒" : item.getDate());
                    helper.setText(R.id.tvDate, TimeUtil.getTimeFormatText(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                helper.setText(R.id.tvPraise,CommonUtil.isStrEmpty(item.getPraise_num()+"")?"未知":item.getPraise_num()+"");
                break;
            case Comment.TYPE_HIDE:
                break;
        }
    }

}
