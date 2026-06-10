package org.example.gendatabase;
import java.util.HashSet;
import java.util.Set;

public class FilterParameters {
    private Set<String> name = new HashSet<>();
    private Set<String> surname = new HashSet<>();
    private Set<String> type = new HashSet<>();
    private int firstYear= 0; //without inputting the first year or last year, the default value is set to the boundary value
    private int lastYear= 9999;
    private Set<String> parish = new HashSet<>();
    private Set<String> city = new HashSet<>();
    private Set<String> village = new HashSet<>();
    private Set<String> branch = new HashSet<>();

    protected FilterParameters(){};
    protected void addName(String name){
        this.name.add(name);
    }
    protected void addSurname(String surname){
        this.surname.add(surname);
    }
    protected void addType(String type){
        this.type.add(type);
    }
    protected void addFirstYear(int y1){
        this.firstYear = y1;
    }
    protected void addLastYear(int y2){
        this.lastYear=y2;
    }
    protected void addParish(String parish){
        this.parish.add(parish);
    }
    protected void addCity(String city){
        this.city.add(city);
    }
    protected void addVillage(String village){
        this.village.add(village);
    }
    protected void addBranch(String branch){
        this.branch.add(branch);
    }
    protected Set<String> getName() {
        return name;
    }
    protected Set<String> getSurname() {
        return surname;
    }
    protected Set<String> getType() {
        return type;
    }
    protected Set<String> getParish() {
        return parish;
    }
    protected Set<String> getCity() {
        return city;
    }
    protected Set<String> getVillage() {
        return village;
    }
    protected Set<String> getBranch() {
        return branch;
    }
    protected int getFirstYear() {
        return firstYear;
    }
    protected int getLastYear() {
        return lastYear;
    }





}


