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

        private final StudentRepository repository = mock(StudentRepository.class);

        private final StudentController controller = new StudentController(repository);

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
                                () -> controller.getById(999L));

                assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        }

        @Test
        void testUpdateStudent() {
                Student existing = new Student();
                existing.setId(1L);
                existing.setFullName("Old Name");
                existing.setGroupName("Old Group");
                existing.setStudentId("37056");

                Student updated = new Student();
                updated.setFullName("Daniyar Namet");
                updated.setGroupName("IT2-2312 SE");
                updated.setStudentId("37056");

                when(repository.findById(1L))
                                .thenReturn(Optional.of(existing));

                when(repository.save(any(Student.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                Student result = controller.update(1L, updated);

                assertEquals("Daniyar Namet", result.getFullName());
                assertEquals("IT2-2312 SE", result.getGroupName());
                assertEquals(1L, result.getId());

                verify(repository).save(existing);
        }

        @Test
        void testDeleteStudent() {
                Student student = new Student();
                student.setId(1L);
                student.setFullName("Daniyar Namet");

                when(repository.findById(1L))
                                .thenReturn(Optional.of(student));

                ResponseEntity<Void> response = controller.delete(1L);

                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

                verify(repository).delete(student);
        }

        @Test
        void testCountStudents() {
                when(repository.count()).thenReturn(2L);

                long result = controller.countStudents();

                assertEquals(2L, result);
                verify(repository).count();
        }
}
