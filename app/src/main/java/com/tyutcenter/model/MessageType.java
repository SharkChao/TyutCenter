package com.tyutcenter.model;

import java.io.Serializable;

public class MessageType implements Serializable{
    private int id;
    private String msg_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }
}
