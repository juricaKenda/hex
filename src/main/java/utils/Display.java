package utils;

public class Display {
    private static String head = "/ \\ ";
    private static String body = "|";
    private static String tail = "\\ / ";
    private static String edge = "\\ ";

    private static char dot = '●';

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void show(char[][] grid) {
        int N = grid.length;

        for (int i = 0; i < N; i++){
            if (i == 0){
                System.out.print("  ");
            }
            if (i < 9){
                System.out.print("  " + (i+1) + " ");
            } else {
                System.out.print(" " + (i+1) + " ");
            }
        }

        System.out.println();
        System.out.println(red("   " + head.repeat(N)));
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                String dot = getCellColor(grid,row, col);

                if (col == 0){
                    if (row >= 9){
                        System.out.printf((row+1)+blue(body)+" %s ", dot);
                    } else {
                        System.out.printf(" "+(row+1)+blue(body)+" %s ", dot);
                    }
                    continue;
                }
                System.out.printf("| %s ", dot);
                if (col == N-1){
                    System.out.print(blue(body));
                }
            }
            String spacing = "\n" + " ".repeat(2 * row + 1);
            if (row == N-1){
                System.out.print(red(spacing + "  " + tail.repeat(N)));
            }else {
                System.out.print(spacing + "  " + blue(edge) + head.repeat(N-1) + "/ " +  blue(edge));
            }

            System.out.print(spacing + " ");
        }
        System.out.println();
        System.out.println("-".repeat(50));
    }

    private static String getCellColor(char[][] chars, int row, int col) {
        switch (chars[row][col]){
            case 'b':
                return blue(dot+"");
            case 'r':
                return red(dot+"");
            default:
                return " ";
        }
    }


    private static String red(String text){
        return ANSI_RED + text + ANSI_RESET;
    }

    private static String blue(String text){
        return ANSI_BLUE + text + ANSI_RESET;
    }
}
