package com.lj.ljengineeringcollege;

/**
 * Created by solan on 14-02-18.
 */

public class Users {
    private String userFullName;
    private String userEmail;
    private String userPassword;
    private String userEnrollment;
    private String userMobile;

    public Users() {
    }

    public Users(String userFullName, String userEmail, String userPassword, String userEnrollment, String userMobile) {
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userEnrollment = userEnrollment;
        this.userMobile = userMobile;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEnrollment() {
        return userEnrollment;
    }

    public void setUserEnrollment(String userEnrollment) {
        this.userEnrollment = userEnrollment;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
