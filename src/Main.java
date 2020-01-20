import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		int easyPuzzles = 0;
		int medPuzzles = 0;
		int hardPuzzles = 0;
		
		try {
	        easyPuzzles = Integer.parseInt(args[0]);
		}
		catch (Exception e){
			System.out.println("Invalid arguments. Please enter a value between 1-500 for each type of puzzle to generate.");
		}
		try {
	        medPuzzles = Integer.parseInt(args[1]);
		}
		catch (Exception e){
			System.out.println("Invalid arguments. Please enter a value between 1-500 for each type of puzzle to generate.");
		}
		try {
	        hardPuzzles = Integer.parseInt(args[2]);
		}
		catch (Exception e){
			System.out.println("Invalid arguments. Please enter a value between 1-500 for each type of puzzle to generate.");
		}

		System.out.println("Generating...");
		
		SudokuSolver solver = new SudokuSolver();
		SudokuPuzzleGenerator generator = new SudokuPuzzleGenerator();

		// Create Easy Puzzles
		generatePuzzles(easyPuzzles, solver, generator, Difficulty.EASY, "puzzles_easy.txt");
		
		// Create Medium Puzzles		
		generatePuzzles(medPuzzles, solver, generator, Difficulty.MEDIUM, "puzzles_medium.txt");
		
		// Create Hard Puzzles		        
        generatePuzzles(hardPuzzles, solver, generator, Difficulty.HARD, "puzzles_hard.txt");
                
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
    		    System.out.println("Puzzles Created: " + ++count);
    		    out.newLine();
    		    
    		    // Reflect the board and solution vertically and horizontally
    		    refVert = puzzle.reflectVertically();    
    		    solVert = sol.reflectVertically();   
    		    seed = solVert.generateSeed() + refVert.generateSeed();
    		    out.write(seed);
    		    System.out.println("Puzzles Created: " + ++count);
    		    out.newLine();
    		    
    		    refHoriz = puzzle.reflectHorizontally();
    		    solHoriz = sol.reflectHorizontally();
    		    seed = solHoriz.generateSeed() + refHoriz.generateSeed();
    		    // print the seed
    		    out.write(seed);
    		    System.out.println("Puzzles Created: " + ++count);
    		    out.newLine();
    		    
    		    rotPuzzle = puzzle.copy();
    		    rotSol = sol.copy();
    		    
    		    // Then rotate and reflect vertically and horizontally, loop 3 times
    		    for (int i = 0; i < 3; ++i) {
    		    	rotPuzzle = rotPuzzle.rotateClockwise();
    		    	rotSol = rotSol.rotateClockwise();
    		    	seed = rotSol.generateSeed() + rotPuzzle.generateSeed();
    		    	out.write(seed);
    		    	System.out.println("Puzzles Created: " + ++count);
    		    	out.newLine();
    		    }
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
	}

}
