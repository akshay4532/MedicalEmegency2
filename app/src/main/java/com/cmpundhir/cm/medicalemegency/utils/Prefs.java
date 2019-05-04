package com.cmpundhir.cm.medicalemegency.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static final String USER_F_NAME = "userName";
    private static final String USER_L_NAME = "lastName";
    private static final String MALE = "male";
    private static final String FEMALE = "female";
    private static final String OTHERS = "others";
    private static final String EMAIL = "email";
    private static final String MOBILE = "mobile";
    private static final String PASSWORD = "password";
    private static final String AUTH = "auth";
    private static final String USER_TYPE = "userType";

    public static void init(Context context){
        preferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public static void setUserName(String userName){
        editor.putString(USER_F_NAME,userName);
    }
    public static String getUserName(){
        return preferences.getString(USER_F_NAME,"");

    }
    public static void setUser_L_Name(String lastName){
        editor.putString(USER_L_NAME,lastName);
    }
    public static String getUser_L_Name(){
        return preferences.getString(USER_L_NAME,"");
    }

    public static void setMale(String male){
        editor.putString(MALE,male);
    }
    public static String getMale(){
        return preferences.getString(MALE,"");
    }

    public static void setFemale(String female){
        editor.putString(FEMALE,female);
    }
    public static String getFemale(){
        return preferences.getString(FEMALE,"");
    }

    public static void setOthers(String others){
        editor.putString(OTHERS,others);
    }
    public static String getOthers(){
        return preferences.getString(OTHERS,"");
    }

    public static void setEmail(String email){
        editor.putString(EMAIL,email);
    }
    public static String getEmail(){
        return preferences.getString(EMAIL,"");
    }

    public static void setMobile(String mobile){
        editor.putString(MOBILE,mobile);
    }
    public static String getMobile(){
        return preferences.getString(MOBILE,"");
    }

    public static void setPassword(String password){
        editor.putString(PASSWORD,password);
    }
    public static String getPassword(){
        return preferences.getString(PASSWORD,"");
    }

    public static void setUserType(int userType){
        editor.putInt(USER_TYPE,userType);
    }
    public static int getUserType(){
        return preferences.getInt(USER_TYPE,0);
    }

    public static void setAuth(Boolean auth){
        editor.putBoolean(AUTH,auth);
    }
    public static boolean getAuth(){
        return preferences.getBoolean(AUTH,false);
    }

    public static void commit(){
        editor.commit();
    }
    public static void apply(){
        editor.apply();
    }
    public static void clear(){
        editor.clear();
    }
}
