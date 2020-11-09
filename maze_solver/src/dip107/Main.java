package dip107;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Labyrinth lab;
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
            // empty comment

            System.out.println("results:");
            for (int i=0; i<2; i++) {
                System.out.println("");
            }

        } catch (Exception e) {
            System.out.println("input error");
        }

    }
}
