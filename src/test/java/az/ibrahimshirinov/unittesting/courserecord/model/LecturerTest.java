package az.ibrahimshirinov.unittesting.courserecord.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author IbrahimShirinov
 * @date 30.08.2021
 * @project UnitTesting
 */
class LecturerTest {


    /**
     * When a lecturer course record is added to lecturer then lecturer course record size increase
     * Lecturer course record has lecturer when added to lecturer
     * Throws illegal argument exception when a null course is added to lecturer
     * Throws not active semester exception when a course is added for not active semester
     */

    private Lecturer lecturer;

    @BeforeEach
    void init() {
        lecturer = new Lecturer();
    }

    private LecturerCourseRecord lecturerCourseRecord() {
        return new LecturerCourseRecord(new Course(), new Semester());
    }

    @Test
    @DisplayName("When a lecturer course record is added to lecturer then lecturer course record size increase")
    void whenACourseIsAddedToLecturerThenLecturerCourseSizeIncrease() {


        assertEquals(0, lecturer.getLecturerCourseRecords().size());
        lecturer.addLecturerCourseRecord(lecturerCourseRecord());
        assertEquals(1, lecturer.getLecturerCourseRecords().size());
    }


    @Test
    @DisplayName("Lecturer course record has lecturer when added to lecturer")
    void LecturerCourseRecordHasLecturerInfoWhenAddedToALecturer() {
        final LecturerCourseRecord lecturerCourseRecord = lecturerCourseRecord();
        lecturer.addLecturerCourseRecord(lecturerCourseRecord);
        assertEquals(lecturer, lecturerCourseRecord.getLecturer());
    }

    @Test
    @DisplayName("Throws illegal argument exception when a null course is added to lecturer")
    void throwsIllegalArgumentExceptionWhenANullCourseIsAddedToLecturer() {
        LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(null, new Semester());
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> lecturer.addLecturerCourseRecord(lecturerCourseRecord));
        assertEquals("Can't add a null course to lecturer", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("Throws not active semester exception when a course is added for not active semester")
    void throwsNotActiveSemesterExceptionWhenACourseIsAddedForNotActiveSemesterToLecturer() {
        final Semester activeSemester = new Semester();
        final LocalDate lastYear = LocalDate.of(activeSemester.getYear() - 1, 1, 1);
        final Semester notActiveSemester=new Semester(lastYear);

        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course(), notActiveSemester);
        NotActiveSemesterException notActiveSemesterException = assertThrows(NotActiveSemesterException.class, () -> lecturer.addLecturerCourseRecord(lecturerCourseRecord));
        assertEquals(notActiveSemester.toString(),notActiveSemesterException.getMessage());

    }
}