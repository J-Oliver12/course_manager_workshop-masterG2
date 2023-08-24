package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.model.Course;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;


public class CourseCollectionRepository implements CourseDao{

    private Collection<Course> courses;


    public CourseCollectionRepository(Collection<Course> courses) {
        this.courses = courses;
    }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {
        Course newCourse = new Course(courseName, startDate, weekDuration);                   // Create a new course
        courses.add(newCourse);                                                               // Add the new course to the collection
        return newCourse;                                                                     // Return the created course object
    }

    @Override
    public Course findById(int id) {                                                         // Implement the logic to find a course by ID
        return null;
    }

    @Override
    public Collection<Course> findByNameContains(String name) {                              // Implement the logic to find courses by name
        return null;
    }

    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {                              // Implement the logic to find courses by date before a given date
        return null;
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {                             // Implement the logic to find courses by date after a given date
        return null;
    }

    @Override
    public Collection<Course> findAll() {                                                    // Implement the logic to find all courses
        return null;
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {                               // Implement the logic to find courses by student ID
        return null;
    }

    @Override
    public boolean removeCourse(Course course) {                                             // Implement the logic to remove a course
        return false;
    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }
}
