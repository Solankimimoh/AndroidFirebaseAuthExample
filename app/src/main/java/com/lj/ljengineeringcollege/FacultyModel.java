package com.lj.ljengineeringcollege;

/**
 * Created by solan on 08-03-18.
 */

public class FacultyModel {
    public String fullname;
    public String email;
    public String password;
    public String mobile;
    public String department;
    public boolean isActivated;;

    public FacultyModel(String fullname, String email, String password, String mobile, String department, boolean isActivated) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.department = department;
        this.isActivated = isActivated;
    }


    public FacultyModel() {
    }


}
