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
                        break;
                    case 2:
                        lab.createRDFS(row, col, 11);
                        break;
                }

                for (int i = 0; i < row + 2; i++) {
                    for (int j = 0; j < col + 1; j++) {
                        if (i == 0 || i == row + 1) {
                            System.out.print("#");
                        }
                        if (i > 0 && i < row + 1 && j > 0 && j < col + 1) {
                            if (lab.maze[i - 1][j - 1] == 0) {
                                System.out.print(" ");
                            } else {
                                System.out.print("#");
                            }
                        }
                        if (j == 0 || j == col && i != 0 && i != row + 1) {
                            System.out.print("#");
                        }
                    }
                    System.out.println();
                }

                for(int r = 0; r < row; r++) {
                    for(int c = 0; c < col; c++) {
                        if(lab.maze[r][c] == 0) {
                            System.out.print("░");
                        } else {
                            System.out.print("█");
                        }
                    }
                    System.out.println();
                }
            }
            else {
                // error
                throw new Exception("Not your turn, ham");
            }

            // check if int > 3 && < 1
            System.out.println("1) DFS method");
            System.out.println("2) BFS method");
            System.out.println("3) A* method");
            System.out.print("method number (1-3):");
            solveMethod = sc.nextInt();

            switch (solveMethod) {
                case 1:
                    lab.solveDFS();
                    lab.prettyPrint();
                    break;
                case 2:
                    lab.solveBFS();
                    lab.prettyPrint();
                    break;
                case 3:
                    LinkedList<Pair> path = lab.solveAStar();
                    for (Pair pair : path) {
                        System.out.print(pair.x + " " + pair.y + " - ");
                    }
                    System.out.println();
                    lab.prettyPrint();
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
