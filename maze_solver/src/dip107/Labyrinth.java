package dip107;

import java.util.Random;
import java.util.LinkedList;

public class Labyrinth {

    public int[][] maze;

    public void createEmpty(int row, int col) {
        maze = new int[row][col];
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
            for(int i = 0; i < 4; i++) { // Compute list of neighbours
                int nRow = neighbors[i][0] + frontierCell.x, nCol = neighbors[i][1] + frontierCell.y;
                if(nRow >= 0 && nRow < row && nCol >= 0 && nCol < col && maze[nRow][nCol] == 0) {
                    neighborList.add(new Pair(nRow, nCol));
                }
            }

            int randomNeighbourIdx = random.nextInt(neighborList.size());
            Pair neighborCell = neighborList.get(randomNeighbourIdx);
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

    }

    public void solveBFS() {

    }

    public void solveAStar() {}
}
