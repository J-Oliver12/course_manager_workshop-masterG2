package se.lexicon.course_manager_assignment.data.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentManager implements StudentService {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final Converters converters;

    @Autowired
    public StudentManager(StudentDao studentDao, CourseDao courseDao, Converters converters) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.converters = converters;
    }

    @Override
    public StudentView create(CreateStudentForm form) {
        Student newStudent = studentDao.createStudent(form.getName(), form.getEmail(), form.getAddress());
        return converters.studentToStudentView(newStudent);
    }

    @Override
    public StudentView update(UpdateStudentForm form) {
        Student existingStudent = studentDao.findById(form.getId());
        if (existingStudent != null) {
            existingStudent.setName(form.getName());
            existingStudent.setEmail(form.getEmail());
            existingStudent.setAddress(form.getAddress());
            return converters.studentToStudentView(existingStudent);
        }
        return null;                                                                    // Student not found
    }

    @Override
    public StudentView findById(int id) {
        Student student = studentDao.findById(id);
        if ( student != null) {
            return converters.studentToStudentView(student);
        }
        return null;                                                                   // Student not found
    }

    @Override
    public StudentView searchByEmail(String email) {
        Student student = studentDao.findByEmailIgnoreCase(email);
        if (student != null) {
            return  converters.studentToStudentView(student);
        }
        return null;                                                                   // Student not found
    }

    @Override
    public List<StudentView> searchByName(String name) {
        List<Student> studentsByName = studentDao.findByNameContains(name)
                .stream().collect(Collectors.toList());
        return studentsByName.stream()
                .map(converters::studentToStudentView)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentView> findAll() {
        List<Student> allStudents = studentDao.findAll()
                .stream().collect(Collectors.toList());
        return allStudents.stream()
                .map(converters::studentToStudentView)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteStudent(int id) {
        Student studentToDelete = studentDao.findById(id);
        if (studentToDelete != null) {
            return studentDao.removeStudent(studentToDelete);
        }
        return false;                                                          // Student not found
    }
}
