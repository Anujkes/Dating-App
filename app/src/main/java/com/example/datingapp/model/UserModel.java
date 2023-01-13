package com.example.datingapp.model;

public class UserModel {
    private String name,email,city,number,gender,relationShipStatus,star,image,age,status;

    public UserModel() {
    }

    public UserModel(String name, String email,String image, String city, String number, String gender, String relationShipStatus, String star,  String age, String status) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.city = city;
        this.number = number;
        this.gender = gender;
        this.relationShipStatus = relationShipStatus;
        this.star = star;

        this.age = age;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelationShipStatus() {
        return relationShipStatus;
    }

    public void setRelationShipStatus(String relationShipStatus) {
        this.relationShipStatus = relationShipStatus;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
