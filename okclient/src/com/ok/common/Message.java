package com.ok.common;

import com.ok.po.User;

import java.io.Serializable;

public class Message implements Serializable {

    private int messageType;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
