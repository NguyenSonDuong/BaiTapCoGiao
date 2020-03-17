package com.group1.baitapcogiao;

public class SinhVien {
    public String id;
    public String name;
    public String birthday;
    public int sex;
    public String address;

    public SinhVien(String id, String name, String birthday, int sex, String address) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.address = address;
    }

    public SinhVien() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
