package tents;
import java.util.*;

public class TentsOld {
    private String[][] gameBoard;
    private Boolean solutionFound = false;
    private int[] rowCount;
    private int[] colCount;
    private int step = 1;
    private int numberOfRows;
    private int numberOfCols;

    public TentsOld(String[][] s, int[] r, int[] c){
        String[][] g = s;
        rowCount = r;
        colCount = c;

        numberOfRows = colCount.length;
        numberOfCols = rowCount.length;

        //System.out.println("Step " + step + ": Input:\n");
        //step++;
        //printBoard(g);

        //System.out.println("\nStep " + step + ": Ignore zero rows and columns:\n");
        //step++;
        g = ignoreZero(g);
        //printBoard(g);

        //System.out.println("\nStep " + step + ": Exclude open land:\n");
        //step++;
        g = excludeOpenLand(g);
        //printBoard(g);

        loopSteps(g);

    }

    public String[][] ignoreZero(String[][] g){ //Step 2: Ignore zero rows and columns
        for (int i = 0; i < numberOfCols; i++){
            if (rowCount[i] == 0){
                for (int j = 0; j < numberOfRows; j++){
                    if (g[i][j].equals(".")){
                        g[i][j] = "0";
                    }
                }
            }
        }

        for (int i = 0; i < numberOfRows; i++){
            if (colCount[i] == 0){
                for (int j = 0; j < numberOfCols; j++){
                    if (g[j][i].equals(".")){
                        g[j][i] = "0";
                    }
                }
            }
        }
        return g;
    }

