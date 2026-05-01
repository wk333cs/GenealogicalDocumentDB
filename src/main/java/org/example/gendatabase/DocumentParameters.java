package org.example.gendatabase;

public class DocumentParameters {
    int id; //used only in editing
    int profile; //used only in adding
    String name;
    String surname;
    char type;
    int year;
    String parish;
    String city;
    String village;
    String branch;
    String info;

    public DocumentParameters(){};
    //For adding
    public DocumentParameters(String name, String surname, char type, int year, String parish, String city, String village, String branch, String info, int profile) {
        setName(name);
        setSurname(surname);
        setYear(year);
        setType(type);
        setParish(parish);
        setVillage(village);
        setCity(city);
        setBranch(branch);
        setInfo(info);
        setProfile(profile);
    };

    public int getId() {
        return id;
    }

    public int getProfile() {
        return profile;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public char getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public String getParish() {
        return parish;
    }

    public String getCity() {
        return city;
    }

    public String getVillage() {
        return village;
    }

    public String getBranch() {
        return branch;
    }

    public String getInfo() {
        return info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setParish(String parish) {
        this.parish = parish;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}

