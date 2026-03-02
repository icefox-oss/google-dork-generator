package com.icefox.finder;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiHelper implements ActionListener{
    ExportHelper helper = new ExportHelper();

    JFrame frame;

    JTextField desc, sites, excludedSites, tags, required;

    JList<String> dictionaries, filetypes, excludedFiletypes, tagsList, requiredWordsList, excludedSitesList, includedSitesList;

    JScrollPane dictPane, filePane, excludedFilePane, includedSitesPane, excludedSitesPane, tagsPane, requiredWordsPane;

    JButton button, saveIncludedSitesButton, saveTagsButton, saveRequiredWordsButton, saveExludedSitesButton;

    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    JMenu helpMenu = new JMenu("Help");
    JMenu profilesMenu = new JMenu("Profiles");
    JMenu currentProfileMenu = new JMenu();
    JMenu deleteProfileMenu = new JMenu("Delete Profile");
    
    JMenuItem openHelpItem = new JMenuItem("Open");
    JMenuItem addItem = new JMenuItem("Add Dictionary");
    JMenuItem editItem = new JMenuItem("Edit Existing Dictionary");
    JMenuItem editSavesItem = new JMenuItem("Edit Saved Words/sites/tags");
    JMenuItem importProfile = new JMenuItem("Import a profile");
    JMenuItem exportCurrentProfile = new JMenuItem("Export current profile");
    JMenuItem createNewProfile = new JMenuItem("Create New Profile");

    ArrayList<JMenuItem> profileItems = new ArrayList<>();
    ArrayList<JMenuItem> deleteProfileItems = new ArrayList<>();

    public GuiHelper(){
        frame = new JFrame();
        button = new JButton("Generate");
        saveIncludedSitesButton = new JButton("Add");
        saveTagsButton = new JButton("Add");
        saveRequiredWordsButton = new JButton("Add");
        saveExludedSitesButton = new JButton("Add");
        button.addActionListener(this);
        saveIncludedSitesButton.addActionListener(this);
        saveTagsButton.addActionListener(this);
        saveRequiredWordsButton.addActionListener(this);
        saveExludedSitesButton.addActionListener(this);
        addItem.addActionListener(this);
        editItem.addActionListener(this);
        editSavesItem.addActionListener(this);
        importProfile.addActionListener(this);
        exportCurrentProfile.addActionListener(this);
        createNewProfile.addActionListener(this);
        openHelpItem.addActionListener(this);
        fileMenu.add(addItem);
        fileMenu.add(editItem);
        fileMenu.add(editSavesItem);
        helpMenu.add(openHelpItem);
        profilesMenu.add(importProfile);
        profilesMenu.add(exportCurrentProfile);
        profilesMenu.add(createNewProfile);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        menuBar.add(profilesMenu);
        menuBar.add(currentProfileMenu);
        menuBar.add(deleteProfileMenu);
        for(ProfileObject p : helper.getCacheObject().getProfiles()){
            JMenuItem item = new JMenuItem(p.getName());
            item.addActionListener(this);
            profilesMenu.add(item);
            profileItems.add(item);
        }
        for(ProfileObject p : helper.getCacheObject().getProfiles()){
            JMenuItem item = new JMenuItem(p.getName());
            item.addActionListener(this);
            deleteProfileMenu.add(item);
            deleteProfileItems.add(item);
        }
        currentProfileMenu.setText("Current Profile: "+helper.getCacheObject().getCurrentProfile());
        JLabel descLabel = new JLabel("Enter Description");
        JLabel dictLabel = new JLabel("Select A Dictionary");
        JLabel filetypesLabel = new JLabel("Select File Types");
        JLabel excludedFiletypesLabel = new JLabel("Select Excluded File Types");
        JLabel sitesLabel = new JLabel("Enter site domains(comma-separated)");
        JLabel excludedSitesLabel = new JLabel("Enter Excluded site domains(comma-separated)");
        JLabel tagsLabel = new JLabel("Enter Tags(comma-separated, for what you want)");
        JLabel requiredLabel = new JLabel("Enter Words Required in search results");
        
        desc = new JTextField();
        sites = new JTextField();
        excludedSites = new JTextField();
        tags = new JTextField();
        required = new JTextField();

        dictionaries = new JList<>(getDictionaries());
        filetypes = new JList<>(GlobalVars.filetypes);
        excludedFiletypes = new JList<>(GlobalVars.filetypes);

        tagsList = new JList<>(helper.getTags());
        requiredWordsList = new JList<>(helper.getWords());
        excludedSitesList = new JList<>(helper.getExcludedSites());
        includedSitesList = new JList<>(helper.getIncludedSites());

        dictPane = new JScrollPane(dictionaries);
        filePane = new JScrollPane(filetypes);
        excludedFilePane = new JScrollPane(excludedFiletypes);

        includedSitesPane = new JScrollPane(includedSitesList);
        excludedSitesPane = new JScrollPane(excludedSitesList);
        tagsPane = new JScrollPane(tagsList);
        requiredWordsPane = new JScrollPane(requiredWordsList);

        dictLabel.setBounds(50, 10, 150, 20);
        dictPane.setBounds(50, 40, 100, 100);

        descLabel.setBounds(290, 10, 100, 20);
        desc.setBounds(290, 40, 200, 20);

        int xForIncSites = 50;
        int yForIncSites = 350;
        sitesLabel.setBounds(xForIncSites, 70+yForIncSites, 250, 20);
        includedSitesPane.setBounds(xForIncSites, 100+yForIncSites, 200, 100);
        sites.setBounds(xForIncSites, 200+yForIncSites, 100, 20);
        saveIncludedSitesButton.setBounds(xForIncSites+100, 200+yForIncSites, 100, 20);
        
        filetypesLabel.setBounds(50, 150, 150, 20);
        filePane.setBounds(50, 180, 100, 100);
        
        excludedFiletypesLabel.setBounds(50, 290, 200, 20);
        excludedFilePane.setBounds(50, 320, 100, 100);
        
        int xForTags = 290;
        int yForTags = 70;
        tagsLabel.setBounds(xForTags, yForTags, 280, 20);
        tagsPane.setBounds(xForTags, yForTags+30, 200, 100);
        tags.setBounds(xForTags, yForTags+130, 100, 20);
        saveTagsButton.setBounds(xForTags+100, yForTags+130, 100, 20);
        
        int xForRecWords = 290;
        int yForRecWords = 220;
        requiredLabel.setBounds(xForRecWords, yForRecWords, 280, 20);
        requiredWordsPane.setBounds(xForRecWords, yForRecWords+20, 200, 100);
        required.setBounds(xForRecWords, yForRecWords+120, 100, 20);
        saveRequiredWordsButton.setBounds(xForRecWords+100, yForRecWords+120, 100, 20);
        
        int xForExSites = 290;
        int yForExSites = 360;
        excludedSitesLabel.setBounds(xForExSites, yForExSites, 280, 20);
        excludedSitesPane.setBounds(xForExSites, yForExSites+20, 200, 100);
        excludedSites.setBounds(xForExSites, yForExSites+120, 100, 20);
        saveExludedSitesButton.setBounds(xForExSites+100, yForExSites+120, 100, 20);
        
        button.setBounds(630, 20, 200, 500);
        
        frame.setBounds(100, 100, 900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setJMenuBar(menuBar);

        frame.add(dictPane);
        frame.add(desc);

        frame.add(descLabel);
        frame.add(dictLabel);

        frame.add(sitesLabel);
        frame.add(includedSitesPane);
        frame.add(sites);
        
        frame.add(filetypesLabel);
        frame.add(filePane);
        
        frame.add(excludedFiletypesLabel);
        frame.add(excludedFilePane);
        
        frame.add(tagsLabel);
        frame.add(tagsPane);
        frame.add(tags);
        
        frame.add(requiredLabel);
        frame.add(requiredWordsPane);
        frame.add(required);
        
        frame.add(excludedSitesLabel);
        frame.add(excludedSitesPane);
        frame.add(excludedSites);

        frame.add(button);
        frame.add(saveIncludedSitesButton);
        frame.add(saveTagsButton);
        frame.add(saveRequiredWordsButton);
        frame.add(saveExludedSitesButton);

        frame.setVisible(true);
    }
    public static String[] getDictionaries(){
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
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==saveIncludedSitesButton){
            if(!sites.getText().isEmpty()){
                helper.saveIncludedSites(sites.getText(), false);
                sites.setText("");
                DefaultListModel<String> model = new DefaultListModel<>();
                for(String site : helper.getIncludedSites()){
                    model.addElement(site);
                }
                includedSitesList.setModel(model);

                includedSitesList.revalidate();
                includedSitesList.repaint();
                includedSitesPane.revalidate();
                includedSitesPane.repaint();
            }
        }
        if(e.getSource()==saveTagsButton){
            if(!tags.getText().isEmpty()){
                helper.saveTags(tags.getText(), false);
                tags.setText("");
                DefaultListModel<String> model = new DefaultListModel<>();
                for(String site : helper.getTags()){
                    model.addElement(site);
                }
                tagsList.setModel(model);

                tagsList.revalidate();
                tagsList.repaint();
                tagsPane.revalidate();
                tagsPane.repaint();
            }
        }
        if(e.getSource()==saveRequiredWordsButton){
            if(!required.getText().isEmpty()){
                helper.saveWords(required.getText(), false);
                required.setText("");
                DefaultListModel<String> model = new DefaultListModel<>();
                for(String site : helper.getWords()){
                    model.addElement(site);
                }
                requiredWordsList.setModel(model);

                requiredWordsList.revalidate();
                requiredWordsList.repaint();
                requiredWordsPane.revalidate();
                requiredWordsPane.repaint();
            }
        }
        if(e.getSource()==saveExludedSitesButton){
            if(!excludedSites.getText().isEmpty()){
                helper.saveExcludedSites(excludedSites.getText(), false);
                excludedSites.setText("");
                DefaultListModel<String> model = new DefaultListModel<>();
                for(String site : helper.getExcludedSites()){
                    model.addElement(site);
                }
                excludedSitesList.setModel(model);

                excludedSitesList.revalidate();
                excludedSitesList.repaint();
                excludedSitesPane.revalidate();
                excludedSitesPane.repaint();
            }
        }
        if(e.getSource()==button){
            DorkBuilder builder = new DorkBuilder();
            if(!desc.getText().isEmpty())builder.addDesc(desc.getText());
            if(!sites.getText().isEmpty())builder.addSites(sites.getText().split(","));
            if(!tags.getText().isEmpty())builder.addTags(tags.getText().split(","));
            if(!required.getText().isEmpty())builder.addRequired(required.getText().split(","));

            int[] sitesIncluded = includedSitesList.getSelectedIndices();
            if(sitesIncluded.length != 0){
                ArrayList<String> includedSites = new ArrayList<>();
                for(int i : sitesIncluded){
                    includedSites.add(includedSitesList.getModel().getElementAt(i));
                }
                if(!includedSites.isEmpty())builder.addSites(includedSites.toArray(new String[0]));
            }

            int[] tagsSelected = tagsList.getSelectedIndices();
            if(tagsSelected.length != 0){
                ArrayList<String> selectedTags = new ArrayList<>();
                for(int i : tagsSelected){
                    selectedTags.add(tagsList.getModel().getElementAt(i));
                }
                if(!selectedTags.isEmpty())builder.addTags(selectedTags.toArray(new String[0]));
            }

            int[] requiredWords = requiredWordsList.getSelectedIndices();
            if(requiredWords.length != 0){
                ArrayList<String> words = new ArrayList<>();
                for(int i : requiredWords){
                    words.add(requiredWordsList.getModel().getElementAt(i));
                }
                if(!words.isEmpty())builder.addRequired(words.toArray(new String[0]));
            }

            int[] excludedSites = excludedSitesList.getSelectedIndices();
            if(excludedSites.length != 0){
                ArrayList<String> sites = new ArrayList<>();
                for(int i : excludedSites){
                    sites.add(excludedSitesList.getModel().getElementAt(i));
                }
                if(!sites.isEmpty())builder.addExcludedSites(sites.toArray(new String[0]));
            }
            
            int[] dictSelected = dictionaries.getSelectedIndices();
            if(dictSelected.length != 0){
                ArrayList<String> selectedDictionaries = new ArrayList<>();
                for(int i : dictSelected){
                    selectedDictionaries.add(dictionaries.getModel().getElementAt(i)+".txt");
                }
                if(!selectedDictionaries.isEmpty())builder.addDict(selectedDictionaries.toArray(new String[0]));
            }
            int[] filetypesSelected = filetypes.getSelectedIndices();
            if(filetypesSelected.length != 0){
                ArrayList<String> selectedfiletypes = new ArrayList<>();
                for(int i : filetypesSelected){
                    selectedfiletypes.add(filetypes.getModel().getElementAt(i));
                }
                if(!selectedfiletypes.isEmpty())builder.addFileTypes(selectedfiletypes.toArray(new String[0]));
            }
            int[] excludedFiletypesSelected = excludedFiletypes.getSelectedIndices();
            if(excludedFiletypesSelected.length != 0){
                ArrayList<String> selectedfiletypes = new ArrayList<>();
                for(int i : excludedFiletypesSelected){
                    selectedfiletypes.add(excludedFiletypes.getModel().getElementAt(i));
                }
                if(!selectedfiletypes.isEmpty())builder.addExcludedFiletypes(selectedfiletypes.toArray(new String[0]));
            }
            String dork = builder.build();
            if(dork.contains("bwd: ")){
                String message = dork.substring(dork.indexOf("bwd: ")+"bwd: ".length());
                JOptionPane.showMessageDialog(null, message, "Disgrace", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String[] options = {"open", "copy", "close"};
            int chosen = JOptionPane.showOptionDialog(
                null, 
                dork, 
                "options", 
                JOptionPane.YES_NO_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                options, 
                255
            );
            switch (chosen) {
                case 0:
                    
                    try {
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                                URI uri = new URI("https://www.google.com/search?q="+URLEncoder.encode(dork, StandardCharsets.UTF_8));
                                desktop.browse(uri);
                            }
                        }
                    } catch (IOException | URISyntaxException err) {
                        err.printStackTrace();
                    }
                    break;
                case 1:
                    StringSelection stringSelection = new StringSelection(dork);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null); 
                    break;
                default:
                    break;
            }
        }
        if(e.getSource()==openHelpItem){
            JOptionPane.showMessageDialog(null, GlobalVars.helpText);
        }
        if(e.getSource()==addItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
            if(fileChooser.getSelectedFile()!=null){
                new CLIHelper().addNewDictionary(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
        if(e.getSource()==editItem){
            String homeDir = GlobalVars.homeDir;
            File folder = new File(homeDir, GlobalVars.dictionaryFolder);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                folder.mkdirs();
                try {
                    desktop.open(folder);
                } catch (IOException | IllegalArgumentException err) {
                    System.err.println("Error opening folder: " + err.getMessage());
                }
            }
        }
        if(e.getSource()==editSavesItem){
            String appData = System.getenv("APPDATA");
            File folder = new File(appData, "googleDorker");
            File save = new File(folder, "saves");
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                save.mkdirs();
                try {
                    desktop.open(save);
                } catch (IOException | IllegalArgumentException err) {
                    System.err.println("Error opening folder: " + err.getMessage());
                }
            }
        }
        if(e.getSource()==importProfile){
            helper.importProfile(null, false);
            this.frame.dispose();
            new GuiHelper();
        }
        if(e.getSource()==exportCurrentProfile){
            helper.exportProfile();
        }
        if(e.getSource()==createNewProfile){
            helper.newProfile();
            this.frame.dispose();
            new GuiHelper();
        }
        for(JMenuItem obj : profileItems){
            if(e.getSource()==obj){
                helper.setProfile(obj.getText());
                this.frame.dispose();
                new GuiHelper();                
            }
        }
        for(JMenuItem obj : deleteProfileItems){
            if(e.getSource()==obj){
                helper.deleteProfile(obj.getText());
                this.frame.dispose();
                new GuiHelper();       
            }
        }
    }
}