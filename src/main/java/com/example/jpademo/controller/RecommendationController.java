package com.example.jpademo.controller;


import com.example.jpademo.dto.RecommendationRequest;
import com.example.jpademo.dto.RecommendationResponse;
import com.example.jpademo.model.Activity;
import com.example.jpademo.model.Recommendation;
import com.example.jpademo.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<RecommendationResponse> generateRecommendation(@RequestBody RecommendationRequest recommendationRequest){

         return ResponseEntity.ok(recommendationService.generateRecommendations(recommendationRequest));
    }


    @GetMapping("/user/{userId}")

   public ResponseEntity <List<Recommendation>> getUserRecommendation  (@PathVariable String userId){

        List<Recommendation> recommendationList = recommendationService.getRecommendations(userId);

        return ResponseEntity.ok(recommendationList);

    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity <List <RecommendationResponse>> getActivity(@PathVariable String activityId){
        List <RecommendationResponse> activityList = recommendationService.getActivities (activityId);
        return ResponseEntity.ok(activityList);
    }

}
