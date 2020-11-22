package dip107;

interface Test {
    void test();
}

public class Testing {

    public static long exectuionTime(Test test) {
        long start = System.nanoTime();
        test.test();
        long end = System.nanoTime();

        long timeElapsed = end - start;
        return timeElapsed / 1000;
    }
}
