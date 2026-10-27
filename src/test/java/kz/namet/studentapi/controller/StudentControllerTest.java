package kz.namet.studentapi.controller;

import kz.namet.studentapi.model.Student;
import kz.namet.studentapi.repository.StudentRepository;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    private final StudentRepository repository =
            mock(StudentRepository.class);

    private final StudentController controller =
            new StudentController(repository);

    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setFullName("Daniyar Namet");
        student.setGroupName("IT2-2312 SE");
        student.setStudentId("37056");

        when(repository.save(any(Student.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Student> response = controller.create(student);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Daniyar Namet", response.getBody().getFullName());
        assertEquals("37056", response.getBody().getStudentId());
    }

    @Test
    void testGetAllStudents() {
        Student student = new Student();
        student.setFullName("Daniyar Namet");

        when(repository.findAll()).thenReturn(List.of(student));

        List<Student> students = controller.getAll();

        assertEquals(1, students.size());
        assertEquals("Daniyar Namet", students.get(0).getFullName());
    }

    @Test
    void testStudentNotFound() {
        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.getById(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
