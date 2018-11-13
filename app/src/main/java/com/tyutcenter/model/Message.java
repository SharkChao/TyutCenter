package com.tyutcenter.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tyutcenter.utils.CommonUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;

public class Message implements MultiItemEntity,Serializable {

    public static final int TYPE_IMG0 = 0;
    public static final int TYPE_IMG1 = 1;
    public static final int TYPE_IMG2 = 2;
    public static final int TYPE_IMG3 = 3;
    private int id;
    private String images[];
    private String msg_title;
    private String msg_type;
    private String msg_date;
    private String msg_imgs;
    private String msg_content;
    private int publish_person;
    private int layout_type;
    private String publish_person_name;
    private String head_url;

    public String getMsg_title() {
        return msg_title;
    }

    public void setMsg_title(String msg_title) {
        this.msg_title = msg_title;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getMsg_date() {
        return msg_date;
    }

    public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }

    public String getMsg_imgs() {
        return msg_imgs;
    }

    public void setMsg_imgs(String msg_imgs) {
        this.msg_imgs = msg_imgs;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public int getLayout_type() {
        return layout_type;
    }

    public void setLayout_type(int layout_type) {
        this.layout_type = layout_type;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    @Override
    public int getItemType() {
        JSONArray param = null;
        String[] IDList;
        try {
            if (!CommonUtil.isStrEmpty(getMsg_imgs())){
                String replace = getMsg_imgs().replace("[", "[\"").replace("]", "\"]").replace(",", "\",\"").replace(" ","");
                param = new JSONArray(replace);
                //设置String数组大小
                IDList = new String[param.length()];
                for (int i =0;i<param.length();i++){
                    IDList[i] = param.get(i).toString();
                }
                images = IDList;
                return IDList.length < 3 ? IDList.length:3;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPublish_person() {
        return publish_person;
    }

    public void setPublish_person(int publish_person) {
        this.publish_person = publish_person;
    }

    public String getPublish_person_name() {
        return publish_person_name;
    }

    public void setPublish_person_name(String publish_person_name) {
        this.publish_person_name = publish_person_name;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
