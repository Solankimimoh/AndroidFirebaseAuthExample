package com.lj.ljengineeringcollege;

/**
 * Created by solan on 08-03-18.
 */

public class FacultyModel {
    private String fullname;
    private String email;
    private String password;
    private String mobile;
    private String department;
    private boolean isactivated;

    public FacultyModel(String fullname, String email, String password, String mobile, String department, boolean isactivated) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.department = department;
        this.isactivated = isactivated;
    }

    public FacultyModel(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    public FacultyModel() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isIsactivated() {
        return isactivated;
    }

    public void setIsActivated(boolean isActivated) {
        this.isactivated = isActivated;
    }
}
