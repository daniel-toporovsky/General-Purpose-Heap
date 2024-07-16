/*
Represents a heap of comparable objects
 */
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class GeneralPurposeHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size;

    public GeneralPurposeHeap(){
        this(30);
    }

    public GeneralPurposeHeap(int initialCapacity) {
        heap = castObject(new Object[initialCapacity+1]);
        size = 0;
    }

    public GeneralPurposeHeap(T[] initialData) {
        heap =  castObject(new Object[initialData.length+1]);
        System.arraycopy(initialData, 0, heap, 1, initialData.length);
        size = initialData.length;
        buildHeap();
    }

    // Inserts the given obj to heap
    // !! Modifies size !!
    // Checks heap size
    // If the element is null throws IllegalArgumentException
    public void insert(T element) {
        if (element == null) { throw new IllegalArgumentException("Element cannot be null"); }
        checkHeapSize();
        size++;
        percUp(size, element, heap);
    }

    // Returns the smallest object in a heap
    // If the heap is empty throws IllegalStateException
    public T findMin() {
        if (size == 0) { throw new IllegalStateException("Heap is empty"); }
        return heap[1];
    }

    // Returns a heap size
    public int getSize() { return size; }

    // Deletes the smallest element in the heap
    // If the heap is empty throws IllegalStateException
    public T deleteMin() {
        if (size == 0) { throw new IllegalStateException("Heap is empty"); }
        T min = heap[1];
        heap[1] = null;
        T x = heap[size];
        heap[size] = null;
        size--;
        percDown(1, x, size);
        return min;
    }

    // Adds other.heap elements to this.heap
    // If the heap type is not defined throws IllegalArgumentException
    // If the other heap is empty does nothing
    public void mergeHeap(GeneralPurposeHeap<T> otherHeap) {
        if (otherHeap == null) { throw new IllegalArgumentException("otherHeap is null"); }
        if (otherHeap.size == 0) { return; }
        checkHeapSize(size + otherHeap.size);
        System.arraycopy(otherHeap.heap, 1, heap, size+1, otherHeap.size);
        size += otherHeap.size;
        buildHeap();
    }

    // Sorts array to binary heap
    private void buildHeap() {
        T[] arr = heap;
        int n = size;
        for(int i = n/2; i >= 1; i--){
            percDown(i, arr[i], n);
        }
    }

    // Moves the given object from index i down until it violates the heap property
    private void percDown(int i, T x, int n) {
        T[] arr = heap;
        if ((2*i) > n) { arr[i] = x; }

        if ((2*i) == n) {
            if (arr[2*i].compareTo(x) < 0) {
                arr[i] = arr[2*i];
                arr[2*i] = x;
            }
            else { arr[i] = x; }
        }

        if ((2*i) < n) {
            int j;

            if (arr[2*i].compareTo(arr[2*i + 1]) < 0) { j = 2*i; }
            else { j = 2*i + 1; }

            if (arr[j].compareTo(x) < 0) {
                arr[i] = arr[j];
                percDown(j ,x, n);
            }
            else { arr[i] = x;}
        }
    }

    // Moves the given object from index i up until it violates the heap property
    private void percUp(int i, T x, T[] arr) {
        int p = i / 2;

        if (i == 1) { arr[1] = x; }

        else if (arr[p].compareTo(x) < 0) { arr[i] = x; }

        else {
            arr[i] = arr[p];
            percUp(p, x, arr);
        }
    }

    // If the heap is full doubles array capacity
    private void checkHeapSize(){
        checkHeapSize(1);
    }

    // If heap is small for another num elements doubles array capacity until fits
    private void checkHeapSize(int num) {
        if (size+num >= heap.length) {
            T[] tmp = castObject(new Object[heap.length*2]);
            System.arraycopy(heap, 0, tmp, 0, heap.length);
            heap = tmp;
            checkHeapSize(num);
        }
    }

    // Casts the given object to the type T
    // If the object is not comparable throws ClassCastException
    @SuppressWarnings("unchecked")
    private T castObject(Object o) {
        if (o.getClass().getComponentType().isInstance(Comparable.class)) {
            return (T) o;
        }
        else { throw new ClassCastException("Unable to cast"); }
    }

    // Casts the given array to array of objects of generic type T
    // If the array elements are not comparable throws ClassCastException
    @SuppressWarnings("unchecked")
    private T[] castObject(Object[] arr) {
        if (Arrays.stream(arr).allMatch(Objects::isNull)) {
            return  (T[]) Array.newInstance(Comparable.class, arr.length);
        }
        else {
            T[] resultArr = (T[]) Array.newInstance(Comparable.class, arr.length);
            for (int i = 0; i < arr.length; i++) {
                resultArr[i] = castObject(arr[i]);
            }
            return resultArr;
        }
    }















}
