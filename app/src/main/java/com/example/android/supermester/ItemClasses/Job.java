package com.example.android.supermester.ItemClasses;

public class Job {

    private String category;
    private String description;
    private String city;
    private String country;
    private String address;
    private String zip_code;
    private String max_start;
    private int validity_days;
    private boolean payment;
    private String photo_URL;
    private String requester_id;
    private String tradesman_id;

public Job () {}

    public Job(String category, String description, String city, String country, String address, String zip_code, String max_start, int validity_days, boolean payment, String photo_URL, String requester_id, String tradesman_id) {
        this.category = category;
        this.description = description;
        this.city = city;
        this.country = country;
        this.address = address;
        this.zip_code = zip_code;
        this.max_start = max_start;
        this.validity_days = validity_days;
        this.payment = payment;
        this.photo_URL = photo_URL;
        this.requester_id = requester_id;
        this.tradesman_id = tradesman_id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getZip_code() {
        return zip_code;
    }

    public String getMax_start() {
        return max_start;
    }

    public int getValidity_days() {
        return validity_days;
    }

    public boolean isPayment() {
        return payment;
    }

    public String getPhoto_URL() {
        return photo_URL;
    }

    public String getRequester_id() {
        return requester_id;
    }

    public String getTradesman_id() {
        return tradesman_id;
    }
}
