package com.tyutcenter.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tyutcenter.R;
import com.tyutcenter.adapter.ExpressDetailAdapter;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.model.Emojicon;
import com.tyutcenter.model.Faceicon;
import com.tyutcenter.model.Message;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.utils.CommonUtil;
import com.tyutcenter.utils.DensityUtil;
import com.tyutcenter.views.KJChatKeyboard;
import com.tyutcenter.views.chat.OnOperationListener;

import java.util.ArrayList;
import java.util.Arrays;

@ContentView(R.layout.activity_express_detail)
public class ExpressDetailActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.ExpressDetailUi{

    private Message mMessage;
    private TextView mTvName;
    private TextView mTvTime;
    private TextView mTvTitle;
    private TextView mTvContent;
    private RecyclerView mRvList;
    private ExpressDetailAdapter mAdapter;
    private KJChatKeyboard mChatMsgInputBox;
    private LinearLayout mLayout;
    private NestedScrollView mScrollView;
    @SuppressLint("NewApi")
    @Override
    public void initTitle() {
        isShowToolBar(true);
        setCenterTitle("");
        isShowLeft(true);
        setLeftDefault();
        setRightTitle("200跟帖", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.startCommentActivity(ExpressDetailActivity.this);
            }
        });
       mMessage = (Message) getIntent().getExtras().getSerializable("msg");
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mTvName = findViewById(R.id.tvName);
        mTvTime = findViewById(R.id.tvTime);
        mTvContent = findViewById(R.id.tvContent);
        mTvTitle = findViewById(R.id.tvTitle);
        mRvList = findViewById(R.id.rvList);
        mLayout = findViewById(R.id.llView);
        mScrollView = findViewById(R.id.scrollView);
        mChatMsgInputBox = findViewById(R.id.chat_msg_input_box);
        mTvTime.setFocusableInTouchMode(true);
        mTvTime.requestFocus();
        mRvList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new ExpressDetailAdapter(R.layout.activity_express_detail_item);
        //第一次不需要进入加载更多的回调中
        mRvList.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        mTvName.setText(CommonUtil.isStrEmpty(mMessage.getPublish_person_name())?"未知":mMessage.getPublish_person_name());
        mTvContent.setText(CommonUtil.isStrEmpty(mMessage.getMsg_content())?"未知":mMessage.getMsg_content());
        mTvTime.setText(CommonUtil.isStrEmpty(mMessage.getMsg_date())?"未知":mMessage.getMsg_date());
        mTvTitle.setText(CommonUtil.isStrEmpty(mMessage.getMsg_title())?"未知":mMessage.getMsg_title());

        if (CommonUtil.isStrEmpty(mMessage.getMsg_content())){
            mTvContent.setVisibility(View.GONE);
        }
        mAdapter.setNewData(Arrays.asList(mMessage.getImages()));
    }

    @SuppressLint("NewApi")
    @Override
    protected void initEvent() {
        mChatMsgInputBox.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {
                hideInputKeyboard();
            }

            @Override
            public void selectedFace(Faceicon content) {
            }

            @Override
            public void selectedEmoji(Emojicon content) {
            }

            @Override
            public void selectedBackSpace(Emojicon back) {
            }

            @Override
            public void selectedFunction(int index) {
            }

        });

        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 0){
                  setLargeAnimation();
                }else {
                   setSmallAnimation();
                }
                hideInputKeyboard();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PhotoPreviewActivity.startPhotoPreview(ExpressDetailActivity.this, new ArrayList<String>( Arrays.asList(mMessage.getImages())),position);
            }
        });
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInputKeyboard();
            }
        });

    }
    @SuppressLint("NewApi")
    public void hideInputKeyboard(){
        mChatMsgInputBox.hideLayout();
        mChatMsgInputBox.hideKeyboard(ExpressDetailActivity.this);
//        mRvList.scrollToPosition(mMessages.size()-1);
    }
    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    private View.OnTouchListener getOnTouchListener() {
        return new View.OnTouchListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mChatMsgInputBox.hideLayout();
                mChatMsgInputBox.hideKeyboard(ExpressDetailActivity.this);
                return false;
            }
        };
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mChatMsgInputBox.isShow()) {
            mChatMsgInputBox.hideLayout();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    public static void startExpressDetailActivity(Context context,Message message){
        Intent intent = new Intent(context,ExpressDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("msg",message);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void setLargeAnimation(){
        getRightTitle().setBackground(getResources().getDrawable(R.drawable.shape_top_comment_white));
        getRightTitle().setTextColor(getResources().getColor(R.color.colorPrimary));
// 步骤1：设置属性数值的初始值 & 结束值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getRightTitle().getLayoutParams().width, DensityUtil.dip2px(this,150));

// 步骤2：设置动画的播放各种属性
        valueAnimator.setDuration(500);
        // 设置动画运行时长:1s

// 步骤3：将属性数值手动赋值给对象的属性:此处是将 值 赋给 按钮的宽度
        // 设置更新监听器：即数值每次变化更新都会调用该方法
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                System.out.println(currentValue);
                getRightTitle().getLayoutParams().width = currentValue;
                // 每次值变化时，将值手动赋值给对象的属性
                // 即将每次变化后的值 赋 给按钮的宽度，这样就实现了按钮宽度属性的动态变化

// 步骤4：刷新视图，即重新绘制，从而实现动画效果
                getRightTitle().requestLayout();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setRightTitle("共有200人参与跟帖");
            }
        });
        valueAnimator.start();
    }
    public void setSmallAnimation(){
        getRightTitle().setBackground(getResources().getDrawable(R.drawable.shape_top_comment_red));
        getRightTitle().setTextColor(getResources().getColor(R.color.white));
// 步骤1：设置属性数值的初始值 & 结束值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getRightTitle().getLayoutParams().width,DensityUtil.dip2px(this,80));

// 步骤2：设置动画的播放各种属性
        valueAnimator.setDuration(500);
        // 设置动画运行时长:1s

// 步骤3：将属性数值手动赋值给对象的属性:此处是将 值 赋给 按钮的宽度
        // 设置更新监听器：即数值每次变化更新都会调用该方法
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                System.out.println(currentValue);
                getRightTitle().getLayoutParams().width = currentValue;
                // 每次值变化时，将值手动赋值给对象的属性
                // 即将每次变化后的值 赋 给按钮的宽度，这样就实现了按钮宽度属性的动态变化

// 步骤4：刷新视图，即重新绘制，从而实现动画效果
                getRightTitle().requestLayout();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setRightTitle("200跟帖");
            }
        });
        valueAnimator.start();

    }
}
