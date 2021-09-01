package az.ibrahimshirinov.unittesting.courserecord.application;



import az.ibrahimshirinov.unittesting.courserecord.model.Course;
import az.ibrahimshirinov.unittesting.courserecord.model.Lecturer;
import az.ibrahimshirinov.unittesting.courserecord.model.Semester;

import java.util.Optional;

/**
 * @author mucahitkurt
 * @since 30.04.2018
 */
public interface LecturerService {

    Optional<Lecturer> findLecturer(Course course, Semester semester);


}
