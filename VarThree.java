import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class VarThree {
    private TreeSet<Student> students;
    private TreeMap<String, double[]> studentTable = new TreeMap<>();

    public VarThree(List<Student> studentList) {
        this.students = new TreeSet<>(Comparator.comparingDouble(Student::getRating).reversed().thenComparing(Student::getEmail));
        for(Student student : students) {
            students.add(student);
            if(!studentTable.containsKey(student.getGroup())) {
                studentTable.put(student.getGroup(), new double[]{student.getRating(), 1});
            } else {
                double[] data = studentTable.get(student.getGroup());
                data[0] += student.getRating();
                data[1] += 1;
            }
        }
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
        String bestGroup = null;
        double bestAverage = 0;

        for(String group : studentTable.keySet()) {
            double[] data = studentTable.get(group);
            double average = data[0] / data[1];
            if(average > bestAverage) {
                bestAverage = average;
                bestGroup = group;
            }
        }

        return bestGroup;
    }
}
