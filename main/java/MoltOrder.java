/*
Represents a single order made by a customer. Each Object stores details about what is being ordered
from Molt Delivery, the customer’s name, how long it takes to drive to the customer, etc.
 */
public class MoltOrder implements Comparable<MoltOrder> {
    private final String name;
    private final String orderDescription;
    private final int orderReadyTime;
    private final int timeNeededToDeliver;
    private final int priority;

    // Constructor
    public MoltOrder(String name, String orderDescription, int orderReadyTime, int timeNeededToDeliver, int priority) {
        if (orderReadyTime < 0 || timeNeededToDeliver < 0) { throw new IllegalArgumentException("Time needs to be greater than 0"); }
        this.name = name;
        this.orderDescription = orderDescription;
        this.orderReadyTime = orderReadyTime;
        this.timeNeededToDeliver = timeNeededToDeliver;
        this.priority = priority;
    }

    // Returns every this.MoltOrder property in new line as "Property: value"
    public String toString() {
        return "Name: " + name + "\nOrder Description: " + orderDescription
                + "\nOrder ReadyTime: " + orderReadyTime + "\nTimeNeededToDeliver: " + timeNeededToDeliver
                + "\nPriority: " + priority;
    }

    public int getOrderReadyTime() { return orderReadyTime; }

    public int getTimeNeededToDeliver() { return timeNeededToDeliver; }

    public String getName() { return name; }

    public String getOrderDescription() {return orderDescription;}

    /*
    Compares two MoltOrder objects by their priority values:
    If (this > other) returns negative int
    If (this < other) returns positive int
    If (this == other) returns 0
     */
    public int compareTo(MoltOrder otherOrder) {
        if (otherOrder == null) { throw new IllegalArgumentException(); }
        return Integer.compare(this.priority, otherOrder.priority);
    }
}
