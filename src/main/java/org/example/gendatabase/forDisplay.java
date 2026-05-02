package org.example.gendatabase;

public class forDisplay {
    String name;
    String surname;
    String type;
    int year;
    String parish;
    String city;
    String village;
    String branch;

    protected forDisplay(){}
    protected forDisplay(String name,String surname, String type, int year, String parish,String city, String village, String branch){
        setName(name);
        setSurname(surname);
        setYear(year);
        setType(type);
        setParish(parish);
        setVillage(village);
        setCity(city);
        setBranch(branch);
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getType() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setType(String type) {
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
}
