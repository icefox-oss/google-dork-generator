package com.icefox.finder;

public class DictionaryObject {
    public String name;
    public String[] words;
    public DictionaryObject(String name, String[] words){
        this.name = name;
        this.words = words;
    }
    public DictionaryObject(){
    }
    public String getName(){return this.name;}
    public String[] getWords(){return this.words;}
}