package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.Course;
import az.ibrahimshirinov.unittesting.courserecord.model.LecturerCourseRecord;
import az.ibrahimshirinov.unittesting.courserecord.model.Semester;
import az.ibrahimshirinov.unittesting.courserecord.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;


@Tag("Student")
public class StudentTest {

    @Test
    @DisplayName("Test every student must have an id , name and surname")
    @Tag("Create a student")
    void shouldCreateStudentWithIdNameAndSurname() {

        /**
         * Assertions.*
         * assertEquals
         * assertEquals with message
         * assertNotEquals
         * assertTrue with lazily evaluated message
         * assertFalse with boolean supplier
         * assertNull
         * assertNotNull
         * assertArrayEquals
         * assertSame
         */

        Student ibrahim = new Student("1", "Ibrahim", "Shirinov");
        assertEquals("Ibrahim", ibrahim.getName()); // "Ibrahim".equals(ibrahim.getName())
        assertEquals("Ibrahim", ibrahim.getName(), "Student's name");
        assertNotEquals("Fuad", ibrahim.getName(), "Student's name");

        assertTrue(ibrahim.getName().startsWith("I"));
        assertTrue(ibrahim.getName().startsWith("I"), () -> "Student's name " + " starts with Ib");
        assertFalse(() -> {
            Student student2 = new Student("id1", "Fuad", "Shirinov");
            return student2.getName().endsWith("M");
        }, () -> "Student's name " + " ends with M");

        final Student student3 = new Student("2", "Fuad", "Shirinov");
        assertArrayEquals(new String[]{"Fuad", "Ibrahim"}, Stream.of(student3, ibrahim).map(Student::getName).toArray());

        Student student4 = ibrahim;

        assertSame(ibrahim, student4); // ==
        assertNotSame(student3, ibrahim);
    }


    @Test
    @DisplayName("Test every student must have and id , name and surname with grouped assertions")
    @Tag("Create a student")
    void shouldCreateStudentWithIdNameAndSurnameGroupedAssertions() {

        /**
         * grouped assertions
         * failed grouped assertions
         * dependent assertions
         */

        //In a grouped assertion all assertions are executed
        Student ibrahim = new Student("1", "Ibrahim", "Shirinov");

        assertAll("Student's name check",
                () -> assertEquals("Ibrahim", ibrahim.getName()),
                () -> assertEquals("Ibrahim", ibrahim.getName(), () -> "Student's name"),
                () -> assertNotEquals("Ibbrahiimm", ibrahim.getName(), () -> "Student's name")
        );


        // and any failures will be reported together
        assertAll("Student's name character check",
                () -> assertTrue(ibrahim.getName().startsWith("I")),
                () -> assertTrue(ibrahim.getName().startsWith("I"), () -> "Student's name " + " starts with Ibr"),
                () -> assertFalse(() -> {
                    Student fuad = new Student("id1", "Fuad", "Shirinov");
                    return fuad.getName().endsWith("F");
                }, () -> "Student's name " + " ends with F"));


        //dependent assertions
        assertAll(() -> {
                    final Student fuad = new Student("2", "Fuad", "Shirinov");
                    assertArrayEquals(new String[]{"Ibrahim", "Fuad"}, Stream.of(ibrahim, fuad).map(Student::getName).toArray());
                },
                () -> {
                    Student student = ibrahim;
                    final Student fuad = new Student("2", "Fuad", "Shirinov");
                    assertSame(ibrahim, student); // ==
                    assertNotSame(student, fuad); // !=

                });
    }

    @Test
    @DisplayName("Got an exception when add a null lecturer course record to student")
    @Tag("Add course")
    void throwsExceptionWhenAddToNullCourseToStudent() {
        final Student ibrahim = new Student("1", "Ibrahim", "Shirinov");
        assertThrows(IllegalArgumentException.class, () -> ibrahim.addCourse(null));
        assertThrows(IllegalArgumentException.class, () -> ibrahim.addCourse(null), "Throws IllegalArgumentException");
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> ibrahim.addCourse(null));
        assertEquals("Can't add course with null lecturer course record", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("Add a course to student less than 10ms")
    @Tag("Add course")
    void addCourseToStudentWithATimeConstraint() {

        /**
         * timeoutNotExceeded
         * timeoutNotExceededWithResult
         * timeoutNotExceededWithMethod
         * timeoutExceeded
         * timeoutExceededWithPreemptiveTermination
         */

        assertTimeout(Duration.ofMillis(10), () -> {
            // nothing will be done and this code run under 10ms
        });

        final String result = assertTimeout(Duration.ofMillis(10), () -> {
            //return a string and this code run under 10 ms
            return "some string result";
        });
        assertEquals("some string result", result);

        final Student ibrahim = new Student("1", "Ibrahim", "Shirinov");
        LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord();
        assertTimeout(Duration.ofMillis(6), () -> ibrahim.addCourse(lecturerCourseRecord));

        assertTimeoutPreemptively(Duration.ofMillis(6), () -> ibrahim.addCourse(lecturerCourseRecord));
    }

    @Test
    @DisplayName("Test student creation at only development machine")
    @Tag("Create a student")
    void shouldCreateStudentWithNameAndSurnameAtDevelopmentMachine() {
        assumeTrue(System.getProperty("ENV") != null, "Aborting Test: System property ENV doesnt exist");
        assumeTrue(System.getProperty("ENV").equals("dev"), "Aborting Test: Not on a developer machine!");

        final Student student = new Student("1", "Ibrahim", "Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim", student.getName()),
                () -> assertEquals("Shirinov", student.getSurname()),
                () -> assertEquals("1", student.getId()));
    }

    @Test
    @DisplayName("Test student creation at different environments")
    @Tag("Create a student")
    void shouldCreateStudentWithNameAndSurnameWithSpecificEnvironment() {

        final Student student = new Student("1", "Ibrahim", "Shirinov");
        final String env = System.getProperty("ENV");
        assumingThat(env!=null && env.equals("dev") , () -> {
            LecturerCourseRecord lecturerCourseRecord=new LecturerCourseRecord();
            student.addCourse(lecturerCourseRecord);
            assertEquals(1,student.getStudentCourseRecords().size());
        });

        assertAll("Student information" ,
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
                );
    }

}