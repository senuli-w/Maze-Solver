// Implements a custom linked list for managing dynamic data structures.
// Contains methods for adding, removing, searching, and printing nodes.
public class CustomLinkedList{
    // Head and tail nodes of the linked list
    private CustomNode head;
    private CustomNode tail;

    CustomLinkedList(){
        head = null;
        tail = null;
    }

    // Method to add a new node at the end of the list - type 1
    public void add(int data) {
        CustomNode newNode = new CustomNode(data);  // create new node
        if (head == null) {             // check is list is empty - head points to null
            head = newNode;
            tail = newNode;
            // Setting the new node as the first node of the list
        } else {
            // Adding new node to the end and updating the tail
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
    }

    // adding a node with two values - type 2
    public void add(int parent, int current) {
        CustomNode newCell = new CustomNode(parent, current); //create new node
        if (head == null) {    //check is list is empty - start points to null
            // assigning the new node as the first and last cell of the list
            head = newCell;
            tail = newCell;
        } else {
            // else adding to the end of the list and updating the end
            tail.setNext(newCell);
            newCell.setPrevious(tail);
            tail = newCell;
        }
    }

    // Removing the first node - type 1
    public int removeFirst() {
        int returning = 0;
        if (tail == null) {
            // List is empty, nothing to remove
            return returning;
        }
        if (head == tail) {
            returning = head.getSingleValue();
            // List has only one node
            head = null;
            tail = null;
        } else {
            returning = head.getSingleValue();
            // removing and updating the head
            head = head.getNext();
            if (head != null) {
                head.setPrevious(null);
            }
        }
        return returning;
    }

    // Method to check if some value is in the list - type 1
    // uses a linear search technique
    public boolean has(int value) {
        CustomNode current = head;
        while (current != null) {
            if (current.getSingleValue() == value) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    // returns the size of the linked list - type 1
    // counts the size starting from the head node
    public int size(){
        CustomNode current = head;
        int count = 0;
        while (current != null){
            count ++;
            current = current.getNext();
        }
        return count;
    }

    public boolean isEmpty(){return (head == null);}

    // Implemented for unit testing (Robot 45)
    // Method to print the table of data of the cells traversed - type 2
    public void print(){
        CustomNode current = head;
        while (current != null){
            System.out.print(current.getParent() + "\t");
            System.out.print(current.getCurrent() + "\t\n");

            current = current.getNext();
        }
    }

    // method to return the cell data of a certain value - type 2
    // uses a linear search technique
    public CustomNode find(int val){
        CustomNode current = head;

        while(current != null){
            if (val == current.getCurrent()){
                break;
            }
            current = current.getNext();
        }
        return current;
    }

    // Method to return the node which has the current value as the input value (parent of the value)
    public CustomNode findParent(int val){
        CustomNode inputCell = find(val); // get the cell data of the value

        CustomNode current = tail;     // starting from the end of the list

        // initializing the returning output
        CustomNode parent = new CustomNode(-1,-1);

        while(current != null){
            if(current.getCurrent() == inputCell.getParent()){
                parent = current;
                break;
            }
            current = current.getPrevious();
        }
        return parent;
    }

    // getters and setters
    public CustomNode getHead() {
        return head;
    }

    public void setHead(CustomNode head) {
        this.head = head;
    }

    public CustomNode getTail() {
        return tail;
    }

    public void setTail(CustomNode tail) {
        this.tail = tail;
    }
}