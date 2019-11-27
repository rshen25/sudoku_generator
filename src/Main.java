import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
        
		int counter = 0;
		SudokuBoard board;
		SudokuSolver solver = new SudokuSolver();
		SudokuPuzzleGenerator generator = new SudokuPuzzleGenerator();
		SudokuBoard sol = new SudokuBoard();
		SudokuBoard puzzle;
		SudokuBoard refVert;
		SudokuBoard refHoriz;
		SudokuBoard solVert;
		SudokuBoard solHoriz;
		SudokuBoard rotPuzzle;
		SudokuBoard rotSol;
		String seed, solution;
		        
        BufferedWriter out = null;        
        File f = new File("hard_test.txt");
        if (!f.isFile()) {
        	// Create a text file if none found
        	try {
				if (f.createNewFile()) {
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        // Open puzzle file for writing
        try {
        	FileWriter fstream = new FileWriter(f, true);
        	out = new BufferedWriter(fstream);
    		while (counter < 1 ) {
    	        board = new SudokuBoard();
    			board.initSudokuBoard();
    	        solver.setBoard(board);
    	        solver.solveSudokuBacktrack(0, 0);
    	        board = solver.getBoard();
    	        sol = board.copy();
    	        solution = board.generateSeed();
    	        // System.out.println(board.printBoard());
    	        generator.setBoard(board);
    	        puzzle = generator.generateUniquePuzzle(Difficulty.HARD);
    	        seed = solution + puzzle.generateSeed();
    	        out.write(seed);
    	        out.newLine();
    	        
    	        // Reflect the board and solution vertically and horizontally
    	        refVert = puzzle.reflectVertically();    
    	        solVert = sol.reflectVertically();   
    	        seed = solVert.generateSeed() + refVert.generateSeed();
    	        out.write(seed);
    	        out.newLine();
    	        
    	        refHoriz = puzzle.reflectHorizontally();
    	        solHoriz = sol.reflectHorizontally();
    	        seed = solHoriz.generateSeed() + refHoriz.generateSeed();
    	        // print the seed
    	        out.write(seed);
    	        out.newLine();
    	        
    	        rotPuzzle = puzzle.copy();
    	        rotSol = sol.copy();
    	        
    	        // Then rotate and reflect vertically and horizontally, loop 3 times
    	        for (int i = 0; i < 3; ++i) {
    	        	rotPuzzle = rotPuzzle.rotateClockwise();
    	        	rotSol = rotSol.rotateClockwise();
    	        	seed = rotSol.generateSeed() + rotPuzzle.generateSeed();
    	        	out.write(seed);
    	        	out.newLine();
    	        	
        	        // Reflect the board and solution vertically and horizontally
//        	        refVert = rotPuzzle.reflectVertically();    
//        	        solVert = rotSol.reflectVertically();   
//        	        seed = solVert.generateSeed() + refVert.generateSeed();
//        	        out.write(seed);
//        	        out.newLine();
//        	        
//        	        refHoriz = rotPuzzle.reflectHorizontally();
//        	        solHoriz = rotSol.reflectHorizontally();
//        	        seed = solHoriz.generateSeed() + refHoriz.generateSeed();
//        	        // print the seed
//        	        out.write(seed);
//        	        out.newLine();
    	        	
    	        }
    	        
    	        
    	        counter++;
    			}
        		
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
        finally {
        	if (out != null) {
        		try {
					out.close();
					System.out.println("closed file");
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return;
    }
}
