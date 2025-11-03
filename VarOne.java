import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class VarOne {
    private HashMap<String, Student> studentMap = new HashMap<>();
    private HashMap<String, double[]> studentTable = new HashMap<>();


    public VarOne(List<Student> students) {
        for (Student student: students) {
            studentMap.put(student.getEmail(), student);
            if(!studentTable.containsKey(student.getGroup())){
                studentTable.put(student.getGroup(), new double[]{student.getRating(), 1});
            }
            else {
                double[] data = studentTable.get(student.getGroup());
                data[0] += student.getRating();
                data[1] += 1;
            }
        }
    }

    public List<Student> firstHundred() {
        PriorityQueue<Student> heap = new PriorityQueue<>(100, Comparator.comparingDouble(Student::getRating));
        for (Student s : studentMap.values()) {
            if (heap.size() < 100) heap.offer(s);
            else if (s.getRating() > heap.peek().getRating()) {
                heap.poll();
                heap.offer(s);
            }
        }
        List<Student> top = new ArrayList<>(heap);
        top.sort(Comparator.comparingDouble(Student::getRating).reversed());
        return top;
    }

    public void findByEmail(String email, double newRating) {
        Student s = studentMap.get(email);
        if (s != null) {
            s.setRating(newRating);
        }
    }
    
    public String biggestGroupAverage(List<Student> students) {
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
