package az.ibrahimshirinov.unittesting.courserecord.application;


import az.ibrahimshirinov.unittesting.courserecord.model.Course;
import az.ibrahimshirinov.unittesting.courserecord.model.Department;

import java.util.Optional;

/**
 * @author mucahitkurt
 * @since 30.04.2018
 */
public interface CourseService {

    Optional<Course> findCourse(Course course);

    Optional<Course> findCourse(Department department, String code, String name);
}
