import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class VarThree {
    private TreeSet<Student> students;

    public VarThree(List<Student> studentList) {
        this.students = new TreeSet<>(Comparator.comparingDouble(Student::getRating).reversed().thenComparing(Student::getEmail));
        this.students.addAll(studentList);
    }

    public List<Student> firstHundred() {
        List<Student> top = new ArrayList<>();
        int count = 0;
        for (Student s : students) {
            if (count++ == 100) break;
            top.add(s);
        }
        return top;
    }

    public void findByEmail(String email, double newRating) {
        Student target = null;

        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                target = student;
                break;
            }
        }
        
        if (target != null) {
            students.remove(target);
            target.setRating(newRating);
            students.add(target);
        }
    }


    public String biggestGroupAverage() {
        HashMap<String, Double> sumMap = new java.util.HashMap<>();
        HashMap<String, Integer> countMap = new java.util.HashMap<>();

        for (Student student : students) {
            sumMap.put(student.getGroup(), sumMap.getOrDefault(student.getGroup(), 0.0) + student.getRating());
            countMap.put(student.getGroup(), countMap.getOrDefault(student.getGroup(), 0) + 1);
        }

        String bestGroup = null;
        double bestAverage = 0;

        for (String group : sumMap.keySet()) {
            double avg = sumMap.get(group) / countMap.get(group);
            if (avg > bestAverage) {
                bestAverage = avg;
                bestGroup = group;
            }
        }

        return bestGroup;
    }
}
