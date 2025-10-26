import java.util.Collections;
import java.util.List;


public class StandartSort {
    List<Student> sortedStudents;
    StandartSort(List<Student> students) {
        this.sortedStudents = students;
        Collections.sort(sortedStudents);
    }
}
