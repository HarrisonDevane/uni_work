import java.util.*;

public class Person {
    private int startTime;
    private int endTime;
    private int timeDiff;
    private int idNum;

    private int startFloor;
    private int endFloor;

    public Person(int idNum, int startTime, int startFloor, int endFloor) {
        this.idNum = idNum;
        this.startTime = startTime;
        this.startFloor = startFloor;
        this.endFloor = endFloor;
    }

    public int getStartFloor(){
        return startFloor;
    }

    public int getPersonNum(){
        return idNum;
    }

    public int getEndFloor(){
        return endFloor;
    }

    public int getStartTime(){
        return startTime;
    }

    public int giveTimeDiff(int end){
        endTime = end;
        timeDiff = endTime - startTime;
        return timeDiff;
    }
}