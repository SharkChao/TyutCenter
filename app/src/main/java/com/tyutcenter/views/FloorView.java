package com.tyutcenter.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tyutcenter.R;
import com.tyutcenter.adapter.FloorAdapter;
import com.tyutcenter.model.Comment;
import com.tyutcenter.utils.CommentFloorItemDecoration;
import com.tyutcenter.utils.CommonUtil;
import com.tyutcenter.utils.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FloorView extends NestedScrollView{

    private ImageView mIvHead;
    private TextView mTvName;
    private TextView mTvLocation;
    private TextView mTvPhoneType;
    private TextView mTvDate;
    private TextView mTvContent;
    private TextView mTvPraise;
    private CommentRecyclerView mRvList;
    private int maxComment = 5;
    private boolean isExpend;
    private FloorAdapter mAdapter;
    private List<Comment> mCommentList = new ArrayList<>();


    public FloorView(Context context) {
        super(context);
        initView(context);
    }

    public FloorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FloorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_floor, this,true);

        mIvHead = findViewById(R.id.ivHead);
        mTvName = findViewById(R.id.tvName);
        mTvLocation = findViewById(R.id.tvLocation);
        mTvPhoneType = findViewById(R.id.tvPhoneType);
        mTvDate = findViewById(R.id.tvDate);
        mTvContent = findViewById(R.id.tvContent);
        mTvPraise = findViewById(R.id.tvPraise);
        mRvList = findViewById(R.id.rvList);
        mRvList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRvList.addItemDecoration(new CommentFloorItemDecoration(getContext()));
        mAdapter = new FloorAdapter(mCommentList);
        mRvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 2 && !isExpend){
                    isExpend = true;
                    refreshFloor();
                }
            }
        });
    }
    public void initFloor(List<Comment>list){
        if (list != null && list.size() > 0){
            Comment comment = list.get(list.size()-1);
            Glide.with(getContext()).load(comment.getUrl()).into(mIvHead);
            mTvName.setText(CommonUtil.isStrEmpty(comment.getNick_name())?"未知":comment.getNick_name());
            SimpleDateFormat sf=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            try {
                Date date = sf.parse(CommonUtil.isStrEmpty(comment.getDate()) ? "2000年01月01日 01时01分01秒" : comment.getDate());
                mTvDate.setText(TimeUtil.getTimeFormatText(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mTvLocation.setText(CommonUtil.isStrEmpty(comment.getLocation())?"山西省太原市":comment.getLocation());
            mTvPhoneType.setText(CommonUtil.isStrEmpty(comment.getPhone_type())?"国产手机":comment.getPhone_type());
            mTvContent.setText(CommonUtil.isStrEmpty(comment.getContent())?"未知":comment.getContent());
            mTvPraise.setText(CommonUtil.isStrEmpty(comment.getPraise_num()+"")?"未知":comment.getPraise_num()+"");
            if (list != null && list.size() > 1){
                mRvList.setVisibility(VISIBLE);
                mCommentList = list.subList(0, list.size() - 2);
                refreshFloor();
            }else {
                mRvList.setVisibility(GONE);
            }
        }
    }
    private void refreshFloor(){
        if (mCommentList != null){
            if (mCommentList.size() >= maxComment){
                if (isExpend){
                    mCommentList.get(2).setView_type(Comment.TYPE_NORMAL);
                    mAdapter.setNewData(mCommentList);
                }else {
                    mCommentList.get(2).setView_type(Comment.TYPE_HIDE);
                    mAdapter.setNewData(cloneList(mCommentList).subList(0,maxComment-1));
                }
            }else {
                mAdapter.setNewData(mCommentList);
            }
        }
    }
    private static <T> List<T> cloneList(List<T> originList) {
        List<T> dest = new ArrayList<>();
        if (originList == null || originList.isEmpty()) {
            return dest;
        }
        dest.addAll(originList);
        return dest;
    }

}
