
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//import SudokuBoard;
//import SudokuPuzzleGenerator.Difficulty;

public class SudokuPuzzleGenerator {

    private static final int MIN_EASY_CLUES = 28;
    private static final int MIN_MEDIUM_CLUES = 24;
    private static final int MIN_HARD_CLUES = 20;
    private static final int MAX_EASY_CLUES = 34;
    private static final int MAX_MEDIUM_CLUES = 27;
    private static final int MAX_HARD_CLUES = 23;

    // private SudokuBoard board;
    // private Difficulty difficulty;
    private Random rand = new Random();
    private SudokuBoard board;
    
    SudokuPuzzleGenerator() {
    	board = new SudokuBoard();
    }
    
    SudokuPuzzleGenerator(SudokuBoard b) {
    	board = b.copy();
    }
    
    /** A method to randomly remove clues
     *  param: int 0: easy, 1: medium, 2: hard
     *  output - a unique puzzle with X amount of clues
     */
    public SudokuBoard generatePuzzleRandomly(Difficulty diff) {
        int currentClues = getNumberOfClues(diff);
        int row;
        int col;
        while (currentClues > 0) {
            row = rand.nextInt(9);
            col = rand.nextInt(9);
            if (!board.isEmpty(row, col)) {
                board.editVal(row, col, 0);
                currentClues--;
            }
        }
        return board; 
    }

    /**
     * A method to remove clues symmetrically
     * param: int 0: easy, 1: medium, 2: hard
     * output - a unique puzzle with X amount of clues
     */
    public SudokuBoard generatePuzzleSymm(SudokuBoard board, Difficulty diff)
    {
        return null;
    }
        
    /**
     * Generates a unique sudoku puzzle
     * @param board - a SudokuBoard object that is a completely solved sudoku puzzle
     * @param diff - the difficulty of the puzzle, it determines the amount of clues in the final puzzle
     * @return a unique sudoku puzzle with X amount of clues based on the difficulty
     */
//    public SudokuBoard generateUniquePuzzleRandom(Difficulty diff) {
//    	// create a new empty board
//    	SudokuBoard puzzle = new SudokuBoard();
//    	SudokuBoard b = board.copy();
////    	ArrayList<Integer> numbers = new ArrayList<Integer>();
//    	int count = 0;
//    	int clues = getNumberOfClues(diff);
//    	// loop until designated number of clues are on the new board
//    	while (count < clues) {
//    		// remove a number at random from puzzle board
//    		int row = rand.nextInt(9);
//    		int col = rand.nextInt(9);
//    		int num = b.getCellValue(row, col);
//    		// create a list of possible choices in the new empty spot
//    		for (int i = 0; i < 9; i++) {
//    			// if more than one choice, pick one at random and add it to the empty board
//    			if ( i != num && num != 0 && b.checkValidity(row, col, i)) {
//    				puzzle.editVal(row, col, num);
//    				b.removeVal(row, col);
//    				count++;
//    			}
//    			else {
//    				b.removeVal(row, col);
//    			}
//    		}
//    	}
//    	return puzzle;
//    }
    
    // TODO: add a difficulty
    public SudokuBoard generateUniquePuzzle(Difficulty diff) {
    	// Create a copy of the current Sudoku board
    	SudokuBoard puzzle = board.copy();
    	// Create a solver with the copied board (will eventually be the unique puzzle)
    	SudokuSolver solver = new SudokuSolver(puzzle);
    	// Create a shuffled list of all 81 positions of the Sudoku board
    	ArrayList<Integer> positions = createRandomPositionList();
    	// System.out.println("Positions: " + positions);
    	
    	// The number of positions that are available to be removed and checked for a unique solution
    	int oldLength = positions.size();
    	int newLength = 0;
		int row = 0;
		int col = 0;
		
		int clues = getNumberOfClues(diff);
		System.out.println("Clues: " + clues);
		
		// Loop until the no more numbers can be removed, i.e. the length of the list remains unchanged after it reaches the end twice.
    	while (oldLength != newLength) {
    		// System.out.println("PreLoop - New Length: " + newLength + " old Length: " + oldLength + "Clues: " + clues);
    		oldLength = positions.size();
    	// Iterate through the list
    		int i = 0;
    		while (oldLength >= (81 - clues) && i < positions.size()) {
    		//for (int i = 0; i < positions.size(); i++) {
    			// Remove the value related to the position in the list
    			row = positions.get(i) / 9;
    			col = positions.get(i) % 9;
    			puzzle.softCopyOtherBoard(board);
    			// value = puzzle.getCellValue(row, col);
    			
    			puzzle.removeVal(row, col);
      			// Run the solver to check if the Sudoku puzzle generates the same solution
    			solver.setBoard(puzzle);
    			solver.resetSolutionCount();
    			
    			solver.solveSudokuBackTrack();
    			// If yes, remove from list, and decrement clues
    			if (solver.getSolutionCount() == 1) {
    				board.removeVal(row, col);
    				positions.remove(i);
    			}
    			// If no, move on to the next position in the list
//    			else{
//    				puzzle.editVal(row, col, value);
//    			}
    			// System.out.println(i);
    			i++;
    		}
    		// System.out.println(board.printBoard());
    		newLength = positions.size();
    		// System.out.println("PostLoop - New Length: " + newLength + " old Length: " + oldLength);
        	//seed = board.generateSeed();
    	//}
    	}
    	return board;
    }
        
    
    /**
     * 
     * @return
     */
    private ArrayList<Integer> createRandomPositionList() {
    	ArrayList<Integer> positions = new ArrayList<Integer>();
    	for (int i = 0; i < 81; i++) {
    		positions.add(i);
    	}
    	Collections.shuffle(positions);
    	return positions;
    }
    
    /**
     * Returns the number of clues, number of numbers remain on the sudoku board
     * for the player to see, based on the difficulty given
     * @param diff - The difficulty, either EASY, MEDIUM, or HARD
     * @return an integer of clues to remain on the board
     */
    private int getNumberOfClues(Difficulty diff) {
    	int clues = 0;
    	switch (diff) {
        // Create a puzzle with EASY number of clues
        case EASY:
            clues = 81 - (MIN_EASY_CLUES + (rand.nextInt((MAX_EASY_CLUES - MIN_EASY_CLUES) + 1)));
            break;

         // Create a puzzle with MEDIUM number of clues
        case MEDIUM:
            clues = 81 - (MIN_MEDIUM_CLUES + (rand.nextInt((MAX_MEDIUM_CLUES - MIN_MEDIUM_CLUES) + 1)));
            break;

         // Create a puzzle with HARD number of clues
        case HARD:
            clues = 81 - (MIN_HARD_CLUES + (rand.nextInt((MAX_HARD_CLUES - MIN_HARD_CLUES) + 1)));
            break;
            }
    	return clues;
    }
    
    public SudokuBoard getBoard() {
    	return board;
    }
    
    public void setBoard(SudokuBoard b) {
    	board = b;
    }
}