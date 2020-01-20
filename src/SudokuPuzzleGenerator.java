
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class that generates a unique Sudoku puzzle when given a completely solved Sudoku
 */
public class SudokuPuzzleGenerator {

    private static final int MIN_EASY_CLUES = 28;
    private static final int MIN_MEDIUM_CLUES = 24;
    private static final int MIN_HARD_CLUES = 20;
    private static final int MAX_EASY_CLUES = 34;
    private static final int MAX_MEDIUM_CLUES = 27;
    private static final int MAX_HARD_CLUES = 23;

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
    
    public SudokuBoard generateUniquePuzzle(Difficulty diff) {
    	// Create a copy of the current Sudoku board, this will be the puzzle (incomplete) board to solve
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

		// Loop until the no more numbers can be removed, i.e. the length of the list remains unchanged after it reaches the end twice.
    	while (oldLength != newLength && clues > 0) {
    		// System.out.println("PreLoop - New Length: " + newLength + " old Length: " + oldLength + "Clues: " + clues);
    		oldLength = positions.size();
    		int i = 0;
    		
    		while (oldLength >= (81 - clues) && i < positions.size()) {
    			// Remove the value related to the position in the list
    			row = positions.get(i) / 9;
    			col = positions.get(i) % 9;
    			int value = puzzle.getCellValue(row, col);
    			
    			puzzle.removeVal(row, col);
      			// Run the solver to check if the Sudoku puzzle generates the same solution
    			solver.setBoard(puzzle);
    			solver.resetSolutionCount();
    			
    			solver.solveSudokuBackTrack(0);
    			// System.out.println("Solutions: " + solver.getSolutionCount());
    			// If yes, remove from list, and decrement clues
    			if (solver.getSolutionCount() == 1) {
    				board.removeVal(row, col);
    				positions.remove(i);
    				clues--;
    			} 
    			// Otherwise, we do not remove and try another position
    			else {
    				puzzle.editVal(row, col, value);
    				i++;
    			}
    		}
    		newLength = positions.size();
    	}
    	return board;
    }
        
    
    /**
     * Creates an array list of positions, from 0 to 80 for all 81 positions on the Sudoku board
     * @return An array list of positions within a Sudoku board
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
     * Returns the number of clues, number of numbers remain on the Sudoku board
     * for the player to see, based on the difficulty given
     * @param diff - The difficulty, either EASY, MEDIUM, or HARD
     * @return an integer of clues to remain on the board
     */
    private int getNumberOfClues(Difficulty diff) {
    	int clues = 0;
    	switch (diff) {
        // Create a puzzle with EASY number of clues
        case EASY:
            clues = 81 - (MIN_EASY_CLUES + (rand.nextInt((MAX_EASY_CLUES - MIN_EASY_CLUES))));
            break;

         // Create a puzzle with MEDIUM number of clues
        case MEDIUM:
            clues = 81 - (MIN_MEDIUM_CLUES + (rand.nextInt((MAX_MEDIUM_CLUES - MIN_MEDIUM_CLUES))));
            break;

         // Create a puzzle with HARD number of clues
        case HARD:
            clues = 81 - (MIN_HARD_CLUES + (rand.nextInt((MAX_HARD_CLUES - MIN_HARD_CLUES))));
            break;
            }
    	return clues - 1;
    }
    
    public SudokuBoard getBoard() {
    	return board;
    }
    
    public void setBoard(SudokuBoard b) {
    	board = b;
    }
}