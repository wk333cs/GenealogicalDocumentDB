package org.example.gendatabase;
import java.util.HashSet;
import java.util.Set;

public class FilterParameters {
    Set<String> name = new HashSet<>();
    Set<String> surname = new HashSet<>();
    Set<String> type = new HashSet<>();
    int firstYear= 0;
    int lastYear= 9999;
    Set<String> parish = new HashSet<>();
    Set<String> city = new HashSet<>();
    Set<String> village = new HashSet<>();
    Set<String> branch = new HashSet<>();

    public FilterParameters(){};
    public void addName(String name){
        this.name.add(name);
    }
    public void addSurname(String surname){
        this.surname.add(surname);
    }
    public void addType(String type){
        this.type.add(type);
    }
    public void addYearRange(int y1, int y2){
        this.firstYear = y1;
        this.lastYear = y2;
    }
    public void addParish(String parish){
        this.parish.add(parish);
    }
    public void addCity(String city){
        this.city.add(city);
    }
    public void addVillage(String village){
        this.village.add(village);
    }
    public void addBranch(String branch){
        this.branch.add(branch);
    }
    public Set<String> getName() {
        return name;
    }
    public Set<String> getSurname() {
        return surname;
    }public Set<String> getType() {
        return type;
    }public Set<String> getParish() {
        return parish;
    }public Set<String> getCity() {
        return city;
    }public Set<String> getVillage() {
        return village;
    }
    public Set<String> getBranch() {
        return branch;
    }
    public int getFirstYear() {
        return firstYear;
    }
    public int getLastYear() {
        return lastYear;
    }



}


