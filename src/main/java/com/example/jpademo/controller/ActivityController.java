package com.example.jpademo.controller;
import com.example.jpademo.dto.ActivityRequest;
import com.example.jpademo.dto.ActivityResponse;
import com.example.jpademo.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping("/track-activity")
    public ResponseEntity<ActivityResponse> trackActivity( @RequestBody ActivityRequest activityRequest){
        return  ResponseEntity.ok(activityService.trackUserActivity(activityRequest));

    }

    @GetMapping("/get-activity")

    public ResponseEntity<List<ActivityResponse>> getActivities(@RequestHeader(value = "X-USER-ID")  String userId){

        return ResponseEntity.ok(activityService.getUserActivity(userId));
    }


}
