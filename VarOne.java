import java.util.Comparator;
import java.util.HashMap;
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
    
    public String biggestGroupAverage(List<Student> students) {
        HashMap<String, double[]> studentTable = new HashMap<>();
        for(Student student: students) {
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
