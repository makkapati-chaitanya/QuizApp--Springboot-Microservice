package com.chaitanya.quizservice.dao;

import com.chaitanya.quizservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer > {
}
