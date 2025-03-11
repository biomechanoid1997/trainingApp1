package com.example.trainingapp1.helpers;

public class ViewUserHelper {
    private Long id;
    private  String login;
    private  String email;
    private  String userStatus;

    private  String userType;
    private  String userName;

    private String moderatorLine;

    private String banLine;

    public ViewUserHelper(Long id,
                          String login,
                          String email,
                          String userStatus ,
                          String userType,
                          String userName,
                          String moderatorLine,
                          String banLine) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.userStatus = userStatus;
        this.userType = userType;
        this.userName = userName;
        this.moderatorLine = moderatorLine;
        this.banLine = banLine;
    }
    public String getLogin(){
        return login;
    }
    public String getEmail(){
        return email;
    }
    public String getUserStatus(){
        return userStatus;
    }
    public String getUserType(){
        return userType;
    }
    public String getUserName(){
        return userName;
    }
    public Long getId(){
        return id;
    }

    public String getBanLine() {
        return banLine;
    }
    public String getModeratorLine(){
        return moderatorLine;
    }
}
