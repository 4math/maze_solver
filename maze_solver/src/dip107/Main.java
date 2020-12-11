package dip107;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Labyrinth lab = new Labyrinth();

        boolean fullTestFlag = false; // Test every algorithm
        boolean testPrimFlag = false; // Test labyrinth creation with Prim's algorithm
        boolean testRdfsFlag = false; // Test labyrinth creation with RDFS algorithm
        boolean testSolveDfsFlag = false; // Test dfs solve algorithm
        boolean testSolveBfsFlag = false; // Test bfs solve algorithm
        boolean testSolveAstarFlag = false; // Test A* solve algorithm
        boolean isPrettyPrint = false;

        for (String arg : args) {
            if (arg.compareTo("-ft") == 0) {
                fullTestFlag = true;
            }

            if (arg.compareTo("-tprim") == 0) {
                testPrimFlag = true;
            }

            if (arg.compareTo("-trdfs") == 0) {
                testRdfsFlag = true;
            }

            if (arg.compareTo("-tdfs") == 0) {
                testSolveDfsFlag = true;
            }

            if (arg.compareTo("-tbfs") == 0) {
                testSolveBfsFlag = true;
            }

            if (arg.compareTo("-tastar") == 0) {
                testSolveAstarFlag = true;
            }

            if (arg.compareTo("-pretty") == 0) {
                isPrettyPrint = true;
            }
        }

        boolean testing = fullTestFlag ||
                          testPrimFlag ||
                          testSolveAstarFlag ||
                          testRdfsFlag ||
                          testSolveBfsFlag ||
                          testSolveDfsFlag;
        if (testing) {
            lab.verbose = false;
        }

        ArrayList<MazeTest> mazes = new ArrayList<>();
        int[] seeds = {1, 2, 3};
        int seed = 2021;

        if (testRdfsFlag || fullTestFlag) {
            MazeTest mazeTest = lab::createRDFS;
            MazeTestFramework mazeTestFramework = new MazeTestFramework(mazeTest, seeds);
            mazeTestFramework.test();
            System.out.println("Test result RDFS:");
            mazeTestFramework.showResults();
        }

        if (testPrimFlag || fullTestFlag) {
            MazeTest mazeTest = lab::createPrims;
            MazeTestFramework mazeTestFramework = new MazeTestFramework(mazeTest, seeds);
            mazeTestFramework.test();
            System.out.println("Test result PRIM:");
            mazeTestFramework.showResults();
        }

        if (testSolveDfsFlag || fullTestFlag) {
            mazes.add(lab::createRDFS);
            mazes.add(lab::createPrims);
            PathfindingTest pfTest = lab::solveDFS;
            PathfindingTestFramework pfTestFramework = new PathfindingTestFramework(pfTest, mazes, seed);
            pfTestFramework.test();
            System.out.println("Test result DFS:");
            pfTestFramework.showResults();
        }

        if (testSolveBfsFlag || fullTestFlag) {
            mazes.add(lab::createRDFS);
            mazes.add(lab::createPrims);
            PathfindingTest pfTest = lab::solveBFS;
            PathfindingTestFramework pfTestFramework = new PathfindingTestFramework(pfTest, mazes, seed);
            pfTestFramework.test();
            System.out.println("Test result BFS:");
            pfTestFramework.showResults();
        }

        if (testSolveAstarFlag || fullTestFlag) {
            mazes.add(lab::createRDFS);
            mazes.add(lab::createPrims);
            PathfindingTest pfTest = lab::solveAStar;
            PathfindingTestFramework pfTestFramework = new PathfindingTestFramework(pfTest, mazes, seed);
            pfTestFramework.test();
            System.out.println("Test result ASTAR:");
            pfTestFramework.showResults();
        }

        if (testing)
            return;

        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("row count: ");
            int row = sc.nextInt();

            if (row < 0) {
                throw new Exception("Row amount cannot be a negative number!");
            }

            System.out.print("column count: ");
            int col = sc.nextInt();

            if (col < 0) {
                throw new Exception("Column amount cannot be a negative number!");
            }

            sc.nextLine();
            char ans;
            do {
                System.out.print("Auto fill maze (y/n)?");
                ans = sc.nextLine().toLowerCase().charAt(0);
            } while (ans != 'y' && ans != 'n');

            if (ans == 'n') {
                // manual labyrinth creation
                lab.createEmpty(row, col);
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        int answer = -1;
                        while (answer != 0 && answer != 1) {
                            answer = sc.nextInt();
                        }

                        lab.maze[i][j] = answer;
                    }
                }
            } else if (ans == 'y') {
                // generate maze
                // choose generation method
                System.out.println("1) Prim's algorithm");
                System.out.println("2) RDFS algorithm");

                int genMethod = 0;
                while (genMethod < 1 || genMethod > 2) {
                    System.out.print("method number (1-2):");
                    genMethod = sc.nextInt();
                }

                lab.verbose = true;
                switch (genMethod) {
                    case 1:
                        lab.createPrims(row, col);
                        if (isPrettyPrint)
                            lab.prettyPrint();
                        else
                            lab.print();
                        break;
                    case 2:
                        lab.createRDFS(row, col);
                        if (isPrettyPrint)
                            lab.prettyPrint();
                        else
                            lab.print();
                        break;
                    default:
                        return;
                }
            } else return;

            System.out.println("1) DFS method");
            System.out.println("2) BFS method");
            System.out.println("3) A* method");

            int solveMethod = 0;
            while (solveMethod < 1 || solveMethod > 3) {
                System.out.print("method number (1-3):");
                solveMethod = sc.nextInt();
            }

            lab.verbose = true;
            switch (solveMethod) {
                case 1 -> {
                    lab.solveDFS();
                    if (isPrettyPrint) {
                        lab.prettyPrint();
                    }
                }
                case 2 -> {
                    lab.solveBFS();
                    if (isPrettyPrint) {
                        lab.prettyPrint();
                    }
                }
                case 3 -> {
                    lab.solveAStar();
                    if (isPrettyPrint) {
                        lab.prettyPrint();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Incorrect input, please try again!");
        }
    }
}
