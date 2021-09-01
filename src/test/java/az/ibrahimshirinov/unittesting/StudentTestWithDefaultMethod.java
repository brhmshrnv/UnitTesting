package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author IbrahimShirinov
 * @date 30.08.2021
 * @project UnitTesting
 */
public class StudentTestWithDefaultMethod implements CreateDomain<Student> , TestLifeCycleReporter{

    @Override
    public Student createDomain() {
        return new Student("1","Ibrahim","Shirinov");
    }

    @Test
    void createStudent() {
        final Student student = createDomain();
        assertAll("Student",
                ()->assertEquals("1",student.getId()),
                ()->assertEquals("Ibrahim",student.getName()),
                ()->assertEquals("Shirinov",student.getSurname())
        );
    }
}
