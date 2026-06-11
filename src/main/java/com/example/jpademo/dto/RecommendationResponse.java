package com.example.jpademo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class RecommendationResponse {

    private String id;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String>  safety;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
