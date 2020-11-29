package dip107;

interface TimeTest {
    void test();
}

public class TimeTesting {

    public static long executionTime(TimeTest timeTest) {
        long start = System.nanoTime();
        timeTest.test();
        long end = System.nanoTime();

        long timeElapsed = end - start;
        return timeElapsed / 1000;
    }
}
