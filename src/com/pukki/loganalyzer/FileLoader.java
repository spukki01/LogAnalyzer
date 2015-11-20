package com.pukki.loganalyzer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileLoader implements IFileLoader {


    private final static String blacklistPath = "blacklist";


    public List<Path> getAllFilePaths(String folderPath, String fileType) {
        List<Path> filePaths = new ArrayList<>();

        String[] blacklistFiles = readFile(Paths.get(blacklistPath)).replaceAll("\n", "").split(";");
        HashSet<String> blacklist = new HashSet<>(Arrays.asList(blacklistFiles));

        try {
            Files.walk(Paths.get(folderPath)).forEach(filePath -> {
                if (Files.isRegularFile(filePath) &&
                        filePath.toString().toLowerCase().endsWith(fileType) &&
                        !blacklist.contains(filePath.getFileName().toString()) &&
                                !filePath.getFileName().toString().contains("$$"))  //TODO:Excluding files on regex

                {
                    filePaths.add(filePath);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePaths;
    }


    public String readFile(Path path) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }

}
