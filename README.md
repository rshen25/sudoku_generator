# Sudoku Generator
A Sudoku puzzle generator written in Java. Generates puzzles via a backtracking algorithm and 
outputs puzzle seeds into 3 separate text files based on difficulty. The difficulty is based on 
how many clues are given for each puzzle.

# Usage
Open a terminal/command line in the location of the SudokuGenerator.jar file.
To generate puzzles, execute the generator in the terminal with three integer arguments between 0-500. 
Example:
```
java -jar SudokuGenerator.jar 3 3 3
```

![Example GIF](http://g.recordit.co/tsd9Lrvr53.gif)

<p>
The generator will generator 10 times the amount entered Sudoku puzzles, so in the example, it would
generate 500 puzzles. This is because the generator will find a unique Sudoku puzzle, and then it will rotate it four times,
and reflect it twice to get 10 puzzles from one.
</p>

<p>
Once the generator finishes generating it will create a text file consisting of all the puzzles along with its solution. The
puzzles in the text file are represented by this string format:

The first 81 numbers is the solution to the puzzle itself, so the first nine would be the first row, the next nine the second row and so forth.
The 82nd and onward numbers represent the actual puzzle, where the periods represent a missing number '.' on the Sudoku board.
</p>

For example it would look something like this:
<strong>269387154857194236314652798526471983491823675783965412975248361148736529632519847</strong><em>.......5....1.4.....46.2.9...6.....3...8..6.57..96..1...5.483..1....6.2.....1.8..</em>

The <strong>bold</strong> portion is the complete solution to the Sudoku puzzle and the <em>italicized</em> portion is the puzzle
