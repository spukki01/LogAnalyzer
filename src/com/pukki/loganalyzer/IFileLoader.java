package com.pukki.loganalyzer;

import java.nio.file.Path;
import java.util.List;

public interface IFileLoader {

    List<Path> getAllFilePaths(String folderPath, String fileType);
    String readFile(Path path);
    boolean writeFile(Path path, String content);

}
