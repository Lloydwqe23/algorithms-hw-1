import java.util.*;

public class HybridFast {
    private ArrayList<Student> students;
    private HashMap<String, Student> emailMap;
    private HashMap<String, double[]> groupMap;

    public HybridFast(List<Student> list) {
        students = new ArrayList<>(list);
        emailMap = new HashMap<>();
        groupMap = new HashMap<>();
        for (Student s : students) {
            emailMap.put(s.getEmail(), s);
            groupMap.putIfAbsent(s.getGroup(), new double[]{0.0, 0.0});
            double[] data = groupMap.get(s.getGroup());
            data[0] += s.getRating();
            data[1] += 1;
        }
    }

    public List<Student> firstHundred() {
        PriorityQueue<Student> heap = new PriorityQueue<>(100, Comparator.comparingDouble(Student::getRating));
        for (Student s : students) {
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
        Student s = emailMap.get(email);
        if (s != null) {
            double[] data = groupMap.get(s.getGroup());
            data[0] += (newRating - s.getRating());
            s.setRating(newRating);
        }
    }

    public String biggestGroupAverage() {
        String bestGroup = null;
        double bestAvg = 0;
        for (Map.Entry<String, double[]> entry : groupMap.entrySet()) {
            double avg = entry.getValue()[0] / entry.getValue()[1];
            if (avg > bestAvg) {
                bestAvg = avg;
                bestGroup = entry.getKey();
            }
        }
        return bestGroup;
    }
}
