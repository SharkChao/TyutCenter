package com.tyutcenter.model;


public class UpdateData {
	private static AppVersion appVersion;

	public static AppVersion getAppVersion() {
		return appVersion;
	}

	public static void setAppVersion(AppVersion appVersion) {
		UpdateData.appVersion = appVersion;
	}

}
