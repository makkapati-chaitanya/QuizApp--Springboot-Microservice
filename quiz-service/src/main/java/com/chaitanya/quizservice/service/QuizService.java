package com.chaitanya.quizservice.service;

import com.chaitanya.quizservice.dao.QuizDao;
import com.chaitanya.quizservice.feign.QuizInterface;
import com.chaitanya.quizservice.model.QuestionWrapper;
import com.chaitanya.quizservice.model.Quiz;
import com.chaitanya.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try{
            List<Integer>  questionIds = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestionIds(questionIds);
            quizDao.save(quiz);
            return  new ResponseEntity<>("Quiz created", HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("unexpected error", HttpStatus.CONFLICT);

    }

    public  ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        try{
            Quiz quiz = quizDao.findById(id).get();
            List<Integer> questionIds = quiz.getQuestionIds();
            ResponseEntity<List<QuestionWrapper>> questionForUsers = quizInterface.getQuestionsFormId(questionIds);
            return questionForUsers;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        try{
            ResponseEntity<Integer> quizMarks = quizInterface.getScore(responses);
            return quizMarks;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
