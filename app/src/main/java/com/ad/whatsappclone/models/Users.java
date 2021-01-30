package com.ad.whatsappclone.models;

public class Users {
    String userProfilePic, userName, userPassword, userEmail, userId, lastMessage;

    public Users(String userProfilePic, String userName, String userPassword, String userEmail, String userId, String lastMessage) {
        this.userProfilePic = userProfilePic;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public Users() {

    }

    //:// TODO: 30-Jan-21  SignUp constructor
    public Users(String userName, String userPassword, String userEmail) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
