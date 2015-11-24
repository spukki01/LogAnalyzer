package com.pukki.loganalyzer;

public class LogResult extends Result {

    private long noVerboseLogs;
    private long noDebugLogs;
    private long noInfoLogs;
    private long noWarningLogs;
    private long noErrorLogs;
    private long noWtfLogs;

    private boolean hasTestClass;

    public LogResult(String fileName) {
        super(fileName);
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

    public void setHasTestClass(boolean hasTestClass) {
        this.hasTestClass = hasTestClass;
    }

    public boolean hasTestClass() {
        return hasTestClass;
    }
}
