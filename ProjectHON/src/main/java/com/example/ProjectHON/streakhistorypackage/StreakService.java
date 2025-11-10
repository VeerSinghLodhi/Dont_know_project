package com.example.ProjectHON.streakhistorypackage;


import com.example.ProjectHON.Post_masterpackage.PostRepository;
import com.example.ProjectHON.User_masterpackage.UserMaster;
import com.example.ProjectHON.User_masterpackage.UserMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class StreakService {

    @Autowired
    private UserMasterRepository userRepository;

    @Autowired
    private StreakHistoryRepository streakHistoryRepository;

    @Autowired
    PostRepository postRepository;

    // Call this when a user posts something
    public void handleUserPost(Long userId) {
        UserMaster user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("Inside the HandleUserPost Method!!");
        LocalDate today = LocalDate.now();

        if (user.getLastPostDate() == null) {
            // First post ever
            user.setStreakDays(1);
            user.setStreakWeeks(0);
            user.setCurrentPointsPerPost(0);
        } else {
            long daysDiff = ChronoUnit.DAYS.between(user.getLastPostDate(), today);
            int postsCount=postRepository.getCountPostInAWeek(today.minusDays(6).atStartOfDay(),today.atStartOfDay());
            if (daysDiff == 1) {
                // Consecutive day — continue streak
                user.setStreakDays(user.getStreakDays() + 1);

                // Every 7 days -> new week completed
                if (user.getStreakDays() % 7 == 0) {
                    user.setStreakWeeks(user.getStreakWeeks() + 1);
                    if(user.getStreakDays() <= 22) {
                        user.setCurrentPointsPerPost(user.getCurrentPointsPerPost() + 1);
                    }


                    // Save a new record for this completed week
                    StreakHistory history = new StreakHistory(
                            user,
                            user.getStreakWeeks(),
                            postsCount, // posts_count per week
                            user.getCurrentPointsPerPost() * postsCount,
                            today.minusDays(6),
                            today
                    );
//                    int postsCount=postRepository.getCountPostInAWeek(today.minusDays(6).atStartOfDay(),today.atStartOfDay());
                    System.out.println("Total Posts" + postsCount);
                    streakHistoryRepository.save(history);
                }

            } else if (daysDiff > 1) {
                // Missed a day — reset everything
                user.setStreakDays(1);
                user.setStreakWeeks(0);
                user.setCurrentPointsPerPost(0);
            }
        }

        // Award points for this post
//        int earnedPoints = user.getCurrentPointsPerPost()*post;
//        user.setPoints(user.getPoints() + earnedPoints);
        user.setLastPostDate(today);


        userRepository.save(user);
    }
}

