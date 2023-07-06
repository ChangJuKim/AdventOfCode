package advent_of_code_2022.common;

public class PrintArray {
    public static void printArray(int[] ary) {
        if (ary.length == 0) System.out.println("[]");
        System.out.print("[");
        for (int i = 0; i < ary.length - 1; i++) {
            System.out.print(ary[i] + ", ");
        }
        System.out.println(ary[ary.length - 1] + "]");
    }

    public static void print2DArray(int[][] ary) {
        System.out.println("[");
        for (int i = 0; i < ary.length; i++) {
            printArray(ary[i]);
        }
        System.out.println("]");
    }
}
