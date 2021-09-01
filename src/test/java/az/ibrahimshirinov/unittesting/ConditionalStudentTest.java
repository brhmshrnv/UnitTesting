package az.ibrahimshirinov.unittesting;

import az.ibrahimshirinov.unittesting.courserecord.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.condition.OS.MAC;
import static org.junit.jupiter.api.condition.OS.WINDOWS;

@EnabledOnOs(OS.WINDOWS)
public class ConditionalStudentTest {

    @EnabledOnOs({MAC})
    @Test
    void shouldCreateStudentOnlyOnMac(){
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
                );
    }

    @DisabledOnOs({MAC})
    @Test
    void shouldCreateStudentOnlyOnNonMac(){
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @EnabledOnJre(JRE.JAVA_10)
    @Test
    void shouldCreateStudentOnlyOnJRE10(){
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @EnabledOnJre({JRE.JAVA_8,JRE.JAVA_11})
    @Test
    void shouldCreateStudentOnlyOnJRE8AndJRE11(){
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @DisabledOnJre({JRE.JAVA_9,JRE.JAVA_16})
    @Test
    void shouldCreateStudentOnlyOnNonJRE9AndJRE16(){
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @DisabledOnJre(JRE.JAVA_17)
    @Test
    void shouldCreateStudentOnlyOnNonJRE17(){
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @EnabledIfSystemProperty(named = "os.arch",matches = ".*64.*")
    @Test
    void shouldCreateStudentOnlyOn64Architectures(){
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @EnabledIfSystemProperty(named = "ENV",matches = "dev")
    @Test
    void shouldCreateStudentOnlyOnDevMachine(){
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @EnabledIfEnvironmentVariable(named = "ENV",matches = "staging-server")
    @Test
    void shouldCreateStudentOnlyOnStagingServerEnv(){
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @DisabledIfEnvironmentVariable(named = "ENV",matches = "CI")
    @Test
    void shouldCreateStudentOnlyOnNonCIEnv(){
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @EnabledIf("2 * 3 == 6") // Static JavaScript expression.
    @Test
    void shouldCreateStudentIfStaticJSExpressionIsEvaluatedToTrue() {
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @DisabledIf("Math.random() < 1000000000") // Dynamic JavaScript expression.
    @Test
    void shouldCreateStudentIfDynamicJSExpressionIsEvaluatedToTrue() {
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @DisabledIf("/64/.test(systemProperty.get('os.arch'))") // Regular expression testing bound system property.
    @Test
    void shouldCreateStudentOnlyOn32BitArchitectures() {
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @EnabledIf("'staging-server' == systemEnvironment.get('ENV')")
    @Test
    void shouldCreateStudentOnlyOnStagingEnvEvaluatedWithJS() {
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @TestOnWindowsWithJRE11
    void shouldCreateStudentOnlyOnWindowsWithJRE11() {
        final Student student = new Student("1","Ibrahim","Shirinov");
        assertAll("Student information",
                () -> assertEquals("Ibrahim",student.getName()),
                () -> assertEquals("Shirinov",student.getSurname()),
                () -> assertEquals("1",student.getId())
        );
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @EnabledOnOs(WINDOWS)
    @EnabledOnJre(JRE.JAVA_11)
    @interface TestOnWindowsWithJRE11 {
    }
}
