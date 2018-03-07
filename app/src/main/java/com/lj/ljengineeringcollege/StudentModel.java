package com.lj.ljengineeringcollege;

/**
 * Created by solan on 14-02-18.
 */

public class StudentModel {

    private String fullName;
    private String email;
    private String password;
    private String enrollment;
    private String mobile;
    private String department;
    private String semester;
    private boolean isActivated;

    public StudentModel() {
    }


    public StudentModel(String fullName, String email, String password, String enrollment, String mobile, String department, String semester, boolean isActivated) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.enrollment = enrollment;
        this.mobile = mobile;
        this.department = department;
        this.semester = semester;
        this.isActivated = isActivated;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
        return isActivated;
    }

    public void setIsActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

}
