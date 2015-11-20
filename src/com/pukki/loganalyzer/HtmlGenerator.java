package com.pukki.loganalyzer;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

public class HtmlGenerator {

    private final static String htmlTemplatePath = "main_template.html";
    private final static String blacklistPath = "blacklist";

    private IFileLoader mFileLoader;

    public HtmlGenerator(IFileLoader fileLoader) {
        this.mFileLoader = fileLoader;
    }

    public boolean generateHtmlReport(List<LogResult> result) {
        String htmlPage = this.mFileLoader.readFile(Paths.get(htmlTemplatePath));

        htmlPage= htmlPage.replace("@timestamp@", Util.getTimeStamp());

        htmlPage = htmlPage.replace("@totalFiles@", Integer.toString(result.size()));

        String[] blacklistFiles = this.mFileLoader.readFile(Paths.get(blacklistPath)).split(";");

        String blacklistHtml = "";
        for (String file : blacklistFiles) {
            blacklistHtml += String.format(HtmlTagHelper.liTag, file);
        }

        htmlPage = htmlPage.replace("@blacklist@", blacklistHtml);

        StringBuilder sb = new StringBuilder(4096);

        double totalGrade = 0;
        long totalMethods = 0;
        long totalLogs = 0;
        long totalLinesCode = 0;

        for (int i=0; i<result.size(); i++) {
            LogResult res = result.get(i);

            //Row 1 start
            sb.append(HtmlTagHelper.rowWellDiv);

            //Row 2 start
            sb.append(HtmlTagHelper.rowDiv);

            //Column 1
            sb.append(HtmlTagHelper.expandButton.replace("@detailsId", "details_" + i));

            //Column 2
            sb.append(String.format(HtmlTagHelper.divTagCol9, "<b>Filename:</b> " + res.getFileName()));

            //Column 3
            double grade = GradeCalculationStrategy.calculateGrade(res);
            sb.append(HtmlTagHelper.getGradeButton(grade));

            //Row 2 end
            sb.append(HtmlTagHelper.divCloseTag);

            //Row 3 start
            sb.append(HtmlTagHelper.rowDiv);

            //Details
            buildDetails(res, i, sb);

            //Row 3 end
            sb.append(HtmlTagHelper.divCloseTag);

            //Row 1 end
            sb.append(HtmlTagHelper.divCloseTag);

            totalGrade += grade;
            totalLinesCode += res.getNoLines();
            totalLogs += res.getNoLogs();
            totalMethods += res.getNoMethods();
        }

        totalGrade = GradeCalculationStrategy.round(totalGrade / (double) result.size());

        htmlPage = htmlPage.replace("@totalGrade@", HtmlTagHelper.getGradeButton(totalGrade, ""));

        htmlPage = htmlPage.replace("@totalPublicMethods@", Long.toString(totalMethods));
        htmlPage = htmlPage.replace("@totalLogs@", Long.toString(totalLogs));
        htmlPage = htmlPage.replace("@totalLinesCode@", Long.toString(totalLinesCode));


        htmlPage = htmlPage.replace("@LogResultContent@", sb.toString());


        String urlPath = createHtmlResultPage(htmlPage);
        return openResultPage(urlPath);
    }

    private void buildDetails(LogResult result, int index, StringBuilder sb) {
        sb.append(HtmlTagHelper.detailStartTemplate.replace("@detailsId", "details_" + index));

        sb.append(HtmlTagHelper.divider);


        sb.append(String.format(HtmlTagHelper.dividerHeader, "Logs (" + result.getNoLogs() + ")"));
        //===============================First row ===============================
        sb.append(HtmlTagHelper.rowDiv);

        //Column 1
        String temp = String.format(HtmlTagHelper.divTag, "<b>No. verbose logs:</b> " + result.getNoVerboseLogs());
        temp += String.format(HtmlTagHelper.divTag, "<b>No. debug logs:</b> " + result.getNoDebugLogs());
        sb.append(String.format(HtmlTagHelper.divTagCol3, temp));

        //Column 2
        String temp2 = String.format(HtmlTagHelper.divTag, "<b>No. info logs:</b> " + result.getNoInfoLogs());
        temp2 += String.format(HtmlTagHelper.divTag, "<b>No. warning logs:</b> " + result.getNoWarningLogs());
        sb.append(String.format(HtmlTagHelper.divTagCol3, temp2));

        //Column 3
        String temp3 = String.format(HtmlTagHelper.divTag, "<b>No. error logs:</b> " + result.getNoErrorLogs());
        temp3 += String.format(HtmlTagHelper.divTag, "<b>No. wtf logs:</b> " + result.getNoWtfLogs());
        sb.append(String.format(HtmlTagHelper.divTagCol4, temp3));

        //Column 5
        sb.append(HtmlTagHelper.getGradeButton(GradeCalculationStrategy.calculateLogGrade(result)));

        //===============================Close row 1 ===============================
        sb.append(HtmlTagHelper.divCloseTag);


        sb.append(String.format(HtmlTagHelper.dividerHeader, "Methods (" + result.getNoMethods() + ")"));
        //===============================Second row ===============================
        sb.append(HtmlTagHelper.rowDiv);

        //Column 3
        sb.append(String.format(HtmlTagHelper.divTagCol3, "<b>No. public methods:</b> " + result.getNoPublicMethods()));

        //Column 1
        sb.append(String.format(HtmlTagHelper.divTagCol3, "<b>No. protected methods:</b> " + result.getNoProtectedMethods()));

        //Column 2
        sb.append(String.format(HtmlTagHelper.divTagCol4, "<b>No. private methods:</b> " + result.getNoPrivateMethods()));

        //Column 3
        sb.append(HtmlTagHelper.getGradeButton(GradeCalculationStrategy.calculateMethodGrade(result)));

        //===============================Close row 2===============================
        sb.append(HtmlTagHelper.divCloseTag);


        sb.append(String.format(HtmlTagHelper.dividerHeader, "Other"));
        //===============================Third row===============================
        sb.append(HtmlTagHelper.rowDiv);

        //Column 1
        sb.append(String.format(HtmlTagHelper.divTagCol10, "<b>No. Lines code:</b> " + result.getNoLines()));

        //Column 2
        sb.append(HtmlTagHelper.getGradeButton(GradeCalculationStrategy.calculateCodeLinesGrade(result)));

        //===============================Close row 3===============================
        sb.append(HtmlTagHelper.divCloseTag);


        //Close tag
        sb.append(HtmlTagHelper.divCloseTag);
    }


    private String createHtmlResultPage(String htmlContent) {
        String filename = "results_" + Util.getTimeStamp("yyyyMMdd-HHmm") + ".html";

        try (PrintWriter out = new PrintWriter(filename)) {
            out.print(htmlContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return filename;
    }

    private boolean openResultPage(String urlPath) {
        File htmlFile = new File(urlPath);

        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}