package com.ok.common;

public interface MessageType {

    public static final int LOGIN = 5;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAIL = 0;

    public static final int REGISTER = 4;
    public static final int REGISTER_SUCCESS = 2;
    public static final int REGISTER_FAIL = 3;

    public static final int GET_USERS = 6;

    public static final int TALK_CONNECTION = 7;
    public static final int TALK = 8;
    public static final int TALK_CLOSE = 9;
    public static final int TALK_LEAVING_MESSAGE = 10;
}
