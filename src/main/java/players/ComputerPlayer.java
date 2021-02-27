package players;

import engine.GridChecker;
import javafx.util.Pair;

public class ComputerPlayer implements Player{
    public static final double NONE = -Double.MAX_VALUE;

    @Override
    public void makeMove(char[][] grid, char tag) {
        char opponentTag = tag == 'r' ? 'b' : 'r';
        double[][] heuristics = new double[grid.length][grid.length];
        double[][] heuristicsOpponent = new double[grid.length][grid.length];

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                // this hex is occupied, neither me or the opponent can play this move
                if (grid[row][col] != ' ') {
                    heuristics[row][col] = Double.MAX_VALUE;
                    heuristicsOpponent[row][col] = Double.MAX_VALUE;
                    continue;
                }

                // simulation
                grid[row][col] = tag;
                double movesToWin = numberOfMovesToWin(grid,tag);
                heuristics[row][col] = movesToWin;
                if (movesToWin == NONE){
                    return;
                }

                double movesToWinOpponent = numberOfMovesToWin(grid,opponentTag);
                heuristicsOpponent[row][col] = movesToWinOpponent;

                grid[row][col] = ' ';
                // end simulation
            }
        }

        // choose and play the selected move
        Pair<Integer, Integer> moveToPlay = pickBestMove(heuristics, heuristicsOpponent);
        grid[moveToPlay.getKey()][moveToPlay.getValue()] = tag;
    }

    private double numberOfMovesToWin(char[][] grid, char tag) {
        if (GridChecker.gameDone(grid,tag)){
            return NONE;
        }
        return GridChecker.minMovesToWin(grid,tag);
    }

    private Pair<Integer, Integer> pickBestMove(double[][] heuristics, double[][] heuristicsOpponent) {
        int N = heuristics.length;
        double leastMovesToWin = Double.MAX_VALUE;
        double mostOpponentMoves = Double.MIN_VALUE;

        int bestX = 0,bestY = 0;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                double movesToWin = heuristics[row][col];
                double opponentMoves = heuristicsOpponent[row][col];

                if (leastMovesToWin > movesToWin || (leastMovesToWin == movesToWin && mostOpponentMoves < opponentMoves)){
                    leastMovesToWin = movesToWin;
                    mostOpponentMoves = opponentMoves;
                    bestX = row;
                    bestY = col;
                }
            }
        }
        return new Pair<>(bestX, bestY);
    }

}
