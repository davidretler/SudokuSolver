package Sudoku.Solver;

public class SudokuTester {

    static int calls = 0;

    public static void main(String[] args) {
        
        int[][] initial = new int[][]
            {
                { 0, 0, 0, 0, 9, 0, 0, 0, 4 },
                { 4, 1, 0, 0, 0, 3, 0, 0, 0 },
                { 8, 0, 7, 6, 0, 4, 2, 1, 0 },
                { 0, 0, 1, 0, 0, 7, 0, 0, 2 },
                { 0, 6, 0, 0, 4, 0, 0, 9, 0 },
                { 2, 0, 0, 5, 0, 0, 7, 0, 0 },
                { 0, 4, 8, 3, 0, 6, 9, 0, 7 },
                { 0, 0, 0, 4, 0, 0, 0, 2, 1 },
                { 6, 0, 0, 0, 1, 0, 0, 0, 0 } };

        SudokuBoard myBoard = new SudokuBoard(initial);

        System.out.println(myBoard);
        try {
            if (solve(myBoard)) {
                System.out.println("\nSolved!\n" + myBoard);
            } else {
                System.out.println("Could not solve board...\n" + myBoard);
            }
        } catch (StackOverflowError e) {
            System.out.println("\nOveflow while solving. Partial output:\n" + myBoard);
        }

        System.out.println(calls + " recursive calls");
        
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

        calls++;

        if (!board.check())
            return false;

        if (board.solved()) {
            return true;
        }

        // make sure to save state before making moves
        int[][] oldState = board.getState();

        makeDefiniteMoves(board);

        if (board.getNum(i, j) == 0) {
            // try each number for current configuration, if not already solved
            for (int n = 1; n <= 9; n++) {
                if (board.set(i, j, n)) {
                    // if we can set the current position to, do so and solve
                    // rest
                    // of board
                    // next i value is either i+1 or 1 if i is already 9
                    int nextI = i < 8 ? i + 1 : 1;
                    int nextJ = j;
                    if (nextI < i) {
                        // only change the j if i has moved back to 1
                        nextJ = j < 8 ? j + 1 : 1;

                    }
                    if (solve(board, nextI, nextJ)) {
                        // if rest of board can be solved from here, backtrack
                        return true;
                    } else {
                        // rest of board cannot be solved so undo the current
                        // change, and restart loop
                        board.unSet(i, j);
                    }
                }
            }
        } else {
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

        // restore state and then return (backtrack)
        board.setState(oldState);
        return false;
    }

    /**
     * Makes moves to the board that we can be certain must be made
     */
    private static void makeDefiniteMoves(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getNum(i, j) == 0) {
                    board.definiteMove(i, j);
                }
            }
        }
    }

}
