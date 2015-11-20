package com.pukki.loganalyzer;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Missing parameter values; [project type] [project path]");
            return;
        }

        String projectType = args[0];
        String projectPath = args[1];

        if (projectType.length() == 0) {
            System.out.println("No project type defined");
            return;
        }

        if (projectPath.length() == 0) {
            System.out.println("No project path defined");
            return;
        }

//        projectPath = "";

        IFileLoader fileLoader = new FileLoader();

        IAnalyzer analyzer = AnalyzerFactory.resolve(projectType, fileLoader);
        List<LogResult> result = analyzer.analyzeFiles(projectPath);

        HtmlGenerator htmlGenerator = new HtmlGenerator(fileLoader);
        htmlGenerator.generateHtmlReport(result);
    }
}
