package howlong;
import java.util.*;

public class HowLongApp {
    public static void main(String [] args){
        ArrayList<String> a1 = new ArrayList<String>();
        Scanner in = new Scanner(System.in);

        while(in.hasNextLine()){
            a1.add(in.nextLine());
        }

        in.close();

        String output = "";

        for (int index = 0; index < a1.size(); index++){
            if (!(a1.get(index).isBlank())){
                ArrayList<String> a2 = new ArrayList<String>();

                while (index < a1.size() && !(a1.get(index).isBlank())){
                    a2.add(a1.get(index));  
                    index++;
                }
                HowLong h = new HowLong(a2);
                output += h.returnStrVal();

                if (index != a1.size()){
                    output += "\n";
                }
            } else {
                index++;
            }
        }

        System.out.print(output);
    }
}