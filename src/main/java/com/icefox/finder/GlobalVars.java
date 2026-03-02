package com.icefox.finder;
public class GlobalVars {
    public static String header = "01001001 00101110 01000001 01001101 00101110 01001000 01001111 01010010 01001110 01011001";
    public static String folder = "AppData/Roaming/googleDorker/";
    public static String dictionaryFolder = folder+"dictionaries";
    public static String homeDir = System.getProperty("user.home");
    public static String[] filetypes = {
        //image
        "gif",
        "jpg",
        "png",
        "jpeg",
        "jfif",
        "tiff",
        "bmp",
        //video
        "mp4",
        "mov",
        "wmv",
        "avi",
        "mkv",
        "webm",
        //document
        "pdf",
        "txt"
    };
    public static String helpText ="""
            ----------Help----------
                -d(--dict): use a preset of keywords, add many dictionaries like "-d dict1; -d dict2", use -dla(--dictListAll) for list of available dictionaries
                -h(--help): display the Help Menu
                --desc: description of what you want
                -f(--filetypes): file types you want, comma separated like: gif, jpg, png, jpeg
                -s(--sites): sites you want results from, comma separated like: site1.com,site2.com,site3.com... site+n.com
                -t(--tags): tags you want, comma separated like: tag1,tag2,tag3...tag+n (warning: if you use tags then don't add a dictionary, it messes with the results)
                -dla(--dictListAll): List all available dictionaries
                -ad(--addDictionary): add dictionary, usage: -ad dictionary path, dictionaries has to have each word(or sentence) in a new line
                -e(--edit): show dictionaries folder
                -re(--required): Words required in the search results
                -es(--excludeSites): Which sites to exclude
                -ef(--excludeFiletypes): Which file types to exclude
                GUI: on tags, sites and required words, you can either select from past saved words/sites/tags, or write it yourself, you can also do both
            ----------End Help----------
            """;
}