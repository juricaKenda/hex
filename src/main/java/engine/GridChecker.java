package engine;

import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class GridChecker {

    public static boolean gameDone(char[][] grid, char tag) {
        // for RED -> check top down
        // for BLUE -> check left to right
        int N = grid.length;
        List<Pair<Integer,Integer>> starters = tag == 'r' ? firstRow(N) : firstColumn(N);

        for (Pair<Integer, Integer> starter : starters) {
            int row = starter.getKey();
            int col = starter.getValue();
            if (grid[row][col] == tag){
                if (DFS(tag,row,col,N, new ArrayList<>(), grid)){
                    return true;
                }
            }
        }
        return false;
    }

    private static List<Pair<Integer,Integer>> firstRow(int N){
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        int row = 0;
        for (int col = 0; col < N; col++) {
            result.add(new Pair<>(row,col));
        }
        return result;
    }

    private static List<Pair<Integer,Integer>> firstColumn(int N){
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        int col = 0;
        for (int row = 0; row < N; row++) {
            result.add(new Pair<>(row,col));
        }
        return result;
    }

    public static int minMovesToWin(char[][]grid, char tag){
        int N = grid.length;
        int minMovesToWin = Integer.MAX_VALUE;

        char opponentTag = tag == 'r' ? 'b' : 'r';
        List<Pair<Integer,Integer>> starters = tag == 'r' ? firstRow(N) : firstColumn(N);

        for (Pair<Integer, Integer> starter : starters) {
            int row = starter.getKey();
            int col = starter.getValue();
            char hex = grid[row][col];

            Queue<PairWithDist> queue = new LinkedBlockingQueue<>();
            if (hex == opponentTag){
                continue;
            }
            if (hex == tag){
                queue.add(new PairWithDist(row,col,0));
            }
            if (hex == ' '){
                queue.add(new PairWithDist(row,col,1));
            }

            int movesToWin = BFS(grid, tag, N, queue);
            if (minMovesToWin > movesToWin){
                minMovesToWin = movesToWin;
            }
        }

        return minMovesToWin;
    }

    private static int BFS(char[][] grid, char tag, int N, Queue<PairWithDist> queue) {
        int minimalDistance = Integer.MAX_VALUE;
        HashSet<Pair> visited = new HashSet<>();

        while (!queue.isEmpty()){
            PairWithDist current = queue.remove();

            Pair<Integer, Integer> hex = current.pair;
            int currentDistance = current.distance;
            Integer row = hex.getKey();
            Integer column = hex.getValue();

            if ((tag == 'r' && isEnd(row, N)) || (tag == 'b' && isEnd(column, N))){
                if (currentDistance < minimalDistance){
                    minimalDistance = currentDistance;
                }
            }

            List<Pair<Integer, Integer>> neighbours = getNeighbours(row, column, N);
            visited.add(hex);
            for (Pair<Integer, Integer> neighbour : neighbours) {
                Integer neighbourX = neighbour.getKey();
                Integer neighbourY = neighbour.getValue();

                if (!visited.contains(neighbour)) {
                    char valueNeighbour = grid[neighbourX][neighbourY];
                    if (valueNeighbour == ' ') {
                        queue.add(new PairWithDist(neighbourX, neighbourY, currentDistance + 1));
                    }
                    if (valueNeighbour == tag) {
                        queue.add(new PairWithDist(neighbourX, neighbourY, currentDistance));
                    }
                }
            }
        }

        return minimalDistance;
    }

    private static boolean isEnd(int var, int N) {
        return var == N-1 ;
    }

    static class PairWithDist{
        Pair<Integer,Integer> pair;
        int distance;
        PairWithDist(int x, int y, int dist){
            pair = new Pair<>(x,y);
            distance = dist;
        }
    }


    private static boolean DFS(char tag, int x, int y, int N, List<Pair<Integer, Integer>> visited, char[][] grid) {
        if(grid[x][y] != tag){
            return false;
        }

        if ((tag == 'r' && x == N-1) || (tag == 'b' && y == N-1)){
            return true;
        }


        for (Pair<Integer, Integer> neighbour : getNeighbours(x,y,N)) {
            if (!visited.contains(neighbour)) {
                visited.add(neighbour);
                boolean reachedEnd = DFS(tag, neighbour.getKey(), neighbour.getValue(), N, visited, grid);
                if (reachedEnd) {
                    return true;
                }
            }
        }
        return false;
    }


    public static List<Pair<Integer, Integer>> getNeighbours(int x, int y, int N) {
        // generate all possible neighbours
        List<Pair<Integer,Integer>> neighbours = Arrays.asList(
                new Pair<>(x - 1, y), new Pair<>(x - 1, y + 1),
                new Pair<>(x, y - 1), new Pair<>(x, y + 1),
                new Pair<>(x + 1, y - 1), new Pair<>(x + 1, y)
        );

        // keep only the neighbours within the grid
        List<Pair<Integer,Integer>> valid = new ArrayList<>();
        for (Pair<Integer,Integer> neighbour : neighbours) {
            Integer neighbourX = neighbour.getKey();
            Integer neighbourY = neighbour.getValue();

            boolean xValid = neighbourX >= 0 && neighbourX < N;
            boolean yValid = neighbourY >= 0 && neighbourY < N;
            if (xValid && yValid){
                valid.add(neighbour);
            }
        }

        return valid;
    }

}
