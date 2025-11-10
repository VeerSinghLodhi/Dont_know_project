package com.example.ProjectHON.streakhistorypackage;

import com.example.ProjectHON.User_masterpackage.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreakHistoryRepository extends JpaRepository<StreakHistory, Long> {

    // Get all streak history records of a user
    List<StreakHistory> findByUser(UserMaster user);

    // Optional: find latest streak record for analytics
    StreakHistory findTopByUserOrderByEndDateDesc(UserMaster user);
}

