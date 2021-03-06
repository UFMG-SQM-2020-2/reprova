package br.ufmg.reuso.marcelosg.reprova.controller;

import br.ufmg.reuso.marcelosg.reprova.model.Question;
import br.ufmg.reuso.marcelosg.reprova.service.QuestionService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Profile("student")
@RequestMapping("/students/questions")
public class StudentController {

    QuestionService questionService;

    public StudentController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{id}")
    public Question findById(@PathVariable String id) {
        return questionService.findById(id);
    }

    @GetMapping
    public Collection<Question> find() {
        return questionService.find();
    }
}