    public String[][] excludeOpenLand(String[][] g){ //Step 3: Exclude open land
        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                if (g[i][j].equals(".")){
                    if (checkOpenSquares(i, j, "T", g) == 0){
                        g[i][j] = "0";
                    }
                }
            }
        }
        return g;
    }

    public int checkOpenSquares(int i, int j, String s, String[][] g){
        int num = 0;
        if ((i - 1) >= 0){ // Check left of square
            if (g[i-1][j].equals(s)){
                num++;
            }
        }

        if ((j - 1) >= 0){ // Check above square
            if (g[i][j-1].equals(s)){
                num++;
            }
        }

        if ((i + 1) < numberOfCols){ // Check right of square
            if (g[i+1][j].equals(s)){
                num++;
            }
        }

        if ((j + 1) < numberOfRows){ // Check below square
            if (g[i][j+1].equals(s)){
                num++;
            }
        }
        
        return num;
    }

    public String[][] checkAdjacentTrees(int i, int j, String[][] g){
        if ((i - 1) >= 0){
            if (g[i-1][j].equals(".") && checkOpenSquares(i-1, j, "T", g) == 1){
                g[i-1][j] = "0";
            }
        }

        if ((j - 1) >= 0){
            if (g[i][j-1].equals(".") && checkOpenSquares(i, j-1, "T", g) == 1){
                g[i][j-1] = "0";
            }
        }

        if ((i + 1) < numberOfCols){
            if (g[i+1][j].equals(".") && checkOpenSquares(i+1, j, "T", g) == 1){
                g[i+1][j] = "0";
            }
        }

        if ((j + 1) < numberOfRows){
            if (g[i][j+1].equals(".") && checkOpenSquares(i, j+1, "T", g) == 1){
                g[i][j+1] = "0";
            }
        }
        return g;
    }

    public String[][] checkNumAdjacentTrees(int i, int j, String[][] g){
        int numTrees = 0;
        int treeRow = 0;
        int treeCol = 0;

        if ((i - 1) >= 0){ // Check left of square
            if (g[i-1][j].equals("T")){
                treeRow = i-1;
                treeCol = j;
                numTrees++;
            }
        }

        if ((j - 1) >= 0){ // Check above square
            if (g[i][j-1].equals("T")){
                treeRow = i;
                treeCol = j-1;
                numTrees++;
            }
        }

        if ((i + 1) < numberOfCols){ // Check right of square
            if (g[i+1][j].equals("T")){
                treeRow = i+1;
                treeCol = j;
                numTrees++;
            }
        }

        if ((j + 1) < numberOfRows){ // Check below square
            if (g[i][j+1].equals("T")){
                treeRow = i;
                treeCol = j+1;
                numTrees++;
            }
        }

        if (numTrees == 1){
            g = checkAdjacentTrees(treeRow, treeCol, g);
        }
        return g;
    }

    public String[][] checkAdjacentSquares(int i, int j, String[][] g){
        if ((i - 1) >= 0 && (j - 1) >= 0){ // Check top left of square
            if (g[i-1][j-1].equals(".")){
                g[i-1][j-1] = "0";
            }
        }

        if ((i - 1) >= 0){ // Check left of square
            if (g[i-1][j].equals(".")){
                g[i-1][j] = "0";
            }
        }

        if ((i - 1) >= 0 && (j + 1) < numberOfRows){ // Check bottom left of square
            if (g[i-1][j+1].equals(".")){
                g[i-1][j+1] = "0";
            }
        }

        if ((j - 1) >= 0){ // Check above square
            if (g[i][j-1].equals(".")){
                g[i][j-1] = "0";
            }
        }

        if ((j + 1) < numberOfRows){ // Check below square
            if (g[i][j+1].equals(".")){
                g[i][j+1] = "0";
            }
        }

        if (((j - 1) >= 0) && ((i + 1) < numberOfCols)){
            if (g[i+1][j-1].equals(".")){
                g[i+1][j-1] = "0";
            }
        }

        if ((i + 1) < numberOfCols){
            if (g[i+1][j].equals(".")){
                g[i+1][j] = "0";
            }
        }

        if ((i + 1) < numberOfCols && (j + 1) < numberOfRows){
            if (g[i+1][j+1].equals(".")){
                g[i+1][j+1] = "0";
            }
        }

        return g;
    }

    public String[][] changeTent(int i, int j, String[][] g){
        g[i][j] = "C";
        g = checkAdjacentSquares(i, j, g);
        g = checkNumAdjacentTrees(i, j, g);
        return g;
    }

    public String[][] checkDiagonalCells(String[][] g){
        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                if (g[i][j].equals(".")){
                    int rowCountAboveD = 0;
                    int rowCountAbove = 0;

                    int rowCountBelowD = 0;
                    int rowCountBelow = 0;

                    int colCountLeft = 0;
                    int colCountLeftD = 0;

                    int colCountRight = 0;
                    int colCountRightD = 0;

                    if ((i - 1) >= 0 && (j - 1) >= 0){
                        if (g[i-1][j-1].equals(".")){
                            if (i - 2 >= 0 && g[i-2][j-1].equals(".")){
                                colCountLeftD--;
                            }
                            if (j - 2 >= 0 && g[i-1][j-2].equals(".")){
                                rowCountAboveD--;
                            }
                            rowCountAboveD++;
                            colCountLeftD++;
                        }
                    }

                    if ((i - 1) >= 0 && (j + 1) < numberOfRows){
                        if (g[i-1][j+1].equals(".")){
                            if (i - 2 >= 0 && g[i-2][j+1].equals(".")){
                                colCountRightD--;
                            }
                            if (j + 2 < numberOfRows && g[i-1][j+2].equals(".")){
                                rowCountAboveD--;
                            }
                            rowCountAboveD++;
                            colCountRightD++;
                        }
                    }

                    if ((i + 1) < numberOfCols && (j + 1) < numberOfRows){
                        if (g[i+1][j+1].equals(".")){
                            if (i + 2 < numberOfCols && g[i+2][j+1].equals(".")){
                                colCountRightD--;
                            }
                            if (j + 2 < numberOfRows && g[i+1][j+2].equals(".")){
                                rowCountBelowD--;
                            }
                            rowCountBelowD++;
                            colCountRightD++;
                        }
                    }

                    if ((i + 1) < numberOfCols && (j - 1) >= 0){
                        if (g[i+1][j-1].equals(".")){
                            if (i + 2 < numberOfCols && g[i+2][j-1].equals(".")){
                                colCountLeftD--;
                            }
                            if (j - 2 >= 0 && g[i+1][j-2].equals(".")){
                                rowCountBelowD--;
                            }
                            rowCountBelowD++;
                            colCountLeftD++;
                        }
                    }

                    if ((i - 1) >= 0){
                        for (int k = 0; k < numberOfRows; k++){
                            if (g[i-1][k].equals(".") || g[i-1][k].equals("C")){
                                rowCountAbove++;
                            }
                        }

                        if ((rowCountAbove - rowCountAboveD  - numPairsRow(i-1, g) < rowCount[i-1])){
                            g[i][j] = "0";
                        }
                    }

                    if ((i + 1) < numberOfCols){
                        for (int k = 0; k < numberOfRows; k++){
                            if (g[i+1][k].equals(".") || g[i+1][k].equals("C")){
                                rowCountBelow++;
                            }
                        }

                        if (rowCountBelow - rowCountBelowD  - numPairsRow (i+1, g) < rowCount[i+1]){
                            g[i][j] = "0";
                        }
                    }

                    if ((j - 1) >= 0){
                        for (int k = 0; k < numberOfCols; k++){
                            if (g[k][j-1].equals(".") || g[k][j-1].equals("C")){
                                colCountLeft++;
                            }
                        }

                        if (colCountLeft - colCountLeftD - numPairsColumn(j - 1, g) < colCount[j-1]){
                            g[i][j] = "0";
                        }
                    }

                    if ((j + 1) < numberOfCols){
                        for (int k = 0; k < numberOfCols; k++){
                            if (g[k][j+1].equals(".") || g[k][j+1].equals("C")){
                                colCountRight++;
                            }
                        }

                        if (colCountRight - colCountRightD - numPairsColumn(j + 1, g) < colCount[j+1]){
                            g[i][j] = "0";
                        }
                    }
                }
            }
        }
        return g;
    }

    public String[][] checkLonelytrees(String[][] g){
        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                if (g[i][j].equals("T") && checkOpenSquares(i, j, ".", g) == 1 && checkOpenSquares(i, j, "C", g) == 0){
                    if ((i - 1) >= 0){
                        if (g[i-1][j].equals(".")){
                            g = changeTent(i-1, j, g);
                        }
                    }
            
                    if ((j - 1) >= 0){
                        if (g[i][j-1].equals(".")){
                            g = changeTent(i, j-1, g);
                        }
                    }
            
                    if ((i + 1) < numberOfCols){
                        if (g[i+1][j].equals(".")){
                            g = changeTent(i+1, j, g);
                        }
                    }
            
                    if ((j + 1) < numberOfRows){
                        if (g[i][j+1].equals(".")){
                            g = changeTent(i, j+1, g);
                        }
                    }
                }
            }
        }
        return g;
    }

    public int numPairsRow(int row, String[][] g){
        int x = 0;
        for (int i = 0; i < numberOfRows; i++){
            if (g[row][i].equals(".")){
                if (i+1 < numberOfRows){
                    if (g[row][i+1].equals(".")){
                        x++;
                        i++;
                    }
                }
            }
        }
        return x;
    }

    public int numPairsColumn(int col, String[][] g){
        int x = 0;
        for (int i = 0; i < numberOfCols; i++){
            if (g[i][col].equals(".")){
                if (i+1 < numberOfCols){
                    if (g[i+1][col].equals(".")){
                        x++;
                        i++;
                    }
                }
            }
        }
        return x;
    }

    public String[][] fillInCamps(String[][] g){ //Step 4: Fill in camp sites based on hints.
        g = checkLonelytrees(g);
        g = checkDiagonalCells(g);

        for (int i = 0; i < numberOfCols; i++){
            int rowSize = 0;
            int campSize = 0;
            int numPairs = 0;

            for (int j = 0; j < numberOfRows; j++){
                if (g[i][j].equals(".")){
                    if (j+1 < numberOfRows){
                        if (g[i][j+1].equals(".")){
                            rowSize++;
                            numPairs++;
                            j++;
                        }
                    }
                    rowSize++;
                } else if (g[i][j].equals("C")){
                    rowSize++;
                    campSize++;
                }
            }

            if (rowSize == rowCount[i]){
                for (int j = 0; j < numberOfRows; j++){
                    if (g[i][j].equals(".")){
                        g = changeTent(i, j, g);
                    }
                }
            } else if (campSize == rowCount[i]){
                for (int j = 0; j < numberOfRows; j++){
                    if (g[i][j].equals(".")){
                        g[i][j] = "0";
                    }
                }
            } else if (rowSize - numPairs == rowCount[i]){
                for (int j = 0; j < numberOfRows; j++){
                    if (g[i][j].equals(".")){
                        Boolean hasNeighbours = false;
                        if (j - 1 >= 0){
                            if (g[i][j-1].equals(".")){
                                hasNeighbours = true;
                            }
                        }   

                        if (j + 1 < numberOfRows){
                            if (g[i][j+1].equals(".")){
                                hasNeighbours = true;
                            }
                        }

                        if (hasNeighbours == false){
                            g = changeTent(i, j, g);
                        }
                    }
                }
            }
        }

        for (int j = 0; j < numberOfRows; j++){
            int colSize = 0;
            int campSize = 0;
            int numPairs = 0;

            for (int i = 0; i < numberOfCols; i++){
                if (g[i][j].equals(".")){
                    if (i + 1 < numberOfCols){
                        if (g[i + 1][j].equals(".")){
                            colSize++;
                            numPairs++;
                            i++;
                        }
                    }
                    colSize++;
                } else if (g[i][j].equals("C")){
                    colSize++;
                    campSize++;
                }
            }

            if (colSize == colCount[j]){
                for (int i = 0; i < numberOfCols; i++){
                    if (g[i][j].equals(".")){
                        g = changeTent(i, j, g);
                    }
                }
            } else if (campSize == colCount[j]){
                for (int i = 0; i < numberOfCols; i++){
                    if (g[i][j].equals(".")){
                        g[i][j] = "0";
                    }
                }
            } else if (colSize - numPairs == colCount[j]){
                for (int i = 0; i < numberOfCols; i++){
                    if (g[i][j].equals(".")){
                        Boolean hasNeighbours = false;
                        if (i - 1 >= 0){
                            if (g[i-1][j].equals(".")){
                                hasNeighbours = true;
                            }
                        }   

                        if (i + 1 < numberOfCols){
                            if (g[i+1][j].equals(".")){
                                hasNeighbours = true;
                            }
                        }

                        if (hasNeighbours == false){
                            g = changeTent(i, j, g);
                        }
                    }
                }
            }
        }
        return g;
    }

    public Boolean checkAdjCamps(String[][] g){
        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                if (g[i][j].equals("C")){
                    if (checkOpenSquares(i, j, "T", g) != 1){
                        int numTreesAttached = 0;
                        if (i - 1 >= 0){
                            if (g[i-1][j].equals("T")){
                                if (checkOpenSquares(i-1, j, "C", g) == 1){
                                    numTreesAttached++;
                                }   
                            }
                        }
                        
                        if (j - 1 >= 0){
                            if (g[i][j-1].equals("T")){
                                if (checkOpenSquares(i, j-1, "C", g) == 1){
                                    numTreesAttached++;
                                }   
                            }
                        }
                        
                        if (i + 1 < numberOfCols){
                            if (g[i+1][j].equals("T")){
                                if (checkOpenSquares(i+1, j, "C", g) == 1){
                                    numTreesAttached++;
                                }   
                            }
                        }
                        
                        if (j + 1 < numberOfRows){
                            if (g[i][j+1].equals("T")){
                                if (checkOpenSquares(i, j+1, "C", g) == 1){
                                    numTreesAttached++;
                                }   
                            }
                        }
                                            
                        if (numTreesAttached >= 2){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void dfs(String[][] g, int index){
        if (arrayEmpty(g) == true){
            index--;
            //System.out.println("Moving Up");
            return;
        }

        index++;

        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                String[][] gCpy = arrayCopy(g);
                if (gCpy[i][j].equals(".") && solutionFound == false && index < 3){
                    changeTent(i, j, gCpy);

                    //System.out.println("\nChanging index at: " + i + " " + j + ":\n");
                    //System.out.println("Current Depth: " + index);

                    Boolean currentT = false;

                    while (arrayEmpty(gCpy) == false && currentT == false){
                        String[][] gCpy2 = arrayCopy(gCpy);

                        fillInCamps(gCpy);
            
                        if (arrayCompare(gCpy, gCpy2) == true){
                            currentT = true;
                            //printBoard(gCpy);
                            dfs(gCpy, index);
                        }
                    }

                    Boolean treesTrue = true;

                    for (int k = 0; k < numberOfCols; k++){
                        for (int l = 0; l < numberOfRows; l++){
                            if (gCpy[k][l].equals("T")){
                                if(checkOpenSquares(k, l, "C", gCpy) == 0){
                                    treesTrue = false;
                                }
                            }
                        }
                    }

                    if (arrayEmpty(gCpy) == true){
                        if (checkNumberOfTentsAndTrees(gCpy) == true && treesTrue == true && checkAdjCamps(gCpy) == true){
                            solutionFound = true;
                            gameBoard = gCpy;
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean arrayEmpty(String[][] g){
        for (int i = 0; i < g.length; i++){
            for (int j = 0; j < g[i].length; j++){
                if (g[i][j].equals(".")){
                    return false;
                }
            }
        }
        return true;
    }

    public String[][] arrayCopy(String[][] s){
        String s2[][] = new String[s.length][s[0].length];
        for (int i = 0; i < s.length; i++){
            for (int j = 0; j < s[1].length; j++){
                s2[i][j] = s[i][j];
            }
        }
        return s2;
    }

    public boolean arrayCompare(String[][] s1, String[][] s2){
        for (int i = 0; i < s1.length; i++){
            for (int j = 0; j < s1[i].length; j++){
                if (!s1[i][j].equals(s2[i][j])){
                    return false;
                }
            }
        }
        return true;
    }

    public Boolean checkNumberOfTentsAndTrees(String[][] g){
        int cCount = 0;
        int tCount = 0;
        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                if (g[i][j].equals("C")){
                    cCount++;
                } else if (g[i][j].equals("T")){
                    tCount++;
                }
            }
        }

        for (int i = 0; i < numberOfCols; i++){
            int tentsRow = 0;
            for (int j = 0; j < numberOfRows; j++){
                if (g[i][j].equals("C")){
                    tentsRow++;
                }
            }

            if (tentsRow != rowCount[i]){
                return false;
            }
        }

        for (int j = 0; j < numberOfRows; j++){
            int tentsCol = 0;
            for (int i = 0; i < numberOfCols; i++){
                if (g[i][j].equals("C")){
                    tentsCol++;
                }
            }
            if (tentsCol != colCount[j]){
                return false;
            }
        }

        if (cCount == tCount){
            return true;
        } else {
            return false;
        }
    }

    public void loopSteps(String[][] g){
        Boolean dfsBool = false;

        while (arrayEmpty(g) == false){
            String[][] gCopy = arrayCopy(g);
            g = fillInCamps(g);

            if (arrayCompare(g, gCopy) == true){
                gameBoard = g;
                dfsBool = true;
                dfs(g, 0);

                if (solutionFound == true){
                    convertBoard(gameBoard);
                    printBoard(gameBoard);
                } else {
                    printErrorTent();
                }
                break;
            }
        }

        if (arrayEmpty(g) == true){
            if (checkNumberOfTentsAndTrees(g) == true)
                solutionFound = true;
        }

        if (dfsBool == false){
            if (solutionFound == true){
                convertBoard(g);
                printBoard(g);
            } else {
                printErrorTent();
            }
        }
    }

    public String[][] convertBoard(String[][] g){
        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                if (g[i][j].equals("0")){
                    g[i][j] = ".";
                }
            }
        }
        return g;
    }

    public void printErrorTent(){
        System.out.println("The problem is impossible");
    }

    public void printBoard(String[][] g){
        for (int i = 0; i < numberOfCols; i++){
            for (int j = 0; j < numberOfRows; j++){
                System.out.print(g[i][j]);
            }
            System.out.println();
        }
    }
}