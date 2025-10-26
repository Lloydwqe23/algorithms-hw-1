import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Initialization {
    List<Student> students = new ArrayList<>();
    Initialization(String filename) {

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) { 
                String[] splitted = line.split(",");
                if(splitted.length != 9) {
                    System.out.println("Line is not correct");
                }
                else{
                    String name = splitted[0];
                    String surname = splitted[1];
                    String email = splitted[2];
                    int birthYear = Integer.parseInt(splitted[3]);
                    int birthMonth = Integer.parseInt(splitted[4]);
                    int birthDay = Integer.parseInt(splitted[5]);
                    String group = splitted[6];
                    double rating = Float.parseFloat(splitted[7]);
                    String phoneNumber = splitted[8];

                    Student newStudent = new Student(name, surname, email, birthYear, birthMonth, birthDay, group, rating, phoneNumber);
                    this.students.add(newStudent);
                }
            }
        }

        catch (IOException e) {
            throw new RuntimeException("Error while reading message!");
        }

    }
}
