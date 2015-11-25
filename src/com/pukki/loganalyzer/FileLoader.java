package com.pukki.loganalyzer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileLoader implements IFileLoader {


    private final static String blacklistPath = "blacklist";

    private static HashSet<String> mBlacklist = null;

    @Override
    public List<Path> getAllFilePaths(String folderPath, String fileType) {
        List<Path> filePaths = new ArrayList<>();

        try {
            Files.walk(Paths.get(folderPath)).forEach(filePath -> {
                if (Files.isRegularFile(filePath) && isValidFile(filePath, fileType)) {
                    filePaths.add(filePath);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePaths;
    }

    @Override
    public String readFile(Path path) {
        try {
            byte[] encoded = Files.readAllBytes(path);
            return new String(encoded, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    private boolean isValidFile(Path path, String fileType) {
        if (mBlacklist == null) {
            String[] blacklistFiles = readFile(Paths.get(blacklistPath)).replaceAll("\n", "").split(";");
            mBlacklist = new HashSet<>(Arrays.asList(blacklistFiles));
        }

        String fileName = path.getFileName().toString();
        return fileName.toLowerCase().endsWith(fileType) &&
                !mBlacklist.contains(fileName) &&
                !fileName.contains("$$");
    }

}
