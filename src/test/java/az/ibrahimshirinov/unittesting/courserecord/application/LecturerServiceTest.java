package az.ibrahimshirinov.unittesting.courserecord.application;

import az.ibrahimshirinov.unittesting.courserecord.model.Course;
import az.ibrahimshirinov.unittesting.courserecord.model.Lecturer;
import az.ibrahimshirinov.unittesting.courserecord.model.LecturerRepository;
import az.ibrahimshirinov.unittesting.courserecord.model.Semester;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author IbrahimShirinov
 * @since 31.08.2021
 */
class LecturerServiceTest {


    @Test
    void findLecturer() {

        final Course course = new Course("101");
        final Semester semester = new Semester();

        final LecturerRepository lecturerRepository = Mockito.mock(LecturerRepository.class);

        final Lecturer lecturer = new Lecturer();
        Mockito.when(lecturerRepository.findByCourseAndSemester(course,semester)).thenReturn(Optional.of(lecturer));

        final LecturerServiceImpl lecturerService = new LecturerServiceImpl(lecturerRepository);
        final Optional<Lecturer> lecturerOpt = lecturerService.findLecturer(course, semester);
        assertThat(lecturerOpt)
                .isPresent()
                .get()
                .isSameAs(lecturer)
        ;

        Mockito.verify(lecturerRepository).findByCourseAndSemester(course,semester);
    }

}