package org.example.gendatabase;

public class forDisplay {
    private int id;
    private String name;
    private String surname;
    private String type;
    private int year;
    private String parish;
    private String city;
    private String village;
    private String branch;
    private String info; // additional information
    private int profile; // profile id
    private Boolean isPinned= false;
    //constructor used for transferring parameters from the AddController to the DBManager's addDocument() method
    protected forDisplay( String name,String surname, String type, int year, String parish,String city, String village, String branch, String info, int profile){
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
    }
    //constructor used for transporting parameters to be displayed
    protected forDisplay(int id, String name,String surname, String type, int year, String parish,String city, String village, String branch, String info, int profile, boolean isPinned){
        setId(id);
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
        setIsPinned(isPinned);
    }
    //used for adding
    protected boolean getIsPinned(){
        return isPinned;
    }

    protected int getId() {
        return id;
    }
// getters for displayed parameters have to be public for propper display through table view
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

    protected String getInfo() {
        return info;
    }

    protected int getProfile() {
        return profile;
    }

    protected void setIsPinned(boolean isPinned){
        this.isPinned=isPinned;
    }

    protected void setId(int id) { this.id = id;}

    protected void setName(String name) {
        this.name = name;
    }

    protected void setSurname(String surname) {
        this.surname = surname;
    }

    protected void setType(String type) {
        this.type = type;
    }

    protected void setYear(int year) {
        this.year = year;
    }

    protected void setParish(String parish) {
        this.parish = parish;
    }

    protected void setCity(String city) {
        this.city = city;
    }

    protected void setVillage(String village) {
        this.village = village;
    }

    protected void setBranch(String branch) {
        this.branch = branch;
    }

    protected void setInfo(String info) {
        this.info = info;
    }

    protected void setProfile(int profile) {
        this.profile = profile;
    }
}
