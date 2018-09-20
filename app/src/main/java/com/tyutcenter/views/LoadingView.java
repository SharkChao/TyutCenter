package com.tyutcenter.views;

import android.app.Dialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tyutcenter.R;
import com.tyutcenter.base.BaseActivity;

/**
 * 自定义加载view
 * Created by yuzhijun on 2017/7/1.
 */
public class LoadingView extends Dialog {
    private LoadingView currentDialog;
    private ImageView ivLoading;

    public LoadingView() {
        super(BaseActivity.currentActivity);
        currentDialog = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wn_loading_view_layout);
        Window window = this.getWindow();
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        currentDialog.setCancelable(true);
        currentDialog.setCanceledOnTouchOutside(false);

        ivLoading = (ImageView) this.findViewById(R.id.ivLoading);
        ivLoading.setImageResource(R.mipmap.wn_iv_loading_view);
        ivLoading.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        Animation animation = AnimationUtils.loadAnimation(BaseActivity.currentActivity,R.anim.rotate_loading);
        ivLoading.startAnimation(animation);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        BaseActivity.currentActivity.setLoading(false);
    }
}
