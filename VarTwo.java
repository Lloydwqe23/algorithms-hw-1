import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class VarTwo {
    HashMap<String, Student> studentEmail = new HashMap<>();

    public List<Student> firstHundred(List<Student> students) {
        PriorityQueue<Student> bestHundred = new PriorityQueue<>(Comparator.comparingDouble(Student::getRating));
        for(Student student: students) {
            if(bestHundred.size() < 100) {
                bestHundred.add(student);
            }
            else if (student.getRating() > bestHundred.peek().getRating()) {
                bestHundred.poll();
                bestHundred.add(student);
            }
        }
        return new ArrayList<>(bestHundred);
    }

    public void findByEmail(List<Student> students, String email, double newRating) {
        for(Student student: students) {
            studentEmail.put(student.getEmail(), student);
        }
        Student our_student = this.studentEmail.get(email);
        our_student.setRating(newRating);
    }
}
