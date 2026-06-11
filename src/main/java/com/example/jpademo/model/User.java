package com.example.jpademo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "fitness_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

     @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;



    @Enumerated (EnumType.STRING)
    private UserRoles role = UserRoles.USER;

@CreationTimestamp
private LocalDateTime createdAt;
@UpdateTimestamp
private LocalDateTime updatedAt;

   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , orphanRemoval = true)
   @JsonIgnore
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<Recommendation> recommendations  = new ArrayList<>();
}
