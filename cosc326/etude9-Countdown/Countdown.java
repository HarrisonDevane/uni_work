package Countdown;
import java.util.*;
import java.lang.Math;

public class Countdown {
    int[] nums;
    char side;
    int desiredNum;
    String path = "";
    Boolean pathFound = false;

    public Countdown(int[] nums, char side, int desiredNum) {
        this.nums = nums;
        this.side = side;
        this.desiredNum = desiredNum;

        if (side == 'N'){
            properOrder(1, nums[0], -1);
        } else if (side == 'L'){
            leftToRight(1, nums[0]);
        }

        if (pathFound == false){
            printFailure();
        }
    }

    public String removeLastChar(String s){
        if (s.length() >= 1){
            return s.substring(0, s.length()-1);
        } else {
            return s;
        }
    }

    public void printFailure(){
        System.out.println(side + " " + desiredNum + " impossible");
    }

    public void printSuccess(String s){
        String n = side + " " + desiredNum + " " + nums[0];

        for (int i = 0; i < s.length(); i++){
            n += " " + s.charAt(i) + " " + nums[i + 1];
        }
        System.out.println(n);
    }

    public void leftToRight(int index, int currentNum){
        if (index == nums.length){
            if (currentNum == desiredNum){
                printSuccess(path);
                pathFound = true;
            }
            return;
        }

        if (pathFound == true){
            return;
        }

        int currentNumAdd = currentNum + nums[index];
        int currentNumMult = currentNum * nums[index];

        if (currentNumAdd <= desiredNum){
            path += "+";
            leftToRight(index + 1, currentNumAdd);
            path = removeLastChar(path);
        }

        if (currentNumMult <= desiredNum){
            path += "*";
            leftToRight(index + 1, currentNumMult);
            path = removeLastChar(path);
        }
    }

    public void properOrder(int index, int currentNum, int partialSum){
        if (index == nums.length){
            int finalNum = 0;

            if (partialSum == -1){
                finalNum = currentNum; 
            } else {
                finalNum = currentNum + partialSum;
            }

            if (finalNum == desiredNum){
                pathFound = true;
                printSuccess(path);
            }
            return;
        }

        if (pathFound == true){
            return;
        }

        int tempPart = 0;
        int tempCurrent = 0;


        path += "+";
        if (partialSum == -1){
            tempPart = nums[index];
            if (currentNum + tempPart <= desiredNum){
                properOrder(index + 1, currentNum, tempPart);
            }
        } else {
            tempCurrent = currentNum + partialSum;
            if (tempCurrent + nums[index] <= desiredNum){
                properOrder(index + 1, tempCurrent, nums[index]);
            }
        }
        path = removeLastChar(path);

        path += "*";
        if (partialSum == -1){
            currentNum = currentNum * nums[index];
            if (currentNum <= desiredNum){
                properOrder(index + 1, currentNum, -1);
            }
        } else {
            partialSum = partialSum * nums[index];
            if (currentNum + partialSum <= desiredNum){
                properOrder(index + 1, currentNum, partialSum);
            }
        }
        path = removeLastChar(path);
    }
}