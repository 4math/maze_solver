package dip107;

import java.util.*;

public class Labyrinth {

    public int[][] maze;
    public int width, height;
    public boolean verbose = false;

    public void createEmpty(int row, int col) {
        maze = new int[row][col];
        width = col;
        height = row;
    }

    public void createPrims(int row, int col) {
        Random random = new Random();
        int seed = random.nextInt();
//        if(verbose) System.out.println("Seed: " + seed);
        createPrims(row, col, seed);
    }

    public void createPrims(int row, int col, int seed) {
        boolean isRowEven = false;
        boolean isColEven = false;
        if (row % 2 == 0) {
            row -= 1;
            isRowEven = true;
        }
        if (col % 2 == 0) {
            col -= 1;
            isColEven = true;
        }
        createEmpty(row, col);
        Random random = new Random(seed);

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                maze[r][c] = 1; // Cell state = blocked
            }
        }

        int r = 0, c = 0;
        maze[r][c] = 0; // Cell state = passage

        int[][] neighbors = {
                {-2, 0}, {+2, 0}, {0, -2}, {0, +2}
        };

        LinkedList<Pair> frontierList = new LinkedList<>();
        for (int i = 0; i < 4; i++) { // Compute first frontiers
            int nRow = neighbors[i][0] + r, nCol = neighbors[i][1] + c;
            if (nRow >= 0 && nRow < row && nCol >= 0 && nCol < col && maze[nRow][nCol] == 1) {
                frontierList.add(new Pair(nRow, nCol));
                maze[nRow][nCol] = 2; // Cell state = visited
            }
        }

        while (!frontierList.isEmpty()) {
            int randomFrontierIdx = random.nextInt(frontierList.size());
            Pair frontierCell = frontierList.get(randomFrontierIdx);
            maze[frontierCell.x][frontierCell.y] = 0;

            LinkedList<Pair> neighborList = new LinkedList<>();
            for (int i = 0; i < 4; i++) { // Compute list of neighbors
                int nRow = neighbors[i][0] + frontierCell.x, nCol = neighbors[i][1] + frontierCell.y;
                if (nRow >= 0 && nRow < row && nCol >= 0 && nCol < col && maze[nRow][nCol] == 0) {
                    neighborList.add(new Pair(nRow, nCol));
                }
            }

            int randomNeighborIdx = random.nextInt(neighborList.size());
            Pair neighborCell = neighborList.get(randomNeighborIdx);
            maze[(frontierCell.x + neighborCell.x) / 2][(frontierCell.y + neighborCell.y) / 2] = 0;

//             additional empty spaces
            int randomNeighborIdx2 = random.nextInt(neighborList.size());
            Pair neighborCell2 = neighborList.get(randomNeighborIdx2);
            maze[(frontierCell.x + neighborCell2.x) / 2][(frontierCell.y + neighborCell2.y) / 2] = 0;

            for (int i = 0; i < 4; i++) { // Compute next frontiers
                int nRow = neighbors[i][0] + frontierCell.x, nCol = neighbors[i][1] + frontierCell.y;
                if (nRow >= 0 && nRow < row && nCol >= 0 && nCol < col && maze[nRow][nCol] == 1) {
                    frontierList.add(new Pair(nRow, nCol));
                    maze[nRow][nCol] = 2;
                }
            }

            frontierList.remove(randomFrontierIdx);
        }

        checkEvenDimensions(row, col, isRowEven, isColEven, random);
    }

    public void createRDFS(int row, int col) {
        Random random = new Random();
        int seed = random.nextInt();
//        if(verbose) System.out.println("Seed: " + seed);
        createRDFS(row, col, seed);
    }

    public void createRDFS(int row, int col, int seed) {
        boolean isRowEven = false, isColEven = false;
        if (row % 2 == 0) {
            row -= 1;
            isRowEven = true;
        }
        if (col % 2 == 0) {
            col -= 1;
            isColEven = true;
        }
        createEmpty(row, col); // creating an empty maze
        Random random = new Random(seed);
        Stack<Pair> stack = new Stack<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = 1;
            }
        }

        boolean[][] visited = new boolean[row][col];
        stack.push(new Pair(0, 0));
        visited[0][0] = true;

        maze[0][0] = 0;
        maze[row - 1][col - 1] = 0;

        // neighbour cells positions
        int[][] neighPos = {
                {2, 0}, {0, -2}, {-2, 0}, {0, 2}
        };

        while (!stack.isEmpty()) {

            Pair current = stack.pop();

            LinkedList<Pair> neighbours = new LinkedList<>();
            for (int k = 0; k < 4; k++) {
                int[] pos = neighPos[k];
                int x = current.x + pos[0];
                int y = current.y + pos[1];
                if (x < row && x >= 0 && y < col && y >= 0) {
                    if (visited[x][y]) {
                        continue;
                    }
                    neighbours.add(new Pair(x, y));
                }
            }

            if (neighbours.size() == 0) {
                continue;
            }

            stack.push(current);
            maze[current.x][current.y] = 0;

            int randomNeighbourIdx = random.nextInt(neighbours.size());
            Pair randomNeighbour = neighbours.get(randomNeighbourIdx);
            stack.push(randomNeighbour);

            // additional empty spaces for walls
            int randomNeighbourIdx1 = random.nextInt(neighbours.size());
            Pair randomNeighbour1 = neighbours.get(randomNeighbourIdx1);
            stack.push(randomNeighbour1);

            Pair wall1 = new Pair((randomNeighbour1.x + current.x) / 2, (randomNeighbour1.y + current.y) / 2);
            visited[wall1.x][wall1.y] = true;
            visited[randomNeighbour1.x][randomNeighbour1.y] = true;
            maze[wall1.x][wall1.y] = 0;

            Pair wall = new Pair((randomNeighbour.x + current.x) / 2, (randomNeighbour.y + current.y) / 2);
            visited[wall.x][wall.y] = true;
            visited[randomNeighbour.x][randomNeighbour.y] = true;

            maze[randomNeighbour.x][randomNeighbour.y] = 0;
            maze[wall.x][wall.y] = 0;
        }

        checkEvenDimensions(row, col, isRowEven, isColEven, random);
    }

    private void checkEvenDimensions(int row, int col, boolean isRowEven, boolean isColEven, Random random) {
        if (isColEven && isRowEven) {
            int[][] newMaze = new int[row + 1][col + 1];

            for (int i = 0; i < height + 1; i++) {
                for (int j = 0; j < width + 1; j++) {
                    if (i == height || j == width) {
                        newMaze[i][j] = random.nextInt(2);
                        continue;
                    }
                    newMaze[i][j] = maze[i][j];
                }
            }

            newMaze[row][col] = 0;
            newMaze[row][col - 1] = 0;
            newMaze[row - 1][col - 1] = 0;
            newMaze[row - 1][col] = 0;
            width += 1;
            height += 1;
            this.maze = newMaze;
        } else if (isColEven) {
            int[][] newMaze = new int[row][col + 1];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width + 1; j++) {
                    if (j == width) {
                        newMaze[i][j] = random.nextInt(2);
                        continue;
                    }
                    newMaze[i][j] = maze[i][j];
                }
            }

            newMaze[row - 1][col] = 0;
            width += 1;
            this.maze = newMaze;
        } else if (isRowEven) {
            int[][] newMaze = new int[row + 1][col];

            for (int i = 0; i < height + 1; i++) {
                for (int j = 0; j < width; j++) {
                    if (i == height) {
                        newMaze[i][j] = random.nextInt(2);
                        continue;
                    }
                    newMaze[i][j] = maze[i][j];
                }
            }

            newMaze[row][col - 1] = 0;
            height += 1;
            this.maze = newMaze;

        }
    }

    public int solveDFS() {
        int[][] neighbors = {
                {-1, 0}, {+1, 0}, {0, -1}, {0, +1}
        };

        boolean[][] visited = new boolean[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                visited[row][col] = false;
            }
        }

        Deque<Pair> deque = new LinkedList<>();
        deque.push(new Pair(0, 0));

        while (true) {
            Pair current = deque.peekLast();
            assert current != null;
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

        if(verbose)
            System.out.println("results:");

        int pathLength = 0;
        while (!deque.isEmpty()) {
            Pair node = deque.pollFirst();
            maze[node.x][node.y] = 2;

            if(verbose)
               System.out.printf("(%d, %d) ", node.x, node.y);

            pathLength++;
        }
        if (verbose)
            System.out.println();

        return pathLength;
    }

    public int solveBFS() {
        int[][] neighbors = {
                {-1, 0}, {+1, 0}, {0, -1}, {0, +1}
        };

        boolean[][] visited = new boolean[height][width];
        int[][] weight = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
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

        while (n < m) {
            Pair current = new Pair(nextMoves[n][0], nextMoves[n][1]);

            if (current.x == height - 1 && current.y == width - 1) {
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nRow = neighbors[i][0] + current.x, nCol = neighbors[i][1] + current.y;
                if (nRow >= 0 && nRow < height && nCol >= 0 && nCol < width && !visited[nRow][nCol] && maze[nRow][nCol] == 0) {
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

        if (n >= m) {
            // path does not exist
            if(verbose)
                System.out.println("No solution");

            return -1;
        }

        // Reconstruct the path
        LinkedList<Pair> outputList = new LinkedList<>();
        {
            Pair node = new Pair(nextMoves[n][0], nextMoves[n][1]);
            int previousNodeIdx = nextMoves[n][2];
            while (previousNodeIdx != -1) {
                outputList.add(new Pair(node.x, node.y));
                node = new Pair(nextMoves[previousNodeIdx][0], nextMoves[previousNodeIdx][1]);
                previousNodeIdx = nextMoves[previousNodeIdx][2];
            }
            outputList.add(new Pair(0, 0));
        }

        // Output in reverse order
        if(verbose)
            System.out.println("results:");

        int pathLength = 0;
        while (!outputList.isEmpty()) {
            Pair node = outputList.pollLast();
            maze[node.x][node.y] = 2;

            if(verbose)
                System.out.printf("(%d,%d) ", node.x, node.y);

            pathLength++;
        }
        if (verbose)
            System.out.println();
        return pathLength;
    }

    //    public LinkedList<Pair> solveAStar() {
    public int solveAStar() {
        LinkedList<Pair> path = new LinkedList<>();

        boolean isEndReached = false;
        Pair start = new Pair(0, 0);
        Pair end = new Pair(height - 1, width - 1);

        // A set where nodes are being processed
        PriorityQueue<Tuple> openSet = new PriorityQueue<>(new TupleComparator());
        openSet.add(new Tuple(start, h(start, end)));

        // A set which holds already seen nodes
        boolean[][] closedSet = new boolean[height][width];

        // An array to reconstruct path. Indices(i, j) show the position of the next node, but the value
        // is the previous node
        // For example, start(0, 0) is the next node, but the previous node is (-1, -1), i.e. there is no previous node
        Pair[][] cameFrom = new Pair[height][width];
        cameFrom[0][0] = new Pair(-1, -1);

        // An array to hold the g values of nodes
        double[][] gScore = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gScore[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        gScore[0][0] = 0.0;

        int[][] directions = {
                // South, North, East, West
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}
        };

        while (!openSet.isEmpty()) {
            Tuple currentTuple = openSet.poll(); // fetching the node with the least f value
            Pair current = currentTuple.pair;
            closedSet[current.x][current.y] = true;

            if (current.equals(end)) {
                path = reconstructPath(cameFrom, current);
                isEndReached = true;
                break;
            }

            for (int[] direction : directions) {
                int x = current.x + direction[0];
                int y = current.y + direction[1];
                if (x < 0 || y < 0 || x > height - 1 || y > width - 1 || maze[x][y] == 1 || closedSet[x][y]) {
                    continue;
                }
                Pair neighbor = new Pair(x, y);

                // distance to the neighbor node from the current node, it is always 1, since there are no diagonal neighbors
                int d = 1;

                double gCurrent = gScore[current.x][current.y];
                double tentativegScore = gCurrent + d;

                if (tentativegScore < gScore[neighbor.x][neighbor.y]) {
//                    maze[neighbor.x][neighbor.y] = 2;
//                    System.out.println();
//                    prettyPrint();

                    // f(n) = g(n) + h(n)
                    // empirically found that value about sqrt(2) is optimal,
                    // so that A* could faster find appropriate ending node
                    double f = tentativegScore + h(neighbor, end) * 1.41;
//                    double f = h(neighbor, end); // it will be best-first search
                    Tuple neighborTuple = new Tuple(neighbor, f);

                    cameFrom[neighbor.x][neighbor.y] = current;

                    gScore[neighbor.x][neighbor.y] = tentativegScore;

                    openSet.add(neighborTuple);
                }
            }
        }

        if (!isEndReached) {
            path.addFirst(new Pair(-1, -1));
            if(verbose)
                System.out.println("No solution!");
        }

        return path.size();
    }

    public void prettyPrint() {
        if(!verbose)
            return;

        for (int i = 0; i < height + 2; i++) {
            for (int j = 0; j < width + 1; j++) {
                if (i == 0 || i == height + 1) {
                    System.out.print("#");
                }
                if (i > 0 && i < height + 1 && j > 0 && j < width + 1) {
                    switch (maze[i - 1][j - 1]) {
                        case 0 -> System.out.print(" ");
                        case 1 -> System.out.print("#");
                        case 2 -> System.out.print("\033[1;31m" + "*" + "\033[0m"); // red color for found path
                    }
                }
                if (j == 0 || j == width && i != 0 && i != height + 1) {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }

    public void print() {
        if(!verbose)
            return;

        for (int i = 0; i < height + 2; i++) {
            for (int j = 0; j < width + 1; j++) {
                if (i > 0 && i < height + 1 && j > 0 && j < width + 1) {
                    switch (maze[i - 1][j - 1]) {
                        case 0 -> System.out.print("0");
                        case 1 -> System.out.print("1");
                    }
                }
            }
            System.out.println();
        }
    }


    // heuristic function for A* using Euclidean distance
    private double h(Pair currentNode, Pair endNode) {
        Pair vector = new Pair(endNode.x - currentNode.x, endNode.y - currentNode.y);
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
    }

    private LinkedList<Pair> reconstructPath(Pair[][] cameFrom, Pair current) {
        LinkedList<Pair> path = new LinkedList<>();

        path.addFirst(current);

        maze[current.x][current.y] = 2;

        int i = current.x;
        int j = current.y;

        Pair startingNode = new Pair(-1, -1);
        while (!cameFrom[i][j].equals(startingNode)) {
            current = cameFrom[i][j];
            i = current.x;
            j = current.y;


            path.addFirst(current);

            maze[current.x][current.y] = 2;
        }

        while (!path.isEmpty()) {
            Pair element = path.pollFirst();
            if(verbose)
                System.out.printf("(%d, %d) ", element.x, element.y);
        }

        if (verbose)
            System.out.println();
        return path;
    }
}
