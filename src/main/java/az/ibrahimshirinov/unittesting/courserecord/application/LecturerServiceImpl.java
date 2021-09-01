package az.ibrahimshirinov.unittesting.courserecord.application;

import az.ibrahimshirinov.unittesting.courserecord.model.Course;
import az.ibrahimshirinov.unittesting.courserecord.model.Lecturer;
import az.ibrahimshirinov.unittesting.courserecord.model.LecturerRepository;
import az.ibrahimshirinov.unittesting.courserecord.model.Semester;

import java.util.Optional;

/**
 * @author mucahitkurt
 * @since 30.04.2018
 */
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;

    public LecturerServiceImpl(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public Optional<Lecturer> findLecturer(Course course, Semester semester) {
        if (course == null || semester == null) {
            throw new IllegalArgumentException("Can't find lecturer without course and semester");
        }
        return lecturerRepository.findByCourseAndSemester(course, semester);
    }
}
