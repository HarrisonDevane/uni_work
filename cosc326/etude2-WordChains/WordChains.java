package wordchains;
import java.util.*;
import java.lang.*;

public class WordChains {
    private String[] words;
    private Boolean pathFound = false;


    public WordChains(String[] words, ArrayList<String> d){
        this.words = words;
        ArrayList<String> dict = new ArrayList<String>(d);
        
        String[] s = null;

        if (checkWords(dict) == true){
            ArrayList<String> currentDict = removeWords(dict, words[0].length());

            if (words.length == 2){
                s = shortestChain(words[0], words[1], currentDict);
            } else if (words.length == 3){
                s = shortestChainNum(words[0], words[1], currentDict, Integer.parseInt(words[2]));
            }
        }

        if (s != null){
            printSuccess(s);
        } else {
            printFailure();
        }
    }

    public void printSuccess(String[] s){
        System.out.print(words[0] + " ");
        for (int i = 1; i < s.length; i++){
            System.out.print(s[i] + " ");
        }
        System.out.println();
    }

    public void printFailure(){
        System.out.print(words[0] + " " + words[1]);
        if (words.length == 3){
            System.out.print(" " + words[2]);
        }
        System.out.print(" impossible");
        System.out.println();
    }

    public ArrayList<String> removeWords(ArrayList<String> s1, int size){
        ArrayList<String> s2 = new ArrayList<String>();
        for (int i = 0; i < s1.size(); i++){
            if (s1.get(i).length() == size){
                s2.add(s1.get(i));
            }
        }
        return s2;
    }

    public Boolean checkWords(ArrayList<String> dict){
        Boolean check1 = false;
        Boolean check2 = false;
        Boolean checkSize = false;

        for (int i = 0; i < dict.size(); i++){
            if (dict.get(i).equals(words[0])){
                check1 = true;
            }

            if (dict.get(i).equals(words[1])){
                check2 = true;
            }
        }

        if (words[0].length() == words[1].length()){
            checkSize = true;
        }

        if (check1 == true && check2 == true && checkSize == true){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> similarWords(String s1, ArrayList<String> currentDict){
        ArrayList<String> simWords = new ArrayList<String>();
        int wordLength = s1.length();

        for (int i = 0; i < currentDict.size(); i++){
            int numOfSameChars = 0;
            String s2 = currentDict.get(i);
            
            for(int j = 0; j < wordLength; j++) {
                if(s1.charAt(j) == s2.charAt(j)) {
                    numOfSameChars++;
                }
            }

            if (numOfSameChars == wordLength - 1){
                simWords.add(s2);
            }
        }
        return simWords;
    }

    public String[] shortestChainNum(String startWord, String endWord, ArrayList<String> dict, int size){
        Stack<String[]> stack = new Stack<>();

        String[] s = new String [1];
        s[0] = words[0];

        stack.push(s);

        while (stack.size() != 0){
            String[] currentArr = stack.pop();
            String currentWord = currentArr[currentArr.length - 1];

            if (currentWord.equals(endWord) && currentArr.length == size){
                return currentArr;
            }

            if (currentArr.length < size){
                ArrayList<String> currentDict = new ArrayList<String>(dict);
                for (int i = 0; i < currentArr.length; i++){
                    currentDict.remove(currentArr[i]);
                }

                ArrayList<String> similarWords = similarWords(currentWord, currentDict);

                for (int i = 0; i < similarWords.size(); i++){
                    String[] newStack = new String[currentArr.length + 1];

                    for (int j = 0; j < currentArr.length; j++){
                        newStack[j] = currentArr[j];
                    }

                    newStack[newStack.length - 1] = similarWords.get(i);

                    //for (int j = 0; j < newStack.length; j++){
                        //System.out.print(newStack[j] + " ");
                    //}
                    //System.out.println();

                    stack.push(newStack);
                }
            }
        }

        return null;
    }

    public String[] shortestChain(String startWord, String endWord, ArrayList<String> dict){
        Queue<String[]> q = new LinkedList<>();

        String[] s = new String [1];
        s[0] = words[0];

        q.add(s);

        while (q.size() != 0){
            String[] currentArr = q.remove();
            String currentWord = currentArr[currentArr.length - 1];
            
            //for (int i = 0; i < currentArr.length; i++){
                //System.out.print(currentArr[i] + " ");
            //}
            //System.out.println();

            if (currentWord.equals(endWord)){
                return currentArr;
            }

            ArrayList<String> similarWords = similarWords(currentWord, dict);

            for (int i = 0; i < similarWords.size(); i++){
                String[] newQueue = new String[currentArr.length + 1];
                for (int j = 0; j < currentArr.length; j++){
                    newQueue[j] = currentArr[j];
                }
                
                dict.remove(similarWords.get(i));
                newQueue[newQueue.length - 1] = similarWords.get(i);
                q.add(newQueue);
            }
        }

        return null;
    }
}