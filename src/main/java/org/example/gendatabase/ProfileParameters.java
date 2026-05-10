package org.example.gendatabase;

public class ProfileParameters {
    private int profileId;
    private String profileName;
    private String colour;

    ProfileParameters(int id, String name, String colour){
        setProfileId(id);
        setProfileName(name);
        setColour(colour);
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }


}

