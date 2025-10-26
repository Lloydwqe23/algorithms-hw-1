import java.util.Comparator;
import java.util.List;

public class VarOne {
    public static List<Student> firstHundred(List<Student> students) {
        students.sort(Comparator.comparingDouble(Student::getRating).reversed());
        return students.subList(0, Math.min(100, students.size()));
    }

    public static void findByEmail(List<Student> students, String email, double newRating) {
        for(Student student: students) {
            if(student.getEmail().equals(email)) {
                student.setRating(newRating);
            }
        }
    }
    
    
}
