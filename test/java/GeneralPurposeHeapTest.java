import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class GeneralPurposeHeapTest {


    List<String> expectedMethodNames = List.of("insert", "findMin", "getSize", "deleteMin", "mergeHeap");

    @Test
    void allMethodsPresent() {
        List<String> actualMethodNames = Arrays.stream(GeneralPurposeHeap.class.getMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .map(Method::getName)
                .collect(Collectors.toList());

        List<String> missingNames = expectedMethodNames.stream()
                .filter(name -> !actualMethodNames.contains(name))
                .collect(Collectors.toList());

        String message = "The following methods are missing: " + String.join(", ", missingNames);
        assertEquals(0, missingNames.size(), message);
    }


    @Test
    void testInsertNull() {
        GeneralPurposeHeap<Integer> heap = new GeneralPurposeHeap<>();
        assertThrows(IllegalArgumentException.class, () -> heap.insert(null));
    }

    @Test
    void testFindMinEmptyHeap() {
        GeneralPurposeHeap<Integer> heap = new GeneralPurposeHeap<>();
        assertThrows(IllegalStateException.class, heap::findMin);
    }

    @Test
    void testDeleteMinEmptyHeap() {
        GeneralPurposeHeap<Integer> heap = new GeneralPurposeHeap<>();
        assertThrows(IllegalStateException.class, heap::deleteMin);
    }

    @Test
    void testMergeHeapWithNull() {
        GeneralPurposeHeap<Integer> heap = new GeneralPurposeHeap<>();
        assertThrows(IllegalArgumentException.class, () -> heap.mergeHeap(null));
    }

    @Test
    void testMergeHeapWithEmptyHeap() {
        GeneralPurposeHeap<Integer> heap1 = new GeneralPurposeHeap<>();
        heap1.insert(5);
        heap1.insert(3);
        heap1.insert(4);

        GeneralPurposeHeap<Integer> heap2 = new GeneralPurposeHeap<>();

        heap1.mergeHeap(heap2);
        assertEquals(3, heap1.getSize());
        assertEquals(3, heap1.findMin());
    }
}

