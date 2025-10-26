import java.util.ArrayList;
import java.util.List;

public class BSTSort {
    List<Student> sortedStudents;
    Node root = null;

    class Node {
        Student student;
        Node left;
        Node right;

        Node(Student newStudent) {
            this.student = newStudent;
            this.left = null;
            this.right = null;
        }
    }

    public BSTSort(List<Student> students) {
        this.sortedStudents = sort(students);
    }


    Node insert(Node node, Student student) {
        if (node == null) {
            return new Node(student);
        }

        if (student.compareTo(node.student) < 0) {
            node.left = insert(node.left, student);
        }
        else {
            node.right = insert(node.right, student);
        }

        return node;
    }

    void inorder(Node node, List<Student> newList) {
        if(node!=null) {
            inorder(node.left, newList);
            newList.add(node.student);
            inorder(node.right, newList);
        }
    }

    public List<Student> sort(List<Student> studentList) {
        List<Student> result = new ArrayList<>();
        for (Student student : studentList) {
           root =  insert(root, student);
        }
        inorder(root, result);
        return result;
    }
}
