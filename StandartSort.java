import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class StandartSort {
    List<Student> sortedStudents;
    StandartSort(List<Student> students) {
        this.sortedStudents = students;
        Collections.sort(sortedStudents);
    }

    public static void save(List<Student> students, String filename) {
        try(FileWriter writer = new FileWriter(filename)) {
            writer.write("name,surname,email,birthYear,birthMonth,birthDay,group,rating,phone\n");
            for(Student s: students){
                writer.write(String.format("%s,%s,%s,%d,%d,%d,%s,%.6f,%s\n",
                        s.getName(), s.getSurname(), s.getEmail(), s.getBirthYear(),
                        s.getBirthMonth(), s.getBirthDay(), s.getGroup(),
                        s.getRating(), s.getPhoneNumber()));
            }
        } catch(IOException e){ throw new RuntimeException(e); }
    }
}
