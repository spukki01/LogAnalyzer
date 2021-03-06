public final class GradeCalculationStrategy {

    public enum Classification {
        Good,
        Warning,
        Bad
    }

    public static double calculateTotalGrade(double totalGradeSum, long noFiles) {
        return round(totalGradeSum / (double) noFiles);
    }

    public static double calculateGrade(LogResult result) {
        double total =  (calculateLogGrade(result) * 0.8) +
                        (calculateCodeLinesGrade(result) * 0.1) +
                        (calculateMethodGrade(result) * 0.1);

        return total > 1.0 ? 1.0 : round(total);
    }


    public static double calculateLogGrade(LogResult result) {
        double grade;

        if (result.getNoPublicMethods() > 0 || result.getNoProtectedMethods() > 0) {
            long noMethods = result.getNoPublicMethods() + result.getNoProtectedMethods();
            grade = ((double) result.getNoLogs())/((double)noMethods);
        }
        else if (result.getNoPrivateMethods() > 0) {
            grade = result.getNoLogs() > 0 ? 1.0 : 0.0;
        }
        else {
            grade = 1.0;
        }

        return grade > 1.0 ? 1.0 : round(grade);
    }


    public static double calculateCodeLinesGrade(Result result) {
        long linesCode = result.getNoLines();

        if (linesCode <= 450)                       return 1.0;
        if (linesCode > 450 && linesCode <= 500)    return 0.8;
        if (linesCode > 500 && linesCode <= 550)    return 0.6;
        if (linesCode > 550 && linesCode <= 600)    return 0.4;
        if (linesCode > 600 && linesCode <= 650)    return 0.2;

        return 0.0;
    }


    public static double calculateMethodGrade(Result result) {
        long noMethods = result.getNoMethods();

        if (noMethods <= 20)                    return 1.0;
        if (noMethods > 20 && noMethods <= 22)  return 0.8;
        if (noMethods > 22 && noMethods <= 25)  return 0.6;
        if (noMethods > 25 && noMethods <= 28)  return 0.4;
        if (noMethods > 28 && noMethods <= 32)  return 0.2;

        return 0.0;
    }

    public static Classification getClassification(double grade) {
        if (grade >= 0.8)   return Classification.Good;
        if (grade >= 0.4)   return Classification.Warning;

        return Classification.Bad;
    }

    private static double round(double value) {
        return (double)Math.round(value*100)/100;
    }

}
