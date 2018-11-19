package com.tyutcenter.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tyutcenter.R;
import com.tyutcenter.data.UserData;
import com.tyutcenter.model.Comment;
import com.tyutcenter.utils.CommonUtil;
import com.tyutcenter.utils.TimeUtil;
import com.tyutcenter.views.FloorView;

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
    private FloorView.OnPraiseClickListener mOnPraiseClickListener;
    public FloorAdapter(List<Comment> data) {
        super(data);
        addItemType(Comment.TYPE_NORMAL, R.layout.view_comment_normal);
        addItemType(Comment.TYPE_HIDE, R.layout.view_comment_hide);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Comment item) {
        switch (helper.getItemViewType()){
            case Comment.TYPE_NORMAL:
                helper.setText(R.id.tvContent, CommonUtil.isStrEmpty(item.getContent())?"未知":item.getContent());
                helper.setText(R.id.tvName, CommonUtil.isStrEmpty(item.getNick_name())?"未知":item.getNick_name());
                helper.setText(R.id.tvLocation,CommonUtil.isStrEmpty(item.getLocation())?"山西省太原市":item.getLocation());
                helper.setText(R.id.tvPhoneType,CommonUtil.isStrEmpty(item.getPhone_type())?"国产手机":item.getPhone_type());
                SimpleDateFormat sf=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                try {
                    Date date = sf.parse(CommonUtil.isStrEmpty(item.getDate()) ? "2000年01月01日 01时01分01秒" : item.getDate());
                    helper.setText(R.id.tvDate, TimeUtil.getTimeFormatText(date));
                    Log.e("tag",TimeUtil.getTimeFormatText(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                helper.setText(R.id.tvPraise,CommonUtil.isStrEmpty(item.getPraise_num()+"")?"未知":item.getPraise_num()+"");
                final ImageView ivPraise = helper.getView(R.id.ivPraise);
                ivPraise.setImageResource(CommonUtil.isStrEmpty(item.getPraise_me_id())?R.mipmap.praise_normal:R.mipmap.praise_red);
                final TextView tvPraise = helper.getView(R.id.tvPraise);
                tvPraise.setTextColor(CommonUtil.isStrEmpty(item.getPraise_me_id())?mContext.getResources().getColor(R.color.gray_1):mContext.getResources().getColor(R.color.colorPrimary));

                //设置点击事件
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnPraiseClickListener != null && item != null) {
                            if (item.getPraise_me_id() == UserData.getUser().getId()) {
                                Toast.makeText(mContext, "请勿重复点赞!", Toast.LENGTH_SHORT).show();
                            } else {
                                mOnPraiseClickListener.onPraise(item);
                                ivPraise.setImageResource(CommonUtil.isStrEmpty(item.getPraise_me_id()) ? R.mipmap.praise_normal : R.mipmap.praise_red);
                                tvPraise.setTextColor(CommonUtil.isStrEmpty(item.getPraise_me_id()) ? mContext.getResources().getColor(R.color.gray_1) : mContext.getResources().getColor(R.color.colorPrimary));
                            }
                        }
                    }
                };
                ivPraise.setOnClickListener(listener);
                tvPraise.setOnClickListener(listener);
                break;
            case Comment.TYPE_HIDE:
                break;
        }
    }

    public void registerPraiseListener(FloorView.OnPraiseClickListener onClickListener){
        mOnPraiseClickListener = onClickListener;
    }
}
