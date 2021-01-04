package com.example.buysell.home;

import android.content.SharedPreferences;

public class ProductModel {
    private String product_id;
    private String product_photo;
    //Date of purchase
    private String date_of_purchase;
    private String product_description;
    //MRP of product
    private String mrp;
    //selling price
    private String selling_price;
    private String city;
    private String category;
    private String title;
    private String date;
    private String time;
    private String user_id;


    public ProductModel() {

    }

    public ProductModel(String product_id, String product_photo, String date_of_purchase, String product_description, String mrp, String selling_price, String category, String title,String city, String date, String time,String user_id) {
        this.product_id = product_id;
        this.product_photo = product_photo;
        this.date_of_purchase = date_of_purchase;
        this.product_description = product_description;
        this.mrp = mrp;
        this.selling_price = selling_price;
        this.category = category;
        this.title = title;
        this.city=city;
        this.date = date;
        this.time = time;
        this.user_id=user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public String getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(String date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "product_id='" + product_id + '\'' +
                ", product_photo='" + product_photo + '\'' +
                ", date_of_purchase='" + date_of_purchase + '\'' +
                ", product_description='" + product_description + '\'' +
                ", mrp='" + mrp + '\'' +
                ", selling_price='" + selling_price + '\'' +
                ", city='" + city + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
