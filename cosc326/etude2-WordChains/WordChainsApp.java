package wordchains;
import java.util.*;

public class WordChainsApp {
    public static void printFailure(){
        System.out.println("Input in incorrect format");
    }

    public static Boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false;
        }
        return true;
    }
    public static void main(String [] args){
        ArrayList<String> a1 = new ArrayList<String>();
        ArrayList<String> a2 = new ArrayList<String>();
        ArrayList<String> d = new ArrayList<String>();

        Scanner in = new Scanner(System.in);

        while(in.hasNextLine()){
            a1.add(in.nextLine());
        }

        in.close();

        int index = 0;
        while (!a1.get(index).isBlank()){
            a2.add(a1.get(index));
            index++;
        }

        index++;

        int i = 0;
        while (index < a1.size()){
            d.add(a1.get(index));
            i++;
            index++;
        }
        for (int j = 0; j < a2.size(); j++){
            String[] words = a2.get(j).split("\\s+");
            if (words.length == 3){
                if (isInteger(words[2]) == true){
                    WordChains w = new WordChains(words, d);
                }
            } else if (words.length == 2){
                WordChains w = new WordChains(words, d);
            } else {
                printFailure();
            }
        }
    }
}