package dip107;

import java.util.*;

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

            for (int i = 0; i < 4; i++) { // Compute next frontiers
                int nRow = neighbors[i][0] + frontierCell.x, nCol = neighbors[i][1] + frontierCell.y;
                if (nRow >= 0 && nRow < row && nCol >= 0 && nCol < col && maze[nRow][nCol] == 1) {
                    frontierList.add(new Pair(nRow, nCol));
                    maze[nRow][nCol] = 2;
                }
            }

            frontierList.remove(randomFrontierIdx);
        }
    }

    public void createRDFS(int row, int col) {
        Random random = new Random();
        int seed = random.nextInt();
        System.out.println("Seed: " + seed);
        createRDFS(row, col, seed);
    }

    public void createRDFS(int row, int col, int seed) {
        createEmpty(row, col);
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
                if (x < row && x >= 0 && y < col && y >= 0 && maze[row - 1][col - 1] == 1) {
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

            Pair wall = new Pair((randomNeighbour.x + current.x) / 2, (randomNeighbour.y + current.y) / 2);
            visited[wall.x][wall.y] = true;
            visited[randomNeighbour.x][randomNeighbour.y] = true;

            maze[randomNeighbour.x][randomNeighbour.y] = 0;
            maze[wall.x][wall.y] = 0;
        }
    }

    public void solveDFS() {
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

        System.out.println("results:");
        while (!deque.isEmpty()) {
            Pair node = deque.pollFirst();
            maze[node.x][node.y] = 2;
            System.out.printf("(%d,%d) ", node.x, node.y);
        }
    }

    public void solveBFS() {
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
            System.out.println("ceļa nav?");
            return;
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
        System.out.println("results:");
        while (!outputList.isEmpty()) {
            Pair node = outputList.pollLast();
            maze[node.x][node.y] = 2;
            System.out.printf("(%d,%d) ", node.x, node.y);
        }
    }

    public LinkedList<Pair> solveAStar() {
        LinkedList<Pair> path = new LinkedList<>();
        boolean isEndReached = false;
        Pair start = new Pair(0, 0);
        Pair end = new Pair(height - 1, width - 1);

        HashSet<Pair> openSet = new HashSet<>();
        openSet.add(start);

        HashMap<Pair, Pair> cameFrom = new HashMap<>();

        HashMap<Pair, Double> gScore = new HashMap<>();
        fillHashmap(gScore);
        gScore.replace(start, 0.0);

        HashMap<Pair, Double> fScore = new HashMap<>();
        fillHashmap(fScore);
        fScore.replace(start, h(start, end));

        int[][] neighborPos = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}
        };

        while (!openSet.isEmpty()) {
            Pair current = findLowestfScoreNode(openSet, fScore);

            if (current.equals(end)) {
                path = reconstructPath(cameFrom, current);
                isEndReached = true;
                break;
            }
            openSet.remove(current);

            for (int[] neighbors : neighborPos) {
                int x = current.x + neighbors[0];
                int y = current.y + neighbors[1];
                if (x < 0 || y < 0 || x > height - 1 || y > width - 1 || maze[x][y] == 1) {
                    continue;
                }

                Pair neighbor = new Pair(x, y);
                int d = Math.abs(current.x - neighbor.x) + Math.abs(current.y - neighbor.y);
                double tentativegScore = gScore.get(current) + d;
                if (tentativegScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.replace(neighbor, tentativegScore);
                    fScore.replace(neighbor, gScore.get(neighbor) + h(neighbor, end));
                    openSet.add(neighbor);
                }
            }
        }

        if (!isEndReached) {
            path.addFirst(new Pair(-1, -1));
            System.out.println("No solution!");
            return path;
        }
        return path;
    }


    public void prettyPrint() {
        for (int i = 0; i < height + 2; i++) {
            for (int j = 0; j < width + 1; j++) {
                if (i == 0 || i == height + 1) {
                    System.out.print("#");
                }
                if (i > 0 && i < height + 1 && j > 0 && j < width + 1) {
                    switch (maze[i - 1][j - 1]) {
                        case 0 -> System.out.print(" ");
                        case 1 -> System.out.print("#");
                        case 2 -> System.out.print("\033[1;31m" + "*" + "\033[0m");
                    }
                }
                if (j == 0 || j == width && i != 0 && i != height + 1) {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }

    private void fillHashmap(HashMap<Pair, Double> map) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map.put(new Pair(i, j), Double.POSITIVE_INFINITY);
            }
        }
    }

    // heuristic function for A* using Euclidean distance
    private double h(Pair currentNode, Pair endCell) {
        Pair vector = new Pair(endCell.x - currentNode.x, endCell.y - currentNode.y);
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
    }

    private Pair findLowestfScoreNode(HashSet<Pair> set, HashMap<Pair, Double> hashMap) {
        Pair minKey = null;
        double minValue = Double.MAX_VALUE;
        for (Pair node : set) {
            double value = hashMap.get(node);
            if (minValue > value) {
                minValue = value;
                minKey = node;
            }
        }
        return minKey;
    }

    private LinkedList<Pair> reconstructPath(HashMap<Pair, Pair> cameFrom, Pair current) {
        LinkedList<Pair> totalPath = new LinkedList<>();
        totalPath.addFirst(current);
        maze[current.x][current.y] = 2;
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.addFirst(current);
            maze[current.x][current.y] = 2;
        }
        return totalPath;
    }
}
