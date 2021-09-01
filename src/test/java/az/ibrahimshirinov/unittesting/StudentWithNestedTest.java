package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * @author IbrahimShirinov
 * @since 30.08.2021
 * @project UnitTesting
 */

@ExtendWith(DropCourseConditionExtension.class)
//@ExtendWith(TestLoggerExtension.class)
@Tag("student")
@DisplayName("Student Test With Nested Tests")
public class StudentWithNestedTest {

    @RegisterExtension
    static TestLoggerExtension testLoggerExtension = new TestLoggerExtension();

    @Nested
    @DisplayName("Create Student")
    @Tag("createStudent")
    class CreateStudent {

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
            assumingThat(env != null && env.equals("dev"), () -> {
                LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord();
                student.addCourse(lecturerCourseRecord);
                assertEquals(1, student.getStudentCourseRecords().size());
            });

            assertAll("Student information",
                    () -> assertEquals("Ibrahim", student.getName()),
                    () -> assertEquals("Shirinov", student.getSurname()),
                    () -> assertEquals("1", student.getId())
            );
        }

    }

    @Nested
    @DisplayName("Add Course to Student")
    @Tag("addCourse")
    class AddCourseToStudent {

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


        @Nested
        @DisplayName("Add Course to Student(exceptional)")
        class AddCourseToStudentExceptionScenario {

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


        }

    }

    @Nested
    @DisplayName("Drop Course from Student")
    @Tag("dropCourse")
    class DropCourseFromStudent {

        //throws illegal argument exception for null lecturer course record
        //throws illegal argument exception if the student didn't register course before
        //throws not active semester exception if the semester is  not active
        //throws not active semester exception if the add drop period is closed for the semester
        //drop course from student


        @TestFactory
        Stream<DynamicTest> dropCourseFromStudentFactory() {
            final Student student = new Student("1", "Ibrahim", "Shirinov");
            return Stream.of(
                    dynamicTest("throws illegal argument exception for null lecturer course record", () -> {
                        assertThrows(IllegalArgumentException.class, () -> student.dropCourse(null));
                    }),
                    dynamicTest("throws illegal argument exception if the student didn't register course before", () -> {
                        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course("101"), new Semester());
                        assertThrows(IllegalArgumentException.class, () -> student.dropCourse(lecturerCourseRecord));
                    }),
                    dynamicTest("throws not active semester exception if the semester is  not active", () -> {
                        final Semester notActiveSemester = notActiveSemester();
                        assumeTrue(!notActiveSemester.isActive());
                        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course("101"), notActiveSemester);
                        Student student2 = new Student("1", "Fuad", "Shirinov", Set.of(new StudentCourseRecord(lecturerCourseRecord)));
                        assertThrows(NotActiveSemesterException.class, () -> student2.dropCourse(lecturerCourseRecord));
                    }),
                    dynamicTest("throws not active semester exception if the add drop period is closed for the semester", () -> {
                        final Semester addDroppedPeriodClosedSemester = addDropPeriodClosedSemester();
                        assumeTrue(!addDroppedPeriodClosedSemester.isAddDropAllowed());
                        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course("101"), addDroppedPeriodClosedSemester);
                        Student student3 = new Student("1", "Ibrahim", "Sh", Set.of(new StudentCourseRecord(lecturerCourseRecord)));
                        assertThrows(NotActiveSemesterException.class, () -> student3.dropCourse(lecturerCourseRecord));
                    }),
                    dynamicTest("drop course from student",() -> {
                        final Semester addDropPeriodOpenSemester = addDropPeriodOpenSemester();
                        assumeTrue(addDropPeriodOpenSemester.isAddDropAllowed());
                        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course("101"), addDropPeriodOpenSemester);
                        Student student4 = new Student("1", "Ibrahim", "Sh", Set.of(new StudentCourseRecord(lecturerCourseRecord)));
                        assertEquals(1,student4.getStudentCourseRecords().size());
                        student4.dropCourse(lecturerCourseRecord);
                        assertTrue(student4.getStudentCourseRecords().isEmpty());

                    })
            );
        }

        private Semester addDropPeriodOpenSemester() {
            final Semester activeSemester = new Semester();
            final LocalDate semesterDate = LocalDate.of(activeSemester.getYear(), activeSemester.getTerm().getStartMonth(), 1);
            final LocalDate now = LocalDate.now();
            activeSemester.setAddDropPeriodInWeek(Long.valueOf(semesterDate.until(now, ChronoUnit.WEEKS)).intValue());
            return activeSemester;
        }

        private Semester addDropPeriodClosedSemester() {
            final Semester activeSemester = new Semester();
            activeSemester.setAddDropPeriodInWeek(0);
            if (LocalDate.now().getDayOfMonth() == 1) {
                activeSemester.setAddDropPeriodInWeek(-1);
            }
            return activeSemester;
        }

        private Semester notActiveSemester() {
            final Semester activeSemester = new Semester();
            return new Semester(LocalDate.of(activeSemester.getYear() - 1, 1, 1));
        }
    }



}
