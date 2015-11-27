package com.pukki.loganalyzer;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Analyzer {

    abstract String filter(String fileContent);

    long countOccurrences(String text, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(text);

        long count = 0;
        while(matcher.find()) {
            count++;
        }

        return count;
    }

    static long countLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return  lines.length;
    }

    static List<String> convertPathsToFileNames(List<Path> filePaths) {
        return filePaths.stream().map(path -> path.getFileName().toString()).collect(Collectors.toList());
    }

}