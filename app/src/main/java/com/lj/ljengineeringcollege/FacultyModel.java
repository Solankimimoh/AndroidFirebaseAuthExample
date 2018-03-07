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
    private boolean isActivated;

    public FacultyModel() {
    }

    public FacultyModel(String fullname, String email, String password, String mobile, String department, boolean isActivated) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.department = department;
        this.isActivated = isActivated;
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

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }
}
