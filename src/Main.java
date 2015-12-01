import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Missing parameter values; [project type] [project path]");
            return;
        }

        String projectType = args[0];
        String projectPath = args[1];

        IFileLoader fileLoader = new FileLoader();
        IAnalyzer analyzer = AnalyzerFactory.resolve(projectType, fileLoader);

        List<Result> result = analyzer.analyzeFiles(projectPath);

        HtmlGenerator htmlGenerator = new HtmlGenerator(fileLoader);
        htmlGenerator.generateHtmlReport(result);

    }
}
