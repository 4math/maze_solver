package dip107;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Labyrinth lab = new Labyrinth();

        boolean full_test_flag = false; // Test every algorithm
        boolean test_prim_flag = false; // Test labyrinth creation with Prim's algorithm
        boolean test_rdfs_flag = false; // Test labyrinth creation with RDFS algorithm
        boolean test_solve_dfs_flag = false; // Test dfs solve algorithm
        boolean test_solve_bfs_flag = false; // Test bfs solve algorithm
        boolean test_solve_astar_flag = false; // Test A* solve algorithm
        boolean pretty_print = false;

        for (String arg : args) {
            if (arg.compareTo("-ft") == 0) {
                full_test_flag = true;
            }

            if (arg.compareTo("-tprim") == 0) {
                test_prim_flag = true;
            }

            if (arg.compareTo("-trdfs") == 0) {
                test_rdfs_flag = true;
            }

            if (arg.compareTo("-tdfs") == 0) {
                test_solve_dfs_flag = true;
            }

            if (arg.compareTo("-tbfs") == 0) {
                test_solve_bfs_flag = true;
            }

            if (arg.compareTo("-tastar") == 0) {
                test_solve_astar_flag = true;
            }

            if (arg.compareTo("-pretty") == 0) {
                pretty_print = true;
            }
        }

        boolean testing = full_test_flag || test_prim_flag || test_solve_astar_flag || test_rdfs_flag || test_solve_bfs_flag || test_solve_dfs_flag;
        if (testing)
            lab.verbose = false;

        if (test_rdfs_flag || full_test_flag) {
            MazeTest mazeTest = lab::createRDFS;
            int[] seeds = {1, 2, 3};
            MazeTestFramework mazeTestFramework = new MazeTestFramework(mazeTest, seeds);
            mazeTestFramework.test();
            System.out.println("Test result RDFS:");
            mazeTestFramework.showResults();
        }

        if (test_prim_flag || full_test_flag) {
            MazeTest mazeTest = lab::createPrims;
            int[] seeds = {1, 2, 3};
            MazeTestFramework mazeTestFramework = new MazeTestFramework(mazeTest, seeds);
            mazeTestFramework.test();
            System.out.println("Test result PRIM:");
            mazeTestFramework.showResults();
        }

        if (test_solve_dfs_flag || full_test_flag) {
            ArrayList<MazeTest> mazes = new ArrayList<>();
            mazes.add(lab::createRDFS);
            mazes.add(lab::createPrims);
            PathfindingTest pfTest = lab::solveDFS;
            int seed = 2021;
            PathfindingTestFramework pfTestFramework = new PathfindingTestFramework(pfTest, mazes, seed);
            pfTestFramework.test();
            System.out.println("Test result DFS:");
            pfTestFramework.showResults();
        }

        if (test_solve_bfs_flag || full_test_flag) {
            ArrayList<MazeTest> mazes = new ArrayList<>();
            mazes.add(lab::createRDFS);
            mazes.add(lab::createPrims);
            PathfindingTest pfTest = lab::solveBFS;
            int seed = 2021;
            PathfindingTestFramework pfTestFramework = new PathfindingTestFramework(pfTest, mazes, seed);
            pfTestFramework.test();
            System.out.println("Test result BFS:");
            pfTestFramework.showResults();
        }

        if (test_solve_astar_flag || full_test_flag) {
            ArrayList<MazeTest> mazes = new ArrayList<>();
            mazes.add(lab::createRDFS);
            mazes.add(lab::createPrims);
            PathfindingTest pfTest = lab::solveAStar;
            int seed = 2021;
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

            System.out.print("column count: ");
            int col = sc.nextInt();

            sc.nextLine();
            System.out.print("Auto fill maze (y/n)?");
            char ans = sc.nextLine().toLowerCase().charAt(0);

            if (ans == 'n') {
                // manual labyrinth creation
                lab.createEmpty(row, col);
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        int answer = sc.nextInt();
                        if (answer != 0 && answer != 1) {
                            j--;
                            if (j < 0) {
                                j = col - 1;
                                i--;
                            }
                            continue;
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
                    System.out.print("method number (1-3):");
                    genMethod = sc.nextInt();
                }

                lab.verbose = true;
                switch (genMethod) {
                    case 1:
                        lab.createPrims(row, col, 1);
                        if (pretty_print)
                            lab.prettyPrint();
                        else
                            lab.Print();
                        break;
                    case 2:
                        lab.createRDFS(row, col, 1);
                        if (pretty_print)
                            lab.prettyPrint();
                        else
                            lab.Print();
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
                case 1:
                    lab.solveDFS();
                    break;
                case 2:
                    lab.solveBFS();
                    break;
                case 3:
                    lab.solveAStar();
                    break;
                default:
                    return;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
