import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


// Represents a grid environment for the robot navigation.
// Contains methods for grid generation, pathfinding, and printing.
public class Grid {
    private int size; // size of the grid (ex: size 5 means grid is a 5x5 grid with 25 cells)
    // Represents the grid using 2D arrays - represented in 4 different ways for clarity and simplicity
    private String[][] gridSymbols; // grid of symbols (+ as obstacles, _ as free cells or the paths)
    private int[][] gridValues;     // grid of values (0 as obstacles, free cells are numbered from 1)
    private String[][] gridTraversal; // grid containing the traversal details
    private String[][] gridPath;    // grid after finding the path (has the starting cell, ending cell and the path marked.)

    /* created using 2d array
     * each array has 5 elements
     * 1st value represents the key (0th index)
     * other indexes represent the surrounding cells of the key (left, right, up, down)
     */
    private int[][] adjacencyList; // adjacency list of the grid representation using numbers


    // constructor
    Grid(int size, int opt){
        this.size = size;
        gridSymbols = new String[size][size];
        gridValues = new int[size][size];
        gridPath = new String[size][size];
        gridTraversal = new String[size][size];
        if(opt == 1){
            if(!read()){
                System.out.println("\tError caught while reading the file!");
//                System.exit(1);
                return;
            }
            System.out.println("\n\tGrid of size " + size + " is read!");
        } else{
        generateGrid();
            System.out.println("\n\tGrid of size " + size + " is generated!");
        }
        adjacencyList = new int[size * size][5];
        createAdjacencyList();
//        printAdjacencyList();
        getCopyOfGrids();
    }

