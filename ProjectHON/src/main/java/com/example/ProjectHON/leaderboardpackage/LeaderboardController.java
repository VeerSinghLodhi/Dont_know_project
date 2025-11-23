package com.example.ProjectHON.leaderboardpackage;

import com.example.ProjectHON.User_masterpackage.UserMaster;
import com.example.ProjectHON.User_masterpackage.UserMasterRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class LeaderboardController {

    @Autowired
    UserMasterRepository userRepository;

    @GetMapping("/leaderboard")
    public String showLeaderboard(Model model, HttpSession session) {
       // Long userId = getSessionUserId(session);

        Long userId=(Long)session.getAttribute("userId");

        Optional<UserMaster> user1 = userRepository.findById(userId);
        List<UserMaster> users = userRepository.findAllByOrderByPointsDesc();

        user1.ifPresent(user -> {
            model.addAttribute("whereami", user.getUsername());
            model.addAttribute("userPoints", user.getPoints());
        });
        model.addAttribute("users", users);
        model.addAttribute("user_master",user1.get());
        return "MergePart/leaderboard";
    }

    @GetMapping("/{userId}/photo")
    public ResponseEntity<byte[]> getUserProfilePhoto(@PathVariable Long userId) {

            UserMaster user = userRepository.findById(userId).orElse(null);

            if(user == null || user.getCompleteProfile()){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                    .body(user.getProfilePhoto());

    }

}
