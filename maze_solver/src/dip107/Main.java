package dip107;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Labyrinth lab = new Labyrinth();
        int row, col, genMethod, solveMethod;
        char ans;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("row count: ");
            row = sc.nextInt();

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
                        lab.createPrims(row, col, 0);
//                        lab.prettyPrint();
                        break;
                    case 2:
                        lab.createRDFS(row, col, 11);
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
                    Test testDFS = lab::solveDFS;
                    timeResult = Testing.exectuionTime(testDFS);
                    System.out.println();
                    System.out.println("timeResult = " + timeResult);
//                    lab.prettyPrint();
                    break;
                case 2:
                    Test testBFS = lab::solveBFS;
                    timeResult = Testing.exectuionTime(testBFS);
                    System.out.println();
                    System.out.println("timeResult = " + timeResult);
//                    lab.prettyPrint();
                    break;
                case 3:
                    Test testAStar = lab::solveAStar;
                    timeResult = Testing.exectuionTime(testAStar);
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
