package JAO.tests;


public class AllTests {
    public void run(int n) {
        for(int i = 0; i < n; i++) {
            var parameter = TestParameters.randomTest(
                    1000,
                    200,
                    30,
                    200,
                    30
            );
            var test = new SingleTest(parameter);
            test.runTest(5);
            test.appendResultToFile("results.csv");
        }
    }

}
