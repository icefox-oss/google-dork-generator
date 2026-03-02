package com.icefox.finder;

public class ProfileObject {
    public String name;
    public DictionaryObject[] dictionaries;
    public SitesObject includedSites, excludedSites;
    public TagsObject tags;
    public RequiredWordsObject requiredWords;
    public ProfileObject(){
    }
    public ProfileObject(
        String name,
        DictionaryObject[] dictionaries,
        SitesObject includedSites,
        SitesObject excludedSites,
        TagsObject tags,
        RequiredWordsObject requiredWords
    ){
        this.name = name;
        this.dictionaries = dictionaries;
        this.includedSites = includedSites;
        this.excludedSites = excludedSites;
        this.tags = tags;
        this.requiredWords = requiredWords;
    }
    public String getName(){return this.name;}
    public DictionaryObject[] getDictionaries(){return this.dictionaries;}
    public SitesObject getIncludedSites(){return this.includedSites;}
    public SitesObject getExcludedSites(){return this.excludedSites;}
    public TagsObject getTags(){return this.tags;}
    public RequiredWordsObject getRequiredWords(){return this.requiredWords;}
}
