package Sudoku.Solver;

public class SudokuTester {

    static int calls = 0;

    public static void main(String[] args) {

        long start, stop;

        int[][] initial = new int[][]
            {
                { 0, 1, 3, 0, 0, 8, 0, 2, 0 },
                { 6, 0, 0, 3, 0, 0, 0, 0, 9 },
                { 0, 9, 0, 7, 0, 0, 1, 0, 4 },
                { 9, 0, 0, 2, 0, 0, 5, 4, 3 },
                { 0, 8, 0, 4, 0, 1, 0, 0, 0 },
                { 4, 6, 2, 0, 0, 7, 0, 0, 1 },
                { 1, 0, 6, 0, 0, 3, 0, 9, 0 },
                { 8, 0, 0, 0, 0, 9, 3, 0, 6 },
                { 0, 3, 0, 6, 0, 0, 7, 0, 0 } };

        SudokuBoard myBoard = new SudokuBoard(/*initial*/);

        System.out.println(myBoard);

        start = System.nanoTime();
        try {
            if (solve(myBoard)) {
                System.out.println("\nSolved!\n" + myBoard);
            } else {
                System.out.println("Could not solve board...\n" + myBoard);
            }
        } catch (StackOverflowError e) {
            System.out.println("\nOveflow while solving. Partial output:\n" + myBoard);
        }
        stop = System.nanoTime();
        System.out.println(calls + " recursive calls\n" + (stop - start) / 1000000 + " ms");

        /*
         * makeDefiniteMoves(myBoard); System.out.println("\n" + myBoard);
         */
    }

    /**
     * Solves a sudoku board given initial configuration. Returns false if no solution
     * exists
     * 
     * @param board
     * @return
     */
    public static boolean solve(SudokuBoard board) {
        if (!board.check())
            return false;

        return solve(board, 0, 0);
    }

    /**
     * Recursively solves sudoku board using backtracking Starts at (i,j) Returns 0 if no
     * solution
     * 
     * @return
     */
    private static boolean solve(SudokuBoard board, int i, int j) {

        // keep track of the number of function calls
        calls++;

        if (!board.check())
            // base case: return false if current config is invalid
            return false;

        if (board.solved()) {
            // base case: return true if board is already solved
            return true;
        }

        // make sure to save state before making moves
        int[][] oldState = board.getState();

        // make all moves that certainly must be made
        makeDefiniteMoves(board);

        if (board.getNum(i, j) == 0) {
            // try each number for current configuration, if not already solved
            for (int n = 1; n <= 9; n++) {
                if (board.set(i, j, n)) {
                    // if we can set the current position to, do so and solve
                    // rest of board

                    // next i value is either i+1 or 1 if i is already 9
                    int nextI = i < 8 ? i + 1 : 1;
                    int nextJ = j;
                    if (nextI < i) {
                        // only change the j if i has moved back to 1
                        nextJ = j < 8 ? j + 1 : 1;

                    }
                    if (solve(board, nextI, nextJ)) {
                        // if rest of board can be solved from here, then this is a valid
                        // solution
                        return true;
                    } else {
                        // rest of board cannot be solved so undo the current
                        // change, and restart loop to try next possible value for this
                        // location
                        board.unSet(i, j);
                    }
                }
            }
        } else {
            // current position already has entry, so go to the next one and solve from
            // there
            int nextI = i < 8 ? i + 1 : 1;
            int nextJ = j;
            if (nextI < i) {
                // only change the j if i has moved back to 1
                nextJ = j < 8 ? j + 1 : 1;

            }
            if (solve(board, nextI, nextJ)) {
                // if rest of board can be solved from here, backtrack
                return true;
            }
        }

        // if we make it here than we could not solve the board given this configuration
        // so we need to restore the old state and then return (backtrack)
        board.setState(oldState);
        return false;
    }

    /**
     * Makes moves to the board that we can be certain must be made
     */
    private static void makeDefiniteMoves(SudokuBoard board) {

        boolean move = false;
        do {
            // keep looping until none of the entries can be decided
            move = false;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    // check each (i,j) to see if it hasn't been set yet
                    if (board.getNum(i, j) == 0) {
                        if (board.definiteMove(i, j)) {
                            // for unset (i,j), set the value if it can be decided
                            // keep track whether a value was changed
                            move = true;
                        }
                    }
                }
            }
        } while (move); // restart loop as long as we can make changes

    }

}
