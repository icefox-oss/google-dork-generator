package com.icefox.finder;

public class CacheObject {
    public String currentProfile;
    public ProfileObject[] profiles;
    public CacheObject(){}
    public CacheObject(String cur, ProfileObject[] profiles){
        this.currentProfile = cur;
        this.profiles = profiles;
    }
    public String getCurrentProfile(){
        return this.currentProfile;
    }
    public ProfileObject[] getProfiles(){
        return this.profiles;
    }
    public void setCurrentProfile(String currentProfile){
        this.currentProfile = currentProfile;
    }
    public void setProfiles(ProfileObject[] profiles){
        this.profiles = profiles;
    }
}
