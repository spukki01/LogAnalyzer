package com.pukki.loganalyzer;

import java.util.List;

public interface IAnalyzer {

    List<LogResult> analyzeFiles(String folderPath);

}
