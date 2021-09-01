package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.Course;
import az.ibrahimshirinov.unittesting.courserecord.model.LecturerCourseRecord;
import az.ibrahimshirinov.unittesting.courserecord.model.Semester;
import az.ibrahimshirinov.unittesting.courserecord.model.Student;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author IbrahimShirinov
 * @project UnitTesting
 * @since 30.08.2021
 */
public class StudentTestWithDynamicTest {

    private Student student;


    @BeforeEach
    void setUp() {
        student = new Student("1", "Ibrahim", "Shirinov");
    }

    @TestFactory
    Stream<DynamicNode> addCourseToStudentWithCourseCodeAndCourseType() {

        return Stream.of("101", "103", "105")
                .map(courseCode -> DynamicContainer.dynamicContainer("Add course<" + courseCode + "> to student",
                        Stream.of(Course.CourseType.MANDATORY, Course.CourseType.ELECTIVE)
                                .map(courseType -> DynamicTest.dynamicTest("Added course<" + courseType + "> to student", () -> {

                                    final Course course = Course.newCourse().withCode(courseCode).withCourseType(courseType).course();
                                    final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
                                    student.addCourse(lecturerCourseRecord);
                                    assertTrue(student.isTakeCourse(course));
                                }))
                ));
    }

    @TestFactory
    Stream<DynamicTest> addCourseToStudentWithCourseCode(){

        final Stream<String> courseCodeGenerator = Stream.of("101", "103", "105");

        Function<String,String> displayNameGenerator = courseCode -> "Add course<"+courseCode+"> to student";

        ThrowingConsumer<String> testExecutor = courseCode -> {

            final Course course = Course.newCourse().withCode(courseCode).withCourseType(Course.CourseType.MANDATORY).course();
            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
            student.addCourse(lecturerCourseRecord);
            assertTrue(student.isTakeCourse(course));
        };

       return DynamicTest.stream(courseCodeGenerator,displayNameGenerator,testExecutor);
    }
}
