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

import com.tyutcenter.R;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;

/**
 * 2018/11/17
 * Created by SharkChao
 * 827623353@qq.com
 * https://github.com/sharkchao
 */
public class EmojiView extends LinearLayout{
    private EmojiEditText mEmojiEditText;
    private EmojiPopup mEmojiPopup;
    private InputMethodManager mService;
    private TextView mTvSmile;
    private ImageView mIvSmile;
    private TextView mtvSend;
    private RelativeLayout rlTextTv;
    private RelativeLayout rlText;
    private RelativeLayout rlEdit;
    private onSendClickListenr mSendListener;
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
        mTvSmile = findViewById(R.id.tvSmile);
        mtvSend = findViewById(R.id.tvSend);
        mIvSmile = findViewById(R.id.ivSmile);
        rlEdit = findViewById(R.id.rlEdit);
        rlText = findViewById(R.id.rlText);
        rlTextTv = findViewById(R.id.rlTextTv);
        mEmojiEditText = findViewById(R.id.emojiEditText);
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

        mTvSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmojiPopup.toggle();
            }
        });
        mIvSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmojiPopup.toggle();
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

    public void registerSendListener(onSendClickListenr listener){
        mSendListener = listener;
    }
    public interface  onSendClickListenr{
        void onclick(View view,String content);
    }
}
