package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.Course;
import az.ibrahimshirinov.unittesting.courserecord.model.LecturerCourseRecord;
import az.ibrahimshirinov.unittesting.courserecord.model.Semester;
import az.ibrahimshirinov.unittesting.courserecord.model.Student;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * @author IbrahimShirinov
 * @since 30.08.2021
 */
public class StudentTestWithParameterizedMethods {

    private Student student;

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class ValueSourceDemo {

        private int studentCourseSize = 0;


        @BeforeAll
        void setUp() {
            student = new Student("1", "Ibrahim", "Shirinov");
        }

        @ParameterizedTest
        @ValueSource(strings = {"101", "103", "105"})
        void addCourseToStudent(String courseCode) {

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course(courseCode), new Semester());
            student.addCourse(lecturerCourseRecord);
            studentCourseSize++;
            assertEquals(studentCourseSize, student.getStudentCourseRecords().size());
            assertTrue(student.isTakeCourse(new Course(courseCode)));
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class EnumSourceDemo {

        @BeforeAll
        void setup() {
            student = new Student("2", "Fuad", "Shirinov");
        }

        @ParameterizedTest
        @EnumSource(Course.CourseType.class)
        void addCourseStudent(Course.CourseType courseType) {

            final Course course = Course.newCourse()
                    .withCode(String.valueOf(new Random().nextInt(200)))
                    .withCourseType(courseType).course();

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
            student.addCourse(lecturerCourseRecord);
            assertFalse(student.getStudentCourseRecords().isEmpty());
            assertTrue(student.isTakeCourse(course));

        }

        @ParameterizedTest
        @EnumSource(value = Course.CourseType.class, names = "MANDATORY")
        void addMandatoryCourseStudent(Course.CourseType courseType) {

            final Course course = Course.newCourse()
                    .withCode(String.valueOf(new Random().nextInt(200)))
                    .withCourseType(courseType).course();

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
            student.addCourse(lecturerCourseRecord);
            assertFalse(student.getStudentCourseRecords().isEmpty());
            assertTrue(student.isTakeCourse(course));
            assertEquals(Course.CourseType.MANDATORY, course.getCourseType());
        }

        @ParameterizedTest
        @EnumSource(value = Course.CourseType.class, mode = EnumSource.Mode.EXCLUDE, names = "MANDATORY")
        void addElectiveCourseStudent(Course.CourseType courseType) {

            final Course course = Course.newCourse()
                    .withCode(String.valueOf(new Random().nextInt(200)))
                    .withCourseType(courseType).course();

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
            student.addCourse(lecturerCourseRecord);
            assertFalse(student.getStudentCourseRecords().isEmpty());
            assertTrue(student.isTakeCourse(course));
            assertEquals(Course.CourseType.ELECTIVE, course.getCourseType());
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class MethodSourceDemo {


        private int studentCourseSize = 0;

        @BeforeAll
        void setUp() {
            student = new Student("1", "Ibrahim", "Shirinov");
        }

        @ParameterizedTest
        @MethodSource
        void addCourseToStudent(String courseCode) {

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course(courseCode), new Semester());
            student.addCourse(lecturerCourseRecord);
            studentCourseSize++;
            assertEquals(studentCourseSize, student.getStudentCourseRecords().size());
            assertTrue(student.isTakeCourse(new Course(courseCode)));
        }

        Stream<String> addCourseToStudent() {
            return Stream.of("101", "102", "105");
        }

        @ParameterizedTest
        @MethodSource("courseWithCodeAndType")
        void addCourseToStudent(String courseCode, Course.CourseType courseType) {

            final Course course = new Course(courseCode);
            course.setCourseType(courseType);

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
            student.addCourse(lecturerCourseRecord);
            studentCourseSize++;

            assertEquals(studentCourseSize, student.getStudentCourseRecords().size());
            assertTrue(student.isTakeCourse(new Course(courseCode)));

            assumingThat(courseCode.equals("101"),
                    () -> assertEquals(Course.CourseType.MANDATORY, courseType)
            );

            assumingThat(courseCode.equals("103"),
                    () -> assertEquals(Course.CourseType.ELECTIVE, courseType)
            );

            assumingThat(courseCode.equals("105"),
                    () -> assertEquals(Course.CourseType.MANDATORY, courseType)
            );
        }

        Stream<Arguments> courseWithCodeAndType() {
            return Stream.of(
                    Arguments.of("101", Course.CourseType.MANDATORY),
                    Arguments.of("103", Course.CourseType.ELECTIVE),
                    Arguments.of("105", Course.CourseType.MANDATORY)


            );
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class CsvSourceDemo {

        private int studentCourseSize = 0;

        @BeforeAll
        void setUp() {
            student = new Student("1", "Ibrahim", "Shirinov");
        }

        @ParameterizedTest
        @CsvSource({"101,MANDATORY","103,ELECTIVE","105,MANDATORY"})
        void addCourseToStudent(String courseCode, Course.CourseType courseType) {

            final Course course = new Course(courseCode);
            course.setCourseType(courseType);

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
            student.addCourse(lecturerCourseRecord);
            studentCourseSize++;

            assertEquals(studentCourseSize, student.getStudentCourseRecords().size());
            assertTrue(student.isTakeCourse(new Course(courseCode)));

            assumingThat(courseCode.equals("101"),
                    () -> assertEquals(Course.CourseType.MANDATORY, courseType)
            );

            assumingThat(courseCode.equals("103"),
                    () -> assertEquals(Course.CourseType.ELECTIVE, courseType)
            );

            assumingThat(courseCode.equals("105"),
                    () -> assertEquals(Course.CourseType.MANDATORY, courseType)
            );
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/courseCodeAndTypes.csv", numLinesToSkip = 1)
        void addCourseToStudentWithCSVFile(String courseCode, Course.CourseType courseType) {

            final Course course = new Course(courseCode);
            course.setCourseType(courseType);

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
            student.addCourse(lecturerCourseRecord);
            studentCourseSize++;

            assertEquals(studentCourseSize, student.getStudentCourseRecords().size());
            assertTrue(student.isTakeCourse(new Course(courseCode)));

            assumingThat(courseCode.equals("101"),
                    () -> assertEquals(Course.CourseType.MANDATORY, courseType)
            );

            assumingThat(courseCode.equals("103"),
                    () -> assertEquals(Course.CourseType.ELECTIVE, courseType)
            );

            assumingThat(courseCode.equals("105"),
                    () -> assertEquals(Course.CourseType.MANDATORY, courseType)
            );
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class ArgumentsSourceDemo{

        private int studentCourseSize = 0;

        @BeforeAll
        void setUp() {
            student = new Student("1", "Ibrahim", "Shirinov");
        }

        @ParameterizedTest
        @ArgumentsSource(CourseCodeAndTypeProvider.class)
        void addCourseToStudent(String courseCode, Course.CourseType courseType) {

            final Course course = new Course(courseCode);
            course.setCourseType(courseType);

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
            student.addCourse(lecturerCourseRecord);
            studentCourseSize++;

            assertEquals(studentCourseSize, student.getStudentCourseRecords().size());
            assertTrue(student.isTakeCourse(new Course(courseCode)));

            assumingThat(courseCode.equals("101"),
                    () -> assertEquals(Course.CourseType.MANDATORY, courseType)
            );

            assumingThat(courseCode.equals("103"),
                    () -> assertEquals(Course.CourseType.ELECTIVE, courseType)
            );

            assumingThat(courseCode.equals("105"),
                    () -> assertEquals(Course.CourseType.MANDATORY, courseType)
            );
        }

    }

    static class  CourseCodeAndTypeProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of("101", Course.CourseType.MANDATORY),
                    Arguments.of("103", Course.CourseType.ELECTIVE),
                    Arguments.of("105", Course.CourseType.MANDATORY)
            );
        }
    }


    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class TypeConversionAndCustomDisplayNameDemo{
        //enum conversion

        @BeforeAll
        void setup() {
            student = new Student("2", "Fuad", "Shirinov");
        }

        @ParameterizedTest
        @ValueSource(strings = {"MANDATORY","ELECTIVE"})
        void addCourseStudent(Course.CourseType courseType) {

            final Course course = Course.newCourse()
                    .withCode(String.valueOf(new Random().nextInt(200)))
                    .withCourseType(courseType).course();

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
            student.addCourse(lecturerCourseRecord);
            assertFalse(student.getStudentCourseRecords().isEmpty());
            assertTrue(student.isTakeCourse(course));

        }

        //user guide for other options


        //factory method or constructor conversion
        @ParameterizedTest
        @ValueSource(strings = {"101","103"})
        void addCourseStudent(Course course) {

            final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
            student.addCourse(lecturerCourseRecord);
            assertFalse(student.getStudentCourseRecords().isEmpty());
            assertTrue(student.isTakeCourse(course));

        }

        //conversion using SimpleConverter with @ConvertWith
        //conversion with @JavaTimeConversionPattern

        //display name {index} , {argument}, {0} usage
    }

}
