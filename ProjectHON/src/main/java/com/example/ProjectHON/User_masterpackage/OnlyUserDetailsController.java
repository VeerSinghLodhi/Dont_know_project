package com.example.ProjectHON.User_masterpackage;

import com.example.ProjectHON.Post_masterpackage.PostMaster;
import com.example.ProjectHON.Post_masterpackage.PostRepository;
import com.example.ProjectHON.Rating_masterpackage.RatingMaster;
import com.example.ProjectHON.Rating_masterpackage.RatingRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class OnlyUserDetailsController {

    @Autowired
    UserMasterRepository userMasterRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RatingRepository ratingRepository;

    @GetMapping("/dashboard")
    public String getUserDashboard(Model model, HttpSession session){
        UserMaster userMaster = (UserMaster) session.getAttribute("user_master");

        System.out.println("Inside the User Dashboard Method" +userMaster.getEmail());
        if (userMaster == null) {
            return "redirect:/login"; // if session expired
        }
        System.out.println("===== Logged-in User =====");
        System.out.println("Email: " + userMaster.getEmail());
        System.out.println("Authorities: " + userMaster.getAuthorities());
        System.out.println("==========================");

        // 1️⃣ Get all post IDs already rated by this user
        List<Integer> ratedPostIds = ratingRepository.findRatedPostIdsByUser(userMaster.getUserId());

        // 2️⃣ Fetch posts that are NOT rated yet
        List<PostMaster> unRatedPosts;

        if (ratedPostIds.isEmpty()) {
            unRatedPosts = postRepository.findAll(); // if no ratings yet
        } else {
            unRatedPosts = postRepository.findByPostIdNotIn(ratedPostIds);
        }

        List<PostMaster> postMasterList=postRepository.findByUser(userMaster);

        Double sumAllPostRating=0.0;
        int count=0;
        for(PostMaster postMaster : postMasterList){
            Double rating=0.0;
            for(RatingMaster ratingMaster : postMaster.getRatings()) {
                rating += ratingMaster.getRating();
                count++;
            }
            sumAllPostRating +=rating;
        }

        // 3️⃣ Pass data to frontend
        model.addAttribute("posts", unRatedPosts);
        model.addAttribute("allPostRatingAverage",String.format("%.2f", sumAllPostRating/count));
        model.addAttribute("sumAllPostRating",String.format("%.2f", sumAllPostRating));
        model.addAttribute("own_user_posts", postMasterList);
        model.addAttribute("user_master", userMaster);
        return "rating";
    }

}
