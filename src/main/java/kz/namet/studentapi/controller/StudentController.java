package kz.namet.studentapi.controller;

import jakarta.validation.Valid;
import kz.namet.studentapi.model.Student;
import kz.namet.studentapi.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // CREATE - Add a new student
    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
        student.setId(null);
        Student saved = repository.save(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    // READ - Get all students
    @GetMapping
    public List<Student> getAll() {
        return repository.findAll();
    }

    // READ - Get student by ID
    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Student not found"));
    }

    // UPDATE - Update student information
    @PutMapping("/{id}")
    public Student update(
            @PathVariable Long id,
            @Valid @RequestBody Student input) {

        Student student = getById(id);

        student.setFullName(input.getFullName());
        student.setGroupName(input.getGroupName());
        student.setStudentId(input.getStudentId());

        return repository.save(student);
    }

    // DELETE - Remove student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Student student = getById(id);
        repository.delete(student);

        return ResponseEntity.noContent().build();
    }

    // COUNT - Get total number of students
    @GetMapping("/count")
    public long countStudents() {
        return repository.count();
    }
}
