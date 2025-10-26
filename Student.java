public class Student implements Comparable<Student> {
    private String name;
    private String surname;
    private String email;
    private int birthYear;
    private int birthMonth;
    private int birthDay;
    private String group;
    private double rating;
    private String phoneNumber;

    public Student(String name, String surname, String email, int birthYear,
                   int birthMonth, int birthDay, String group, double rating,
                   String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.group = group;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
    }


    public String getName() { 
        return name; 
    }
    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }
    public int getBirthYear() {
        return birthYear;
    }
    public int getBirthMonth() {
        return birthMonth;
    }
    public int getBirthDay() {
        return birthDay;
    }
    public String getGroup() {
        return group;
    }
    public double getRating() {
        return rating;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }


    @Override
    public int compareTo(Student other) {
        if (this.birthMonth != other.birthMonth) {
            return Integer.compare(this.birthMonth, other.birthMonth);
        }
        return Integer.compare(this.birthDay, other.birthDay);
    }

    public void setRating(double newRating) {
        this.rating = newRating;
    }
}
