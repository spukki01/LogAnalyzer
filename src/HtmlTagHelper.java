public final class HtmlTagHelper {

    private final static String Success         = "btn-success";
    private final static String Warning         = "btn-warning";
    private final static String Danger          = "btn-danger";
    private final static String Primary         = "btn-primary";

    private final static String newLine = System.getProperty("line.separator");

    private final static String checkMarkTag    = "<div class=\"col-md-2 %s\"></div>" + newLine;

    public final static String detailStartTemplate =
            "<div class=\"col-md-12 collapse\" id=\"@detailsId\" aria-expanded=\"true\">";


    public final static String expandButton =
            "<div class=\"col-md-1\">" +
                    "<button class=\"btn btn-primary glyphicon glyphicon-plus\" type=\"button\" data-toggle=\"collapse\" data-target=\"#@detailsId\" title=\"Expand\" aria-expanded=\"true\" aria-controls=\"@detailsId\"></button>" +
                    "</div>" + newLine;


    public final static String divider          = "<div class=\"row divider\"></div>" + newLine;
    public final static String dividerHeader    = "<div class=\"row divider\"><h5>%s</h5></div>" + newLine;

    public final static String rowWellDiv       = "<div class=\"row well\">";
    public final static String rowDiv           = "<div class=\"row\">";

    public final static String divTagCol1       = "<div class=\"col-md-1\">%s</div>" + newLine;
    public final static String divTagCol3       = "<div class=\"col-md-3\">%s</div>" + newLine;
    public final static String divTagCol4       = "<div class=\"col-md-4\">%s</div>" + newLine;
    public final static String divTagCol6       = "<div class=\"col-md-6\">%s</div>" + newLine;
    public final static String divTagCol10      = "<div class=\"col-md-10\">%s</div>" + newLine;

    public final static String divTag           = "<div>%s</div>" + newLine;
    public final static String divCloseTag      = "</div>" + newLine;

    public final static String liTag            = "<li>%s</li>" + newLine;


    public static String getCheckMark(boolean hasValue) {
        return String.format(checkMarkTag, hasValue ? "glyphicon glyphicon-ok" : "glyphicon glyphicon-remove");
    }

    public static String getGradeButton(double grade) {
        return getGradeButton(grade, "Grade: ");
    }

    public static String getGradeButton(double grade, String prefix) {
        String button = "<div class=\"col-md-2\">";
        button += "<button class=\"btn " + getGradeClass(grade) + "\">";
        button += prefix + grade;
        button += "</button>";
        button += divCloseTag;

        return button;
    }

    private static String getGradeClass(double grade) {
        switch (GradeCalculationStrategy.getClassification(grade)) {
            case Good:
                return Success;
            case Warning:
                return Warning;
            case Bad:
                return Danger;
            default:
                return Primary;
        }
    }

}