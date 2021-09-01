package az.ibrahimshirinov.unittesting.courserecord.model;

import java.util.Optional;

public interface LecturerRepository {

    Optional<Lecturer> findByCourseAndSemester(Course course, Semester semester);
}
