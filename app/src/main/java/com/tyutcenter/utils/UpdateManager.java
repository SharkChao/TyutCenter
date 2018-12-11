package com.tyutcenter.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tyutcenter.R;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.model.UpdateData;
import com.tyutcenter.views.UpdateDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 更新模块
 * 
 * @author LinHao 439224@qq.com
 * @version 创建时间： 2013-7-30 下午4:25:08
 */
@SuppressLint("HandlerLeak")
public class UpdateManager {
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	private String sdName = "wn_iv_app_icon.apk";
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	public static boolean hasUpdate = false;

	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				mTvProgress.setText(progress+"%");
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};
	private TextView mTvProgress;

	public UpdateManager() {
		this.mContext = BaseActivity.currentActivity;
	}

	/**
	 * 检测软件更新
	 */
	public void checkUpdate() {
		if (isUpdate()) {
			// 显示提示对话框
			showNoticeDialog();
		}
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间： 2013-8-1 下午3:05:51
	 */
	public boolean isUpdate() {
		int serviceCode = Integer.parseInt(UpdateData.getAppVersion().getVersion_code());
		int versionCode = CommonUtil.getVersionCode(BaseActivity.currentActivity);
		int need = Integer.parseInt(UpdateData.getAppVersion().getUpdate_type());
		// 是否必须更新
		if (need == 1) {
			hasUpdate = true;
		} else {
			hasUpdate = false;
		}
		// 版本判断
		if (serviceCode > versionCode) {
			return true;
		}
		return false;
	}

	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog() {
		final UpdateDialog updateDialog = new UpdateDialog(
				BaseActivity.currentActivity); // , 400, 300
		updateDialog.show();
		Button btnUpdate = (Button) updateDialog.findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				updateDialog.dismiss();
				// 显示下载对话框
				showDownloadDialog();
			}
		});
		Button btnClose = (Button) updateDialog.findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (hasUpdate) {
					Toast.makeText(BaseActivity.currentActivity,"此版本必须更新才能继续使用!", Toast.LENGTH_SHORT).show();
					new Handler() {
						@Override
						public void handleMessage(Message msg) {
							super.handleMessage(msg);
							System.exit(0);
						}
					}.sendEmptyMessageDelayed(1, 2000);
				} else {
					updateDialog.cancel();
				}
			}
		});
		String content = UpdateData.getAppVersion().getApp_intro();
		if (!CommonUtil.isStrEmpty(content)) {
			TextView tvUpdateContent = (TextView) updateDialog
					.findViewById(R.id.tvUpdateContent);
			tvUpdateContent.setText(content);
		}
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {
		// 构造软件下载对话框
		Builder builder = new Builder(mContext);
		builder.setCancelable(false);
		builder.setTitle("正在更新");
		builder.setTitle("");
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		mTvProgress = (TextView) v.findViewById(R.id.tvProgress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton("取消",
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 设置取消状态
						cancelUpdate = true;
//						System.exit(0);
					}
				});
		mDownloadDialog = builder.create();
		mDownloadDialog.setCancelable(false);
		mDownloadDialog.setCanceledOnTouchOutside(false);
		mDownloadDialog.show();
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间： 2013-7-30 下午4:25:08
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					URL url = new URL(UpdateData.getAppVersion().getDownload_url());
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, sdName);
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
//		CommonUtil.setShardPString(Constants.LOGIN_USER, "");
		File apkfile = new File(mSavePath, sdName);
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		Uri data;
		// 判断版本大于等于7.0
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			// "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
			data = FileProvider.getUriForFile(mContext, "com.winning.hospitalgroup.provider", apkfile);
			// 给目标应用一个临时授权
			i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		} else {
			data = Uri.fromFile(apkfile);
		}
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		i.setDataAndType(data,
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}

}
