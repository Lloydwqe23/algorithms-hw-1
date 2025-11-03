import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VarTwo {
    private ArrayList<Student> students;
    private List<GroupData> groups = new ArrayList<>();

    public VarTwo(List<Student> students) {
        this.students = new ArrayList<>(students);
        for(Student student: students) {
            boolean found = false;
            for (GroupData g : groups) {
                if (g.name.equals(student.getGroup())) {
                    g.addRating(student.getRating());
                    found = true;
                    break;
                }
            }
            if(!found) {
                groups.add(new GroupData(student.getGroup(), student.getRating()));
            }
        }
    }

    public List<Student> firstHundred() {
        students.sort(Comparator.comparingDouble(Student::getRating).reversed());
        return students.subList(0, Math.min(100, students.size()));
    }

    public void findByEmail(String email, double newRating) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {

                double oldRating = student.getRating();
                student.setRating(newRating);

                for (GroupData group : groups) {
                    if (group.name.equals(student.getGroup())) {
                        group.sum -= oldRating;
                        group.sum += newRating;
                        break;
                    }
                }
                break;
            }
        }
    }


    public String biggestGroupAverage() {
        GroupData bestGroup = null;
        double bestAverage = 0;
        for(GroupData group: groups) {
            double avg = group.average();
            if (avg > bestAverage) {
                bestAverage = avg;
                bestGroup = group;
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