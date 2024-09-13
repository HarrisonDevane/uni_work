package tents;
import java.util.*;

public class Tents {
    private String[][] gameBoard;

    private int[][] treesIndex;
    private int[][] tentsIndex;
    private int[][] tentsIndexFinal;

    private int[] rowCount;
    private int[] colCount;

    private int numberOfRows;
    private int numberOfCols;

    private Boolean answerFound = false;

    public Tents( String[][] s, int[] r, int[] c){
        gameBoard = s;
        rowCount = r;
        colCount = c;

        numberOfRows = colCount.length;
        numberOfCols = rowCount.length;

        getIndex();
        dfs(0);

        if (tentsIndexFinal != null){
            for (int i = 0; i < tentsIndexFinal.length; i++){
                gameBoard[tentsIndexFinal[i][0]][tentsIndexFinal[i][1]] = "C";
            }
            printBoard();
        } else {
            System.out.println("Bad Format");
        }
    }

    public void getIndex(){
        int treeIndexSize = 0;
        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                if (gameBoard[i][j].equals("T")){
                    treeIndexSize++;
                }
            }
        }

        treesIndex = new int[treeIndexSize][2];
        tentsIndex = new int[treeIndexSize][2];

        treeIndexSize = 0;;
        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                if (gameBoard[i][j].equals("T")){
                    treesIndex[treeIndexSize][0] = i;
                    treesIndex[treeIndexSize][1] = j;
                    treeIndexSize++;
                }
            }
        }
    }

    public void dfs(int index){
        if (index == treesIndex.length && answerFound == false){
            answerFound = true;
            tentsIndexFinal = new int[tentsIndex.length][2];
            for (int i = 0; i < tentsIndex.length; i++){
                tentsIndexFinal[i][0] = tentsIndex[i][0];
                tentsIndexFinal[i][1] = tentsIndex[i][1];
            }
        }

        if (answerFound == false){
            int currentTreeX = treesIndex[index][0];
            int currentTreeY = treesIndex[index][1];

            int[][] newCoords = getPossibleTentCoords(currentTreeX, currentTreeY);
            for (int i = 0; i < newCoords.length; i++){
                int possibleX = newCoords[i][0];
                int possibleY = newCoords[i][1];

                gameBoard[possibleX][possibleY] = "C";
                tentsIndex[index][0] = possibleX;
                tentsIndex[index][1] = possibleY;

                dfs(index + 1);

                gameBoard[possibleX][possibleY] = ".";
                tentsIndex[index][0] = -1;
                tentsIndex[index][1] = -1;
            }
        }
    }

    public Boolean checkForAdjacentTents(int row, int col){
        if ((row - 1) >= 0 && (col - 1) >= 0){
            if (gameBoard[row - 1][col - 1].equals("C")){
                return false;
            }
        }

        if ((row - 1) >= 0){
            if (gameBoard[row - 1][col].equals("C")){
                return false;
            }
        }

        if ((row - 1) >= 0 && (col + 1) < numberOfRows){
            if (gameBoard[row - 1][col + 1].equals("C")){
                return false;
            }
        }

        if ((col - 1) >= 0){
            if (gameBoard[row][col - 1].equals("C")){
                return false;
            }
        }

        if ((col + 1) < numberOfRows){
            if (gameBoard[row][col + 1].equals("C")){
                return false;
            }
        }

        if (((col - 1) >= 0) && ((row + 1) < numberOfCols)){
            if (gameBoard[row + 1][col - 1].equals("C")){
                return false;
            }
        }

        if ((row + 1) < numberOfCols){
            if (gameBoard[row + 1][col].equals("C")){
                return false;
            }
        }

        if ((row + 1) < numberOfCols && (col + 1) < numberOfRows){
            if (gameBoard[row + 1][col + 1].equals("C")){
                return false;
            }
        }

        return true;
    }

    public Boolean checkTotals(int row, int col){
        int numRow = 0;
        int numCol = 0;

        for (int i = 0; i < numberOfCols; i++){
            if (gameBoard[row][i].equals("C")){
                numRow++;
            }
        }

        for (int j = 0; j < numberOfCols; j++){
            if (gameBoard[j][col].equals("C")){
                numCol++;
            }
        }

        if (numRow == rowCount[row]){
            return false;
        }

        if (numCol == colCount[col]){
            return false;
        }

        return true;
    }

    public int[][] getPossibleTentCoords(int row, int col){
        ArrayList<int[]> allAdjacent = new ArrayList<int[]>();
        if ((row - 1) >= 0){
            if (gameBoard[row - 1][col].equals(".")){
                int[] temp = new int[2];
                temp[0] = row - 1;
                temp[1] = col;
                allAdjacent.add(temp);
            }
        }

        if ((col - 1) >= 0){
            if (gameBoard[row][col - 1].equals(".")){
                int[] temp = new int[2];
                temp[0] = row;
                temp[1] = col - 1;
                allAdjacent.add(temp);
            }
        }

        if ((row + 1) < numberOfCols){
            if (gameBoard[row + 1][col].equals(".")){
                int[] temp = new int[2];
                temp[0] = row + 1;
                temp[1] = col;
                allAdjacent.add(temp);
            }
        }

        if ((col + 1) < numberOfRows){
            if (gameBoard[row][col + 1].equals(".")){
                int[] temp = new int[2];
                temp[0] = row;
                temp[1] = col + 1;
                allAdjacent.add(temp);
            }
        }
        
        ArrayList<int[]> temp = new ArrayList<int[]>();

        for (int i = 0; i < allAdjacent.size(); i++){
            int rowTemp = allAdjacent.get(i)[0];
            int colTemp = allAdjacent.get(i)[1];

            if (checkTotals(rowTemp, colTemp) == true && checkForAdjacentTents(rowTemp, colTemp) == true){
                int[] tempArr = new int[2];
                tempArr[0] = rowTemp;
                tempArr[1] = colTemp;
                temp.add(tempArr);
            }
        }

        int[][] finalArr = new int[temp.size()][2];
        for (int i = 0; i < temp.size(); i++){
            finalArr[i][0] = temp.get(i)[0];
            finalArr[i][1] = temp.get(i)[1];
        }
        
        return finalArr;
    }

    public void printBoard(){
        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                System.out.print(gameBoard[i][j]);
            }
            System.out.println();
        }
    }
}