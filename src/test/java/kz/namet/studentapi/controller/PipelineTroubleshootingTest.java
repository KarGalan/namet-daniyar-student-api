package kz.namet.studentapi.controller;

import kz.namet.studentapi.repository.StudentRepository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PipelineTroubleshootingTest {

    @Test
    void testStudentCount() {

        StudentRepository repository =
                mock(StudentRepository.class);

        when(repository.count()).thenReturn(2L);

        StudentController controller =
                new StudentController(repository);

        // Intentional test failure for DevOps Midterm Task 4.3
        // Repository returns 2, but the test incorrectly expects 3.
        assertEquals(
                3L,
                controller.countStudents(),
                "Student count does not match expected value"
        );
    }
}
