package com.example.ProjectHON.Rating_masterpackage;

import com.example.ProjectHON.User_masterpackage.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingMaster,Long> {
    // Find all postIds rated by a specific user
    @Query("SELECT r.post_id.postId FROM RatingMaster r WHERE r.userFrom.userId = :userId")
    List<Integer> findRatedPostIdsByUser(@Param("userId") Long userId);

    // Find average rating for a specific post
    @Query("SELECT AVG(r.rating) FROM RatingMaster r WHERE r.userFrom = :user")
    Double findAllPostRatingAverageByUser(@Param("user") UserMaster user);

    //Find average rating for a specific post
    @Query("SELECT SUM(r.rating) FROM RatingMaster r WHERE r.userFrom = :user")
    Double findAllPostSumRatingByUser(@Param("user") UserMaster user);

//    // (Optional) Find all post averages together
//    @Query("SELECT r.post_id.postId, AVG(r.rating) FROM RatingMaster r GROUP BY r.post_id.postId")
//    List<Object[]> findAllPostAverages();
}
