package com.lj.ljengineeringcollege;

public class NotificationModel {

    private String department;
    private String title;
    private String description;
    private String filename;
    private String fileUrl;

    public NotificationModel(String department, String title, String description, String filename, String fileUrl) {
        this.department = department;
        this.title = title;
        this.description = description;
        this.filename = filename;
        this.fileUrl = fileUrl;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
