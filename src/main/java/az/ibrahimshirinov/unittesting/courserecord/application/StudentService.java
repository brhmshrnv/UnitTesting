package az.ibrahimshirinov.unittesting.courserecord.application;



import az.ibrahimshirinov.unittesting.courserecord.model.Course;
import az.ibrahimshirinov.unittesting.courserecord.model.Semester;
import az.ibrahimshirinov.unittesting.courserecord.model.Student;
import az.ibrahimshirinov.unittesting.courserecord.model.StudentCourseRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author mucahitkurt
 * @since 30.04.2018
 */
public interface StudentService {

    void addCourse(String studentId, Course course);

    void addCourse(String studentId, Course course, Semester semester);

    void dropCourse(String studentId, Course course);

    void addGrade(String studentId, Course course, StudentCourseRecord.Grade grade);

    boolean isTakeCourse(String studentId, Course course);

    BigDecimal gpa(String studentId);

    List<TranscriptItem> transcript(String studentId);

    Optional<Student> findStudent(String studentId);

    void deleteStudent(String studentId);
}
