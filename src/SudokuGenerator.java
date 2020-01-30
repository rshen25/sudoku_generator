import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SudokuGenerator {

	public static void main(String[] args) {
		
		int easyPuzzles = 0;
		int medPuzzles = 0;
		int hardPuzzles = 0;
		
		try {
	        easyPuzzles = Integer.parseInt(args[0]);
		}
		catch (Exception e){
			System.out.println("Invalid arguments. Please enter a value between 0-500 for each type of puzzle to generate.");
		}
		try {
	        medPuzzles = Integer.parseInt(args[1]);
		}
		catch (Exception e){
			System.out.println("Invalid arguments. Please enter a value between 0-500 for each type of puzzle to generate.");
		}
		try {
	        hardPuzzles = Integer.parseInt(args[2]);
		}
		catch (Exception e){
			System.out.println("Invalid arguments. Please enter a value between 0-500 for each type of puzzle to generate.");
		}

		if (easyPuzzles <= 500 && medPuzzles <= 500 && hardPuzzles <= 500) {			
			SudokuSolver solver = new SudokuSolver();
			SudokuPuzzleGenerator generator = new SudokuPuzzleGenerator();

			if (easyPuzzles > 0 ) {
				System.out.println("Generating Easy Puzzles...");
				// Create Easy Puzzles
				generatePuzzles(easyPuzzles * 10, solver, generator, Difficulty.EASY, "puzzles_easy.txt");
				System.out.println("Done.");
			}			
			
			if (medPuzzles > 0) {
				System.out.println("Generating Medium Puzzles...");
				// Create Medium Puzzles		
				generatePuzzles(medPuzzles * 10, solver, generator, Difficulty.MEDIUM, "puzzles_medium.txt");
				System.out.println("Done.");
			}
			
			if (hardPuzzles > 0) {
				System.out.println("Generating Hard Puzzles...");
				// Create Hard Puzzles		        
		        generatePuzzles(hardPuzzles * 10, solver, generator, Difficulty.HARD, "puzzles_hard.txt");
		        System.out.println("Done.");
			}			
		}
                
        return;
    }

	private static void generatePuzzles(int puzzleCount, SudokuSolver solver, SudokuPuzzleGenerator generator, 
										Difficulty diff, String filename) {
		SudokuBoard board;
		SudokuBoard sol;
		SudokuBoard puzzle;
		SudokuBoard refVert;
		SudokuBoard refHoriz;
		SudokuBoard solVert;
		SudokuBoard solHoriz;
		SudokuBoard rotPuzzle;
		SudokuBoard rotSol;
		String seed;
		String solution;
		
		BufferedWriter out = null;        
        File f = new File(filename);
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
    		int count = 0;
    		
    		while (count < puzzleCount) {
    		    board = new SudokuBoard();
    			board.initSudokuBoard();
    		    solver.setBoard(board);
    		    solver.solveSudokuBacktrack(0, 0);
    		    board = solver.getBoard();
    		    sol = board.copy();
    		    solution = board.generateSeed();
    		    // System.out.println(board.printBoard());
    		    generator.setBoard(board);
    		    puzzle = generator.generateUniquePuzzle(diff);
    		    seed = solution + puzzle.generateSeed();
    		    out.write(seed);
    		    ++count;
    		    out.newLine();
    		    
    		    // Reflect the board and solution vertically and horizontally
    		    refVert = puzzle.reflectVertically();    
    		    solVert = sol.reflectVertically();   
    		    seed = solVert.generateSeed() + refVert.generateSeed();
    		    out.write(seed);
    		    ++count;
    		    out.newLine();
    		    
    		    refHoriz = puzzle.reflectHorizontally();
    		    solHoriz = sol.reflectHorizontally();
    		    seed = solHoriz.generateSeed() + refHoriz.generateSeed();
    		    // print the seed
    		    out.write(seed);
    		    ++count;
    		    out.newLine();
    		    
    		    rotPuzzle = puzzle.copy();
    		    rotSol = sol.copy();
    		    
    		    // Then rotate and reflect vertically and horizontally, loop 3 times
    		    for (int i = 0; i < 3; ++i) {
    		    	rotPuzzle = rotPuzzle.rotateClockwise();
    		    	rotSol = rotSol.rotateClockwise();
    		    	seed = rotSol.generateSeed() + rotPuzzle.generateSeed();
    		    	out.write(seed);
    		    	++count;
    		    	out.newLine();
    		    }
    		    
    		    SudokuBoard colShift = puzzle.copy();
    		    SudokuBoard colShiftSol = sol.copy();
    		    SudokuBoard rowShift = puzzle.copy();
    		    SudokuBoard rowShiftSol = sol.copy();
    		    
    		    // Shifts the rows and columns of the Sudoku board to generate more puzzles
    		    for (int i = 0; i < 2; ++i) {
    		    	colShift = colShift.shiftColumns();
    		    	colShiftSol = colShiftSol.shiftColumns();
    		    	seed = colShiftSol.generateSeed() + colShift.generateSeed();
    		    	out.write(seed);
    		    	++count;
    		    	
    		    	rowShift = rowShift.shiftRows();
    		    	rowShiftSol = rowShiftSol.shiftRows();
    		    	seed = rowShiftSol.generateSeed() + rowShift.generateSeed();
    		    	out.write(seed);
    		    	++count;
    		    }
    		    System.out.println("Puzzles Created: " + count);
    		}
        		
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
        finally {
        	if (out != null) {
        		try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}

}
