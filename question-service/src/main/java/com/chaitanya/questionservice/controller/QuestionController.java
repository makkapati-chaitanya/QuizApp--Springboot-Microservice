package com.chaitanya.questionservice.controller;

import com.chaitanya.questionservice.model.Question;
import com.chaitanya.questionservice.model.QuestionWrapper;
import com.chaitanya.questionservice.model.Response;
import com.chaitanya.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("question")
@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    Environment environment;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
       return questionService.addQuestion(question);
    }
    @DeleteMapping("delete")
    public ResponseEntity<String> deleteQuestion(@RequestBody Question question) {
        return questionService.deleteQuestion(question);
    }
    @PostMapping("update")
    public  ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions) {
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFormId(@RequestBody List<Integer> questionIds) {
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        System.out.println(environment.getProperty("local.server.port")); // checking load balancing
        return questionService.getScore(responses);
    }


}
