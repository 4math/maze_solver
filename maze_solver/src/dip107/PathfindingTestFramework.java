package dip107;

import java.util.ArrayList;

interface PathfindingTest {
    int testPathfinding();
}

public class PathfindingTestFramework {

    public final int iterationCount = 5;
    // sizes of mazes. It is crucial them to be in the same order as in the Excel worksheet
    public final int[][] sizes = {
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
    public final int seed;
    public PathfindingTest testingMethod;
    public ArrayList<MazeTest> mazes;
    public double[][][][] results;

    PathfindingTestFramework(PathfindingTest testingMethod, ArrayList<MazeTest> mazes, int seed) {
        this.testingMethod = testingMethod;
        this.mazes = mazes;
        this.seed = seed;
        // first dimension is the type of maze: RDFS(0) or Prim(1), second dimension is a measuring value: time is 0, path is 1
        results = new double[2][2][sizes.length][iterationCount];
    }

    public void test() {

        // iterating through maze types
        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < iterationCount + 1; i++) {
                for (int j = 0; j < sizes.length; j++) {
                    if (i == 0) {
                        // skip one iteration, so that JVM could work faster
                        mazes.get(k).createMaze(sizes[j][0], sizes[j][1], seed);
                        testingMethod.testPathfinding();
                    } else {
                        mazes.get(k).createMaze(sizes[j][0], sizes[j][1], seed);
                        long start = System.nanoTime();
                        int pathLength = testingMethod.testPathfinding();
                        long end = System.nanoTime();

                        double timeElapsed = (end - start) / 1000.0; // time in microseconds
//                        System.out.printf("The testing number %d for iteration %d for size %d x %d, time is %10.2f, path is %d, type of maze is %d %n", i, j,
//                                sizes[j][0], sizes[j][1], timeElapsed, pathLength, k);
                        results[k][0][j][i - 1] = timeElapsed;
                        results[k][1][j][i - 1] = pathLength;
                    }
                }
            }
        }

    }

    public void showResults() {

        // iterating through maze types
        for (int i = 0; i < sizes.length; i++) {
            for (int k = 0; k < 2; k++) {

                for (int j = 0; j < iterationCount * 2; j++) {
                    if (j < iterationCount) {
                        System.out.printf("%10.2f\t", results[k][0][i][j]);
                    } else {
                        if (j == iterationCount) {
                            System.out.print("\t\t");
                        }
                        System.out.printf("%10d\t", (int) results[k][1][i][j - iterationCount]);
                    }
                }
                // tabs after k iteration
                System.out.print("\t\t");
            }
            // new line after all iterationCount and k
            System.out.println();
        }

    }

}
