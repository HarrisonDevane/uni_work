package Countdown;
import java.util.*;

public class CountdownApp {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        ArrayList<String> file = new ArrayList<String>();

        while (scan.hasNextLine()){
            file.add(scan.nextLine());
        }

        scan.close();

        int index = 0;
        while (index < file.size()){
            String line1 = file.get(index);
            String[] line1ArrStr = line1.split("\\s+");
            int[] line1ArrInt = new int[line1ArrStr.length];

            for (int i = 0; i < line1ArrStr.length; i++){
                line1ArrInt[i] = Integer.parseInt(line1ArrStr[i]);
            }

            String line2 = file.get(index + 1);
            String[] line2ArrStr = line2.split("\\s+");

            char leftRight = line2ArrStr[1].charAt(0);
            int desiredNum = Integer.parseInt(line2ArrStr[0]);

            Countdown a = new Countdown(line1ArrInt, leftRight, desiredNum);

            index = index + 2;
        }
    }
}