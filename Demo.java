import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Initialization initialized = new Initialization("students.csv");
        
        StandartSort standartsorted = new StandartSort(initialized.students);
        csvSave(standartsorted.sortedStudents, "standart_sorted_students.csv");

        BSTSort bstsorted = new BSTSort(initialized.students);
        csvSave(bstsorted.sortedStudents, "bst_sorted_students.csv");
    }

    public static void csvSave(List<Student> students, String filename) {
        try(FileWriter writer = new FileWriter(filename)) {
            for(Student student: students) {
                StringBuilder write_line = new StringBuilder();
                write_line.append(student.getName()).append(',');
                write_line.append(student.getSurname()).append(',');
                write_line.append(student.getEmail()).append(',');
                write_line.append(student.getBirthYear()).append(',');
                write_line.append(student.getBirthMonth()).append(',');
                write_line.append(student.getBirthDay()).append(',');
                write_line.append(student.getGroup()).append(',');
                write_line.append(student.getRating()).append(',');
                write_line.append(student.getPhoneNumber()).append(',');
                writer.write(write_line.toString());
                writer.write("\n");
            }
        }

        catch (IOException e) {
            throw new RuntimeException("Error while writing message!");
        }
    }
}
