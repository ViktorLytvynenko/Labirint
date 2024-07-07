import java.util.*;

public class Labirint {
    private static final char forwardIcon = '↑';
    private static final char leftIcon = '←';
    private static final char rightIcon = '→';
    private static final char downIcon = '↓';
    private static int playerCoordsX;
    private static int playerCoordsY;
    private static char playerIcon;
    private static char[][] board;
    private static int countSteps = 0;
    private static int gold = 0;

    public Labirint() {
    }

    public static void game() {
        Scanner in = new Scanner(System.in);
        System.out.println("Game starts!");
        System.out.println("$ - gold");
        System.out.println("X - wall");
        System.out.println("1,2,3 - barriers");
        System.out.println("Choose your level");
        System.out.println("Easy or Hard");
        String level = "";

        do {
            level = in.nextLine().toLowerCase();
        }
        while (!level.equals("easy") && !level.equals("hard"));


        board = createBoard(level);
        printBoard(board);

        while (true) {
            String move = "";

            do {
                System.out.println("Make your move turn left(l), turn right(r), forward(f)");
                move = in.nextLine().toLowerCase();
            } while (!move.equals("l") && !move.equals("r") && !move.equals("f"));
            userMove(move);
            printBoard(board);
        }
    }

    public static char[][] createBoard(String level) {
        char[][] field;
        if (level.equals("easy")) {
            field = new char[10][10];
        } else {
            field = new char[15][15];
        }
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = ' ';
            }
        }
        return createItems(field, level);
    }

    public static char[][] createItems(char[][] field, String level) {
        Random random = new Random();
        int x;
        int y;
        int treasures;
        int walls;
        int barrierType1;
        int barrierType2;
        int barrierType3;
        int min;
        int max;
        char player;

        if (level.equals("easy")) {
            treasures = 4;
            walls = 25;
            barrierType1 = 4;
            barrierType2 = 4;
            barrierType3 = 4;
            min = 0;
            max = 10;
            player = 1;

        } else {
            treasures = 8;
            walls = 50;
            barrierType1 = 8;
            barrierType2 = 8;
            barrierType3 = 8;
            min = 0;
            max = 15;
            player = 1;
        }

        while (treasures != 0) {
            x = random.nextInt(min, max);
            y = random.nextInt(min, max);
            if (field[y][x] != ' ') continue;
            field[y][x] = '$';
            treasures--;
        }

        while (walls != 0) {
            x = random.nextInt(min, max);
            y = random.nextInt(min, max);
            if (field[y][x] != ' ') continue;
            field[y][x] = 'X';
            walls--;
        }

        while (barrierType1 != 0) {
            x = random.nextInt(min, max);
            y = random.nextInt(min, max);
            if (field[y][x] != ' ') continue;
            field[y][x] = '1';
            barrierType1--;
        }

        while (barrierType2 != 0) {
            x = random.nextInt(min, max);
            y = random.nextInt(min, max);
            if (field[y][x] != ' ') continue;
            field[y][x] = '2';
            barrierType2--;
        }

        while (barrierType3 != 0) {
            x = random.nextInt(min, max);
            y = random.nextInt(min, max);
            if (field[y][x] != ' ') continue;
            field[y][x] = '3';
            barrierType3--;
        }

        while (player != 0) {
            x = random.nextInt(min, max);
            y = field.length - 1;
            playerCoordsX = x;
            playerCoordsY = y;
            if (field[y][x] != ' ') continue;
            playerIcon = forwardIcon;
            field[y][x] = playerIcon;
            player--;
        }


        return field;
    }

    public static void printBoard(char[][] field) {
        for (char[] chars : field) {
            System.out.println(Arrays.toString(chars));
        }
        System.out.println("steps: " + countSteps);
        System.out.println("gold: " + gold);
        System.out.println("coords: " + (playerCoordsX + 1) + "x | y" + (playerCoordsY + 1));
    }

    public static void rotateLeft() {

        switch (playerIcon) {
            case '↑', '↓':
                playerIcon = leftIcon;
                board[playerCoordsY][playerCoordsX] = playerIcon;
                break;
            case '←':
                playerIcon = downIcon;
                board[playerCoordsY][playerCoordsX] = playerIcon;
                break;
            case '→':
                playerIcon = forwardIcon;
                board[playerCoordsY][playerCoordsX] = playerIcon;
                break;
        }
    }

    public static void rotateRight() {

        switch (playerIcon) {

            case '↑', '↓':
                playerIcon = rightIcon;
                board[playerCoordsY][playerCoordsX] = playerIcon;
                break;
            case '←':
                playerIcon = forwardIcon;
                board[playerCoordsY][playerCoordsX] = playerIcon;
                break;
            case '→':
                playerIcon = downIcon;
                board[playerCoordsY][playerCoordsX] = playerIcon;
                break;
        }

    }

    public static void step() {

        switch (playerIcon) {

            case '↑':
                if (playerCoordsY == 0) {
                    System.out.println("Illegal move");
                } else {
                    char nextCell = board[playerCoordsY - 1][playerCoordsX];
                    if (nextCell == 'X') {
                        System.out.println("wall");
                    } else if (nextCell > '1') {
                        board[playerCoordsY - 1][playerCoordsX] = (char) (nextCell - 1);
                    } else if (nextCell == '1') {
                        board[playerCoordsY - 1][playerCoordsX] = ' ';
                    } else {
                        if (nextCell == '$') gold++;
                        board[playerCoordsY][playerCoordsX] = ' ';
                        playerCoordsY -= 1;
                        board[playerCoordsY][playerCoordsX] = playerIcon;
                        countSteps++;
                    }
                }
                break;

            case '←':

                if (playerCoordsX == 0) {
                    System.out.println("Illegal move");
                } else {
                    char nextCell = board[playerCoordsY][playerCoordsX - 1];
                    if (nextCell == 'X') {
                        System.out.println("wall");
                    } else if (nextCell > '1') {
                        board[playerCoordsY][playerCoordsX - 1] = (char) (nextCell - 1);
                    } else if (nextCell == '1') {
                        board[playerCoordsY][playerCoordsX - 1] = ' ';
                    } else {
                        if (nextCell == '$') gold++;
                        board[playerCoordsY][playerCoordsX] = ' ';
                        playerCoordsX -= 1;
                        board[playerCoordsY][playerCoordsX] = playerIcon;
                        countSteps++;
                    }
                }
                break;

            case '→':
                if (playerCoordsX == 0) {
                    System.out.println("Illegal move");
                } else {
                    char nextCell = board[playerCoordsY][playerCoordsX + 1];
                    if (nextCell == 'X') {
                        System.out.println("wall");
                    } else if (nextCell > '1') {
                        board[playerCoordsY][playerCoordsX + 1] = (char) (nextCell + 1);
                    } else if (nextCell == '1') {
                        board[playerCoordsY][playerCoordsX + 1] = ' ';
                    } else {
                        if (nextCell == '$') gold++;
                        board[playerCoordsY][playerCoordsX] = ' ';
                        playerCoordsX += 1;
                        board[playerCoordsY][playerCoordsX] = playerIcon;
                        countSteps++;
                    }
                }
                break;

            case '↓':
                if (playerCoordsY == 0) {
                    System.out.println("Illegal move");
                } else {
                    char nextCell = board[playerCoordsY + 1][playerCoordsX];
                    if (nextCell == 'X') {
                        System.out.println("wall");
                    } else if (nextCell > '1') {
                        board[playerCoordsY - 1][playerCoordsX] = (char) (nextCell + 1);
                    } else if (nextCell == '1') {
                        board[playerCoordsY + 1][playerCoordsX] = ' ';
                    } else {
                        if (nextCell == '$') gold++;
                        board[playerCoordsY][playerCoordsX] = ' ';
                        playerCoordsY += 1;
                        board[playerCoordsY][playerCoordsX] = playerIcon;
                        countSteps++;
                    }
                }
                break;
        }
    }


    public static void userMove(String move) {
        if (move.equals("l")) rotateLeft();
        else if (move.equals("r")) rotateRight();
        else step();
    }
}
