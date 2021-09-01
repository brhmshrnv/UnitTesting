package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author IbrahimShirinov
 * @date 30.08.2021
 * @project UnitTesting
 */

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class StudentTestWithTestLifeCycle {

    private Student student = new Student("1","Ibrahim","Shirinov");

    @BeforeAll
    void setup(){

    }


    @Test
    void stateCannotChangeWhenLifeCycleIsPerMethod() {

        assertEquals("Ibrahim",student.getName());
        student = new Student("2","Fuad","Shirinov");
    }

    @Test
    void stateCannotChangeWhenLifeCycleIsPerClass() {

        assertEquals("Ibrahim",student.getName());
        student = new Student("2","Fuad","Shirinov");
    }
}
