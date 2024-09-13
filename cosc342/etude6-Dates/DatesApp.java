package dates;
import java.util.*;

public class DatesApp {
    public static void main(String [] args){
        ArrayList<String> a1 = new ArrayList<String>();
        Scanner in = new Scanner(System.in);

        while(in.hasNextLine()){
            a1.add(in.nextLine());
        }

        in.close();
        int index = 0;

        while (index < a1.size()){
            if (!(a1.get(index).isBlank())){
                String line = a1.get(index);
                int num = 0;

                if (line.contains("-")){
                    String lineArr[] = line.split("-");

                    for (int i = 0; i < line.length(); i++){
                        if (line.charAt(i) == '-'){
                            num++;
                        }
                    }

                    if (!line.contains("/") && !line.contains(" ") && num == 2){
                        Dates d = new Dates(lineArr[0], lineArr[1], lineArr[2]);
                    } else {
                        error(line);
                    }
                } else if (line.contains("/")){
                    String lineArr[] = line.split("/");

                    for (int i = 0; i < line.length(); i++){
                        if (line.charAt(i) == '/'){
                            num++;
                        }
                    }
                    
                    if (!line.contains("-") && !line.contains(" ") && num == 2){
                        Dates d = new Dates(lineArr[0], lineArr[1], lineArr[2]);
                    } else {
                        error(line);
                    }
                } else if (line.contains(" ")){
                    String lineArr[] = line.split(" ");

                    for (int i = 0; i < line.length(); i++){
                        if (line.charAt(i) == ' '){
                            num++;
                        }
                    }

                    if (!line.contains("/") && !line.contains("-") && num == 2){
                        Dates d = new Dates(lineArr[0], lineArr[1], lineArr[2]);
                    } else {
                        error(line);
                    }
                }
                
                index++;

            } else {
                index++;
            }
        }
    }

    public static void error(String s){
        System.out.println(s + " - INVALID: Input in incorrect format.");
    }
}
