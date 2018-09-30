package com.tyutcenter.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;

import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.model.Comment;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.views.FloorView;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_comment)
public class CommentActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.MainUi{

    private FloorView mFloorView;

    @Override
    public void initTitle() {
        isShowToolBar(true);
        setLeftTitle("");
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mFloorView = findViewById(R.id.flView);
    }

    @Override
    public void initData() {
        List<Comment>list = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            Comment comment = new Comment();
            comment.setUrl("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=911350621,571820694&fm=27&gp=0.jpg");
            comment.setNick_name("火星网友");
            comment.setLocation("上海市临汾市尧都区");
            comment.setPhone_type("锤子手机");
            comment.setDate("1小时前");
            comment.setPraise_num(i+20);
            comment.setContent("这只是一条简单的评论");
            list.add(comment);
        }
        mFloorView.initFloor(list);
    }

    @Override
    protected void initEvent() {

    }
    public static void startCommentActivity(Context context){
        Intent intent = new Intent(context,CommentActivity.class);
        context.startActivity(intent);
    }
}
