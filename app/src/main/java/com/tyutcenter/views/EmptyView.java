package com.tyutcenter.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tyutcenter.R;


/**
 * Created by Administrator on 2017-07-06.
 *
 * 适用于 recycleView 的空布局
 * getview方法最后必须调用
 */

public class EmptyView extends LinearLayout {

    public static final String TYPE_LOADING = "000";
    public static final String TYPE_ERROR = "001";
    public static final String TYPE_NO_DATA = "002";
    private TextView tvTitle;
    private ImageView ivEmpty;
    private ProgressBar progressBar;
    private ConstraintLayout plLayout;
    private String mType;

    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
       LayoutInflater.from(context).inflate(R.layout.wn_empty_view,this);
        ivEmpty = findViewById(R.id.ivEmpty);
        tvTitle = findViewById(R.id.tvTitle);
        plLayout= findViewById(R.id.plLayout);
        progressBar = findViewById(R.id.progressBar);
    }

    public  void setType(String type){
        mType = type;
        if (type.equals(TYPE_LOADING)){
            ivEmpty.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else if (type.equals(TYPE_ERROR)){
            ivEmpty.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            ivEmpty.setImageResource(R.mipmap.wn_no_network_default);
        }else if (type.equals(TYPE_NO_DATA)){
            ivEmpty.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            ivEmpty.setImageResource(R.mipmap.wn_iv_no_data);
        }
    }
    public  void setMessage(String text){
        tvTitle.setText(text);
    }
    public void setRefreshListener(OnClickListener listener){
        if (listener != null){
            plLayout.setOnClickListener(listener);
        }
    }

    public String getType() {
        return mType;
    }
}
