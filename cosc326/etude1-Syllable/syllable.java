import java.util.Scanner;

public class syllable{
    private static int syllables;
    private static String vowels = "aeiouAEIOU";
    public static void main(String[] args){
       Scanner in = new Scanner(System.in);
            while(in.hasNextLine()){
                countVowelGroups(in.nextLine());
            } 
    }

    public static void countVowelGroups(String word){
        syllables =0;
        if(vowels.contains(""+word.charAt(0))){
            syllables++;
        }
        for (int index = 1; index < word.length(); index++){
            if (vowels.contains("" + word.charAt(index)) && !vowels.contains("" + word.charAt(index-1))){
                syllables++;
            }
        }
        endCases(word);
        return;
    }

    public static void endCases(String word){
        if(word.charAt((word.length()-1)) == 'e' &&  word.charAt((word.length()-2)) == 'l' ){
            if(!vowels.contains(""+ word.charAt(word.length()-3))){
                syllables++;
                output();
                return;
            }
            
        }
        if( word.charAt((word.length()-1)) == 'y' ){
            syllables++;
            output();
            return;
        }
        if(word.charAt((word.length()-1)) == 'e'){
            syllables--;
            output();
            return;
        }
        if(word.charAt((word.length()-1)) == 's' &&  word.charAt((word.length()-2)) == 'e' ){
            syllables--;
            output();
            return;
        }
        output();
        return;  
    }
    public static void output(){
        System.out.println(syllables);
    }

}