package br.ufmg.reuso.marcelosg.reprova.controller;

import br.ufmg.reuso.marcelosg.reprova.model.Exam;
import br.ufmg.reuso.marcelosg.reprova.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/exams")
public class ExamsController {

    @Autowired
    private ExamService examService;

    @PostMapping
    ResponseEntity<Exam> create(@RequestBody Exam exam) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.createExam(exam));
    }

    @GetMapping("/{id}")
    ResponseEntity<Exam> findById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(examService.findById(id));
    }

    @GetMapping
    ResponseEntity<Collection<Exam>> findExams() {
        return ResponseEntity.ok(examService.find());
    }

    @PutMapping("/{id}/grades")
    ResponseEntity<Exam> updateExamGrades(@PathVariable("id") String id) {
        return ResponseEntity.ok(examService.calculateExamGrades(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteById(@PathVariable("id") String id) {
        examService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