    // grid can be either generated randomly
    // or read from an existing file
    // currently existing files 5, 8, 10
    public void generateGrid() {
        Random rand = new Random();
        int count = 1; // to add the values of the grid to the gridVal

        // for loop for all the cells in the 2D array
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Randomly choose between "_" and "+"
                if (rand.nextBoolean()) { // true
                    gridSymbols[i][j] = ".";
                    gridValues[i][j] = count;
                } else { // false
                    gridSymbols[i][j] = "X";
                    gridValues[i][j] = 0;
                }
                count++;
            }
        }
    }

    // method to read the file
    private boolean read() {
        String filePath = "./src/TextFiles/" + size + ".txt";
        int i = 0;
        int j = 0;

        try (Scanner scanner = new Scanner(new File(filePath))) {
            int count = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (char c : line.toCharArray()) {
                    gridSymbols[i][j] = String.valueOf(c);
                    if (gridSymbols[i][j].equals(".")){
                        gridValues[i][j] = count;
                    } else {
                        gridValues[i][j] = 0;
                    }
                    count++;
                    j++;
                }
                i++;
                j = 0;
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("\tFile not Found!");
            return false;
        }
    }

    // Method to return the row value and the column value of a specific cell in the grid
    public int[] index(int o){
        int[] index = new int[2];
        index[0] = -1; // returns -1 if not found
        index[1] = -1;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if (gridValues[i][j] == o){
                    index[0] = i;
                    index[1] = j;
                    break; // stop the loop once the index is found
                }
            }
        }
        return index;
    }

    // Method to print the grid according to the input value
    // x -> 1 (printing the grid of numbers)
    // x -> 2 (printing the grid of symbols + and _ )
    // x -> 3 (printing the grid of symbols + and _ with the optimal path from start to end)
    // x -> 4 (printing the traversal values of the grid);
    public void printGrid(int x){
        if (x == 3){
            System.out.println("\t_ represents free cells");
            System.out.println("\t+ represents obstacles");
            System.out.println("\tS represents Starting position");
            System.out.println("\tE represents Ending position");
            System.out.println("\tarrows represent the path\n");
        }
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print("\t");
                if (x == 1){
                    System.out.print(gridValues[i][j] + "\t");
                }
                else if(x == 2){
                    System.out.print(gridSymbols[i][j] + " ");
                }
                else if (x == 3){
                    System.out.print(gridPath[i][j] + " ");
                }
                else{
                    System.out.print(gridTraversal[i][j] + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // method to create the adjacencyList according to the grid size
    private void createAdjacencyList(){
        int mCounter = 0;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){

                if(gridValues[i][j] == 0){
                    continue;
                }

                adjacencyList[mCounter][0] = gridValues[i][j];
                // up
                if(i - 1 >= 0) adjacencyList[mCounter][1] = gridValues[i - 1][j];
                else adjacencyList[mCounter][1] = 0; // 0 means no connection with cells
                // down
                if(i + 1 < size) adjacencyList[mCounter][2] = gridValues[i + 1][j];
                else adjacencyList[mCounter][2] = 0;
                // left
                if(j - 1 >= 0) adjacencyList[mCounter][3] = gridValues[i][j - 1];
                else adjacencyList[mCounter][3] = 0;
                // right
                if(j + 1 < size) adjacencyList[mCounter][4] = gridValues[i][j + 1];
                else adjacencyList[mCounter][4] = 0;

                mCounter++;
            }
        }
    }

    // Implemented for unit testing (Grid 45)
    // Method to print the adjacency list
    // Structure -   key | up   down   left   right
    public void printAdjacencyList(){
        for(int i = 0; i < adjacencyList.length; i++){
            if(adjacencyList[i][0] != 0){
                for(int j = 0; j < 5; j ++){
                    if(adjacencyList[i][j] == -1){ // -1 means no connection with cells
                        System.out.print("-");
                    } else {
                        System.out.print(adjacencyList[i][j]);
                    }
                    if(j == 0){
                        System.out.print("\t|\t"); // some space after the key value
                    }
                    else{
                        System.out.print("\t");
                    }
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    // Method to update the optimal path and save in the gridPath 2D array
    // And the grid will be printed
    public void printShortestPath(CustomLinkedList path, int start, int end) {
        CustomNode current = path.getTail();
        CustomNode previous = new CustomNode(end);

        // saving the path in gridPath
        while (current != null) {
            int[] indexes = index(current.getSingleValue());

            if(previous.getSingleValue() - current.getSingleValue() == 1)gridPath[indexes[0]][indexes[1]] = "→";
            if(previous.getSingleValue() - current.getSingleValue() == -1)gridPath[indexes[0]][indexes[1]] = "←";
            if(previous.getSingleValue() - current.getSingleValue() == size)gridPath[indexes[0]][indexes[1]] = "↓";
            if(previous.getSingleValue() - current.getSingleValue() == -size)gridPath[indexes[0]][indexes[1]] = "↑";

            previous = current;
            current = current.getPrevious();
        }

        // Mark the start and end positions
        int[] startIndexes = index(start);
        gridPath[startIndexes[0]][startIndexes[1]] = "S";

        int[] endIndexes = index(end);
        gridPath[endIndexes[0]][endIndexes[1]] = "E";

        printGrid(3); // print grid
    }

    public void setCurrentState(int currentPosition, int stepCount){
        int[] indexes = index(currentPosition);
        gridTraversal[indexes[0]][indexes[1]] = Integer.toString(stepCount); // setting tha step value in the grid
    }

    public void getCopyOfGrids(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                gridPath[i][j] = gridSymbols[i][j];
                gridTraversal[i][j] = gridSymbols[i][j];
            }
        }
    }
    // getters and setters
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String[][] getGridSymbols() {
        return gridSymbols;
    }

    public void setGridSymbols(String[][] gridSymbols) {
        this.gridSymbols = gridSymbols;
    }

    public int[][] getGridValues() {
        return gridValues;
    }

    public void setGridValues(int[][] gridValues) {
        this.gridValues = gridValues;
    }

    public String[][] getGridPath() {
        return gridPath;
    }

    public void setGridPath(String[][] gridPath) {
        this.gridPath = gridPath;
    }

    public int[][] getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(int[][] adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public String[][] getGridTraversal() {
        return gridTraversal;
    }

    public void setGridTraversal(String[][] gridTraversal) {
        this.gridTraversal = gridTraversal;
    }

}
