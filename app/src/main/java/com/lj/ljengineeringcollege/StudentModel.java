package com.lj.ljengineeringcollege;

/**
 * Created by solan on 14-02-18.
 */

public class StudentModel {

    public String fullname;
    public String email;
    public String password;
    public String enrollment;
    public String mobile;
    public String department;
    public String semester;
    public boolean isActivated;

    public StudentModel() {
    }


    public StudentModel(String fullname, String email, String password, String enrollment, String mobile, String department, String semester, boolean isActivated) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.enrollment = enrollment;
        this.mobile = mobile;
        this.department = department;
        this.semester = semester;
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
}
