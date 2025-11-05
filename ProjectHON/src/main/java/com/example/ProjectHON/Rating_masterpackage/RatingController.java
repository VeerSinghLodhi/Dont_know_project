package com.example.ProjectHON.Rating_masterpackage;

import com.example.ProjectHON.Post_masterpackage.PostRepository;
import com.example.ProjectHON.User_masterpackage.UserMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class RatingController {

    @Autowired
    UserMasterRepository userMasterRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RatingRepository ratingRepository;

    @PostMapping("/saveRating")
    @ResponseBody
    public String saveRating(
            @RequestParam("userFrom") Long userFrom,
            @RequestParam("userTo") Long userTo,
            @RequestParam("postId") Long postId,
            @RequestParam("rating") double rating) {

        RatingMaster rate = new RatingMaster();
        rate.setUserFrom(userMasterRepository.findById(userFrom).orElseThrow());
        rate.setUserTo(userMasterRepository.findById(userTo).orElseThrow());
        rate.setPost_id(postRepository.findById(postId).orElseThrow());
        rate.setRating(rating);
        rate.setPostDate(LocalDate.now());
        rate.setPostTime(LocalTime.now());

        ratingRepository.save(rate);

        return "Rating saved successfully!";
    }


}
