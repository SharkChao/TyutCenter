package com.tyutcenter.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Comment implements MultiItemEntity {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HIDE = 1;
    private String id;
    private String message_id;
    private String date;
    private String content;
    private int praise_num;//点赞人数
    private int view_type;

    private String user_id;
    private String url;
    private String nick_name;
    private String location;
    private String phone_type;
    private String praise_me_id;

    private String floor_id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone_type() {
        return phone_type;
    }

    public void setPhone_type(String phone_type) {
        this.phone_type = phone_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(int praise_num) {
        this.praise_num = praise_num;
    }

    @Override
    public int getItemType() {
        return view_type;
    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getPraise_me_id() {
        return praise_me_id;
    }

    public void setPraise_me_id(String praise_me_id) {
        this.praise_me_id = praise_me_id;
    }

    public String getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(String floor_id) {
        this.floor_id = floor_id;
    }
}
