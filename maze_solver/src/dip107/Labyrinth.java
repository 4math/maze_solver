package dip107;

import java.util.Deque;
import java.util.Random;
import java.util.LinkedList;

public class Labyrinth {

    public int[][] maze;
    int width, height;

    public void createEmpty(int row, int col) {
        maze = new int[row][col];
        width = col;
        height = row;
    }

    public void createPrims(int row, int col, int seed) {
        createEmpty(row, col);
        Random random = new Random(seed);

        for(int r = 0; r < row; r++) {
            for(int c = 0; c < col; c++) {
                maze[r][c] = 1; // Cell state = blocked
            }
        }

        int r = 0, c = 0;
        maze[r][c] = 0; // Cell state = passage

        int[][] neighbors = {
                {-2, 0}, {+2, 0}, {0, -2}, {0, +2}
        };

        LinkedList<Pair> frontierList = new LinkedList<>();
        for(int i = 0; i < 4; i++) { // Compute first frontiers
            int nRow = neighbors[i][0] + r, nCol = neighbors[i][1] + c;
            if(nRow >= 0 && nRow < row && nCol >= 0 && nCol < col && maze[nRow][nCol] == 1) {
                frontierList.add(new Pair(nRow, nCol));
                maze[nRow][nCol] = 2; // Cell state = visited
            }
        }

        while(!frontierList.isEmpty()) {
            int randomFrontierIdx = random.nextInt(frontierList.size());
            Pair frontierCell = frontierList.get(randomFrontierIdx);
            maze[frontierCell.x][frontierCell.y] = 0;

            LinkedList<Pair> neighborList = new LinkedList<>();
            for(int i = 0; i < 4; i++) { // Compute list of neighbors
                int nRow = neighbors[i][0] + frontierCell.x, nCol = neighbors[i][1] + frontierCell.y;
                if(nRow >= 0 && nRow < row && nCol >= 0 && nCol < col && maze[nRow][nCol] == 0) {
                    neighborList.add(new Pair(nRow, nCol));
                }
            }

            int randomNeighborIdx = random.nextInt(neighborList.size());
            Pair neighborCell = neighborList.get(randomNeighborIdx);
            maze[(frontierCell.x + neighborCell.x) / 2][(frontierCell.y + neighborCell.y) / 2] = 0;

            for(int i = 0; i < 4; i++) { // Compute next frontiers
                int nRow = neighbors[i][0] + frontierCell.x, nCol = neighbors[i][1] + frontierCell.y;
                if(nRow >= 0 && nRow < row && nCol >= 0 && nCol < col && maze[nRow][nCol] == 1) {
                    frontierList.add(new Pair(nRow, nCol));
                    maze[nRow][nCol] = 2;
                }
            }

            frontierList.remove(randomFrontierIdx);
        }
    }

    public void createRDFS() {

    }

    public void solveDFS() {
        int[][] neighbors = {
                {-1, 0}, {+1, 0}, {0, -1}, {0, +1}
        };

        boolean[][] visited = new boolean[height][width];

        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                visited[row][col] = false;
            }
        }

        Deque<Pair> deque = new LinkedList<>();
        deque.push(new Pair(0, 0));

        while(true) {
            Pair current = deque.peekLast();
            visited[current.x][current.y] = true;

            if (current.x == height - 1 && current.y == width - 1) {
                break;
            }

            boolean flag = false;
            for (int i = 0; i < 4; i++) {
                int nRow = neighbors[i][0] + current.x, nCol = neighbors[i][1] + current.y;
                if (nRow >= 0 && nRow < height && nCol >= 0 && nCol < width && !visited[nRow][nCol] && maze[nRow][nCol] == 0) {
                    deque.addLast(new Pair(nRow, nCol));
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                deque.removeLast();
            }
        }

        System.out.println("results:");
        while(!deque.isEmpty()) {
            Pair node = deque.pollFirst();
            System.out.printf("(%d,%d) ", node.x, node.y);
        }
    }

    public void solveBFS() {
        int[][] neighbors = {
                {-1, 0}, {+1, 0}, {0, -1}, {0, +1}
        };

        boolean[][] visited = new boolean[height][width];
        int[][] weight = new int[height][width];

        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                visited[row][col] = false;
                weight[row][col] = 0;
            }
        }

        int[][] nextMoves = new int[height * width][3]; // array of nodes where to go next
        nextMoves[0][0] = 0; // starting position x
        nextMoves[0][1] = 0; // starting position y
        nextMoves[0][2] = -1; // shows what node lead to current node (first node has -1)
        int n = 0, m = 1; // n - current processed node, m - empty cell
        visited[0][0] = true;

        while(n < m) {
            Pair current = new Pair(nextMoves[n][0], nextMoves[n][1]);

            if(current.x == height - 1 && current.y == width - 1) {
                break;
            }

            for(int i = 0; i < 4; i++) {
                int nRow = neighbors[i][0] + current.x, nCol = neighbors[i][1] + current.y;
                if(nRow >= 0 && nRow < height && nCol >= 0 && nCol < width && !visited[nRow][nCol] && maze[nRow][nCol] == 0) {
                    nextMoves[m][0] = nRow;
                    nextMoves[m][1] = nCol;
                    nextMoves[m][2] = n;
                    m++;
                    visited[nRow][nCol] = true;
                    weight[nRow][nCol] = weight[current.x][current.y] + 1;
                }
            }

            n++;
        }

        if(n >= m) {
            // path does not exist
            System.out.println("ceļa nav?");
            return;
        }

        // Reconstruct the path
        LinkedList<Pair> outputList = new LinkedList<>();
        {
            Pair node = new Pair(nextMoves[n][0], nextMoves[n][1]);
            int previousNodeIdx = nextMoves[n][2];
            while(previousNodeIdx != -1) {
                outputList.add(new Pair(node.x, node.y));
                node = new Pair(nextMoves[previousNodeIdx][0], nextMoves[previousNodeIdx][1]);
                previousNodeIdx = nextMoves[previousNodeIdx][2];
            }
            outputList.add(new Pair(0, 0));
        }

        // Output in reverse order
        System.out.println("results:");
        while(!outputList.isEmpty()) {
            Pair node = outputList.pollLast();
            System.out.printf("(%d,%d) ", node.x, node.y);
        }
    }

    public void solveAStar() {

    }
}
