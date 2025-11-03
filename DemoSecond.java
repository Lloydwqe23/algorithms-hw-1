import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DemoSecond {
    static final int BENCHMARK_SECONDS = 10;
    static final int A = 2;
    static final int B = 10;
    static final int C = 5;
    static final int SUM = A + B + C;
    static FileWriter csv;
    
    static final int MEMORY_BENCHMARK_COUNT = 100;

    static void forceGC() {
        try {
            System.gc(); Thread.sleep(200);
            System.gc(); Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static long measureCurrentMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }

public static void main(String[] args) throws IOException {
    int[] sizes = {100, 1000, 10000, 100000};

    csv = new FileWriter("results.csv");
    csv.write("size,variant,ops,memory\n");

    Initialization init = new Initialization("students.csv");
    List<Student> fullList = init.students;

    List<String> emails = new ArrayList<>();
    for (Student s : fullList) {
        emails.add(s.getEmail());
    }

    for (int size : sizes) {
        System.out.println("===== Database size: " + size + " =====");
        List<Student> sublist = new ArrayList<>(fullList.subList(0, Math.min(size, fullList.size())));

        forceGC();
        long memBefore1 = measureCurrentMemory();
        Object[] arr1 = new Object[MEMORY_BENCHMARK_COUNT];
        for (int i = 0; i < MEMORY_BENCHMARK_COUNT; i++) {
            arr1[i] = new VarOne(sublist);
        }
        long memAfter1 = measureCurrentMemory();
        double mem1 = (memAfter1 - memBefore1) / (double)MEMORY_BENCHMARK_COUNT / 1024.0;
        long ops1 = runPerfTest("VarOne", arr1[0], emails); 
        System.out.println("VarOne estimated memory = " + mem1 + "\n");
        csv.write(size + ",VarOne," + ops1 + "," + mem1 + "\n");
        arr1 = null;

        forceGC();
        long memBefore2 = measureCurrentMemory();
        Object[] arr2 = new Object[MEMORY_BENCHMARK_COUNT];
        for (int i = 0; i < MEMORY_BENCHMARK_COUNT; i++) {
            arr2[i] = new VarTwo(sublist);
        }
        long memAfter2 = measureCurrentMemory();
        double mem2 = (memAfter2 - memBefore2) / (double)MEMORY_BENCHMARK_COUNT / 1024.0;
        long ops2 = runPerfTest("VarTwo", arr2[0], emails);
        System.out.println("VarTwo estimated memory = " + mem2 + "\n");
        csv.write(size + ",VarTwo," + ops2 + "," + mem2 + "\n");
        arr2 = null;

        forceGC();
        long memBefore3 = measureCurrentMemory();
        Object[] arr3 = new Object[MEMORY_BENCHMARK_COUNT];
        for (int i = 0; i < MEMORY_BENCHMARK_COUNT; i++) {
            arr3[i] = new VarThree(sublist);
        }
        long memAfter3 = measureCurrentMemory();
        double mem3 = (memAfter3 - memBefore3) / (double)MEMORY_BENCHMARK_COUNT / 1024.0;
        long ops3 = runPerfTest("VarThree", arr3[0], emails);
        System.out.println("VarThree estimated memory = " + mem3 + "\n");
        csv.write(size + ",VarThree," + ops3 + "," + mem3 + "\n");
        arr3 = null;

        forceGC();
        long memBefore4 = measureCurrentMemory();
        Object[] arr4 = new Object[MEMORY_BENCHMARK_COUNT];
        for (int i = 0; i < MEMORY_BENCHMARK_COUNT; i++) {
            arr4[i] = new HybridFast(sublist);
        }
        long memAfter4 = measureCurrentMemory();
        double mem4 = (memAfter4 - memBefore4) / (double)MEMORY_BENCHMARK_COUNT / 1024.0;
        long ops4 = runPerfTest("HybridFast", arr4[0], emails);
        System.out.println("HybridFast estimated memory = " + mem4 + "\n");
        csv.write(size + ",HybridFast," + ops4 + "," + mem4 + "\n");
        arr4 = null;
        
        csv.flush();
    }

    csv.close();
}
    static long runPerfTest(String name, Object var, List<String> emails) throws IOException {
        Random r = new Random();
        long end = System.currentTimeMillis() + BENCHMARK_SECONDS * 1000;
        long ops = 0;

        while (System.currentTimeMillis() < end) {
            int op = r.nextInt(SUM);
            if (op < A) {
                if (var instanceof VarOne v) v.firstHundred();
                else if (var instanceof VarTwo v) v.firstHundred();
                else if (var instanceof VarThree v) v.firstHundred();
                else if (var instanceof HybridFast v) v.firstHundred();
            } else if (op < A + B) {
                String email = emails.get(r.nextInt(emails.size()));
                double newRating = r.nextInt(100);
                if (var instanceof VarOne v) v.findByEmail(email, newRating);
                else if (var instanceof VarTwo v) v.findByEmail(email, newRating);
                else if (var instanceof VarThree v) v.findByEmail(email, newRating);
                else if (var instanceof HybridFast v) v.findByEmail(email, newRating);
            } else {
                if (var instanceof VarOne v) v.biggestGroupAverage();
                else if (var instanceof VarTwo v) v.biggestGroupAverage();
                else if (var instanceof VarThree v) v.biggestGroupAverage();
                else if (var instanceof HybridFast v) v.biggestGroupAverage();
            }
            ops++;
        }
        System.out.println(name + " operations in " + BENCHMARK_SECONDS + "seconds = " + ops);
        return ops;
    }
}