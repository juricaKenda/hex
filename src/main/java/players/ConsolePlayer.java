package players;

import java.util.Scanner;

public class ConsolePlayer implements Player{
    private Scanner scanner;

    public ConsolePlayer(){
        scanner = new Scanner(System.in);
    }

    @Override
    public void makeMove(char[][] grid, char tag) {
        System.out.print("Make move (x y):");
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        if (isWithinBounds(grid,x,y) && isEmpty(grid,x,y)){
            grid[x-1][y-1] = tag;
            return;
        }

        makeMove(grid,tag);
    }

    private boolean isWithinBounds(char[][] grid, int x, int y) {
        return x <= grid.length && y <= grid[0].length;
    }

    private boolean isEmpty(char[][] grid, int x, int y) {
        return grid[x-1][y-1] == ' ';
    }

}
