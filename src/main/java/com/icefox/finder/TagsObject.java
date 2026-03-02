package com.icefox.finder;

public class TagsObject {
    public String[] tags;
    TagsObject(String[] tags){
        this.tags = tags;
    }
    TagsObject(){
    }
    public String[] getTags(){
        return tags;
    }
}