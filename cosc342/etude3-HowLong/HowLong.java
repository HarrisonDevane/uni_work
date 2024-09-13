package howlong;
import java.util.*;
import java.math.*;

public class HowLong {
    private int[] intArr;
    private String[] wordChain;
    private char[] letterArr;
    private int arr_size;

    public HowLong(ArrayList<String> a){
        arr_size = a.size();
        intArr = new int[arr_size];
        wordChain = new String[arr_size];
        letterArr = new char[arr_size];


        for (int i = 0; i < arr_size; i++){
            String[] parts = a.get(i).split("\\s+");
            letterArr[i] = parts[0].charAt(0);
            wordChain[i] = parts[1];
        }

        initialCheck();
        loopArr();
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public void initialCheck(){
        for (int i = 0; i < arr_size; i++){
            if (isInteger(wordChain[i]) == true){
                intArr[i] = Integer.parseInt(wordChain[i]);
            } else {
                checkLetterValid(wordChain[i], i);
            }
        }
    }

    public String returnStrVal(){
        String s = "";
        for (int i = 0; i < arr_size; i++){
            if (intArr[i] != -1){
                s += letterArr[i] + " " + intArr[i] + "\n";
            } else {
                s += letterArr[i] + " " + "NaN\n";
            }
        }
        return s;
    }

    public boolean checkArrEmpty(){
        for(int i = 0; i < arr_size; i++){
            if (intArr[i] == 0){
                return true;
            }
        }
        return false;
    }

    public boolean checkString(String s){
        if (isInteger(s) == false){
            for (int i = 0; i < s.length(); i++){
                int index = 0;
                for (int j = 0; j < arr_size; j++){
                    if (s.charAt(i) == letterArr[j]){
                        index = j;
                    }
                }

                if (intArr[index] == 0 || intArr[index] == -1){
                    return false;
                }
            }
        }
        return true;
    }
    
    public Boolean checkLetterValid(String s, int index){
        String charAsString = new String(letterArr);
        for (int i = 0; i < s.length(); i++){
            if (!charAsString.contains("" + s.charAt(i))){
                intArr[index] = -1;
                return false;
            }
        }
        return true;
    }

    public void addValues(String s, int index){
        outerloop: {
            for (int i = 0; i < s.length(); i++){
                for (int j = 0; j < arr_size; j++){
                    if (s.charAt(i) == letterArr[j]){
                        if (checkOverflow(intArr[index], intArr[j]) == false){
                            intArr[index] += intArr[j];
                        } else {
                            intArr[index] = -1;
                            break outerloop;
                        }
                    }
                }
            }
        }
    }

    public void endCase(){
        for (int i = 0; i < arr_size; i++){
            if (intArr[i] == 0){
                intArr[i] = -1;
            }
        }
    }

    public boolean checkOverflow(int a, int b){
        BigInteger temp1 = BigInteger.valueOf(a);
        BigInteger temp2 = BigInteger.valueOf(b);
        BigInteger sum = temp1.add(temp2);
        int compareVal = sum.compareTo(BigInteger.valueOf(Integer.MAX_VALUE));

        if (compareVal == 0 || compareVal == 1){
            return true;
        } else {
            return false;
        }
    }

    public void loopArr(){
        while (checkArrEmpty() == true){
            int[] intArrCopy = intArr.clone();
            for (int i = 0; i < arr_size; i++){
                if ((checkString(wordChain[i]) == true && (intArr[i] == 0))){
                    addValues(wordChain[i], i);
                }
            }

            if (Arrays.equals(intArr, intArrCopy)){
                endCase();
                break;
            }
        }
    }
}