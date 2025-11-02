import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class VarOne {
    private HashMap<String, Student> studentMap = new HashMap<>();


    public VarOne(List<Student> students) {
        for (Student student: students) {
            studentMap.put(student.getEmail(), student);
        }
    }

    public List<Student> firstHundred() {
        List<Student> list = new ArrayList<>(studentMap.values());
        list.sort(Comparator.comparingDouble(Student::getRating).reversed());
        return list.subList(0, Math.min(100, list.size()));
    }

    public void findByEmail(String email, double newRating) {
        Student s = studentMap.get(email);
        if (s != null) {
            s.setRating(newRating);
        }
    }
    
    public String biggestGroupAverage(List<Student> students) {
        HashMap<String, double[]> studentTable = new HashMap<>();
        for(Student student: studentMap.values()) {
            if(!studentTable.containsKey(student.getGroup())){
                studentTable.put(student.getGroup(), new double[]{student.getRating(), 1});
            }
            else {
                double[] data = studentTable.get(student.getGroup());
                data[0] += student.getRating();
                data[1] += 1;
            }
        }

        String bestGroup = null;
        double bestAverage = 0;

        for(String group: studentTable.keySet()) {
            double average = studentTable.get(group)[0]/studentTable.get(group)[1];
            if(average > bestAverage) {
                bestAverage = average;
                bestGroup = group;
            }
        }
        return bestGroup;
    }
}
