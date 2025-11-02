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
    
    // Кількість об'єктів для вимірювання пам'яті
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

        // --- VarOne ---
        forceGC();
        long memBefore1 = measureCurrentMemory();
        Object[] arr1 = new Object[MEMORY_BENCHMARK_COUNT];
        for (int i = 0; i < MEMORY_BENCHMARK_COUNT; i++) {
            arr1[i] = new VarOne(sublist);
        }
        long memAfter1 = measureCurrentMemory();
        // ЗМІНА ТУТ
        double mem1 = (memAfter1 - memBefore1) / (double)MEMORY_BENCHMARK_COUNT / 1024.0;
        long ops1 = runPerfTest("VarOne", arr1[0], emails); 
        System.out.println("VarOne estimated memory (KB) = " + mem1 + "\n");
        csv.write(size + ",VarOne," + ops1 + "," + mem1 + "\n");
        arr1 = null;

        // --- VarTwo ---
        forceGC();
        long memBefore2 = measureCurrentMemory();
        Object[] arr2 = new Object[MEMORY_BENCHMARK_COUNT];
        for (int i = 0; i < MEMORY_BENCHMARK_COUNT; i++) {
            arr2[i] = new VarTwo(sublist);
        }
        long memAfter2 = measureCurrentMemory();
        // ЗМІНА ТУТ
        double mem2 = (memAfter2 - memBefore2) / (double)MEMORY_BENCHMARK_COUNT / 1024.0;
        long ops2 = runPerfTest("VarTwo", arr2[0], emails);
        System.out.println("VarTwo estimated memory (KB) = " + mem2 + "\n");
        csv.write(size + ",VarTwo," + ops2 + "," + mem2 + "\n");
        arr2 = null;

        // --- VarThree ---
        forceGC();
        long memBefore3 = measureCurrentMemory();
        Object[] arr3 = new Object[MEMORY_BENCHMARK_COUNT];
        for (int i = 0; i < MEMORY_BENCHMARK_COUNT; i++) {
            arr3[i] = new VarThree(sublist);
        }
        long memAfter3 = measureCurrentMemory();
        // ЗМІНА ТУТ
        double mem3 = (memAfter3 - memBefore3) / (double)MEMORY_BENCHMARK_COUNT / 1024.0;
        long ops3 = runPerfTest("VarThree", arr3[0], emails);
        System.out.println("VarThree estimated memory (KB) = " + mem3 + "\n");
        csv.write(size + ",VarThree," + ops3 + "," + mem3 + "\n");
        arr3 = null;

        // --- HybridFast ---
        forceGC();
        long memBefore4 = measureCurrentMemory();
        Object[] arr4 = new Object[MEMORY_BENCHMARK_COUNT];
        for (int i = 0; i < MEMORY_BENCHMARK_COUNT; i++) {
            arr4[i] = new HybridFast(sublist);
        }
        long memAfter4 = measureCurrentMemory();
        // ЗМІНА ТУТ
        double mem4 = (memAfter4 - memBefore4) / (double)MEMORY_BENCHMARK_COUNT / 1024.0;
        long ops4 = runPerfTest("HybridFast", arr4[0], emails);
        System.out.println("HybridFast estimated memory (KB) = " + mem4 + "\n");
        csv.write(size + ",HybridFast," + ops4 + "," + mem4 + "\n");
        arr4 = null;
        
        csv.flush();
    }

    csv.close();
}

    /**
     * Тестує ТІЛЬКИ ШВИДКІСТЬ (код не змінився)
     */
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
                if (var instanceof VarOne v) v.biggestGroupAverage(null);
                else if (var instanceof VarTwo v) v.biggestGroupAverage();
                else if (var instanceof VarThree v) v.biggestGroupAverage();
                else if (var instanceof HybridFast v) v.biggestGroupAverage();
            }
            ops++;
        }
        System.out.println(name + " ops in " + BENCHMARK_SECONDS + "sec = " + ops);
        return ops;
    }
}