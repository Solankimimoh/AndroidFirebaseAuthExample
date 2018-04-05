package com.lj.ljengineeringcollege;

/**
 * Created by solan on 14-03-18.
 */

public class AppConstant {


    //    DATABASE
    final static String FIREBASE_TABLE_STUDNET = "students";
    final static String FIREBASE_TABLE_FACULTY = "faculties";
    final static String FIREBASE_DETAILS_FACULTY = "faculty";
    final static String FIREBASE_DEPARTMENT = "department";
    final static String FIREBASE_TABLE_FULLNAME = "fullname";
    final static String FIREBASE_TABLE_EMAIL = "email";
    final static String FIREBASE_TABLE_ENROLLMENT = "enrollment";
    final static String FIREBASE_TABLE_MOBILE = "mobile";
    final static String FIREBASE_TABLE_NOTIFICATION = "notification";
    final static String FIREBASE_DB_ISACTIVATED = "isActivated";


    //   NOTIFICATION DEPARTMENT
    final static String FIREBASE_DEPARTMENT_INFORMATION_TECHNOLOGY = "information_technology";
    final static String FIREBASE_DEPARTMENT_COMPUTER_ENGINEERING = "computer_engineering";


    public static final String FIREBASE_NOTIFICATION_BASE_URL_TOPIC = "/topics/";


    //    FIREBASE TOPICS LINKS
    public static final String FIREBASE_NOTIFICATION_DEPARTMENT_INFORMATION_TECHNOLOGY = FIREBASE_NOTIFICATION_BASE_URL_TOPIC + FIREBASE_DEPARTMENT_INFORMATION_TECHNOLOGY;
    public static final String FIREBASE_NOTIFICATION_DEPARTMENT_COMPUTER_ENGINEERING = FIREBASE_NOTIFICATION_BASE_URL_TOPIC + FIREBASE_DEPARTMENT_COMPUTER_ENGINEERING;

    //FIREBASE STORAGE Constance
    public static final String STORAGE_PATH_UPLOADS_NOTIFICATION = "notification/";
}
