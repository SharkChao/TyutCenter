package com.tyutcenter.model;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private String message_id;
    private String comment_ids;
    private String id;
    private List<Comment>comments = new ArrayList<>();

    public String getComment_ids() {
        return comment_ids;
    }

    public void setComment_ids(String comment_ids) {
        this.comment_ids = comment_ids;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
