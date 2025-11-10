package com.example.ProjectHON.streakhistorypackage;

import com.example.ProjectHON.User_masterpackage.UserMaster;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "streak_history")
public class StreakHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserMaster user;

    @Column(name = "week_number")
    private Integer weekNumber;

    @Column(name = "posts_count")
    private Integer postsCount;

    @Column(name = "points_earned")
    private Integer pointsEarned;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // Default constructor
    public StreakHistory() {}

    // Parameterized constructor
    public StreakHistory(UserMaster user, Integer weekNumber, Integer postsCount, Integer pointsEarned, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.weekNumber = weekNumber;
        this.postsCount = postsCount;
        this.pointsEarned = pointsEarned;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserMaster getUser() {
        return user;
    }

    public void setUser(UserMaster user) {
        this.user = user;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public Integer getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(Integer pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
