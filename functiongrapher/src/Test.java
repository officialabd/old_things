import java.io.File;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws Exception {
        String[][] strings = new String[582][4];
        Scanner scanner = new Scanner(new File("C:\\Users\\abedm\\Downloads\\CONVERT.txt"));

        for (int j = 0; j < strings.length; j++) {
            strings[j] = scanner.nextLine().split(" ");

            for (int i = 1; i < 4; i++) {
                strings[j][i] = strings[j][i] + "f,";
                System.out.print(strings[j][i] + " ");
            }
            System.out.println();
        }

    }
}
