package com.example.ProjectHON.Post_masterpackage;

import com.example.ProjectHON.User_masterpackage.UserMaster;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostMaster,Long> {
    List<PostMaster> findByPostIdNotIn(List<Integer> postIds);

//    @Query("SELECT pm from PostMaster pm where pm.user=:user")
    List<PostMaster> findByUser(UserMaster user);
}

