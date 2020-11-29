package dip107;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Labyrinth lab = new Labyrinth();
        int row, col, genMethod, solveMethod;
        char ans;
        try {
            Scanner sc = new Scanner(System.in);

//            int[][] sizes = {{11, 11}, {101, 101}, {501, 501}, {751, 751}, {1001, 1001}, {1251, 1251}, {1501, 1501}, {1751, 1751}, {2001, 2001},
//                    {2251, 2251}, {2501, 2501}, {2751, 2751}, {3001, 3001}, {101, 501}, {501, 101}, {1001, 2001}, {2001, 1001}, {3, 5001},
//                    {5001, 3}, {501, 1001}};
//            int cnt = 20;
//            int seed = 1;
//
//            for(int i = 0; i < cnt; i++) {
//                Labyrinth testLab = new Labyrinth();
//
//                testLab.createPrims(sizes[i][0], sizes[i][1], seed);
//
//                long start = System.nanoTime();
//                testLab.solveAStar();
//                long end = System.nanoTime();
//
//                long timeElapsed = (end - start) / 1000; // microseconds
//
//                //System.out.printf("size = (%d;%d) seed = %d time = %d %n", sizes[i][0], sizes[i][1], seed, timeElapsed);
//                //System.out.printf("%d%n", timeElapsed);
//            }


            System.out.print("row count: ");
            row = sc.nextInt();

            if (row == -1) {
                MazeTest mazeTest = lab::createRDFS;
                int[] seeds = {1, 2, 3};
                TestFramework testFramework = new TestFramework(mazeTest, seeds);
                testFramework.test();
                testFramework.showResults();
                return;
            } else if(row == -2) {
                MazeTest mazeTest = lab::createPrims;
                int[] seeds = {1, 2, 3};
                TestFramework testFramework = new TestFramework(mazeTest, seeds);
                testFramework.test();
                testFramework.showResults();
                return;
            }

            System.out.print("column count: ");
            col = sc.nextInt();

            sc.nextLine();
            System.out.print("Auto fill maze (y/n)?");
            ans = sc.nextLine().toLowerCase().charAt(0);

            if (ans == 'n') {
                // manual labyrinth creation
                // TODO: validate input
                int[][] maze = new int[row][col];
                int answer;
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        answer = sc.nextInt();
                        maze[i][j] = answer;
                    }
                }
                lab.maze = maze;
                lab.width = col;
                lab.height = row;
            } else if (ans == 'y') {
                // generate maze
                // choose generation method
                System.out.println("1) Prim's algorithm");
                System.out.println("2) RDFS algorithm");
                System.out.print("labyrinth method (1-2): ");
                genMethod = sc.nextInt();

                switch (genMethod) {
                    case 1:
                        lab.createPrims(row, col, 1);
//                        lab.prettyPrint();
                        break;
                    case 2:
                        lab.createRDFS(row, col, 3);
//                        lab.prettyPrint();
                        break;
                }

//                for(int r = 0; r < row; r++) {
//                    for(int c = 0; c < col; c++) {
//                        if(lab.maze[r][c] == 0) {
//                            System.out.print("░");
//                        } else {
//                            System.out.print("█");
//                        }
//                    }
//                    System.out.println();
//                }
            }
            else {
                // error
                throw new Exception("Not your turn, ham");
            }

            // TODO: check if int > 3 && < 1
            System.out.println("1) DFS method");
            System.out.println("2) BFS method");
            System.out.println("3) A* method");
            System.out.print("method number (1-3):");
            solveMethod = sc.nextInt();

            long timeResult;
            switch (solveMethod) {
                case 1:
                    TimeTest timeTestDFS = lab::solveDFS;
                    timeResult = TimeTesting.executionTime(timeTestDFS);
                    System.out.println();
                    System.out.println("timeResult = " + timeResult);
//                    lab.prettyPrint();
                    break;
                case 2:
                    TimeTest timeTestBFS = lab::solveBFS;
                    timeResult = TimeTesting.executionTime(timeTestBFS);
                    System.out.println();
                    System.out.println("timeResult = " + timeResult);
//                    lab.prettyPrint();
                    break;
                case 3:
                    TimeTest timeTestAStar = lab::solveAStar;
                    timeResult = TimeTesting.executionTime(timeTestAStar);
                    System.out.println("timeResult = " + timeResult);
//                    lab.prettyPrint();
                    break;
                default:
                    System.out.println("Incorrect input");
                    break;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
