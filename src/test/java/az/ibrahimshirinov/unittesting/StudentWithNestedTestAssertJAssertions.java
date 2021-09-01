package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * @author IbrahimShirinov
 * @project UnitTesting
 * @since 30.08.2021
 */

//@ExtendWith(DropCourseConditionExtension.class)
//@ExtendWith(TestLoggerExtension.class)
@Tag("student")
@DisplayName("Student Test With Nested Tests")
public class StudentWithNestedTestAssertJAssertions {

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
            final Student fuad = new Student("2", "Fuad", "Shirinov");
            Student student4 = ibrahim;

            assertThat(ibrahim.getName()).as("Ibrahim")
                    .isEqualTo("Ibrahim")
                    .isNotEqualTo("Fuad")
                    .startsWith("I")
            ;

            assertThat(new Student("id1", "Fuad", "Shirinov").getName()).as("Fuad")
                    .doesNotEndWith("M")
            ;

            assertThat(List.of(fuad, ibrahim))
                    .extracting(Student::getName)
                    .containsOnly("Fuad", "Ibrahim");

            assertThat(ibrahim)
                    .isSameAs(student4)
                    .isNotSameAs(fuad);
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


            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(ibrahim.getName()).as("Ibrahim's name").isEqualTo("Ibrahim");
            softAssertions.assertThat(ibrahim.getName()).as("Ibrahimmmmm's name").isNotEqualTo("Ibrahimmmmm");
            softAssertions.assertAll();

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

                //assertJ version
                assertThatIllegalArgumentException().as("Throws IllegalArgumentException").isThrownBy(()-> ibrahim.addCourse(null));
                final Throwable throwable = catchThrowable(() -> ibrahim.addCourse(null));
                assertThat(throwable)
                        .isInstanceOf(IllegalArgumentException.class)
                                .hasMessage("Can't add course with null lecturer course record");


                //junit5 version
                assertThrows(IllegalArgumentException.class, () -> ibrahim.addCourse(null));
                assertThrows(IllegalArgumentException.class, () -> ibrahim.addCourse(null), "Throws IllegalArgumentException");
                final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> ibrahim.addCourse(null));
                assertEquals("Can't add course with null lecturer course record", illegalArgumentException.getMessage());
            }


        }

    }


}
