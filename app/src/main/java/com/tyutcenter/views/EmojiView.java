package com.tyutcenter.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tyutcenter.R;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiTextView;

import ezy.ui.view.BadgeButton;

/**
 * 2018/11/17
 * Created by SharkChao
 * 827623353@qq.com
 * https://github.com/sharkchao
 */
public class EmojiView extends LinearLayout implements View.OnClickListener {
    private EmojiEditText mEmojiEditText;
    private EmojiTextView mEmojiTextView1;
    private EmojiTextView mEmojiTextView2;
    private EmojiTextView mEmojiTextView3;
    private EmojiTextView mEmojiTextView4;
    private EmojiPopup mEmojiPopup;
    private InputMethodManager mService;
    private TextView mTvSmile;
    private ImageView mIvSmile;
    private TextView mtvSend;
    private RelativeLayout rlTextTv;
    private RelativeLayout rlText;
    private RelativeLayout rlEdit;
    private onSendClickListenr mSendListener;
    private BadgeButton mIvComment;
    private ImageView mIvCollect;
    private ImageView mIvtransmit;
    private ImageView mIvMore;
    private int mCollect;

    public EmojiView(Context context) {
        super(context);
        init(context);
    }

    public EmojiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmojiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_emoji,this);
        mIvComment = findViewById(R.id.ivComment);
        mIvCollect = findViewById(R.id.wn_iv_collect);
        mIvtransmit = findViewById(R.id.ivTransmit);
        mIvMore = findViewById(R.id.ivMore);
        mTvSmile = findViewById(R.id.tvSmile);
        mtvSend = findViewById(R.id.tvSend);
        mIvSmile = findViewById(R.id.ivSmile);
        rlEdit = findViewById(R.id.rlEdit);
        rlText = findViewById(R.id.rlText);
        rlTextTv = findViewById(R.id.rlTextTv);
        mEmojiEditText = findViewById(R.id.emojiEditText);
        mEmojiTextView1 = findViewById(R.id.ejTextView1);
        mEmojiTextView2 = findViewById(R.id.ejTextView2);
        mEmojiTextView3 = findViewById(R.id.ejTextView3);
        mEmojiTextView4 = findViewById(R.id.ejTextView4);
        mEmojiPopup = EmojiPopup.Builder.fromRootView(findViewById(R.id.rootView)).build(mEmojiEditText);
        mService = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);

        initData();
    }

    private void initData(){
        rlTextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRLInput();
            }
        });

        mEmojiTextView1.setText("\uD83D\uDE04");
        mEmojiTextView2.setText("\uD83D\uDE48");
        mEmojiTextView3.setText("\uD83D\uDE17");
        mEmojiTextView4.setText("\uD83D\uDE0D");
        mTvSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmojiPopup.toggle();
                mTvSmile.setTextColor(mEmojiPopup.isShowing()?getResources().getColor(R.color.colorPrimary):getResources().getColor(R.color.gray_1));
                mIvSmile.setImageDrawable(mEmojiPopup.isShowing()?getResources().getDrawable(R.mipmap.wn_iv_smile_red):getResources().getDrawable(R.mipmap.wn_iv_smile));
            }
        });
        mIvSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmojiPopup.toggle();
                mTvSmile.setTextColor(mEmojiPopup.isShowing()?getResources().getColor(R.color.colorPrimary):getResources().getColor(R.color.gray_1));
                mIvSmile.setImageDrawable(mEmojiPopup.isShowing()?getResources().getDrawable(R.mipmap.wn_iv_smile_red):getResources().getDrawable(R.mipmap.wn_iv_smile));
            }
        });

        mtvSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSendListener != null){
                    String value = mEmojiEditText.getText().toString();
                    mSendListener.onclick(v,value);
                    hideRLInput();
                }
            }
        });
        mEmojiTextView1.setOnClickListener(this);
        mEmojiTextView2.setOnClickListener(this);
        mEmojiTextView3.setOnClickListener(this);
        mEmojiTextView4.setOnClickListener(this);
        mIvComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mIvtransmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "转发功能正在开发中...", Toast.LENGTH_SHORT).show();
            }
        });
        mIvMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "更多功能正在开发中...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    public void hideRLInput(){
        if (rlEdit.getVisibility() == View.VISIBLE){
            rlEdit.setVisibility(View.GONE);
            rlText.setVisibility(View.VISIBLE);
            mService.hideSoftInputFromWindow(rlEdit.getWindowToken(),0);
            if (mEmojiPopup.isShowing()){
                mEmojiPopup.dismiss();
            }
            mEmojiEditText.setText("");
        }
    }
    public void showRLInput(){
        rlText.setVisibility(View.GONE);
        rlEdit.setVisibility(View.VISIBLE);
        mEmojiEditText.setFocusableInTouchMode(true);
        mEmojiEditText.requestFocus();
        mService.showSoftInput(mEmojiEditText,0);
    }

    public boolean isShow(){
        return rlEdit.getVisibility() == VISIBLE;
    }

    public void registerSendListener(onSendClickListenr listener){
        mSendListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ejTextView1:
                mEmojiEditText.setText(mEmojiEditText.getText()+"\uD83D\uDE04");
                break;
            case R.id.ejTextView2:
                mEmojiEditText.setText(mEmojiEditText.getText()+"\uD83D\uDE48");
                break;
            case R.id.ejTextView3:
                mEmojiEditText.setText(mEmojiEditText.getText()+"\uD83D\uDE17");
                break;
            case R.id.ejTextView4:
                mEmojiEditText.setText(mEmojiEditText.getText()+"\uD83D\uDE0D");
                break;
        }
        mEmojiEditText.setSelection(mEmojiEditText.getText().length());
    }

    public interface  onSendClickListenr{
        void onclick(View view,String content);
    }
    public void setHintText(String text){
        mEmojiEditText.setHint(text);
    }

    public void setCollect(int collect){
        mCollect = collect;
        mIvCollect.setImageDrawable(collect == 1 ? getResources().getDrawable(R.mipmap.wn_iv_collect_red) : getResources().getDrawable(R.mipmap.wn_iv_collect));
    }

    public int getCollect() {
        return mCollect;
    }
    public void setCommentMessage(int count){
        mIvComment.setBadgeText(count+"");
        mIvComment.requestLayout();
    }

    public void setCommentClickListener(OnClickListener onClickListener){
        if (onClickListener != null){
            mIvComment.setOnClickListener(onClickListener);
        }
    }
    public void setCollectClickListener(OnClickListener onClickListener){
        if (onClickListener != null){
            mIvCollect.setOnClickListener(onClickListener);
        }
    }
    public void setTransmitVisible(boolean visible){
        mIvtransmit.setVisibility(visible ? VISIBLE : GONE);
    }
    public void setCollectVisible(boolean visible){
        mIvCollect.setVisibility(visible ? VISIBLE : GONE);
    }
    public void setMoreVisible(boolean visible){
        mIvMore.setVisibility(visible ? VISIBLE : GONE);
    }
}
