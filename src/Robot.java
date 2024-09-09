//  Implements the logic for navigating the grid using breadth-first search.
//  It uses a Queue to traverse the grid, finds the shortest path, and stores the path in a CellLinkedList.
public class Robot {
    private Grid grid; // the grid that the robot traverses through
    private CustomLinkedList robotStateList; // linked list to store the state of the robot
    // current cell and the cell that it came from (parent cell)
    private int start;
    private int end;

    Robot(Grid grid, int start, int end){
        this.grid = grid;
        this.start = start;
        this.end = end;
    }

    public boolean breadthFirst(boolean visualize) {
        CustomLinkedList queue = new CustomLinkedList();
        CustomLinkedList visited = new CustomLinkedList(); // linked list to store the numbers of the visited cells
        robotStateList = new CustomLinkedList(); // store the details about the data in the path (parent value, current value)

        // setting the start values
        queue.add(start);
        int parent = 0;
        robotStateList.add(parent, start);
        int traverseStepCount = 1;

        while (!queue.isEmpty()) {
            int current = queue.removeFirst();
            if(visited.has(current)){
                continue;
                // if the current value is already in the visited list, it is ignored
            }
            visited.add(current); // mark value as visited


            grid.setCurrentState(current, traverseStepCount);
            traverseStepCount++;

            if (current == end) {   // to return if the target node is found
                if(visualize){
                    System.out.println("\tThis is the order that the robot traversed through the grid!");
                    grid.printGrid(4);
                }
                System.out.println("\tPath Found!");
//                robotStateList.print();
                return true;
            }

            // looping through the hashmap to get the neighbours of current
            for (int j = 0; j < grid.getAdjacencyList().length; j++) {
                if(grid.getAdjacencyList()[j][0] == 0){ // ignoring the 0 values
                    continue;
                }
                // finding the key value from the hash map
                if (grid.getAdjacencyList()[j][0] == current) {
                    for (int k = 1; k < grid.getAdjacencyList()[j].length; k++) {

                        // obstacle avoidance (cells with 0s)
                        if(grid.getAdjacencyList()[j][k] == 0){
                            continue; // ignoring irrelevant values
                        }
                        int nextNode = grid.getAdjacencyList()[j][k];
                        robotStateList.add(grid.getAdjacencyList()[j][0], nextNode); // adding the node to the robotStateList
                        queue.add(nextNode); // Enqueue adjacent nodes into the queue
                    }
                    break; // Stop searching for adjacent nodes once found
                }
            }
        }
        //queue empty - robot has traversed through every possible node in the grid
        System.out.println("\n\tThere is no path from " + start + " to " + end + "!");
        return false;
    }

    // Method to backtrack from the end value to the start value to get the shortest path
    public CustomLinkedList getShortestPath(){
        CustomNode parent = robotStateList.findParent(end);
        CustomLinkedList reverse = new CustomLinkedList(); // adding values in the order from the end to start
        while(true){
            if (parent.getCurrent() == start){ // stopping if found the way back to the start
                break;
            }
            reverse.add(parent.getCurrent()); // adding the current value
            parent = robotStateList.findParent(parent.getCurrent()); // getting the parent of the current value and updating the parent
        }

        // turning reverse list back to the correct order
        CustomLinkedList correctOrder = new CustomLinkedList();

        // starting from the tail
        int stepCount = 0;
        CustomNode current = reverse.getTail();
        while (current != null){
            correctOrder.add(current.getSingleValue());
            current = current.getPrevious();
            stepCount++;
        }
        System.out.println("\tTotal number of steps: " + (stepCount+1) + "\n");
        return correctOrder;
    }

    // Getters and Setters
    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public CustomLinkedList getRobotStateList() {
        return robotStateList;
    }

    public void setRobotStateList(CustomLinkedList robotStateList) {
        this.robotStateList = robotStateList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
