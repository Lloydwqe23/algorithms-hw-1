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

    public String biggestGroupAverage(List<Student> students) {
        List<GroupData> groups = new ArrayList<>();
        for(Student student: students) {
            boolean found = false;
            for (GroupData g : groups) {
                if (g.name.equals(student.getGroup())) {
                    g.addRating(student.getRating());
                    found = true;
                    break;
                }
            if(!found) {
                groups.add(new GroupData(student.getGroup(), student.getRating()));
            }
            }
        }

        GroupData bestGroup = null;
        double bestRating = 0;
        for(GroupData group: groups) {
            if(group.average() > bestRating) {
                bestGroup = group;
                bestRating = group.average();
            }
        }
        if(bestGroup!=null) {
            return bestGroup.name;
        }
        return null;
    }
}


class GroupData {
    String name;
    double sum;
    int count;

    GroupData(String name, double rating) {
        this.name = name;
        this.sum = rating;
        this.count = 1;
    }

    void addRating(double rating) {
        this.sum += rating;
        this.count++;
    }
    double average() {
        return sum/count;
    }

}