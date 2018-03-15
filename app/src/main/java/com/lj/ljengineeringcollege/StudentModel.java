package com.lj.ljengineeringcollege;

/**
 * Created by solan on 14-02-18.
 */

public class StudentModel {

    private String fullname;
    private String email;
    private String password;
    private String enrollment;
    private String mobile;
    private String department;
    private String semester;
    private boolean isactivated;

    public StudentModel() {
    }


    public StudentModel(String fullname, String email, String password, String enrollment, String mobile, String department, String semester, boolean isactivated) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.enrollment = enrollment;
        this.mobile = mobile;
        this.department = department;
        this.semester = semester;
        this.isactivated = isactivated;
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

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean getIsActivated() {
        return isactivated;
    }

    public void setIsActivated(boolean isActivated) {
        this.isactivated = isActivated;
    }

}
