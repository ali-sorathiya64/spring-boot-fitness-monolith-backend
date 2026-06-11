package com.example.jpademo.service;


import com.example.jpademo.dto.RecommendationRequest;
import com.example.jpademo.dto.RecommendationResponse;
import com.example.jpademo.model.Activity;
import com.example.jpademo.model.Recommendation;
import com.example.jpademo.model.User;
import com.example.jpademo.repository.ActivityRepository;
import com.example.jpademo.repository.RecommendationRepository;
import com.example.jpademo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor


public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;


    public RecommendationResponse generateRecommendations(RecommendationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with this id " + request.getUserId()));


        Activity activity = activityRepository.findById(request.getActivityId())
                .orElseThrow(() -> new RuntimeException("Activity Not found " + request.getActivityId()));


        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .activity(activity)
                .improvements(request.getImprovements())
                .suggestions(request.getSuggestions())
                .safety(request.getSafety())
                .build();


        Recommendation savedResponse = recommendationRepository.save(recommendation);
        return mapToResponse(savedResponse);
    }

    private RecommendationResponse mapToResponse(Recommendation savedResponse) {

        RecommendationResponse response = new RecommendationResponse();
        response.setId(savedResponse.getId());
        response.setImprovements(savedResponse.getImprovements());
        response.setSuggestions(savedResponse.getSuggestions());
        response.setSafety(savedResponse.getSafety());
        response.setCreatedAt(savedResponse.getCreatedAt());
        response.setUpdatedAt(savedResponse.getUpdatedAt());
        return response;

    }


    public List<Recommendation> getRecommendations(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public List<RecommendationResponse> getActivities(String activityId) {

        List <Recommendation> recommendations= recommendationRepository.findByActivityId(activityId);
        return recommendations.stream().
                map(this::mapToResponse)
                .toList();
    }
}
