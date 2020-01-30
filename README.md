# Sudoku Generator
A Sudoku puzzle generator written in Java. Generates puzzles via a backtracking algorithm and 
outputs puzzle seeds into 3 separate text files based on difficulty. The difficulty is based on 
how many clues are given for each puzzle.

### Prerequisites
Requires Java to be installed on your machine.

# Usage
Open a terminal/command line in the location of the SudokuGenerator.jar file.
To generate puzzles, execute the generator in the terminal with three integer arguments between 0-500. 
Example:
```
java -jar SudokuGenerator.jar 50 50 50
```
The generator will generator 10 times the amount entered Sudoku puzzles, so in the example, it would
generate 500 puzzles.

