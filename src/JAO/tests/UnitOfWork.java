package JAO.tests;

public class UnitOfWork {

    static public double doUnitOfWork(double element) {
        double res = 0;
        for(int i = 0; i < 1000; ++i) {
            res +=  Math.asin((Math.cos(i * element + 1) - Math.pow(Math.sin(i * i * element + 1), 3)) / 2);
        }
        return res / 1000.0;
    }
}
