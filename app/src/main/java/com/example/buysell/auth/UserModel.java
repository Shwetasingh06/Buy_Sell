package com.example.buysell.auth;

public class UserModel {
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String pass;
    private String city;

    public UserModel(String fname, String lname, String email, String phone, String pass, String city) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
        this.city = city;
    }

    public UserModel() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
