import java.util.Scanner;
import java.io.StringReader;
import java.math.BigInteger;

public class CalorieIntake {

    
    // // Part 1
    // public static void main(String[] args) {
    //     System.out.println("Paste input here. Type q to quit:");
    //         try (Scanner reader = new Scanner(System.in)) {
    //         BigInteger max = BigInteger.ZERO;
    //         BigInteger current = BigInteger.ZERO;
    //         while (reader.hasNextLine()) {
    //             String s = reader.nextLine().trim();
    //             if (s.equals("q")) {
    //                 break;
    //             } 
    //             if (s.isEmpty()) {
    //                 if (current.compareTo(max) > 0) {
    //                     max = current;
    //                 }
    //                 current = BigInteger.ZERO;
    //             } else {
    //                 BigInteger addend = new BigInteger(s);
    //                 current = current.add(addend);
    //             }
    //         }
    //         System.out.println("The maximum amount of calories: " + max);
    //     }
    // }
    


    // Part 2
    public static void main(String[] args) {
        System.out.println("Paste input here. Type q to quit:");
        try (Scanner reader = new Scanner(System.in)) {
        
            BigInteger max = BigInteger.ZERO;
            BigInteger second = BigInteger.ZERO;
            BigInteger third = BigInteger.ZERO;
            BigInteger current = BigInteger.ZERO;
            while (reader.hasNextLine()) {
                String s = reader.nextLine().trim();
                if (s.equals("q")) {
                    break;
                } 
                if (s.isEmpty()) {
                    if (current.compareTo(max) > 0) {
                        third = second;
                        second = max;
                        max = current;
                    } else if (current.compareTo(second) > 0) {
                        third = second;
                        second = current;
                    } else if (current.compareTo(third) > 0) {
                        third = current;
                    }
                    current = BigInteger.ZERO;
                } else {
                    BigInteger addend = new BigInteger(s);
                    current = current.add(addend);
                }
            }
            System.out.println("The sum of the top 3 amount of calories: " + max.add(second).add(third));
        }
    }
}