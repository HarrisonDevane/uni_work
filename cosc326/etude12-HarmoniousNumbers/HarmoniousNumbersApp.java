import java.util.*;
import java.lang.Math;

public class HarmoniousNumbersApp {
    public static int sumOfFactors(int n){
        int total = 0;
        for (int i = 2; i <= Math.sqrt(n); i++){
            if (n % i == 0){
                total += i;
                if (i != n/i){
                    total += n/i;
                }
            }
        }
        return total;
    }

    public static void main(String[] args){
        ArrayList<Integer>  nums = new ArrayList<Integer>();

        int number = 5;
        while (number < 2000000){
            int sumOfFactors = sumOfFactors(number);
            int factorsOfNum = sumOfFactors(sumOfFactors);

            if (number == factorsOfNum){
                Boolean isContained = false;
                for (int i = 0; i < nums.size(); i++){
                    if (nums.get(i).intValue() == number){
                        isContained = true;
                    } else if (nums.get(i).intValue() == sumOfFactors){
                        isContained = true;
                    }
                }

                if (isContained == false){
                    nums.add(number);
                }
            }
            number++;
        }

        int [] numsArr = new int[nums.size()];

        for (int i = 0; i < nums.size(); i++){
            numsArr[i] = nums.get(i);
        }

        Arrays.sort(numsArr);

        for (int i = 0; i < numsArr.length; i++){
            System.out.println(numsArr[i] + " " + sumOfFactors(numsArr[i]));
        }
    }
}