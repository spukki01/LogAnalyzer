public class TestResult extends Result {


    public TestResult(String fileName) {
        super(fileName);
    }

    public long getNoTestCases() {
        return super.getNoPublicMethods();
    }

}
