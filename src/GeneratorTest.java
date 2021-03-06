import org.junit.jupiter.api.Test;

class GeneratorTest {
	@Test
	void generateUniquePuzzle() {
        SudokuBoard board = new SudokuBoard();
		board.initSudokuBoard();
        SudokuSolver solver = new SudokuSolver(board);
        solver.solveSudokuBacktrack(0, 0);
        board = solver.getBoard();
        System.out.println(board.printBoard());
        SudokuPuzzleGenerator generator = new SudokuPuzzleGenerator(board);
        String solution = board.generateSeed();
        SudokuBoard puzzle = generator.generateUniquePuzzle(Difficulty.MEDIUM);
        System.out.println(solution + puzzle.generateSeed());
        System.out.println("Puzzle - test:");
        System.out.println(puzzle.printBoard());
	}
	
	@Test
	void emptyBoardTest() {
		System.out.println("Empty Board Test:");
		SudokuBoard board = new SudokuBoard();
		SudokuSolver solver = new SudokuSolver(board);
		solver.solveSudokuBacktrack(0, 0);
		board = solver.getBoard();
		System.out.println(solver.printBoard());
		
		System.out.println(board.generateSeed());
	}
		
	@Test
	void testRotation() {
		SudokuBoard board = new SudokuBoard();
		board.initSudokuBoard();
        SudokuSolver solver = new SudokuSolver(board);
        solver.solveSudokuBacktrack(0, 0);
        board = solver.getBoard();
        SudokuBoard rotated = board.rotateClockwise();
        System.out.println("Original: ");
        System.out.println(board.printBoard());
        System.out.println("Rotated: ");
        System.out.println(rotated.printBoard());
	}
	
	@Test
	void testReflection() {
		SudokuBoard board = new SudokuBoard();
		board.initSudokuBoard();
        SudokuSolver solver = new SudokuSolver(board);
        solver.solveSudokuBacktrack(0, 0);
        board = solver.getBoard();
        SudokuBoard refVert = board.reflectVertically();
        SudokuBoard refHor = board.reflectHorizontally();
        System.out.println("Original: ");
        System.out.println(board.printBoard());
        System.out.println("Reflected_Vert: ");
        System.out.println(refVert.printBoard());
        System.out.println("Reflected_Horiz: ");
        System.out.println(refHor.printBoard());
	}
	
	@Test
	void mainTest() {
		SudokuGenerator.main(new String[] {"10", "10", "10"});
	}
	
	@Test
	void columnShiftTest() {
		System.out.println("Board Shift Test:");
		SudokuBoard board = new SudokuBoard();
		SudokuSolver solver = new SudokuSolver(board);
		solver.solveSudokuBacktrack(0, 0);
		board = solver.getBoard();
		System.out.println(solver.printBoard());
		
		System.out.println(board.generateSeed());
		
		
		SudokuBoard shift = board.shiftColumns();
		System.out.println("Shifted Board Test: \n");
		System.out.println(shift.printBoard());
	}
	
	@Test
	void rowShiftTest() {
		System.out.println("Board Shift Test:");
		SudokuBoard board = new SudokuBoard();
		SudokuSolver solver = new SudokuSolver(board);
		solver.solveSudokuBacktrack(0, 0);
		board = solver.getBoard();
		System.out.println(solver.printBoard());
		
		System.out.println(board.generateSeed());
		
		SudokuBoard shift = board.shiftRows();
		System.out.println("Shifted Board Test: \n");
		System.out.println(shift.printBoard());
		
	}
	
}
