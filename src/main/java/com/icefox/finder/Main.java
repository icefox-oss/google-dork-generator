package com.icefox.finder;

public class Main {
    public static void main(String[] args) {
        if(args.length>0){
            new CLIHelper(args);
        }else{
            new GuiHelper();
        }
        // new ExportHelper().importProfile();
    }
}