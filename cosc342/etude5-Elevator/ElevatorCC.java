import java.util.*;

public class ElevatorCC{
    static ArrayList<Person> personQueue = new ArrayList<Person>();
    static ArrayList<ArrayList<Person>> floors = new ArrayList<ArrayList<Person>>(10);

    static ArrayList<Person> elevator = new ArrayList<Person>();
    static int elevatorDirection = -1;
    static int elevatorPreviousDirection = 1;
    static int elevatorCurrentFloor = 0;

    static Boolean entered = true;

    static int currentTick = 0;
    static int tickMultiplier = 5;

    static int maxTicks = 720;
    static int loadSize = 20;
    static int currentPerson = 1;

    static int timeTotal = 0;
    static int peopleTotal = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++){
            ArrayList<Person> a = new ArrayList<Person>();
            floors.add(a);
        }

        while (currentTick < maxTicks){

            printTick();
            createPerson();

            entered = true;
            collectiveControl();

            currentTick++;
            System.out.println();
        }

        System.out.println();
        printQueue();
        printFloors();
        printElevator();
        printTotal();
    }

    public static void leaveElevator(){
        for (int i = 0; i < elevator.size(); i++){
            if (elevator.get(i).getEndFloor() == elevatorCurrentFloor){
                timeTotal += elevator.get(i).giveTimeDiff(currentTick);
                peopleTotal++;

                System.out.print(", Person " + elevator.get(i).getPersonNum() + " has left the elevator");
                elevator.remove(i);
            }
        }
        if (elevator.size() == 0){
            elevatorPreviousDirection = 1;
        }
    }

    public static void enterElevator(){
        int floorSize = floors.get(elevatorCurrentFloor).size();
        ArrayList<Person> floorsTemp = new ArrayList<Person>();

        for (int i = 0; i < floorSize; i++){
            if (elevator.size() < 4){
                Person p1 = floors.get(elevatorCurrentFloor).get(i);
                int personMovingUp;
                if  (p1.getStartFloor() < p1.getEndFloor()){
                    personMovingUp = 2;
                } else {
                    personMovingUp = 0;
                }

                if ((personMovingUp == elevatorPreviousDirection) || (elevatorPreviousDirection == 1)){
                    System.out.print(", Person " + p1.getPersonNum() + " entered the elevator");
                    elevator.add(p1);
                    personQueue.remove(p1);
                    floorsTemp.add(p1);
                } else {
                    entered = false;
                }
            }
        }
        
        for (int i = 0; i < floorsTemp.size(); i++){
            floors.get(elevatorCurrentFloor).remove(floorsTemp.get(i));
        }
    }

    public static void openAndClose(){
        System.out.print(", Doors opening\n");

        currentTick++;
        printTick();
        createPerson();
        leaveElevator();
        enterElevator();
        System.out.println();

        currentTick++;
        printTick();
        createPerson();

        System.out.print(", Doors closing");

        elevatorDirection = -1;

        collectiveControl();
    }

    public static void goToFloor(){
        if (elevatorDirection == 2){
            elevatorCurrentFloor++;
            if (elevator.size() == 0){
                elevatorPreviousDirection = 1;
            } else {
                elevatorPreviousDirection = 2;
            }
        } else if (elevatorDirection == 0){
            elevatorCurrentFloor--;
            if (elevator.size() == 0){
                elevatorPreviousDirection = 1;
            } else {
                elevatorPreviousDirection = 0;
            }
        } else if (elevatorDirection == 1){
            openAndClose();
        } else if (elevatorDirection == -1){
            elevatorPreviousDirection = 1;
        }
    }

    public static void collectiveControl(){
        if (currentTick > maxTicks){
            return;
        }

        if (elevator.size() == 0 && personQueue.size() == 0){
            elevatorDirection = -1;
        } else if (elevator.size() == 0){
            if (floors.get(elevatorCurrentFloor).size() != 0){
                elevatorDirection = 1;
            } else {
                if (elevatorPreviousDirection == 2 || elevatorPreviousDirection == 1){
                    Boolean hasRequests = false;
                    for (int i = 0; i < elevatorCurrentFloor; i++){
                        if (floors.get(i).size() != 0){
                            hasRequests = true;
                        }
                    }
                    if (hasRequests == true){
                        elevatorDirection = 0;
                    }
                }
                
                if (elevatorPreviousDirection == 0 || elevatorPreviousDirection == 1){
                    Boolean hasRequests = false;
                    for (int i = 9; i > elevatorCurrentFloor; i--){
                        if (floors.get(i).size() != 0){
                            hasRequests = true;
                        }
                    }
                    if (hasRequests == true){
                        elevatorDirection = 2;
                    }
                }
            }
        } else if (elevator.size() != 0){
            for (int i = 0; i < elevator.size(); i++){
                if (elevator.get(i).getEndFloor() == elevatorCurrentFloor){
                    elevatorDirection = 1;
                    goToFloor();
                    return;
                }
            }

            if (floors.get(elevatorCurrentFloor).size() != 0 && entered == true && elevator.size() != 4){
                elevatorDirection = 1;
            } else {
                int personMovingUp;
                if  (elevator.get(0).getStartFloor() < elevator.get(0).getEndFloor()){
                    personMovingUp = 2;
                } else {
                    personMovingUp = 0;
                }
                elevatorDirection = personMovingUp;
            }
        }
        goToFloor();
    }

    public static void createPerson(){
        Random r = new Random();

        if (r.nextInt(loadSize / tickMultiplier) == 0){
            Boolean floorTrue = false;

            while (floorTrue == false){
                int startFloor = r.nextInt(10);
                int endFloor = r.nextInt(10);

                if (startFloor != endFloor){
                    Person p = new Person(currentPerson, currentTick, startFloor, endFloor);
                    personQueue.add(p);
                    floors.get(startFloor).add(p);
                    currentPerson++;
                    floorTrue = true;

                    System.out.print(", Adding person " + p.getPersonNum() + " to queue");
                }
            }
        }
    }

    public static void printTick(){
        System.out.print("Current Time: " + currentTick * tickMultiplier + ", Elevator on floor: " + elevatorCurrentFloor);
    }

    public static void printElevator(){
        System.out.print("People currenetly in elevator: ");
        for (int i = 0; i < elevator.size(); i++){
            System.out.print("Person: "+ elevator.get(i).getPersonNum());
            System.out.print(", Start Floor: " + elevator.get(i).getStartFloor());
            System.out.print(", End Floor :" + elevator.get(i).getEndFloor() + "\n");
        }
        System.out.println();
    }

    public static void printFloors(){
        for (int i = 0; i < 10; i++){
            System.out.print("Floor " + i + ": ");
            for (int j = 0; j < floors.get(i).size(); j++){
                System.out.print(floors.get(i).get(j).getPersonNum() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printQueue(){
        System.out.println("People waiting: ");
        for (int i = 0; i < personQueue.size(); i++){
            System.out.print("Person: "+ personQueue.get(i).getPersonNum());
            System.out.print(", Start Floor: " + personQueue.get(i).getStartFloor());
            System.out.print(", End Floor :" + personQueue.get(i).getEndFloor() + "\n");
        }
        System.out.println();
    }

    public static void printTotal(){
        for (int i = 0; i < personQueue.size(); i++){
            timeTotal += personQueue.get(i).giveTimeDiff(currentTick);
        }
        double av = (double) timeTotal / (double) peopleTotal;
        System.out.println("Total Time: " + (currentTick * tickMultiplier) + ", People Served: " + peopleTotal);
        System.out.println("Average waiting time: " + av);
    }
}