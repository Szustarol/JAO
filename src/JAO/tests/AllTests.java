package JAO.tests;


import JAO.tests.utility.TestParameters;

public class AllTests {
    public void run(int n) {
        for(int i = 0; i < n; i++) {
            var parameter = TestParameters.randomTest(
                    1000,
                    50,
                    300,
                    50,
                    300
            );
            var test = new SingleTest(parameter);
            System.out.println("Running test number " + (i + 1) + "...");
            test.runTest(100);
            test.appendResultToFile("results.csv");
            System.out.println("Finished test number " + (i + 1) + ".");
        }
    }

}
