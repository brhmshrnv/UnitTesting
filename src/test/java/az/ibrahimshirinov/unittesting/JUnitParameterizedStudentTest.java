package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.Student;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author IbrahimShirinov
 * @date 30.08.2021
 * @project UnitTesting
 */
@DisplayName("Student Test with TestInfo and TestReporter Parameters")
public class JUnitParameterizedStudentTest {

    private Student student;

    //optional
    public JUnitParameterizedStudentTest(TestInfo testInfo) {
        assertEquals("Student Test with TestInfo and TestReporter Parameters",testInfo.getDisplayName());
    }

    @BeforeEach
    void setupSetStudent(TestInfo testInfo){
        if (testInfo.getTags().contains("create")) {
            student = new Student("1","Ibrahim","Shirinov");
        }else {
            student = new Student("1","Fuad","Shirinov");
        }
    }

    @Test
    @DisplayName("Create Student")
    @Tag("create")
    void createStudent() {
        assertEquals("Ibrahim",student.getName());
    }

    @Test
    @DisplayName("Add course to student")
    @Tag("add course")
    void addCourse() {
        assertEquals("Fuad",student.getName());
    }

}
