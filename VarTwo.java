import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VarTwo {
    private ArrayList<Student> students;

    public VarTwo(List<Student> students) {
        this.students = new ArrayList<>(students);
    }

    public List<Student> firstHundred() {
        students.sort(Comparator.comparingDouble(Student::getRating).reversed());
        return students.subList(0, Math.min(100, students.size()));
    }

    public void findByEmail(String email, double newRating) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                student.setRating(newRating);
                return;
            }
        }
    }

    public String biggestGroupAverage() {
        List<GroupData> groups = new ArrayList<>();
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

        GroupData bestGroup = null;
        double bestAverage = 0;
        for(GroupData group: groups) {
            if(group.average() > bestAverage) {
                bestGroup = group;
                bestAverage = group.average();
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