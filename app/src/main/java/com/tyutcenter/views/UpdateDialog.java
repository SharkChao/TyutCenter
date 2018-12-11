package com.tyutcenter.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.tyutcenter.R;


public class UpdateDialog extends Dialog {

	public UpdateDialog(Context context) {
		super(context);
		setContentView(R.layout.lx_update_activity);
		Window window = getWindow();
		// 去除黑色背景
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
		params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.CENTER;
		this.setCancelable(false);
		this.setCanceledOnTouchOutside(false);
		// params.alpha = 0.1f;
		window.setAttributes(params);
	}
}
