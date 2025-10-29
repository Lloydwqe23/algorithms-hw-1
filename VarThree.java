import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class VarThree {
    public static List<Student> firstHundred(List<Student> students) {
        TreeSet<Student> bestHundred = new TreeSet<>(Comparator.comparingDouble(Student::getRating).thenComparing(Student::getName));
        for(Student student: students) {
            if (bestHundred.size() < 100) {
                bestHundred.add(student);
            }
            else if (student.getRating() > bestHundred.first().getRating()) {
                bestHundred.pollFirst();
                bestHundred.add(student);
            }
        }
        return new ArrayList<>(bestHundred.descendingSet());
    }

    public void findByEmail(List<Student> students, String email, double newRating) {

    }


    public String biggestGroupAverage() {

    }
}
