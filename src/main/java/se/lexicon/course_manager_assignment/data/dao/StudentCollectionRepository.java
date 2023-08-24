package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;

import java.util.Collection;
import java.util.HashSet;


public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {
        Student newStudent = new Student(StudentSequencer.nextStudentId(), name, email, address);   // Create a new student object and generates ID
        students.add(newStudent);                                                                   // Add the new student to the collection
        return newStudent;                                                                          // Return the created student object
    }

    @Override
    public Student findByEmailIgnoreCase(String email) {
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student;
            }
        }
        return null;                                                                                // If not found
    }

    @Override
    public Collection<Student> findByNameContains(String name) {
        Collection<Student> result = new HashSet<>();
        for (Student student : students) {
            if (student.getName().contains(name)){
                result.add(student);
            }
        }
        return result;
    }

    @Override
    public Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;                                                             // If not found
    }

    @Override
    public Collection<Student> findAll() {
        return new HashSet<>(students);
    }

    @Override
    public boolean removeStudent(Student student) {
        return students.remove(student);
    }

    @Override
    public void clear() {
        students.clear();
    }
}
