# Sudoku Solved

## Introduction

This is just a simple Sudoku solver I wrote in Java. It uses recursive backtracking to make guesses for possible solutions, and restore previous state when a particular guess yields an ultimately invalid configuration. To (greatly) reduce the number of recursive calls, a naive algorithm is used to look for moves that definitely must be made given the current configuration. In particular, the algorithm looks for locations that can only be one particular number given the current restrictions (ie, an entry in a row with numbers 1..8 but not 9 would have to be 9 itself). Because this is not sufficent to solve a harder board, backtracking is needed to allow for guessing.

## How to Use

The program is written in Java. There are two classes `SudokuBoard` and `SudokuTester`. `SudokuBoard` represents a board, which can be instantiated based on a double array of integers (0 through 9) representing the initial state. 0 is used to represent that the state has not been decided. `SudokuTester` has a `public static` method called `solve(SudokuBoard)` which solves that board passed, returning `true` if and only if there exists a solution to the board.

For now, to solve a board you have to hard-code it into the tester class. Once you have done that, compile and execute with `SudokuTester` as your main class. When I have free time (probably winter break), I will allow for file input, build a GUI, or do both.

## Sample Output

    ———————  ———————  ———————
    | |1|3|  | | |8|  | |2| |
    ———————  ———————  ———————
    |6| | |  |3| | |  | | |9|
    ———————  ———————  ———————
    | |9| |  |7| | |  |1| |4|
    ———————  ———————  ———————

    ———————  ———————  ———————
    |9| | |  |2| | |  |5|4|3|
    ———————  ———————  ———————
    | |8| |  |4| |1|  | | | |
    ———————  ———————  ———————
    |4|6|2|  | | |7|  | | |1|
    ———————  ———————  ———————

    ———————  ———————  ———————
    |1| |6|  | | |3|  | |9| |
    ———————  ———————  ———————
    |8| | |  | | |9|  |3| |6|
    ———————  ———————  ———————
    | |3| |  |6| | |  |7| | |
    ———————  ———————  ———————



    Solved!
    ———————  ———————  ———————
    |7|1|3|  |9|4|8|  |6|2|5|
    ———————  ———————  ———————
    |6|2|4|  |3|1|5|  |8|7|9|
    ———————  ———————  ———————
    |5|9|8|  |7|6|2|  |1|3|4|
    ———————  ———————  ———————

    ———————  ———————  ———————
    |9|7|1|  |2|8|6|  |5|4|3|
    ———————  ———————  ———————
    |3|8|5|  |4|9|1|  |2|6|7|
    ———————  ———————  ———————
    |4|6|2|  |5|3|7|  |9|8|1|
    ———————  ———————  ———————

    ———————  ———————  ———————
    |1|5|6|  |8|7|3|  |4|9|2|
    ———————  ———————  ———————
    |8|4|7|  |1|2|9|  |3|5|6|
    ———————  ———————  ———————
    |2|3|9|  |6|5|4|  |7|1|8|
    ———————  ———————  ———————


    2 recursive calls
    7 ms

The program can solve easy boards with minimal function calls thanks to the algorithm that iteratively makes obvious moves (without this the program almost always crashes due to stack overflow). The program is also versitile enough to solvw extremly hard problems, including an empty board:

    ———————  ———————  ———————
    | | | |  | | | |  | | | |
    ———————  ———————  ———————
    | | | |  | | | |  | | | |
    ———————  ———————  ———————
    | | | |  | | | |  | | | |
    ———————  ———————  ———————

    ———————  ———————  ———————
    | | | |  | | | |  | | | |
    ———————  ———————  ———————
    | | | |  | | | |  | | | |
    ———————  ———————  ———————
    | | | |  | | | |  | | | |
    ———————  ———————  ———————

    ———————  ———————  ———————
    | | | |  | | | |  | | | |
    ———————  ———————  ———————
    | | | |  | | | |  | | | |
    ———————  ———————  ———————
    | | | |  | | | |  | | | |
    ———————  ———————  ———————



    Solved!
    ———————  ———————  ———————
    |1|9|8|  |7|6|5|  |4|3|2|
    ———————  ———————  ———————
    |2|4|6|  |1|3|8|  |5|7|9|
    ———————  ———————  ———————
    |3|5|7|  |2|4|9|  |1|6|8|
    ———————  ———————  ———————

    ———————  ———————  ———————
    |4|1|2|  |3|8|6|  |7|9|5|
    ———————  ———————  ———————
    |5|7|3|  |4|9|2|  |8|1|6|
    ———————  ———————  ———————
    |6|8|9|  |5|1|7|  |2|4|3|
    ———————  ———————  ———————

    ———————  ———————  ———————
    |7|2|1|  |6|5|3|  |9|8|4|
    ———————  ———————  ———————
    |8|3|4|  |9|2|1|  |6|5|7|
    ———————  ———————  ———————
    |9|6|5|  |8|7|4|  |3|2|1|
    ———————  ———————  ———————


    142 recursive calls
    215 ms

We can see that a harder board requires significantly more recursive calls (more backtracking), but nevertheless the algorithm solves the board rather quickly.
