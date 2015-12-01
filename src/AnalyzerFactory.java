public class AnalyzerFactory {


    public static IAnalyzer resolve(String projectType, IFileLoader fileLoader) {
        switch (projectType.toLowerCase()) {
            case "android":
                return new AndroidAnalyzer(fileLoader);
        }

        throw new UnsupportedOperationException();
    }


}
