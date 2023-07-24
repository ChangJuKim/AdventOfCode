package advent_of_code_2022.problem_14.part2;

/* 
    For this problem I have decided to take a more mathematical approach.
    I know that the lowest point of the rocks is 177 from my print statements. Of course, this is only with my given input, and not all input.
    Thus, the floor is on y = 179. We then know the shape of the resulting pyramid and its size.
    If we let n = the lowest point of rocks (177), the size is (n+2)^2, or (177+2)^2 = 179^2 = 32041. 
        You can get this by adding 1 + 3 + 5 + ... + (2n-1)
    Thus, to get the number of sand that falls down, we can subtract all of the rocks that displace this triangle of sand from the total.
*/
public class SimulateBlockingSandSource {
    
}
