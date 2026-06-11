package com.example.jpademo.service;

import com.example.jpademo.dto.ActivityRequest;
import com.example.jpademo.dto.ActivityResponse;
import com.example.jpademo.model.Activity;
import com.example.jpademo.model.User;
import com.example.jpademo.repository.ActivityRepository;
import com.example.jpademo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;



    public ActivityResponse trackUserActivity(ActivityRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Invalid user :" + request.getUserId()));

        Activity activity = Activity.builder()
                .user(user)
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
       Activity savedActivity =  activityRepository.save(activity);
       return  mapToResponse (savedActivity);
    }

    private ActivityResponse mapToResponse(Activity savedActivity) {

        ActivityResponse response = new ActivityResponse();
        response.setId(savedActivity.getId());
        response.setUserId(savedActivity.getUser().getId());
        response.setType(savedActivity.getType());
        response.setDuration(savedActivity.getDuration());
        response.setCaloriesBurned(savedActivity.getCaloriesBurned());
        response.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
        response.setStartTime(savedActivity.getStartTime());
        response.setCreatedAt(savedActivity.getCreatedAt());
        response.setUpdatedAt(savedActivity.getUpdatedAt());
        return response;

    }

    public  List<ActivityResponse> getUserActivity( String userId) {
   List<Activity> activityList = activityRepository.findByUserId(userId);

   return  activityList.stream()
           .map(this::mapToResponse)
           .collect(Collectors.toList());

    }
}
