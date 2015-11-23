package com.pukki.loganalyzer;

public class Result {

    public Result(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;

    private long noPublicMethods;
    private long noPrivateMethods;
    private long noProtectedMethods;

    private long noLines;


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


    //Other
    public long getNoLines() {
        return this.noLines;
    }

    public void setNoLines(long noLines) {
        this.noLines = noLines;
    }

}