package com.pukki.loganalyzer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AndroidAnalyzer extends Analyzer implements IAnalyzer {

    private final static String mFileType = ".java";

    private final static String publicMethodPattern     = "(public|public static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
    private final static String privateMethodPattern    = "(private|private static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
    private final static String protectedMethodPattern  = "(protected|private static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";

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

        List<String> fileNames = convertPathsToFileNames(filePaths);

        int idx = 0, noFiles = filePaths.size();
        printProgress(idx, noFiles);
        for (Path path : filePaths) {
            idx++;

            String fileName = path.getFileName().toString();
            String fileContent = this.mFileLoader.readFile(path);

            Result result = !fileName.toLowerCase().contains("test")
                                    ? analyzeFile(fileName, filter(fileContent), fileNames)
                                    : analyzeTestFile(fileName, fileContent);

            results.add(result);

            if (idx%10 == 0) printProgress(idx, noFiles);
        }

        printProgress(idx, noFiles);

        return results;
    }

    String filter(String fileContent) {
        StringBuilder sb = new StringBuilder(1024);

        String[] lines = fileContent.replaceAll(javaDocPattern, "").split("\r\n|\r|\n");

        for (String line : lines) {
            if (isLineValid(line)) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
        }

        return sb.toString();
    }

    private LogResult analyzeFile(String fileName, String fileContent, List<String> fileNames) {
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

        boolean hasTestClass = fileNames.contains(fileName.replace(mFileType, "Test" + mFileType));
        result.setHasTestClass(hasTestClass);

        return result;
    }

    private TestResult analyzeTestFile(String fileName, String fileContent) {
        TestResult result = new TestResult(fileName);

        result.setNoPublicMethods(countOccurrences(fileContent, publicMethodPattern));
        result.setNoProtectedMethods(countOccurrences(fileContent, protectedMethodPattern));
        result.setNoPrivateMethods(countOccurrences(fileContent, privateMethodPattern));

        return result;
    }

    private boolean isLineValid(String line) {
        return !(line.length() == 0 ||
                 line.matches(packagePattern) ||
                 line.matches(importPattern)  ||
                 line.trim().matches(commentPattern));
    }

}