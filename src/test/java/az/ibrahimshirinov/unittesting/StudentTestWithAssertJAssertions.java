package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * @author IbrahimShirinov
 * @since 31.08.2021
 */
public class StudentTestWithAssertJAssertions {

    @Test
    void createStudent() {
        final Student student = new Student("1", "Ibrahim", "Shirinov");

        assertThat(student.getName()).as("Student's name %s",student.getName())
                .doesNotContainOnlyWhitespaces()
                .isNotEmpty()
                .isNotBlank()
                .isEqualTo("Ibrahim")
                .isEqualToIgnoringCase("ibrahim")
                .isIn("Ibrahim","Fuad")
                .isNotIn("Fuad")
                .startsWith("I")
                .doesNotStartWith("B")
                .endsWith("m")
                .doesNotEndWith("F")
                .contains("bra")
                .contains(List.of("bra","him","rah"))
                .hasSize(7)
                .matches("^I\\w{5}m$")
        ;
    }


    @Test
    void addCourseToStudent() {

        final Student ibrahim = new Student("1", "Ibrahim", "Shirinov", LocalDate.of(1993, 11, 30));
        final Student fuad = new Student("2", "Fuad", "Shirinov", LocalDate.of(1995, 4, 14));
        final Student aydin = new Student("3", "Aydin", "Abbasov", LocalDate.of(1995, 4, 14));
        final Student samir = new Student("4", "Samir", "Ismayilov", LocalDate.of(1992, 1, 3));
        final Student zaur = new Student("5", "Zaur", "Ismayilov", LocalDate.of(1991, 5, 19));

        final List<Student> students = List.of(ibrahim, fuad, aydin, samir, zaur);

        assertThat(students).as("Student's List")
                .isNotNull()
                .isNotEmpty()
                .hasSize(5)
                .contains(ibrahim,fuad)
                .containsOnly(ibrahim,aydin,fuad,zaur,samir)
                .containsExactly(ibrahim,fuad,aydin,samir,zaur)
                .containsExactlyInAnyOrder(ibrahim, fuad, aydin, samir, zaur)
        ;

        assertThat(students)
                .filteredOn(student -> student.getBirthDate().until(LocalDate.now(), ChronoUnit.YEARS) >=25)
                .hasSize(5)
                .containsOnly(ibrahim, fuad, aydin, samir, zaur)
        ;

        assertThat(students)
                .filteredOn(new Condition<>() {
                    @Override
                    public boolean matches(Student value) {
                        return value.getBirthDate().until(LocalDate.now(), ChronoUnit.YEARS) >=25;
                    }
                })
                .hasSize(5)
                .containsOnly(ibrahim, fuad, aydin, samir, zaur)
                ;


        assertThat(students)
                .filteredOn("birthDate",in(LocalDate.of(1995,4,14)))
                .hasSize(2)
                .containsOnly(fuad,aydin)
                ;


        assertThat(students)
                .extracting(Student::getName)
                .filteredOn(name -> name.contains("a"))
                .hasSize(4)
                .containsOnly("Ibrahim", "Fuad", "Samir", "Zaur")
                ;


        assertThat(students)
                .filteredOn(student -> student.getName().contains("a"))
                .extracting(Student::getName,Student::getSurname)
                .containsOnly(
                        tuple("Ibrahim","Shirinov"),
                        tuple("Fuad","Shirinov"),
                        tuple("Samir","Ismayilov"),
                        tuple("Zaur","Ismayilov")
                        )
                ;

        final LecturerCourseRecord lecturerCourseRecord101 = new LecturerCourseRecord(new Course("101"), new Semester());
        final LecturerCourseRecord lecturerCourseRecord103 = new LecturerCourseRecord(new Course("103"), new Semester());
        final LecturerCourseRecord lecturerCourseRecord105= new LecturerCourseRecord(new Course("105"), new Semester());


        ibrahim.addCourse(lecturerCourseRecord101);
        ibrahim.addCourse(lecturerCourseRecord103);

        fuad.addCourse(lecturerCourseRecord101);
        fuad.addCourse(lecturerCourseRecord103);
        fuad.addCourse(lecturerCourseRecord105);

        assertThat(students)
                .filteredOn(student -> student.getName().equals("Ibrahim") || student.getName().equals("Fuad"))
                .flatExtracting(Student::getStudentCourseRecords)
                .hasSize(5)
                .filteredOn(studentCourseRecord -> studentCourseRecord.getLecturerCourseRecord().getCourse().getCode().equals("101"))
                .hasSize(2)
                ;
    }

    @Test
    void anotherCreateStudentTest() {

        final Department department = new Department();


        final Student ibrahim = new Student("1", "Ibrahim", "Shirinov",LocalDate.of(1993,11,30));
        ibrahim.setDepartment(department);
        final Student fuad = new Student("2", "Fuad", "Shirinov",LocalDate.of(1995,4,14));
        fuad.setDepartment(department);

        assertThat(ibrahim).as("Check student ibrahim info")
                .isNotNull()
                .hasSameClassAs(fuad)
                .isExactlyInstanceOf(Student.class)
                .isInstanceOf(UniversityMember.class)
                .isNotEqualTo(fuad)
                .isEqualToComparingOnlyGivenFields(fuad,"surname")
                .isEqualToIgnoringGivenFields(fuad,"id","name","birthDate")
                .matches(student -> student.getBirthDate().getYear()==1993)
                .hasFieldOrProperty("name")
                .hasNoNullFieldsOrProperties()
                .extracting(Student::getName,Student::getSurname)
                .containsOnly("Ibrahim","Shirinov")
        ;

        StudentAssert.assertThat(ibrahim).as("Student ibrahim info check")
                .hasName("Ibrahim")
        ;

    }

    @Test
    void addCourseStudentWIthExceptionalScenarios() {

        final Student student = new Student("1", "Ibrahim", "Shirinov");

        assertThatThrownBy(() -> student.addCourse(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Can't add course ")
                .hasMessage("Can't add course with null lecturer course record")
                .hasStackTraceContaining("Student")
                ;

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> student.addCourse(null))
                .withMessageContaining("Can't add course with null lecturer course record")
                .withNoCause()
                ;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> student.addCourse(null))
                .withMessageContaining("Can't add course with ")
                .withNoCause()
        ;


        assertThatCode(() -> student.addCourse(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Can't add course with null lecturer course record")
                .hasMessageContaining("Can't add course")
                ;

        assertThatCode(() -> student.addCourse(new LecturerCourseRecord()))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> student.addGrade(null,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasCauseExactlyInstanceOf(NoCourseFoundForStudentException.class)
        ;

        final Throwable throwable = catchThrowable(() -> student.addCourse(null));
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Can't add course")
                ;
    }
}
