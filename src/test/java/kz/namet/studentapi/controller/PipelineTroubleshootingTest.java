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

        // corrected
        assertEquals(
                2L,
                controller.countStudents(),
                "Student count does not match expected value"
        );
    }
}
