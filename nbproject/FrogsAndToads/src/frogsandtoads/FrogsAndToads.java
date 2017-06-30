package frogsandtoads;

/**
 * Plays the game of Frogs and Toads which is illustrated here:
 * http://www.cut-the-knot.org/SimpleGames/FrogsAndToads2d.shtml
 *
 * @author Sadan Mallhi
 * @version Mar 3, 2014
 *
 * I certify that I wrote all of the code in this file myself.
 */
public class FrogsAndToads{

    private final char[][] grid;
    private final int ROWS;
    private final int COLS;
    private int moveCount;

    /**
     * Creates the default game configuration
     *
     * @param x Number of Rows
     * @param y Number of Columns
     */
    public FrogsAndToads(int x, int y) {
        this.grid = new char[x][y];
        this.ROWS = x;
        this.COLS = y;

        initilizeArray(ROWS, COLS);
        moveCount = 0;

    }

    public int getROWS() {
        return ROWS;
    }

    public int getCOLS() {
        return COLS;
    }

    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Represents the game into a String
     *
     * @return the interface to play the game
     */
    @Override
    public String toString() {
        String s = "    "; // 4 spaces

        // column numbers
        for (int i = 0; i < COLS; i++) {
            s += i + "  "; // 2 spaces

        }
        s += "\n";

        for (int i = 0; i < ROWS; i++) {
            s += " " + i + " "; // row number
            for (int j = 0; j < COLS; j++) {
                if (grid[i][j] == 'F') {
                    s += " F ";
                }
                if (grid[i][j] == 'T') {
                    s += " T ";

                }
                if (grid[i][j] == '\0') {
                    s += "   ";
                }
            }
            s += "\n";
        }
        return s;
    }

    /**
     * Makes a legal move at a given point in the array.
     *
     * @param x Rows
     * @param y Column
     */
    public final void move(int x, int y) {
        if (grid[x][y] == 'F') {  // If the given cell is a frog
            // horizonal movement
            if (x != grid[x].length - 1) {
                if (grid[x + 1][y] != 'F') { // 2 spaces movement
                    if (x < (grid[x].length - 1) - 1) {
                        if (grid[x + 2][y] == '\0') {
                            grid[x + 2][y] = 'F';
                            grid[x][y] = '\0';
                            moveCount++;
                        }
                    }

                    if (x < (grid[x].length - 1)) { // 1 spaces movement
                        if (grid[x + 1][y] == '\0') {
                            grid[x + 1][y] = 'F';
                            grid[x][y] = '\0';
                            moveCount++;
                        }
                    }
                }
            }
            // vertical movement
            if (y != grid[y].length - 1) {
                if (grid[x][y + 1] != 'F') { // 2 spaces movement
                    if (y < (grid[y].length - 1) - 1) {
                        if (grid[x][y + 2] == '\0') {
                            grid[x][y + 2] = 'F';
                            grid[x][y] = '\0';
                            moveCount++;
                        }
                    }

                    if (y < (grid[y].length - 1)) { // 1 spaces movement
                        if (grid[x][y + 1] == '\0') {
                            grid[x][y + 1] = 'F';
                            grid[x][y] = '\0';
                            moveCount++;
                        }
                    }
                }
            } else {
                return;
            }

        }
        if (grid[x][y] == 'T') { // If the given cell is a toad
            // horizonal movement
            if (x != 0) {
                if (grid[x - 1][y] != 'T') { // 2 spaces movement
                    if (x > 1) {
                        if (grid[x - 2][y] == '\0') {
                            grid[x - 2][y] = 'T';
                            grid[x][y] = '\0';
                            moveCount++;
                        }
                    }

                    if (x > 0) { // 1 spaces movement
                        if (grid[x - 1][y] == '\0') {
                            grid[x - 1][y] = 'T';
                            grid[x][y] = '\0';
                            moveCount++;
                        }
                    }
                }
            }
            // vertical movement
            if (y != 0) {
                if (grid[x][y - 1] != 'T') {  // 2 spaces movement
                    if (y > 1) {
                        if (grid[x][y - 2] == '\0') {
                            grid[x][y - 2] = 'T';
                            grid[x][y] = '\0';
                            moveCount++;
                        }
                    }

                    if (y > 0) { // 1 spaces movement
                        if (grid[x][y - 1] == '\0') {
                            grid[x][y - 1] = 'T';
                            grid[x][y] = '\0';
                            moveCount++;
                        }
                    }
                }
            }
        } else {
            return;
        }

    }

