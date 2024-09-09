import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.Character.toUpperCase;

// Contains the main method where the program execution starts.

// Takes input for grid size, start, and end positions, creates a grid and a robot,
// then navigates the robot through the grid and prints the shortest path.
public class Simulator {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        while(true){ // looping until the user wants to exit the program
            System.out.println("\n\t\t\tMake the robot navigate through the grid!\n");
            int option;
            while(true){ // for input validations - user option
                System.out.println("\tPress 1 - Get the grid from existing files");
                System.out.println("\tPress 2 - Generate a grid randomly");
                System.out.println("\tPress 0 - Exit");
                System.out.print("\t> ");

                // to avoid possible errors in the program
                try{
                    option = input.nextInt();
                    input.nextLine();
                }catch (InputMismatchException e){
                    System.out.println("\tEnter 1 or 2\n");
                    input.nextLine();
                    continue;
                }

                // to avoid possible errors in the program
                if(option < 0 || option > 2){
                    System.out.println("\tEnter 1 or 2\n");
                    continue;
                }

                if(option == 0){
                    System.exit(1);
                }
                break;
            }

            int size;
            while(true){ // for input validations - size of the grid
                if (option == 1){
                    System.out.print("\tChoose the size of the grid (5, 8, 10, 12)\n\t> ");
                } else {
                    System.out.print("\tEnter the size of the grid\n\tmin - 4 | max - 20\n\t> ");
                }

                // to avoid possible errors in the program
                try{
                    size = input.nextInt();
                    input.nextLine();
                }catch (InputMismatchException e){
                    System.out.println("\tEnter a number.");
                    input.nextLine();
                    continue;
                }

                // to avoid possible errors in the program
                if(option == 1){
                    if((size == 5) || (size == 8) || (size == 10) || (size == 12)){
                        break;
                    }
                } else{
                    if (size >= 4 && size <= 20){
                        break;
                    }
                }

                System.out.println("\tGrid not available");
            }

            // creating the grid object
            // option indicates the type of the grid the user wants (grid from text file or generated grid)
            Grid grid = new Grid(size, option);

            grid.printGrid(2); // printing the grid with symbols

            System.out.println("\t* The robot can travel only in left, right, up and down directions.");
            System.out.println("\t* The robot cannot travel in diagonal directions.");
            System.out.println("\t* 0s represent obstacles, cells without obstacles are numbered.");
            System.out.println("\n\t* Please enter the starting value and the ending value of the grid.\t");
            grid.printGrid(1); // printing the grid with numbers to get the start and end values from the user
            int start;
            while(true){ // for input validations - start value
                try{
                    System.out.print("\tEnter the starting position : ");
                    start = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("\tEnter a number");
                    input.nextLine();
                    continue;
                }
                break;
            }

            int end;
            while(true){ // for input validations - end value
                try{
                    System.out.print("\tEnter the end position : ");
                    end = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("\tEnter a number");
                    input.nextLine();
                    continue;
                }
                break;
            }

            System.out.println("\t");
            // setting the robot object
            Robot robot = new Robot(grid, start, end);

            System.out.print("\tDo you want to see the path planning process? (y/n)\n\t> ");
            char visualize = toUpperCase(input.next().charAt(0));
            input.nextLine();
            boolean visualizeOkay;
            if(visualize == 'Y'){
                visualizeOkay = true;
            } else {
                visualizeOkay = false;
                System.out.println("\tPath planning process will not be printed!");
            }
            System.out.println();

            // in the breadthFirst method, robot traverses through the grid until it finds the target node
            // returns true if path found.
            // if path found, the shortest path is obtained and printed
            if(robot.breadthFirst(visualizeOkay)){
                CustomLinkedList path = robot.getShortestPath();
                System.out.println("\tPath with the lowest step count");
                grid.printShortestPath(path, start, end);
            }

            System.out.print("\tPress enter to try again. ");
            input.nextLine();
        }
    }
}