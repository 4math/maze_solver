package dip107;

interface MazeTest {
    void testMaze(int row, int col, int seed);
}

public class TestFramework {

    public final int iterationCount = 5;
    // sizes of mazes. It is crucial them to be in the same order as in the Excel worksheet
    private final int[][] sizes = {
            {10, 10},
            {11, 11},
            {100, 100},
            {101, 101},
            {500, 500},
            {501, 501},
            {1000, 1000},
            {1001, 1001},
            {1500, 1500},
            {1501, 1501},
            {2000, 2000},
            {2001, 2001},
            {2500, 2500},
            {2501, 2501},
            {3000, 3000},
            {3001, 3001},
            {100, 500},
            {101, 501},
            {501, 101},
            {1000, 2000},
            {1001, 2001},
            {2000, 1000},
            {1001, 2001},
            {3, 5000},
            {3, 5001},
            {5000, 3},
            {5001, 3},
            {500, 1000},
            {501, 1001},
            {900, 1501},
            {901, 1500},
            {2000, 1801},
            {2001, 1800}
    };
    private final int[] seeds;
    public MazeTest testingMethod;
    public double[][][] timeResults;

    TestFramework(MazeTest mazeTest, int[] seeds) {
        testingMethod = mazeTest;
        this.seeds = seeds;
        timeResults = new double[seeds.length][sizes.length][iterationCount];
    }

    public void test() {

        for (int k = 0; k < seeds.length; k++) {
            for (int j = 0; j < iterationCount + 1; j++) {
                for (int i = 0; i < sizes.length; i++) {
                    if (j == 0) {
                        // we skip the first iteration, in order to get JVM work faster
                        testingMethod.testMaze(sizes[i][0], sizes[i][1], seeds[k]);
                    } else {
                        long start = System.nanoTime();
                        testingMethod.testMaze(sizes[i][0], sizes[i][1], seeds[k]);
                        long end = System.nanoTime();

                        double timeElapsed = (end - start) / 1000.0;
//                        System.out.printf("The testing number %d for iteration %d for size %d x %d, time is %10.2f%n", i, j,
//                                sizes[i][0], sizes[i][1], timeElapsed);
                        timeResults[k][i][j - 1] = timeElapsed;
                    }
                }
            }
        }
    }

    public void showResults() {

        // Formatting is chosen, so that it would be easy to copy in Excel worksheet
        for (int i = 0; i < timeResults[0].length; i++) {
            for (int w = 0; w < seeds.length; w++) {
                for (int j = 0; j < timeResults[0][0].length; j++) {
                    System.out.printf("%10.2f\t", timeResults[w][i][j]);
                }
                System.out.print("\t\t");
            }
            System.out.println();
        }

    }

}
