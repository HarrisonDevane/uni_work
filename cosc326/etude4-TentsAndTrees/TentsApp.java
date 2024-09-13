package tents;
import java.util.*;

public class TentsApp {

    public static void printError(){
        System.out.println("Bad Format\n");
    }
    public static void main(String [] args){
        ArrayList<String> a1 = new ArrayList<String>();
        Scanner in = new Scanner(System.in);

        while(in.hasNextLine()){
            a1.add(in.nextLine());
        }

        in.close();

        for (int index = 0; index < a1.size(); index++){
            if (!(a1.get(index).isBlank())){
                ArrayList<String> a2 = new ArrayList<String>();

                while (index < a1.size() && !(a1.get(index).isBlank())){
                    a2.add(a1.get(index));
                    index++;
                }
                
                if (a2.size() >= 3){
                    String[] rowCountRevS = a2.get(a2.size()-2).split(" ");
                    String[] columnCountS = a2.get(a2.size()-1).split(" ");

                    int rowSizeCheck = 0;
                    int columnSizeCheck = 0;

                    for (int i = 0; i < a2.size() - 2; i++){
                        rowSizeCheck++;
                    }

                    for (int i = 0; i < a2.get(0).length(); i++){
                        columnSizeCheck++;
                    }

                    int rowCountLength = rowCountRevS.length;
                    int columnCountLength = columnCountS.length;

                    if (rowSizeCheck == rowCountLength && columnSizeCheck == columnCountLength){

                        Integer[] rowCountRev = new Integer[rowCountLength];
                        int[] rowCount = new int[rowCountLength];
                        int[] columnCount = new int[columnCountLength];

                        for (int i = 0; i < rowCountLength; i++){
                            rowCountRev[i] = Integer.parseInt(rowCountRevS[i]);
                        }

                        for (int i = 0; i < columnCountLength; i++){
                            columnCount[i] = Integer.parseInt(columnCountS[i]);
                        }

                        List<Integer> list = Arrays.asList(rowCountRev);
                        Collections.reverse(list);

                        for (int i = 0; i < list.size(); i++){
                            rowCount[i] = list.get(i).intValue();
                        }

                        String[][] gameBoard = new String[rowCountLength][columnCountLength];

                        for (int i = 0; i < gameBoard.length; i++){
                            String [] line = a2.get(i).split("");
                            for (int j = 0; j < gameBoard[i].length; j++){
                                gameBoard[i][j] = line[j];
                            }
                        }

                        Tents t = new Tents(gameBoard, rowCount, columnCount);
                        System.out.println();
                    } else {
                        printError();
                    }
                } else {
                    printError();
                }

            } else {
                index++;
            }
        }
    }
}