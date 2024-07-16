/*
Represents a Molt Delivery driver. Each Object stores the driver’s name, a unique ID (i.e., an employee number),
how many orders they have delivered, and the time at which they will be available to perform another delivery
(if they are currently delivering an order, then this time will be in the future).
 */
public class MoltDriver implements Comparable<MoltDriver> {
    private final int id;
    private final String name;
    private int nextAvailableTimeForDelivery;
    private int totalOrdersDelivered;

    public MoltDriver(int id, String name, int nextAvailableTimeForDelivery) {
        if (nextAvailableTimeForDelivery < 0) { throw new IllegalArgumentException("The next available time cannot be negative"); }
        if (id < 0) { throw new IllegalArgumentException("The id cannot be negative"); }
        this.id = id;
        this.name = name;
        this.nextAvailableTimeForDelivery = nextAvailableTimeForDelivery;
        this.totalOrdersDelivered = 0;
    }

    public void incrementTotalOrdersDelivered() { totalOrdersDelivered++; }

    public int getNextAvailableTimeForDelivery() { return nextAvailableTimeForDelivery; }

    public void setNextAvailableTimeForDelivery(int time) { this.nextAvailableTimeForDelivery = time; }

    public String getName() { return name; }

    // Returns every this.MoltDriver property in new line as "Property: value"
    public String toString() {
        return "Name: " + name + "\nID: " + id + "\nNext available time for: " + nextAvailableTimeForDelivery
                + "\nTotal orders delivered: " + totalOrdersDelivered;
    }

    /*
    Compares two MoltDriver objects by their nextAvailableTimeForDelivery values:
    If (this > other) returns negative int
    If (this < other) returns positive int
    If (this == other) returns 0
     */
    public int compareTo(MoltDriver otherDriver) {
        return Integer.compare(this.nextAvailableTimeForDelivery , otherDriver.nextAvailableTimeForDelivery);
    }
}