    /**
     * Checks if either a Toad or a Frog can move or not. If not they are stuck.
     *
     * @return true if move possible
     */
    public boolean canMove() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (grid[i][j] == 'F' && frogCanMove(i, j)) {
                    return true;
                }

                if (grid[i][j] == 'T' && toadCanMove(i, j)) {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * Checks if the game has been won or not.
     *
     * @return true if game has been won.
     */
    public boolean win() {
        int numberOfToads = 0;
        numberOfToads = ((ROWS) * (COLS)) / 2;
        int count = 0;

        // if all the toads have replaces the frogs the player wins
        
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < ROWS; i++) {
                count++;
                if (count <= numberOfToads) {
                    if (grid[i][j] != 'T') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Utility Method that initializes the array into the default form.
     *
     * @param ROWS
     * @param COLS
     */
    private void initilizeArray(int ROWS, int COLS) {
        
        int numberOfToads = 0;
        int numberOfFrogs = 0;
        numberOfToads = (ROWS * COLS) / 2;
        // using an even number of rows or cols will cause the number 
        // of toads and frog to not be the same
        if ((ROWS * COLS) % 2 == 0) { 
            numberOfFrogs = numberOfToads - 1;
        } else {
            numberOfFrogs = numberOfToads;
        }
        int count = 0;

        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < ROWS; i++) {
                count++;
                if (count <= numberOfFrogs) {
                    grid[i][j] = 'F';
                }
            }
        }
        count = 0;
        for (int j = COLS - 1; j >= 0; j--) {
            for (int i = ROWS - 1; i >= 0; i--) {
                count++;
                if (count <= numberOfToads) {
                    grid[i][j] = 'T';
                }
            }
        }

    }

    /**
     * Places the array into the default configuration in one instance of a
     * game. All while making sure the right cell is empty.
     */
    public void reset() {
        initilizeArray(ROWS, COLS);
        moveCount = 0;

        int numberOfToads;
        int numberOfFrogs;
        numberOfToads = (ROWS * COLS) / 2;
        if ((ROWS * COLS) % 2 == 0) {
            numberOfFrogs = numberOfToads - 1;
        } else {
            numberOfFrogs = numberOfToads;
        }
        int count = 0;

        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < ROWS; i++) {
                count++;
                if (count == numberOfFrogs + 1) {
                    grid[i][j] = '\0';
                }
            }
        }

    }

    /**
     * Checks if the Frog pieces are able to move.
     *
     * @param x rows
     * @param y cols
     * @return true if frogs can move.
     */
    private boolean frogCanMove(int x, int y) {
        if (x < (grid[x].length - 1) - 1) {
            if ((grid[x + 2][y]) == '\0') {
                return true;
            }
        }
        if ((y < (grid[y].length - 1) - 1)) {
            if ((grid[x][y + 2]) == '\0') {
                return true;
            }
        }

        if (x < (grid[x].length - 1)) {
            if (grid[x + 1][y] == '\0') {
                return true;
            }
        }
        if ((y < (grid[y].length - 1))) {
            if (grid[x][y + 1] == '\0') {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the Toad pieces can make a move.
     *
     * @param x rows
     * @param y cols
     * @return true if toads can make a move.
     */
    private boolean toadCanMove(int x, int y) {
        if (x > 1) {
            if ((grid[x - 2][y]) == '\0') {
                return true;
            }
        }
        if (y > 1) {
            if ((grid[x][y - 2]) == '\0') {
                return true;
            }
        }

        if (x > 0) {
            if ((grid[x - 1][y]) == '\0') {
                return true;
            }
        }
        if (y > 0) {
            if ((grid[x][y - 1]) == '\0') {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given cell is a Toad
     *
     * @param x rows
     * @param y cols
     * @return true if it is a toad.
     */
    public boolean isToad(int x, int y) {
        return grid[x][y] == 'T';
    }

    /**
     * Checks if a given cell is a Frog
     *
     * @param x rows
     * @param y cols
     * @return true if it is a frog
     */
    public boolean isFrog(int x, int y) {
        return grid[x][y] == 'F';
    }
}
