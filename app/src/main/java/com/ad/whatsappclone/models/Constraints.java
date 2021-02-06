package com.ad.whatsappclone.models;

import android.Manifest;

public class Constraints {
    public static final String USER_NODE = "Users";
    public static final String CHATS_NODE = "Chats";


    public static final int PERMISSION_REQUEST_CODE = 100;
    public static final String[] REQUIRED_PERMISSION = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final String USER_ID = "userId";
    public static final String USER_PROFILE_PIC = "userProfilePic";
    public static final String USER_NAME = "userName";
}
