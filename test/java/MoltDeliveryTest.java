/*
A tester that simulates Molt Delivery behavior on using a heap class
Everything has to be intuitive understandable
 */
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MoltDeliveryTest {
    static GeneralPurposeHeap<MoltDriver> drivers = new GeneralPurposeHeap<>();
    static GeneralPurposeHeap<MoltOrder> orders = new GeneralPurposeHeap<>();
    public static void main(String[] args) {

        System.out.println("Welcome to Molt Delivery!");
        System.out.println("For help type -help");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            switch (line) {
                case "-addDr":
                    addDr();
                    break;
                case "-addOr":
                    addOr();
                    break;
                case "-status":
                    status();
                    break;
                case"-statusDr":
                    statusDr();
                    break;
                case "-statusOr":
                    statusOr();
                    break;
                case "-exit":
                    break;
                case "-mergeheap":
                    mergeheap();
                    break;
                default:
                    help();
            }
            if (line.equals("-exit")) break;
        }
    }

    private static void help() {
        System.out.println("Usage:");
        System.out.println("'-addDr' to add a new driver");
        System.out.println("'-addOr' to add a new order");
        System.out.println("'-status' to see all the drivers and orders status");
        System.out.println("'-statusDr' to see all the drivers status");
        System.out.println("'-statusOr' to see all the orders status");
        System.out.println("'-mergeheap' to create two heaps of integers and check the mergeHeap method");
        System.out.println("'-exit' to exit the program");

    }

    private static void addDr() {
        int id, nextAvailableTimeForDelivery = 0;
        String name;
        Scanner scanner = new Scanner(System.in);

        // 'Adding a new driver!' loop
        addingLoop: while (true) {
            System.out.println("Adding a new driver!");

            // Gets name input
            System.out.println("What's new drivers name?");
            while (true) {
                try {
                    name = scanner.nextLine();
                    if (name.isEmpty()) throw new InputMismatchException();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("The name is invalid, please try again:");
                }
            }

            // Gets id input
            System.out.println("What's new drivers id?");
            while (true) {
                try {
                    String answer = scanner.nextLine();
                    if (Integer.parseInt(answer) <= 0) throw new InputMismatchException();
                    else id = Integer.parseInt(answer);
                    break;
                } catch (InputMismatchException | NumberFormatException ex) {
                    System.out.println("The id is invalid, please try again:");
                }
            }

            // Double-checks input values
            System.out.println("The new driver details saved successfully!");
            System.out.println("Name: " + name);
            System.out.println("Id: " + id);
            System.out.println("Do you accept? (Y/N)");


            answer: while (true) {
                 try {
                     String answer = scanner.nextLine();
                    if (answer.equals("Y")) {
                        System.out.println("The new driver added successfully!");
                        drivers.insert(new MoltDriver(id, name, nextAvailableTimeForDelivery));
                        break addingLoop;
                    } else if (answer.equals("N")) {
                        System.out.println("Let's try again");
                        break answer;
                    } else throw new InputMismatchException();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please try again");
                }
            }

        }
    }

    private static void addOr() {
        if (drivers.getSize() == 0) {
            statusDr();
            System.out.println("Please add some drivers and try again");
            return;
        }
        String name, orderDescription;
        int orderReadyTime, timeNeededToDeliver,  priority;
        Scanner scanner = new Scanner(System.in);

        // 'Adding a new order!' loop
        addingLoop: while (true) {
            System.out.println("Adding a new order!");

            // Gets name input
            System.out.println("What are we delivering (name)?");
            while (true) {
                try {
                    name = scanner.nextLine();
                    if (name.isEmpty()) throw new InputMismatchException();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("The name is invalid, please try again:");
                }
            }

            // Gets description input
            System.out.println("Please write some description:");
            while (true) {
                try {
                    orderDescription = scanner.nextLine();
                    if (orderDescription.isEmpty()) throw new InputMismatchException();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("The input is invalid, please try again:");
                }
            }

            // Gets orderReadyTime input
            System.out.println("How much time would it take to prepare " + name + " ?");
            while (true) {
                try {
                    String answer = scanner.nextLine();
                    if (Integer.parseInt(answer) <= 0) throw new InputMismatchException();
                    else orderReadyTime = Integer.parseInt(answer);
                    break;
                } catch (InputMismatchException | NumberFormatException ex) {
                    System.out.println("The value is invalid, please try again:");
                }
            }

            // Gets timeNeededToDeliver input
            System.out.println("How much time would it take to deliver " + name + " ?");
            while (true) {
                try {
                    String answer = scanner.nextLine();
                    if (Integer.parseInt(answer) <= 0) throw new InputMismatchException();
                    else timeNeededToDeliver = Integer.parseInt(answer);
                    break;
                } catch (InputMismatchException | NumberFormatException ex) {
                    System.out.println("The value is invalid, please try again:");
                }
            }

            // Gets priority input
            System.out.println("What's the order priority?");
            while (true) {
                try {
                    priority = scanner.nextInt();
                    if (priority < 0) throw new InputMismatchException();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("The value is invalid, please try again:");
                }
            }

            // Double-checks input values
            System.out.println("The new order details saved successfully!");
            System.out.println("Name: " + name);
            System.out.println("Description: " + orderDescription);
            System.out.println("The order will be ready in " + orderReadyTime + " minutes");
            System.out.println("Delivery time is  " + timeNeededToDeliver + " minutes");
            System.out.println("Do you accept? (Y/N)");

            answer: while (true) {
                try {
                    String answer = scanner.nextLine();
                    if (answer.equals("Y")) {
                        System.out.println("The new order added successfully!");
                        orders.insert(new MoltOrder(name, orderDescription, orderReadyTime, timeNeededToDeliver, priority));
                        System.out.println("Your order will be delivered by " + drivers.findMin().getName());
                        MoltDriver tmp = drivers.deleteMin();
                        int timeOrder = timeNeededToDeliver + orderReadyTime;
                        int timeDriver = tmp.getNextAvailableTimeForDelivery();
                        if (timeDriver <= timeOrder) {
                            tmp.setNextAvailableTimeForDelivery(timeOrder);
                        } else {
                            tmp.setNextAvailableTimeForDelivery(timeOrder + (timeDriver - timeOrder));
                        }
                        System.out.println("The approximate time delivery is " + tmp.getNextAvailableTimeForDelivery());
                        drivers.insert(tmp);
                        break addingLoop;
                    }
                    else if (answer.equals("N")) {
                        System.out.println("Let's try again");
                        break answer;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please try again");
                }
            }
        }

    }

    private static void status() {
        if (drivers.getSize() == 0 && orders.getSize() == 0) {
            System.out.println("There is nothing to display yet...");
        }
        else if (drivers.getSize() == 0) {
            System.out.println("You didn't add any driver yet");
            System.out.println("----------------------------------------------");
            System.out.println("Your orders:");
            System.out.println(orders.toString());
        } else if (orders.getSize() == 0) {
            System.out.println("Your drivers:");
            System.out.println(drivers.toString());
            System.out.println("----------------------------------------------");
            System.out.println("You didn't add any orders yet");
        }
        else {
            System.out.println("Your drivers:");
            System.out.println(drivers.toString());
            System.out.println("----------------------------------------------");
            System.out.println("Your orders:");
            System.out.println(orders.toString());
        }
    }

    private static void statusDr() {
        if (drivers.getSize() == 0) {
            System.out.println("You didn't add any driver yet");
        }
        else {
            System.out.println("Your drivers:");
            System.out.println(drivers.toString());
        }
    }

    private static void statusOr() {
        if (orders.getSize() == 0) {
            System.out.println("You didn't add any orders yet");
        }
        else {
            System.out.println("Your orders:");
            System.out.println(orders.toString());
        }
    }

    private static void mergeheap() {
        // Reads input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the heaps size:");
        int input;
        while (true) {
            try {
                String answer = scanner.nextLine();
                if (Integer.parseInt(answer) <= 0) throw new InputMismatchException();
                else input = Integer.parseInt(answer);
                break;
            } catch (InputMismatchException | NumberFormatException ex) {
                System.out.println("The input is invalid, please try again:");
            }
        }

        // Generates two arrays of random integers
        final int c = input;
        Random x = new Random();
        Random y = new Random();
        int[]  randomNumbers1 = IntStream.generate(() -> x.nextInt(c)).limit(c).toArray();
        int[]  randomNumbers2 = IntStream.generate(() -> y.nextInt(c)).limit(c).toArray();

        // Creates two heaps based on created arrays size
        GeneralPurposeHeap<Integer> heap1 = new GeneralPurposeHeap<>(c);
        GeneralPurposeHeap<Integer> heap2 = new GeneralPurposeHeap<>(c);

        for (int randomNumber : randomNumbers1) {
            heap1.insert(randomNumber);
        }
        System.out.println("Heap1 is ok: " + isHeap(heap1.heap, 1, heap1.getSize()));

        for (int randomNumber : randomNumbers2) {
            heap2.insert(randomNumber);
        }
        System.out.println("Heap1 is ok: " + isHeap(heap2.heap, 1, heap2.getSize()));

        heap1.mergeHeap(heap2);
        System.out.println("Heap merged is ok: " + isHeap(heap1.heap, 1, heap1.getSize()));
    }

    static boolean isHeap(Comparable[] arr, int i, int n) {
        // If (2 * i) + 1 >= n, then leaf node, so return true
        if (i >= (n - 1) / 2)
        {
            return true;
        }

        // If an internal node and
        // is greater than its
        // children, and same is
        // recursively true for the
        // children
        if (arr[i].compareTo(arr[2 * i + 1]) <= 0
                && arr[i].compareTo(arr[2 * i + 2]) <= 0
                && isHeap(arr, 2 * i + 1, n)
                && isHeap(arr, 2 * i + 2, n))
        {
            return true;
        }

        return false;

    }






}