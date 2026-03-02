package com.icefox.finder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class CLIHelper {
    private DorkBuilder dorkBuilder = new DorkBuilder();
    public CLIHelper(){}
    public CLIHelper(String[] args) {
        if(args.length==0){
            System.out.println("No Args");
            return;
        }
        if(args[0].equals("-h") || args[0].equals("--help")){
            System.out.println(GlobalVars.helpText);
            return;
        } else if(args[0].equals("-dla") || args[0].equals("--dictListAll")){
            for(String str : getDictionaries()){
                System.out.println(str);
            }
            return;
        } else if((args[0].equals("-ad") || args[0].equals("--addDictionary")) && args.length!=1){
            addNewDictionary(args[1]);
            return;
        } else if((args[0].equals("-e") || args[0].equals("--edit")) && args.length!=1){
            String homeDir = GlobalVars.homeDir;
            File folder = new File(homeDir, GlobalVars.dictionaryFolder);
            System.out.println("Go to: "+folder.getAbsolutePath());
            return;
        }
        for(int i = 0; i < args.length; i++){
            switch (args[i]) {
                case "-d":
                    addDict(args[i+1]);
                    i++;
                    break;
                case "--dict":
                    addDict(args[i+1]);
                    i++;
                    break;
                case "--desc":
                    dorkBuilder.addDesc(args[i+1]);
                    i++;
                    break;
                case "-f":
                    addFileTypes(args[i+1]);
                    i++;
                    break;
                case "--filetypes":
                    addFileTypes(args[i+1]);
                    i++;
                    break;
                case "-s":
                    addSites(args[i+1]);
                    i++;
                    break;
                case "--sites":
                    addSites(args[i+1]);
                    i++;
                    break;
                case "-t":
                    addTags(args[i+1]);
                    i++;
                    break;
                case "--tags":
                    addTags(args[i+1]);
                    i++;
                    break;
                case "-re":
                    addRequired(args[i+1]);
                    i++;
                    break;
                case "--required":
                    addRequired(args[i+1]);
                    i++;
                    break;

                case "-es":
                    addExcludedSites(args[i+1]);
                    i++;
                    break;
                case "--excludeSites":
                    addExcludedSites(args[i+1]);
                    i++;
                    break;

                case "-ef":
                    addExcludedFiletypes(args[i+1]);
                    i++;
                    break;
                case "--excludeFiletypes":
                    addExcludedFiletypes(args[i+1]);
                    i++;
                    break;

                default:
                    break;
            }
        }
        String dork = dorkBuilder.build();
        System.out.println(dork);
    }
    private String[] getDictionaries(){
        ArrayList<String> dicts = new ArrayList<>();
        String homeDir = GlobalVars.homeDir;
        File folder = new File(homeDir, GlobalVars.dictionaryFolder);
        folder.mkdirs();
        for (File file : folder.listFiles()) {
            String name = file.getName();
            if(name.endsWith(".txt")){
                dicts.add(
                    name.substring(
                        0, 
                        name.indexOf(".txt")
                    )
                );
            }
        }
        return dicts.toArray(new String[0]);
    }
    private void addDict(String arg){
        String[] available = getDictionaries();
        ArrayList<String> selected = new ArrayList<>();
        String[] dicts = null;
        if(arg.contains(",")){
            dicts = arg.split(",");
        }
        for(String a : available){
            if(dicts==null){
                if(a.equals(arg))selected.add(a);
            }else{
                for(String b : dicts){
                    if(a.equals(b))selected.add(a);
                }
            }
        }
        if(selected.isEmpty())return;
        for(String s : selected){
            String fileName = s+".txt";
            dorkBuilder.addDict(fileName);
        }
    }
    private void addFileTypes(String arg){
        String[] types = arg.split(",");
        dorkBuilder.addFileTypes(types);
    }
    private void addSites(String arg){
        String[] sites = arg.split(",");
        dorkBuilder.addSites(sites);
    }
    private void addTags(String arg){
        String[] tags = arg.split(",");
        dorkBuilder.addTags(tags);
    }
    private void addRequired(String arg){
        String[] required = arg.split(",");
        dorkBuilder.addRequired(required);
    }
    private void addExcludedSites(String arg){
        String[] sites = arg.split(",");
        dorkBuilder.addExcludedSites(sites);
    }
    private void addExcludedFiletypes(String arg){
        String[] filetypes = arg.split(",");
        dorkBuilder.addExcludedFiletypes(filetypes);
    }
    public void addNewDictionary(String str){
        Path path = Paths.get(str);
        File file = null;
        if (Files.exists(path)) {
            if (Files.isRegularFile(path)) {
                if(str.endsWith(".txt")){
                    file = path.toFile();
                }else{
                    System.out.println("File is not a txt");
                    return;
                }
            }
        } else {
            System.out.println("The file does not exist.");
        }
        if(file==null)return;
        String homeDir = GlobalVars.homeDir;
        File folder = new File(homeDir, GlobalVars.dictionaryFolder);
        folder.mkdirs();
        Path source = Path.of(file.getAbsolutePath());
        Path targetPath = Paths.get(folder.getAbsolutePath(), source.getFileName().toString());
        try {
            Files.copy(source, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error copying file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
