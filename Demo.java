import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Initialization init = new Initialization("students.csv");
        List<Student> original = init.students;

        List<Student> list1 = new ArrayList<>(original);

        List<Student> list2 = new ArrayList<>(original);

        // --- STANDARD SORT ---
        long start1 = System.currentTimeMillis();
        StandartSort stdSort = new StandartSort(list1);
        long end1 = System.currentTimeMillis();
        StandartSort.save(list1, "standart_sorted_students.csv");
        long timeStandard = end1 - start1;

        // --- BST SORT ---
        long start2 = System.currentTimeMillis();
        List<Student> radixSorted = RadixSort.sort(list2);
        long end2 = System.currentTimeMillis();
        RadixSort.save(radixSorted, "radix_sorted_students.csv");
        long timeRadix = end2 - start2;

        System.out.println("StandartSort time = " + timeStandard + " ms");
        System.out.println("RadixSort time      = " + timeRadix + " ms");
    }
}
