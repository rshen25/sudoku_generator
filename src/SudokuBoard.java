
import java.util.ArrayList;
import java.util.Collections;

import org.junit.platform.commons.util.StringUtils;


public class SudokuBoard {
	// The size of the board SIZE x SIZE (in this case 9x9)
    private static final int SIZE = 9;
    // The board is a 2d array of integers
    private int[][] board;

    public SudokuBoard() {
        board = new int[SIZE][SIZE];
    }

    // Initializes the Sudoku board by randomly filling in 3 diagonal subsquares for the board
    public void initSudokuBoard() {
        // Initialize a list of numbers from 1-9 to randomize
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 1; i <= SIZE; i++) {
            numbers.add(i);
        }
        initSquare(0,0, numbers);
        initSquare(3,3, numbers);
        initSquare(6,6, numbers);
    }

    // Helper function for initializing the Sudoku board
    void initSquare(int sRow, int sCol, ArrayList<Integer> numbers){
        // Randomize the numbers
        Collections.shuffle(numbers);
        int row = 0;
        int col = 0;

        for (int num:numbers) {
            board[row + sRow][col + sCol] = num;
            col++;
            if (col >= 3) {
                row++;
            }
            col = col % 3;
        }
    }

    // Method to get the sudoku board
    public int[][] getSudokuBoard() {
        return board;
    }
    
    public void setSudokuBoard(int[][] newBoard) {
    	board = newBoard;
    }

    // Method to change value from cell
    public void editVal(int row, int col, int val) {
        board[row][col] = val;
    }

    // Method to remove value from cell
    public void removeVal(int row, int col) {
        board[row][col] = 0;
    }

    // Method to get value from cell
    public int getCellValue(int row, int col) {
        return board[row][col];
    }

    // Checks if a cell is empty. i.e it is zero
    public boolean isEmpty(int row, int col) {
        return (board[row][col] == 0);
    }

    /**
     * Prints the Sudoku board
     * @return returns the string representation of the Sudoku board
     */
    public String printBoard() {
        String b = "";
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                b = b + board[row][col];
            }
            b = b + "\n";
        }
        return b;
    }

    /**
     * Copies the sudoku board
     * @return A SudokuBoard object containing the exact SudokuBoard values as the current board
     */
    public SudokuBoard copy(){
        SudokuBoard copy = new SudokuBoard();
        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++) {
                copy.editVal(row, col, this.getCellValue(row, col));
            }
        }
        return copy;
    }
    
    /**
     * Copies the values of another Sudoku Board into the current Sudoku Board
     * @param b - Another Sudoku Board to copy the values from
     */
    public void softCopyOtherBoard(SudokuBoard b) {
        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++) {
                this.editVal(row, col, b.getCellValue(row, col));
            }
        }
    }

    // Checks if a number in a given cell is valid, i.e. it does not have a duplicate number in the same row, column
    // and subsquare.
    public boolean checkValidity(int row, int col, int num) {
        //Check for valid row
        for (int i = 0; i < SIZE; i++) {
            if (getCellValue(row, i) == num) {
                return false;
            }
        }
        //Check for valid column
        for (int i = 0; i < SIZE; i++) {
            if (getCellValue(i, col) == num) {
                return false;
            }
        }

        //Check for valid sub-square
        //Calculate which sub-square
        int subsquareRow = (row) / 3;
        int subsquareCol = (col) / 3;

        //Check for valid sub-square
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (getCellValue((subsquareRow * 3) + i, (subsquareCol * 3) + j) == num) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Rotate the Sudoku board 90 degrees
    SudokuBoard rotateClockwise() {
    	int[][] newBoard = new int[SIZE][SIZE];
    	for (int i = 0; i < SIZE; ++i) {
    		for (int j = 0; j < SIZE; ++j) {
    			
    			newBoard[j][SIZE - 1 - i] = board[i][j];
    			
    			// counter clockwise
    			//newBoard[SIZE - 1 - j][i] = board[i][j];
    		}
    	}
    	SudokuBoard rot = new SudokuBoard();
    	rot.setSudokuBoard(newBoard);
    	return rot;
    }
    
    // TODO: Reflect board horizontally - fix
    SudokuBoard reflectHorizontally() {
    	int[][] newBoard = new int[SIZE][SIZE];
    	for (int i = 0; i < SIZE; ++i) {
    		for (int j = 0; j < SIZE; ++j) {
    			newBoard[i][SIZE - 1 - j] = board[i][j];
    		}
    	}
    	SudokuBoard ref = new SudokuBoard();
    	ref.setSudokuBoard(newBoard);
    	return ref;
    }
    
    // TODO: Reflect board vertically - fix
    SudokuBoard reflectVertically() {
    	int[][] newBoard = new int[SIZE][SIZE];
    	for (int i = 0; i < SIZE; ++i) {
    		for (int j = 0; j < SIZE; ++j) {
    			newBoard[SIZE - 1 - i][j] = board[i][j];
    		}
    	}
    	SudokuBoard ref = new SudokuBoard();
    	ref.setSudokuBoard(newBoard);
    	return ref;
    }
    
    /**
     * Generates a seed representing the board, essentially just a string of integers
     * @return A string representing the Sudoku board
     */
    public String generateSeed() {
    	String[] seed = new String[(SIZE * SIZE)];
    	for (int row = 0; row < SIZE; row++) {
    		for (int col = 0; col < SIZE; col++) {
    			if (this.getCellValue(row, col) == 0) {
    				seed[(row * SIZE) + col] = ".";
    			}
    			else {
    				seed[(row * SIZE) + col] = Integer.toString(this.getCellValue(row, col));
    			}
    		}
       	}
    	String stringSeed = String.join("", seed);
    	return stringSeed;
    }
}