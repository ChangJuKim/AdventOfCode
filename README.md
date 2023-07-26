# AdventOfCode
My solutions to [Advent of Code](https://adventofcode.com/2022) problems.

I will log my journey here.

## [Day 1 Calorie Counting](https://github.com/ChangJuKim/AdventOfCode/tree/main/advent_of_code_2022/problem_01):
  * Simple problem. Decided to parse and solve in a main method. 
  * Asked ChatGPT for feedback on improving my code:
    * Helped me use scanner class to parse my input, and implemented its feedback of using try-with-resources instead of a try block for my scanner class.
    * Learned a lot about scanner.

## [Day 2 Rock Paper Scissors](https://github.com/ChangJuKim/AdventOfCode/tree/main/advent_of_code_2022/problem_02): 
  * Added common folder to hold a ParseInput file with common parsing functions. 
  * Updated file structure for better naming conventions
  * Asked ChatGPT for feedback:
    * Added constants to the top of my file such as the INPUT_PATH
    * Added exception throwing if there is invalid input instead of printing out an error
    * Learned about and implemented JavaDoc comments

## [Day 3 Rucksack Reorganization](https://github.com/ChangJuKim/AdventOfCode/tree/main/advent_of_code_2022/problem_03):
  * Asked ChatGPT for feedback:
    * Improved variable naming

## [Day 4 Camp Cleanup](https://github.com/ChangJuKim/AdventOfCode/tree/main/advent_of_code_2022/problem_04):
  * Asked ChatGPT for feedback:
    * Improved function names
    * Added a constant for a regex
    * For part 2, added constants for error messages to put them in one place
    * Simplified logic in code

...

## [Day 10 Cathode-Ray Tube](https://github.com/ChangJuKim/AdventOfCode/tree/main/advent_of_code_2022/problem_10):
  * Discovering limitations of ChatGPT -- did not ask for feedback on improving code.
  * Continuing to add JavaDoc comments, constants, and input validation

## [Day 11 Monkey in the Middle](https://github.com/ChangJuKim/AdventOfCode/tree/main/advent_of_code_2022/problem_11):
  * Learning about builder design pattern. Using it to create Monkeys.
    * I'm sure I did not use it well, but I learned something.
  * Practiced constants, input validation, and builder design pattern.
    * Didn't even properly use builder design pattern as can be seen in Monkey's worrysomeDivider, and true/falseReceiverId variables.
    * Also did not create a generous input validation -- input is also extremely strict. Would have preferred to make input more lenient.
  
    
    
