
import java.util.ArrayList;
import java.util.Collections;

public class SudokuSolver {

    private static final int SIZE = 9;
    private SudokuBoard board;
    private ArrayList<Integer> numbers = new ArrayList<Integer>();
    private int solutionCount = 0;

    public SudokuSolver() {
        board = new SudokuBoard();
        board.initSudokuBoard();
        resetNumbers();
    }

    public SudokuSolver(SudokuBoard newBoard) {
        board = newBoard.copy();
        // TODO: need a way to check if the board is completely empty
        resetNumbers();
    }

    /**
     * Resets the list of numbers used to help add numbers to a row on the Sudoku board
     */
    void resetNumbers() {
        numbers.clear();
        for (int i = 1; i < SIZE + 1; i++) {
            numbers.add(i);
        }
    }

    /**
     *  Creates a valid Sudoku puzzle via backtracking/recursion
     * @param row The current row of the Sudoku board
     * @param col The current column of the Sudoku board
     * @return True if the current Sudoku board is valid, false otherwise
     */
    public boolean solveSudokuBacktrack(int row, int col) {

        int cRow = row;
        int cCol = col;
        // Checks if a cell is empty or not, if it is filled, move on to the next cell
        while(cRow < SIZE && !board.isEmpty(cRow, cCol)) {
        	
            // Move to the next cell if the current cell is already filled
            cCol++;
            if (cCol > 8) {
                cRow++;
            }
            cCol = cCol % SIZE;
            if (cRow > 8) {
                return true;
            }
        }
        
        // Base Case - if we reach the end of the Sudoku Board
        if (cRow > SIZE - 1) {
        	solutionCount++;
        	System.out.println(solutionCount);
        	return true;
        }

        // Randomly shuffle the list of numbers (1-9)
        Collections.shuffle(numbers);

        // Go through each one of the numbers list, trying each number in order
        for (int num: numbers) {
            if (board.checkValidity(cRow, cCol, num)) {
            	
                // add number to cell
                board.editVal(cRow, cCol, num);

                // increment current cell
                int newCol = cCol + 1;
                int newRow = cRow;
                if (newCol > 8) {
                    newRow = cRow + 1;
                }
                newCol = newCol % SIZE;
                if (solveSudokuBacktrack(newRow, newCol)) {
                    return true;
                } else {
                    board.editVal(cRow, cCol, 0);
                }
            }
        }
        // System.out.print(printBoard());
        return false;
    }
    
    /**
     * Solves a Sudoku puzzle via backtracking and ensures that there is only a unique solution
     */
    public void solveSudokuBackTrack(int tries) {
		if (solutionCount > 1 || tries > 10000) {
			return;
		}
    	int buffer_number;
    	//System.out.println("Tries: " + tries);
    	for (int row = 0; row < SIZE; row++) {
    		for (int col = 0; col < SIZE; col++) {
    			if (board.getCellValue(row, col) == 0) {
    				// Check for each possible number if it could be placed in the current spot
    				for (int num = 1; num <= SIZE; num++) {
    					if (board.checkValidity(row, col, num)) {
    						buffer_number = board.getCellValue(row, col);
    						board.editVal(row, col, num);
    						this.solveSudokuBackTrack(tries + 1);
    						if (!checkForEmptySpaces()) {
    							solutionCount++;
    						}
    						board.editVal(row, col, buffer_number);
    					}
    				}
    				return;
    			}
    		}
    	}
    	return;
    }
    
    /**
     * Checks to the current Sudoku board for any empty spaces, empty spaces are represented as 0
     * @return True if the current board has empty spaces, False otherwise
     */
    private boolean checkForEmptySpaces() {
    	for (int row = 0; row < SIZE; row++) {
    		for (int col = 0; col < SIZE; col++) {
    			if (board.getCellValue(row, col) == 0) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * @return Returns the Sudoku board the solver trying to solve
     */
    public SudokuBoard getBoard() {
    	return board;
    }

    
    /**
     * Sets the current Sudoku board for the solver to solve
     * @param b A Sudoku board object
     */
    public void setBoard(SudokuBoard b) {
    	board = b;
    }
    
    /**
     * Returns the amount of solutions the Sudoku solver has found
     * @return The amount of solutions that the solver has found
     */
    public int getSolutionCount() {
    	return solutionCount;
    }
    
    /*
     * Resets the amount of solutions to the Sudoku board to 0
     */
    public void resetSolutionCount() {
    	solutionCount = 0;
    }
    
    /**
     * Method to print the board to console
     * @return String representation of the board in 9x9 lines
     */
    public String printBoard(){
        return board.printBoard();
    }
}