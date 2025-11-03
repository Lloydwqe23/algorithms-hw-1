import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CountingSort {
    public static List<Student> sort(List<Student> list) {
        int maxKey = 1231;
        int[] count = new int[maxKey + 1];
        Student[] output = new Student[list.size()];

        for (Student s : list) {
            int key = s.getBirthMonth() * 100 + s.getBirthDay();
            count[key]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = list.size() - 1; i >= 0; i--) {
            Student s = list.get(i);
            int key = s.getBirthMonth() * 100 + s.getBirthDay();
            output[--count[key]] = s;
        }

        return Arrays.asList(output);
    }

    public static void save(List<Student> students, String filename) {
        try(FileWriter writer = new FileWriter(filename)) {
            writer.write("name,surname,email,birthYear,birthMonth,birthDay,group,rating,phone\n");
            for(Student student: students){
                writer.write(String.format("%s,%s,%s,%d,%d,%d,%s,%.6f,%s\n",
                        student.getName(), student.getSurname(), student.getEmail(), student.getBirthYear(),
                        student.getBirthMonth(), student.getBirthDay(), student.getGroup(),
                        student.getRating(), student.getPhoneNumber()));
            }
        } catch(IOException e){ throw new RuntimeException(e); }
    }
}
