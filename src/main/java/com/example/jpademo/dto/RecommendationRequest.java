package com.example.jpademo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class RecommendationRequest {
    private String userId;
    private String activityId;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;


}
