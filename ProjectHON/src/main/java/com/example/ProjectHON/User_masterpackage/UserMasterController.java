package com.example.ProjectHON.User_masterpackage;

import com.example.ProjectHON.Post_masterpackage.PostMaster;
import com.example.ProjectHON.Post_masterpackage.PostRepository;
import com.example.ProjectHON.Rating_masterpackage.RatingMaster;
import com.example.ProjectHON.Rating_masterpackage.RatingRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserMasterController {
    @Autowired
    UserMasterRepository userMasterRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RatingRepository ratingRepository;

    @GetMapping("/")
    public String getLogin(){
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String getSignUp(){
        return "signup";
    }





//    @GetMapping("/user/dashboard")
//    public String getUserDashboard(Model model,HttpSession session){
//        UserMaster userMaster = (UserMaster) session.getAttribute("user_master");
//
//        System.out.println("Inside the User Dashboard Method" +userMaster.getEmail());
//        if (userMaster == null) {
//            return "redirect:/login"; // if session expired
//        }
//
//        // 1️⃣ Get all post IDs already rated by this user
//        List<Integer> ratedPostIds = ratingRepository.findRatedPostIdsByUser(userMaster.getUserId());
//
//        // 2️⃣ Fetch posts that are NOT rated yet
//        List<PostMaster> unRatedPosts;
//
//        if (ratedPostIds.isEmpty()) {
//            unRatedPosts = postRepository.findAll(); // if no ratings yet
//        } else {
//            unRatedPosts = postRepository.findByPostIdNotIn(ratedPostIds);
//        }
//
//        List<PostMaster> postMasterList=postRepository.findByUser(userMaster);
//
//        Double sumAllPostRating=0.0;
//        int count=0;
//        for(PostMaster postMaster : postMasterList){
//            Double rating=0.0;
//            for(RatingMaster ratingMaster : postMaster.getRatings()) {
//                rating += ratingMaster.getRating();
//                count++;
//            }
//            sumAllPostRating +=rating;
//        }
//
//        // 3️⃣ Pass data to frontend
//        model.addAttribute("posts", unRatedPosts);
//        model.addAttribute("allPostRatingAverage",String.format("%.2f", sumAllPostRating/count));
//        model.addAttribute("sumAllPostRating",String.format("%.2f", sumAllPostRating));
//        model.addAttribute("own_user_posts", postMasterList);
//        model.addAttribute("user_master", userMaster);
//        return "rating";
//    }

    @GetMapping("/userphoto/{postId}")
    ResponseEntity<byte[]> getUserProfile(@PathVariable("postId")Long postId){

        PostMaster postMaster=postRepository.findById(postId).orElse(null);// Admin Object for session creation
        if(postMaster==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(postMaster.getPhoto());

    }
}
