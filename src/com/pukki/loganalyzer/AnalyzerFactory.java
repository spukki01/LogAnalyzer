package com.pukki.loganalyzer;

public class AnalyzerFactory {


    public static IAnalyzer resolve(String projectType, IFileLoader fileLoader) {
        switch (projectType) {
            case "android":
                return new AndroidAnalyzer(fileLoader);
        }

        throw new UnsupportedOperationException();
    }


}
