package com.lj.ljengineeringcollege;

public class FacultyDetailsModel {
    public String name;
    public String designation;
    public String experience;
    public String interest;
    public String qualification;
    public String profilepic;

    public FacultyDetailsModel(String name, String designation, String experience, String interest, String qualification, String profilepic) {
        this.name = name;
        this.designation = designation;
        this.experience = experience;
        this.interest = interest;
        this.qualification = qualification;
        this.profilepic = profilepic;
    }

    public FacultyDetailsModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
