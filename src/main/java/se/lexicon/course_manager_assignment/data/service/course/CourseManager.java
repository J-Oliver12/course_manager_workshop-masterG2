package se.lexicon.course_manager_assignment.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
        Course newCourse = courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration());
        return converters.courseToCourseView(newCourse);
    }

    @Override
    public CourseView update(UpdateCourseForm form) {
        Course existingCourse = courseDao.findById(form.getId());
        if (existingCourse != null) {
            existingCourse.setCourseName(form.getCourseName());
            existingCourse.setStartDate(form.getStartDate());
            existingCourse.setWeekDuration(form.getWeekDuration());
            return converters.courseToCourseView(existingCourse);
        }
        return null;                                                                    // Course not found
    }

    @Override
    public List<CourseView> searchByCourseName(String courseName) {
        Collection<Course> courses = courseDao.findByNameContains(courseName);
        return courses.stream()
                .map(converters::courseToCourseView)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        Collection<Course> courses = courseDao.findByDateBefore(end);
        return courses.stream()
                .map(converters::courseToCourseView)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {
        Collection<Course> courses =courseDao.findByDateAfter(start);
        return courses.stream()
                .map(converters::courseToCourseView)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
        Course course = courseDao.findById(courseId);
        if (course != null) {
            return course.enrollStudent(studentDao.findById(studentId));
        }
        return false;                                                                    // Course not found
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
        Course course = courseDao.findById(courseId);
        if (course != null) {
            return  course.unEnrollStudent(studentDao.findById(studentId));
        }
        return false;                                                                    // Course not found
    }

    @Override
    public CourseView findById(int id) {
        Course course = courseDao.findById(id);
        if (course != null) {
            return converters.courseToCourseView(course);
        }
        return null;                                                                    // Course not found
    }

    @Override
    public List<CourseView> findAll() {
        Collection<Course> courses = courseDao.findAll();
        return courses.stream()
                .map(converters::courseToCourseView)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) {
        Collection<Course> courses = courseDao.findByStudentId(studentId);
        return courses.stream()
                .map(converters::courseToCourseView)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteCourse(int id) {
        Course courseToDelete = courseDao.findById(id);
        if (courseToDelete != null) {
            return courseDao.removeCourse(courseToDelete);
        }
        return false;                                                                   // Course not found
    }
}
