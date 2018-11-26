package com.tyutcenter.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.tyutcenter.R;
import com.tyutcenter.adapter.CommentAdapter;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.data.UserData;
import com.tyutcenter.model.Comment;
import com.tyutcenter.model.CommentPraise;
import com.tyutcenter.model.Floor;
import com.tyutcenter.model.ResponseError;
import com.tyutcenter.model.Result;
import com.tyutcenter.model.User;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.views.EmojiView;
import com.tyutcenter.views.EmptyView;
import com.tyutcenter.views.FloorView;
import com.tyutcenter.views.RecycleViewDivider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ContentView(R.layout.activity_comment)
public class CommentActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.CommentUi{

    private static final String TAG_MESSAGE_ID = "message_id";
    private String mMessage_id;
    private RecyclerView rvHot;
    private RecyclerView rvAll;
    private CommentAdapter mAllAdapter;
    private CommentAdapter mHotAdapter;
    private EmptyView mEmptyViewHot;
    private EmptyView mEmptyViewAll;
    private EmojiView mEmojiView;
    private NestedScrollView mScrollView;
    private Comment mCurrentComment;

    @Override
    public void initTitle() {
        isShowToolBar(true);
        setCenterTitle("评论");
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        rvAll = findViewById(R.id.rvAll);
        rvHot = findViewById(R.id.rvHot);
        mEmojiView = findViewById(R.id.emojiView);
        mScrollView = findViewById(R.id.scrollView);
        mEmptyViewAll = new EmptyView(this);
        mEmptyViewHot = new EmptyView(this);
        mEmptyViewAll.setType(EmptyView.TYPE_LOADING);
        mEmptyViewHot.setType(EmptyView.TYPE_LOADING);
    }

    @Override
    public void initData() {
        mMessage_id = getIntent().getStringExtra(TAG_MESSAGE_ID);
        getCommentByServer();
        getCallbacks().getCommentCount(mMessage_id);
    }

    private void getCommentByServer(){
        User user = UserData.getUser();
        if (user != null){
            getCallbacks().getAllComment(mMessage_id,user.getId());
            getCallbacks().getHotComment(mMessage_id,user.getId());
        }
    }

    @Override
    protected void initEvent() {
        rvAll.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvHot.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvAll.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
        rvHot.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
        mAllAdapter = new CommentAdapter(R.layout.activity_comment_item);
        mHotAdapter = new CommentAdapter(R.layout.activity_comment_item);
        mAllAdapter.setEmptyView(mEmptyViewAll);
        mHotAdapter.setEmptyView(mEmptyViewHot);
        rvAll.setAdapter(mAllAdapter);
        rvHot.setAdapter(mHotAdapter);

        mEmojiView.registerSendListener(new EmojiView.onSendClickListenr() {
            @Override
            public void onclick(View view, String content) {
                Comment comment = new Comment();
                comment.setMessage_id(mMessage_id);
                comment.setContent(content);
                SimpleDateFormat sf=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                comment.setDate(sf.format(new Date()));
                comment.setUser_id(UserData.getUser().getId());
                if (mCurrentComment != null){
                    comment.setFloor_id(mCurrentComment.getFloor_id());
                }
                getCallbacks().createComment(comment);
            }
        });
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEmojiView.hideRLInput();
                return false;
            }
        });
        rvHot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEmojiView.hideRLInput();
                return false;
            }
        });
        rvAll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEmojiView.hideRLInput();
                return false;
            }
        });
        FloorView.OnPraiseClickListener onPraiseClickListener = new FloorView.OnPraiseClickListener() {
            @Override
            public void onPraise(Comment comment) {
                showProgressDialog();
                CommentPraise commentPraise = new CommentPraise();
                commentPraise.setComment_id(comment.getId());
                commentPraise.setMessage_id(comment.getMessage_id());
                commentPraise.setUser_id(UserData.getUser().getId());
                getCallbacks().createPraise(commentPraise);
            }
        };
        FloorView.onCommentClickListener onCommentClickListener = new FloorView.onCommentClickListener() {
            @Override
            public void onComment(Comment comment) {
                mEmojiView.showRLInput();
                mEmojiView.setHintText("回复"+comment.getNick_name()+":");
                mCurrentComment = comment;
            }
        };
        mAllAdapter.registerPraiseListener(onPraiseClickListener);
        mHotAdapter.registerPraiseListener(onPraiseClickListener);
        mAllAdapter.registerCommentListener(onCommentClickListener);
        mHotAdapter.registerCommentListener(onCommentClickListener);
    }


    public static void startCommentActivity(Context context,String message_id){
        Intent intent = new Intent(context,CommentActivity.class);
        intent.putExtra(TAG_MESSAGE_ID,message_id);
        context.startActivity(intent);
    }

    @Override
    public void getAllComment(List<Floor> list) {
        mAllAdapter.setNewData(list);
        mEmptyViewAll.setType(EmptyView.TYPE_NO_DATA);
        mEmptyViewAll.setMessage("暂无评论");
    }

    @Override
    public void getHotComment(List<Floor> list) {
        mHotAdapter.setNewData(list);
        mEmptyViewHot.setType(EmptyView.TYPE_NO_DATA);
        mEmptyViewHot.setMessage("暂无热门评论");
    }

    @Override
    public void createComment(Result result) {
        Toast.makeText(this, "恭喜你,评论成功!", Toast.LENGTH_SHORT).show();
        getCommentByServer();
    }

    @Override
    public void createPraise(Result result) {
        if (result != null){
            if (result.getCode() > 0){
                Toast.makeText(this, "点赞成功!", Toast.LENGTH_SHORT).show();
                getCommentByServer();
            }else {
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
            hideProgressDialog();
        }
    }

    @Override
    public void getCommentCount(Result result) {
        if (result != null){
            mEmojiView.setCommentMessage(result.getCode());
        }
    }
    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
        hideProgressDialog();
        mEmptyViewHot.setType(EmptyView.TYPE_NO_DATA);
        mEmptyViewHot.setMessage(error.getMessage());
        mEmptyViewAll.setType(EmptyView.TYPE_NO_DATA);
        mEmptyViewAll.setMessage(error.getMessage());
    }
}
