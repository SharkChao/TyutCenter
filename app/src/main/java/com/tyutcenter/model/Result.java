package com.tyutcenter.model;

import java.io.Serializable;

public class Result implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6042320819805962134L;
	private  int code;//1表示成功 0表示失败
    private  String message;//返回消息

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
