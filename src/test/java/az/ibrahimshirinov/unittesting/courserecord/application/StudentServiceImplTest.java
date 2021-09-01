package az.ibrahimshirinov.unittesting.courserecord.application;

import az.ibrahimshirinov.unittesting.courserecord.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private CourseService courseService;

    @Mock
    private LecturerService lecturerService;

    @Mock
    private Lecturer lecturer;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

   /* @BeforeEach
    void initMocks(){
        MockitoAnnotations.initMocks(this);
    }*/

    @Test
    void addCourse() {

        final Course course = new Course("101");
        final Semester semester = new Semester();
       // final Lecturer lecturer = Mockito.mock(Lecturer.class);
        final Student ibrahim = new Student("1", "Ibrahim", "Shirinov");


        // final CourseService courseService = Mockito.mock(CourseService.class);
        // Mockito.when(courseService.findCourse(course)).thenReturn(Optional.of(course));
        Mockito.when(courseService.findCourse(Mockito.any())).thenReturn(Optional.of(course));


       // final LecturerService lecturerService = Mockito.mock(LecturerService.class);
        Mockito.when(lecturer.lecturerCourseRecord(course, semester)).thenReturn(new LecturerCourseRecord(course, semester));
        Mockito.when(lecturerService.findLecturer(course, semester)).thenReturn(Optional.of(lecturer));


      //  final StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
        // Mockito.when(studentRepository.findById("1")).thenReturn(Optional.of(ibrahim));
        Mockito.when(studentRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ibrahim));


       // final StudentServiceImpl studentService = new StudentServiceImpl(courseService, lecturerService, studentRepository);
        studentService.addCourse("1", course, semester);

        final Optional<Student> studentOptional = studentService.findStudent("1");

        assertThat(studentOptional).as("Student")
                .isPresent()
                .get()
                .matches(student -> student.isTakeCourse(course));


        Mockito.verify(courseService).findCourse(course);
        Mockito.verify(courseService, Mockito.times(1)).findCourse(course);
        Mockito.verify(courseService, Mockito.atLeast(1)).findCourse(course);
        Mockito.verify(courseService, Mockito.atMost(1)).findCourse(course);

        Mockito.verify(studentRepository, Mockito.times(2)).findById("1");

        Mockito.verify(lecturerService).findLecturer(Mockito.any(Course.class), Mockito.any(Semester.class));
        Mockito.verify(lecturer).lecturerCourseRecord(Mockito.argThat(argument -> argument.getCode().equals("101")), Mockito.any(Semester.class));
    }


    @Test
    void dropCourse() {

        final Course course = new Course("101");
        final Student student = Mockito.mock(Student.class);
        final Semester semester = new Semester();


       // final StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
       // final LecturerService lecturerService = Mockito.mock(LecturerService.class);
       // final Lecturer lecturer = Mockito.mock(Lecturer.class);
       // final CourseService courseService = Mockito.mock(CourseService.class);

        Mockito.when(courseService.findCourse(course))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(course));

        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, semester);

        Mockito.when(lecturer.lecturerCourseRecord(Mockito.eq(course),Mockito.any(Semester.class)))
                .thenReturn(lecturerCourseRecord);

        Mockito.when(lecturerService.findLecturer(Mockito.eq(course), Mockito.any(Semester.class)))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(lecturer));

        Mockito.when(studentRepository.findById("1"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(student));

      //  final StudentServiceImpl studentService = new StudentServiceImpl(courseService,lecturerService,studentRepository);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> studentService.dropCourse("1",course))
                .withMessageContaining("Can't find a student");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> studentService.dropCourse("1",course))
                .withMessageContaining("Can't find a course ");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> studentService.dropCourse("1",course))
                .withMessageContaining("Can't find a lecturer ");

        studentService.dropCourse("1",course);


        Mockito.verify(lecturer).lecturerCourseRecord(Mockito.argThat(argument -> argument.getCode().equals("101")),Mockito.any(Semester.class));
        Mockito.verify(student).dropCourse(lecturerCourseRecord);
    }
}