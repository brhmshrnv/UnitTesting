package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.Course;
import az.ibrahimshirinov.unittesting.courserecord.model.LecturerCourseRecord;
import az.ibrahimshirinov.unittesting.courserecord.model.Semester;
import az.ibrahimshirinov.unittesting.courserecord.model.Student;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author IbrahimShirinov
 * @date 30.08.2021
 * @project UnitTesting
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentTestWithRepeatedTest implements TestLifeCycleReporter {

    private Student student;

    @BeforeAll
    void setUp(){
        student = new Student("1","Ibrahim","Shirinov");
    }

    @DisplayName("Add course to Student")
    @RepeatedTest(value = 5,name = "{displayName} => Add one course to student and student has {currentRepetition} courses")
    void addCourseToStudent(RepetitionInfo repetitionInfo){
        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course(String.valueOf(repetitionInfo.getCurrentRepetition())), new Semester());
        student.addCourse(lecturerCourseRecord);
        assertEquals(repetitionInfo.getCurrentRepetition(),student.getStudentCourseRecords().size());
    }
}
