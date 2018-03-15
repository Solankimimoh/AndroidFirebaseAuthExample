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


}
