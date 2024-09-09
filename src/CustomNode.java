// Represents a node in a custom linked list.
// Contains either a single value or a parent and current value pair.
public class CustomNode {
    private int singleValue;    // Data stored in the node
    private int parent;         // value in the parent cell (the cell that it came from)
    private int current;        // value of the current cell
    private CustomNode next;    // Reference to the next cell data of the list
    private CustomNode previous;  // Reference to the previous cell data of the list

    // Constructor for a node with a single value - type 1
    CustomNode(int value){
        this.singleValue = value;
        this.next = null;
        this.previous = null;
    }

    // Constructor for a node with parent and current values - type 2
    CustomNode(int parent, int current){
        this.parent = parent;
        this.current = current;
        this.next = null;
        this.previous = null;
    }

    // Getters and setters for node attributes
    public int getSingleValue() {
        return singleValue;
    }

    public void setSingleValue(int singleValue) {
        this.singleValue = singleValue;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public CustomNode getNext() {
        return next;
    }

    public void setNext(CustomNode next) {
        this.next = next;
    }

    public CustomNode getPrevious() {
        return previous;
    }

    public void setPrevious(CustomNode previous) {
        this.previous = previous;
    }
}