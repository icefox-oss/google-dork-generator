package com.icefox.finder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.google.gson.Gson;

public class ExportHelper {
    String appData = System.getenv("APPDATA");
    File folder = new File(appData, "googleDorker");
    String homeDir = GlobalVars.homeDir;
    File dictsFolder = new File(homeDir, GlobalVars.dictionaryFolder);
    File save = new File(folder, "saves");
    File excludedSites = new File(save, "excludedSites.json");
    File includedSites = new File(save, "includedSites.json");
    File tagsFile = new File(save, "tags.json");
    File requiredWords = new File(save, "requiredWords.json");
    File prof = new File(folder, "cache.json");
    ProfileObject lastExported = null;
    public ExportHelper(){
        save.mkdirs();
        try {
            if(!excludedSites.exists()){
                excludedSites.createNewFile();
            }
            if(!includedSites.exists()){
                includedSites.createNewFile();
            }
            if(!tagsFile.exists()){
                tagsFile.createNewFile();
            }
            if(!requiredWords.exists()){
                requiredWords.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveIncludedSites(String includedSitesString, boolean replace){
        Set<String> list = new HashSet<>();
        String[] array = includedSitesString.split(",");
        for(String site : array){
            list.add(site);
        }
        if(!replace){
            String[] arr = getIncludedSites();
            if(arr!=null){
                for(String site : arr){
                    list.add(site);
                }
            }
        }
        SitesObject sites = new SitesObject(list.toArray(new String[0]));
        String json = new Gson().toJson(sites);
        try (FileWriter writer = new FileWriter(includedSites)) {
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String[] getIncludedSites(){
        try (BufferedReader reader = new BufferedReader(new FileReader(includedSites))) {
            SitesObject object = new Gson().fromJson(reader.readLine(), SitesObject.class);
            if(object!=null){
                return object.getSites();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arr = new String[0];
        return arr;
    }
    public void saveExcludedSites(String excludedSitesString, boolean replace){
        Set<String> list = new HashSet<>();
        String[] array = excludedSitesString.split(",");
        for(String site : array){
            list.add(site);
        }
        if(!replace){
            String[] arr = getExcludedSites();
            if(arr!=null){
                for(String site : arr){
                    list.add(site);
                }
            }
        }
        SitesObject sites = new SitesObject(list.toArray(new String[0]));
        String json = new Gson().toJson(sites);
        try (FileWriter writer = new FileWriter(excludedSites)) {
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String[] getExcludedSites(){
        try (BufferedReader reader = new BufferedReader(new FileReader(excludedSites))) {
            SitesObject object = new Gson().fromJson(reader.readLine(), SitesObject.class);
            if(object!=null){
                return object.getSites();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arr = new String[0];
        return arr;
    }
    public void saveTags(String tagsString, boolean replace){
        Set<String> list = new HashSet<>();
        String[] array = tagsString.split(",");
        for(String tag : array){
            list.add(tag);
        }
        if(!replace){
            String[] arr = getTags();
            if(arr!=null){
                for(String tag : arr){
                    list.add(tag);
                }
            }
        }
        TagsObject tags = new TagsObject(list.toArray(new String[0]));
        String json = new Gson().toJson(tags);
        try (FileWriter writer = new FileWriter(tagsFile)) {
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String[] getTags(){
        try (BufferedReader reader = new BufferedReader(new FileReader(tagsFile))) {
            TagsObject object = new Gson().fromJson(reader.readLine(), TagsObject.class);
            if(object!=null){
                return object.getTags();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arr = new String[0];
        return arr;
    }
    public void saveWords(String wordsString, boolean replace){
        Set<String> list = new HashSet<>();
        String[] array = wordsString.split(",");
        for(String word : array){
            list.add(word);
        }
        if(!replace){
            String[] arr = getWords();
            if(arr!=null){
                for(String word : arr){
                    list.add(word);
                }
            }
        }
        RequiredWordsObject words = new RequiredWordsObject(list.toArray(new String[0]));
        String json = new Gson().toJson(words);
        try (FileWriter writer = new FileWriter(requiredWords)) {
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String[] getWords(){
        try (BufferedReader reader = new BufferedReader(new FileReader(requiredWords))) {
            RequiredWordsObject object = new Gson().fromJson(reader.readLine(), RequiredWordsObject.class);
            if(object!=null){
                return object.getWords();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arr = new String[0];
        return arr;
    }
    public DictionaryObject[] getDictionaries(){
        String[] dictionaryFiles = GuiHelper.getDictionaries();
        ArrayList<DictionaryObject> dictionaries = new ArrayList<>();
        for(int i = 0; i < dictionaryFiles.length; i++){
            dictionaryFiles[i] = dictionaryFiles[i]+".txt";
        }
        dictsFolder.mkdirs();
        for(String fileName : dictionaryFiles){
            File file = new File(dictsFolder, fileName);
            ArrayList<String> dictionaryWords = new ArrayList<>();
            try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while((line = reader.readLine())!=null){
                    dictionaryWords.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            dictionaries.add(new DictionaryObject(fileName, dictionaryWords.toArray(new String[0])));
        }
        return dictionaries.toArray(new DictionaryObject[0]);
    }
    public void deletePastDicts(){
        String[] dictionaryFiles = GuiHelper.getDictionaries();
        for(int i = 0; i < dictionaryFiles.length; i++){
            dictionaryFiles[i] = dictionaryFiles[i]+".txt";
        }
        for(String fileName : dictionaryFiles){
            File file = new File(dictsFolder, fileName);
            file.delete();
        }
    }
    public void saveDictionaries(DictionaryObject dictionary){
        String fileName = dictionary.getName();
        String[] words = dictionary.getWords();
        File file = new File(dictsFolder, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            for(int i = 0; i < words.length; i++){
                if(i<words.length-1){
                    writer.append(words[i]+"\n");
                }else{
                    writer.append(words[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exportProfile(){
        String name = JOptionPane.showInputDialog("Name of profile");
        while(name.isEmpty() && !(name.contains(" ") || name.contains(",") || name.contains("/") || name.contains(" \\"))){
            name = JOptionPane.showInputDialog("Name of profile(can not contain spaces,commas,/,\\)");
            if(name==null){
                return;
            }
        }
        SitesObject includedSites = new SitesObject(getIncludedSites());
        SitesObject excludedSites = new SitesObject(getExcludedSites());
        TagsObject tags = new TagsObject(getTags());
        RequiredWordsObject words = new RequiredWordsObject(getWords());
        DictionaryObject[] dictionaries = getDictionaries();
        ProfileObject profile = new ProfileObject(name, dictionaries, includedSites, excludedSites, tags, words);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int op = fileChooser.showOpenDialog(null);
        if(op!=JFileChooser.OPEN_DIALOG){return;}
        File saveFolder = fileChooser.getSelectedFile();
        String json = new Gson().toJson(profile);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        StringBuilder binaryString = new StringBuilder();
        for (byte b : bytes) {
            String binary = Integer.toBinaryString(b);
            String formattedBinary = String.format("%8s", binary).replace(' ', '0');
            if (formattedBinary.length() > 8) {
                formattedBinary = formattedBinary.substring(formattedBinary.length() - 8);
            }
            binaryString.append(formattedBinary).append(" ");
        }
        binaryString.insert(0, GlobalVars.header, 0, GlobalVars.header.length());
        if (binaryString.length() > 0) {
            binaryString.setLength(binaryString.length() - 1);
        }
        try (FileWriter writer = new FileWriter(new File(saveFolder, name+".hpf"))) {
            writer.write(binaryString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        lastExported = profile;
    }
    public void importProfile(ProfileObject profile, boolean replace){
        String result = null;
        if(profile==null){
            JFileChooser fileChooser = new JFileChooser();
            int op = fileChooser.showOpenDialog(null);
            if(op!=JFileChooser.OPEN_DIALOG){return;}
            File chosen = fileChooser.getSelectedFile();
            StringBuffer builder = new StringBuffer();
            try (BufferedReader reader = new BufferedReader(new FileReader(chosen))) {
                String line;
                while((line = reader.readLine())!=null){
                    builder.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(builder.length()<89)return;
            String header = builder.toString().substring(0, 89);
            if(!header.equals(GlobalVars.header)){return;}
            String jsonBinary = builder.toString().substring(89, builder.length()).trim();
            result = Arrays.stream(jsonBinary.split(" "))
                                  .map(binary -> Integer.parseInt(binary, 2))
                                  .map(Character::toString)
                                  .collect(Collectors.joining());
        }
        ProfileObject obj = result==null ? profile : new Gson().fromJson(result, ProfileObject.class);
        CacheObject cacheObject = getCacheObject();
        ArrayList<ProfileObject> profiless = new ArrayList<>();
        if(cacheObject==null) cacheObject = new CacheObject();
        if(cacheObject.getProfiles()!=null){
            for(ProfileObject pOf : cacheObject.getProfiles()){
                profiless.add(pOf);
                if(pOf.getName().equals(obj.getName())){
                    if(replace==true){
                        profiless.remove(pOf);
                    }else{
                        return;
                    }
                }
            }
        }
        cacheObject.setProfiles(profiless.toArray(new ProfileObject[0]));
        String cachee = new Gson().toJson(cacheObject);
        try (FileWriter writer = new FileWriter(prof)) {
            writer.write(cachee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveExcludedSites(
            String.join(",", obj.getExcludedSites().getSites()),
            true
        );
        saveIncludedSites(
            String.join(",", obj.getIncludedSites().getSites()),
            true
        );
        saveTags(
            String.join(",", obj.getTags().getTags()),
            true
        );
        saveWords(
            String.join(",", obj.getRequiredWords().getWords()),
            true
        );
        deletePastDicts();
        for(DictionaryObject dict : obj.getDictionaries()){
            saveDictionaries(dict);
        }

        ArrayList<ProfileObject> profiles = new ArrayList<>();
        if(cacheObject.getProfiles()!=null){
            for(ProfileObject pObj : cacheObject.getProfiles()){
                profiles.add(pObj);
            }
        }
        profiles.add(obj);
        cacheObject.setProfiles(profiles.toArray(new ProfileObject[0]));
        cacheObject.setCurrentProfile(obj.getName());
        String cache = new Gson().toJson(cacheObject);
        try (FileWriter writer = new FileWriter(prof)) {
            writer.write(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setProfile(String name){
        CacheObject cacheObject = getCacheObject();
        ProfileObject profile = null;
        if(cacheObject==null)return;
        if(cacheObject.getProfiles()!=null){
            for(ProfileObject pOf : cacheObject.getProfiles()){
                if(pOf.getName().equals(name)){
                    profile = pOf;
                    break;
                }
            }
        }
        if(profile==null)return;
        saveExcludedSites(
            String.join(",", profile.getExcludedSites().getSites()),
            true
        );
        saveIncludedSites(
            String.join(",", profile.getIncludedSites().getSites()),
            true
        );
        saveTags(
            String.join(",", profile.getTags().getTags()),
            true
        );
        saveWords(
            String.join(",", profile.getRequiredWords().getWords()),
            true
        );
        deletePastDicts();
        for(DictionaryObject dict : profile.getDictionaries()){
            saveDictionaries(dict);
        }
        cacheObject.setCurrentProfile(profile.getName());
        String cache = new Gson().toJson(cacheObject);
        try (FileWriter writer = new FileWriter(prof)) {
            writer.write(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public CacheObject getCacheObject(){
        try{
            if(!prof.exists()){
                prof.createNewFile();
            }
        }catch(Exception e){

        }
        try (BufferedReader reader = new BufferedReader(new FileReader(prof))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                builder.append(line);
            }
            return new Gson().fromJson(builder.toString(), CacheObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void deleteProfile(String name){
        CacheObject cacheObject = getCacheObject();
        ArrayList<ProfileObject> profiles = new ArrayList<>();
        if(cacheObject==null)return;
        if(cacheObject.getProfiles()!=null){
            for(ProfileObject pOf : cacheObject.getProfiles()){
                if(!pOf.getName().equals(name)){
                    profiles.add(pOf);
                }else{
                    if(cacheObject.getCurrentProfile().equals(pOf.getName())){
                        cacheObject.setCurrentProfile(cacheObject.getProfiles()[0].getName());
                    }
                }
            }
        }
        cacheObject.setProfiles(profiles.toArray(new ProfileObject[0]));
        String cache = new Gson().toJson(cacheObject);
        try (FileWriter writer = new FileWriter(prof)) {
            writer.write(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void newProfile(){
        CacheObject cacheObject = getCacheObject();
        ProfileObject obj = new ProfileObject("temp", new DictionaryObject[0], new SitesObject(new String[0]), new SitesObject(new String[0]), new TagsObject(new String[0]), new RequiredWordsObject(new String[0]));
        if(cacheObject.currentProfile.isEmpty()){
            importProfile(obj, false);
            setProfile("temp");
        }else{
            int i = JOptionPane.showConfirmDialog(null, "Save Current Profile?");
            if(i==JOptionPane.YES_OPTION){
                exportProfile();
                if(lastExported!=null){
                    importProfile(lastExported, true);
                }
                importProfile(obj, false);
                setProfile("temp");
            }else if(i==JOptionPane.NO_OPTION){
                importProfile(obj, false);
                setProfile("temp");
            }
        }
    }
}