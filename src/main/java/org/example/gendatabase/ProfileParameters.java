package org.example.gendatabase;

public class ProfileParameters {
    private int profileId;
    private String profileName;
    private String colour;

   protected ProfileParameters(int id, String name, String colour){
        setProfileId(id);
        setProfileName(name);
        setColour(colour);
    }

    protected int getProfileId() {
        return profileId;
    }

    protected void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    protected String getProfileName() {
        return profileName;
    }

    protected void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    protected String getColour() {
        return colour;
    }

    protected void setColour(String colour) {
        this.colour = colour;
    }


}

