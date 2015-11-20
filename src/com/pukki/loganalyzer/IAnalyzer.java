package com.pukki.loganalyzer;

import java.util.List;

public interface IAnalyzer {

    List<Result> analyzeFiles(String folderPath);

}
