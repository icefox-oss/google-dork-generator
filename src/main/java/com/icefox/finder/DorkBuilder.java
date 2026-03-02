package com.icefox.finder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class DorkBuilder {
    private StringBuilder builder;
    private ArrayList<String> dictionaryWords = new ArrayList<>();
    private String description = null;
    private String[] fileTypes = null;
    private String[] sites = null;
    private String[] tags = null;
    private String[] required = null;
    private String[] excludedFileTypes = null;
    private String[] excludedSites = null;
    public DorkBuilder(){
        builder = new StringBuilder();
    }
    public void addDict(String[] fileNames){
        String homeDir = GlobalVars.homeDir;
        File folder = new File(homeDir, GlobalVars.dictionaryFolder);
        folder.mkdirs();
        for(String fileName : fileNames){
            File file = new File(folder, fileName);
            try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while((line = reader.readLine())!=null){
                    dictionaryWords.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addDict(String fileName){
        String homeDir = GlobalVars.homeDir;
        File folder = new File(homeDir, GlobalVars.dictionaryFolder);
        folder.mkdirs();
        File file = new File(folder, fileName);
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine())!=null){
                dictionaryWords.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addDesc(String desc){
        description = desc;
    }
    public void addFileTypes(String[] types){
        fileTypes = types;
    }
    public void addSites(String[] args){
        sites = args;
    }
    public void addTags(String[] args){
        tags = args;
    }
    public void addRequired(String[] args){
        required = args;
    }
    public void addExcludedSites(String[] args){
        excludedSites = args;
    }
    public void addExcludedFiletypes(String[] args){
        excludedFileTypes = args;
    }
    public String build(){
        ArrayList<String> bannedWords = new ArrayList<>();
        String homeDir = GlobalVars.homeDir;
        File folder = new File(homeDir, GlobalVars.folder);
        folder.mkdirs();
        File file = new File(folder, "settings.01100100-01101001-01110011-01100111-01110010-01100001-01100011-01100101-01100110-01110101-01101100.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }        
        if(file.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while((line = reader.readLine())!=null){
                    bannedWords.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(bannedWords.size()!=0){
            for(String word : bannedWords){
                if(description!=null && description.toLowerCase().contains(word)){return "bwd: Disgraceful, Description contains: "+word;}
                for(String dw : dictionaryWords){
                    if(dw.toLowerCase().contains(word)){return "bwd: Disgraceful, Dictionary contains: "+word;}
                }
                if(tags!=null){
                    for(String t : tags){
                        if(t.toLowerCase().contains(word)){return "bwd: Disgraceful, Tags contains: "+word;}
                    }
                }
                if(required!=null){
                    for(String r : required){
                        if(r.toLowerCase().contains(word)){return "bwd: Disgraceful, Required Word(s) contains: "+word;}
                    }
                }
            }
        }
        builder.setLength(0);
        builder.append(description == null ? "" : description);
        if(tags!=null){
            builder.append(" ");
            for(int i = 0; i < tags.length; i++){
                builder.append(tags[i]+" ");
            }
        }
        if(required!=null){
            builder.append(" ");
            for(int i = 0; i < required.length; i++){
                builder.append("\""+required[i]+"\" ");
            }
        }
        if(!dictionaryWords.isEmpty()){
            builder.append(" ");
            for(int i = 0; i < dictionaryWords.size(); i++){
                builder.append(dictionaryWords.get(i)+" ");
            }
        }
        if(fileTypes!=null){
            builder.append(" (");
            for(int i = 0; i < fileTypes.length; i++){
                builder.append("filetype:"+fileTypes[i]);
                if(i+1 != fileTypes.length){
                    builder.append(" OR ");
                }
            }
            builder.append(")");
        }
        if(excludedFileTypes!=null){
            for(int i = 0; i < excludedFileTypes.length; i++){
                builder.append(" -filetype:"+excludedFileTypes[i]);
            }
        }
        if(sites!=null){
            builder.append(" (");
            for(int i = 0; i < sites.length; i++){
                builder.append("site:"+sites[i]);
                if(i+1 != sites.length){
                    builder.append(" OR ");
                }
            }
            builder.append(")");
        }
        if(excludedSites!=null){
            for(int i = 0; i < excludedSites.length; i++){
                builder.append(" -site:"+excludedSites[i]);
            }
        }
        return builder.toString();
    }
}