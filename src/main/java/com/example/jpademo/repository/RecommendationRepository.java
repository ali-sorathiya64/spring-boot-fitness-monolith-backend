package com.example.jpademo.repository;

import com.example.jpademo.dto.RecommendationResponse;
import com.example.jpademo.model.Activity;
import com.example.jpademo.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation,String> {
    List<Recommendation> findByUserId(String userId);

    List<Recommendation> findByActivityId(String activityId);
}
