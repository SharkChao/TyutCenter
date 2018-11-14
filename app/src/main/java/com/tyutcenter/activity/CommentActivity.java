package com.tyutcenter.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;

import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.views.FloorView;

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

    }

    @Override
    protected void initEvent() {

    }
    public static void startCommentActivity(Context context){
        Intent intent = new Intent(context,CommentActivity.class);
        context.startActivity(intent);
    }
}
