package first.winning.com.tyutcenter.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import first.winning.com.tyutcenter.utils.CommonUtil;


@SuppressLint("SetJavaScriptEnabled")
public class MyWebView extends WebView {

	private Context mContext;
	private ProgressBar progressbar;

	@SuppressWarnings("deprecation")
	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        // 设置javascript脚本
        WebSettings webSettings = this.getSettings();
        if (!isInEditMode()) { // 解决可视化编辑器无法识别自定义控件的问题
            webSettings.setJavaScriptEnabled(true);// 启用javascript
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setLoadsImagesAutomatically(true);
            // Dom存储 (处理JS调用错误时开�?
            webSettings.setDomStorageEnabled(true);
            webSettings.setDisplayZoomControls(false);//设定缩放控件隐藏
            webSettings.setSupportZoom(false); // 不支持缩放
            webSettings.setAppCacheEnabled(false);

            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            // 设置可以访问文件
            webSettings.setAllowFileAccess(true);
            // 设置支持缩放
            webSettings.setBuiltInZoomControls(true);
            // 设置webview可以加载网页图片
            webSettings.setBlockNetworkImage(false);
            webSettings.setBlockNetworkLoads(false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            }

            progressbar = new ProgressBar(context, null,
                    android.R.attr.progressBarStyleHorizontal);
            progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    3, 0, 0));
            addView(progressbar);
        }
		/**
		 * 设置WebViewClient WebViewClient是专门辅助WebView处理各种通知，请求等事件的类?
		 * */
		this.setWebViewClient(webViewClient);
		/**
		 * webChromeClient是辅助WebView处理javascript的对话框，网站图标，网站title,加载进度?
		 * */
		this.setWebChromeClient(webChromeClient);

		// setZoomControlGone(this);
	}

	/**
	 * 打开网页
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间2015-1-6 上午8:50:07
	 */
	public void setOpenUrl(String weburl) {
		try {
			String url = weburl;
			// if (URLUtil.isNetworkUrl(url)) {
			// this.loadUrl(url);
			// } else {
			// //
			// ViewUtil.showToast(ViewUtil.getString(R.string.error_netwokerror),
			// false);
			// }
			this.loadUrl(url);
		} catch (Exception e) {

		}
	}

	@SuppressLint("NewApi")
	private WebViewClient webViewClient = new WebViewClient() {

		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
			view.loadUrl(url);
			return true;
		}

		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// 在页面加载开始时调用
			super.onPageStarted(view, url, favicon);
		}

		public void onPageFinished(WebView view, String url) {
			// 在页面加载结束时调用
			super.onPageFinished(view, url);
			if (CommonUtil.isNotEmpty(jsCallBack)) {
				jsCallBack.jsCallBack();
			}
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       android.net.http.SslError error) {
			// 重写此方法可以让webview处理https请求
			handler.proceed();
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			view.loadUrl("file:///android_asset/error.html");
		}

		public void onLoadResource(WebView view, String url) {
			// 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用??
			super.onLoadResource(view, url);
		}

		public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
			// 重写此方法才能够处理在浏览器中的按键事件?
			return super.shouldOverrideKeyEvent(view, event);
		}

		// @SuppressLint("NewApi")
		// @TargetApi(Build.VERSION_CODES.HONEYCOMB)
		// public WebResourceResponse shouldInterceptRequest(WebView view,
		// String url) {
		// WebResourceResponse response = null;
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
		// response = super.shouldInterceptRequest(view,url);
		// if (url.contains("index.js")){
		// try {
		// response = new
		// WebResourceResponse("application/x-javascript","UTF-8",getContext().getAssets().open("index.js"));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// return response;
		// }
	};

	private WebChromeClient webChromeClient = new WebChromeClient() {
		// 处理js中的alert
		public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
			Builder builder = new Builder(mContext);
			builder.setTitle("提示");
			builder.setMessage(message);
			builder.setPositiveButton(android.R.string.ok,
					new AlertDialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 点击确定按键之后，继续执行网页中的操?
							result.confirm();
						}
					});
			builder.setCancelable(false);
			builder.create();
			builder.show();
			return true;
		};

		public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {
			Builder builder = new Builder(mContext);
			builder.setTitle("提示");
			builder.setMessage(message);
			builder.setPositiveButton(android.R.string.ok,
					new AlertDialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 点击确定按键之后，继续执行网页中的操?
							result.confirm();
						}
					});
			builder.setNegativeButton(android.R.string.cancel,
					new AlertDialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 点击取消
							result.cancel();
						}
					});
			builder.setCancelable(false);
			builder.create();
			builder.show();
			return true;
		};

		// 设置网页加载的进度条
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

		@Override
		// 设置应用程序的标题title
		public void onReceivedTitle(WebView view, String title) {
			// mTopBar.SetTvName(title);
			// WebActivity.this.setTitle();
			// Log.v("WebURL", webveiw.getUrl());
			super.onReceivedTitle(view, title);
		}

		@Override
		public void onExceededDatabaseQuota(String url,
                                            String databaseIdentifier, long currentQuota,
                                            long estimatedSize, long totalUsedQuota,
                                            QuotaUpdater quotaUpdater) {
			super.onExceededDatabaseQuota(url, databaseIdentifier,
					currentQuota, estimatedSize, totalUsedQuota, quotaUpdater);
		}

		@Override
		public void onReachedMaxAppCacheSize(long spaceNeeded,
				long totalUsedQuota, QuotaUpdater quotaUpdater) {
			super.onReachedMaxAppCacheSize(spaceNeeded, totalUsedQuota,
					quotaUpdater);
		}

	};

	@SuppressWarnings("deprecation")
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}

	// 实现放大缩小控件隐藏
	// @SuppressWarnings("rawtypes")
	// public void setZoomControlGone(View view) {
	// Field field;
	// try {
	// Class classType = WebView.class;
	// field = classType.getDeclaredField("mZoomButtonsController");
	// field.setAccessible(true);
	// ZoomButtonsController mZoomButtonsController = new
	// ZoomButtonsController(view);
	// mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
	// try {
	// field.set(view, mZoomButtonsController);
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// }
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (NoSuchFieldException e) {
	// e.printStackTrace();
	// }
	// }

	private IJsCallBack jsCallBack;

	public void registerJsCallBack(IJsCallBack jsCallBack) {
		this.jsCallBack = jsCallBack;
	}

	public interface IJsCallBack {
		public void jsCallBack();
	}
}
