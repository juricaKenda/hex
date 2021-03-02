package players;

import java.util.Scanner;

public class ConsolePlayer implements Player{
    private Scanner scanner;

    public ConsolePlayer(){
        scanner = new Scanner(System.in);
    }

    @Override
    public void makeMove(char[][] grid, char tag) {
        System.out.print("Make move (row col):");
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        if (isWithinBounds(grid.length,row,col) && isEmpty(grid,row,col)){
            grid[row-1][col-1] = tag;
            return;
        }

        makeMove(grid,tag);
    }

    private boolean isWithinBounds(int N, int row, int col) {
        return row <= N && col <= N;
    }

    private boolean isEmpty(char[][] grid, int row, int col) {
        return grid[row-1][col-1] == ' ';
    }

}
