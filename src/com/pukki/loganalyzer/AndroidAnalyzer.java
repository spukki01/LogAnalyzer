package com.pukki.loganalyzer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidAnalyzer implements IAnalyzer {

    private final static String mFileType = ".java";

    private final static String publicMethodPattern     = "(public|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
    private final static String privateMethodPattern    = "(private|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
    private final static String protectedMethodPattern  = "(protected|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";

    private final static String javaDocPattern          = "/\\*(.|[\\r\\n])*?\\*/";

    private final static String commentPattern          = "^//.*";

    private final static String importPattern           = "import.+;";
    private final static String packagePattern          = "package.+;";

    private final static String verboseLogPattern       = "Log\\.v\\(.+\\);";
    private final static String debugLogPattern         = "Log\\.d\\(.+\\);";
    private final static String infoLogPattern          = "Log\\.i\\(.+\\);";
    private final static String warningLogPattern       = "Log\\.w\\(.+\\);";
    private final static String errorLogPattern         = "Log\\.e\\(.+\\);";
    private final static String wtfLogPattern           = "Log\\.wtf\\(.+\\);";


    private IFileLoader mFileLoader;


    public AndroidAnalyzer(IFileLoader fileLoader) {
        this.mFileLoader = fileLoader;
    }

    @Override
    public List<Result> analyzeFiles(String folderPath) {
        List<Result> results = new ArrayList<>();
        List<Path> filePaths = this.mFileLoader.getAllFilePaths(folderPath, mFileType);

        int idx = 0, noFiles = filePaths.size();
        printProgress(idx, noFiles);
        for (Path path : filePaths) {
            idx++;

            String fileName = path.getFileName().toString();
            String fileContent = this.mFileLoader.readFile(path);

            Result result = !fileName.toLowerCase().contains("test")
                                    ? analyzeFile(fileName, filter(fileContent))
                                    : analyzeTestFile(fileName, fileContent);

            results.add(result);

            if (idx%10 == 0) printProgress(idx, noFiles);
        }

        printProgress(idx, noFiles);

        return results;
    }


    private TestResult analyzeTestFile(String fileName, String fileContent) {
        TestResult result = new TestResult(fileName);

        result.setNoPublicMethods(countOccurrences(fileContent, publicMethodPattern));
        result.setNoProtectedMethods(countOccurrences(fileContent, protectedMethodPattern));
        result.setNoPrivateMethods(countOccurrences(fileContent, privateMethodPattern));

        return result;
    }

    private LogResult analyzeFile(String fileName, String fileContent) {
        LogResult result = new LogResult(fileName);

        result.setNoPublicMethods(countOccurrences(fileContent, publicMethodPattern));
        result.setNoProtectedMethods(countOccurrences(fileContent, protectedMethodPattern));
        result.setNoPrivateMethods(countOccurrences(fileContent, privateMethodPattern));

        result.setNoVerboseLogs(countOccurrences(fileContent, verboseLogPattern));
        result.setNoDebugLogs(countOccurrences(fileContent, debugLogPattern));
        result.setNoInfoLogs(countOccurrences(fileContent, infoLogPattern));
        result.setNoWarningLogs(countOccurrences(fileContent, warningLogPattern));
        result.setNoErrorLogs(countOccurrences(fileContent, errorLogPattern));
        result.setNoWtfLogs(countOccurrences(fileContent, wtfLogPattern));

        result.setNoLines(countLines(fileContent));

        return result;
    }


    private String filter(String fileContent) {
        StringBuilder sb = new StringBuilder(1024);

        String[] lines = fileContent.replaceAll(javaDocPattern, "").split("\r\n|\r|\n");

        for (String line : lines) {
            if (line.length() == 0 || line.matches(packagePattern) ||
                    line.matches(importPattern)  ||
                    line.trim().matches(commentPattern))
            {
                continue;
            }

            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }

    private long countOccurrences(String text, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(text);

        long count = 0;
        while(matcher.find()) {
            count++;
        }

        return count;
    }


    private static long countLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return  lines.length;
    }

    private static void printProgress(int progress, int total) {
        System.out.println("Done analyzing " + progress + " of " + total + " files.");
    }

}