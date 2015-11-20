package com.pukki.loganalyzer;

public class LogResult {

    public LogResult(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;

    private long noPublicMethods;
    private long noPrivateMethods;
    private long noProtectedMethods;

    private long noLines;

    private long noVerboseLogs;
    private long noDebugLogs;
    private long noInfoLogs;
    private long noWarningLogs;
    private long noErrorLogs;
    private long noWtfLogs;


    public String getFileName() {
        return this.fileName;
    }


    //Methods
    public long getNoMethods() {
        return noPublicMethods + noProtectedMethods + noPrivateMethods;
    }

    public long getNoPublicMethods() {
        return noPublicMethods;
    }

    public void setNoPublicMethods(long noPublicMethods) {
        this.noPublicMethods = noPublicMethods;
    }

    public long getNoProtectedMethods() {
        return noProtectedMethods;
    }

    public void setNoProtectedMethods(long noProtectedMethods) {
        this.noProtectedMethods = noProtectedMethods;
    }

    public long getNoPrivateMethods() {
        return noPrivateMethods;
    }

    public void setNoPrivateMethods(long noPrivateMethods) {
        this.noPrivateMethods = noPrivateMethods;
    }

    //Logs
    public long getNoLogs() {
        return noVerboseLogs + noDebugLogs + noInfoLogs + noWarningLogs + noErrorLogs + noWtfLogs;
    }

    public long getNoVerboseLogs() {
        return noVerboseLogs;
    }

    public void setNoVerboseLogs(long noVerboseLogs) {
        this.noVerboseLogs = noVerboseLogs;
    }

    public long getNoDebugLogs() {
        return noDebugLogs;
    }

    public void setNoDebugLogs(long noDebugLogs) {
        this.noDebugLogs = noDebugLogs;
    }

    public long getNoInfoLogs() {
        return noInfoLogs;
    }

    public void setNoInfoLogs(long noInfoLogs) {
        this.noInfoLogs = noInfoLogs;
    }

    public long getNoWarningLogs() {
        return noWarningLogs;
    }

    public void setNoWarningLogs(long noWarningLogs) {
        this.noWarningLogs = noWarningLogs;
    }

    public long getNoErrorLogs() {
        return noErrorLogs;
    }

    public void setNoErrorLogs(long noErrorLogs) {
        this.noErrorLogs = noErrorLogs;
    }

    public void setNoWtfLogs(long noWtfLogs) {
        this.noWtfLogs = noWtfLogs;
    }

    public long getNoWtfLogs() {
        return noWtfLogs;
    }

    //Other
    public long getNoLines() {
        return this.noLines;
    }

    public void setNoLines(long noLines) {
        this.noLines = noLines;
    }

}