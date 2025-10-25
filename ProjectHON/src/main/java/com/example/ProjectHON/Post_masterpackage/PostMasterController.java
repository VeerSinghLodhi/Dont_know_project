package com.example.ProjectHON.Post_masterpackage;

import com.example.ProjectHON.Theme_masterpackage.ThemeMaster;
import com.example.ProjectHON.Theme_masterpackage.ThemeMasterRepository;
import com.example.ProjectHON.User_masterpackage.UserMaster;
import com.example.ProjectHON.User_masterpackage.UserMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PostMasterController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserMasterRepository userMasterRepository;

    @Autowired
    ThemeMasterRepository themeMasterRepository;



    @PostMapping("/add-post")
    public ResponseEntity<Map<String, String>> addPost(
            @RequestParam("userId")Long userId,
            @RequestParam("theme") String theme,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("hashtags") String hashtags,
            @RequestParam("caption") String caption){
        try{
            UserMaster userMaster = userMasterRepository.findById(userId).orElse(null);
            ThemeMaster themeMaster= themeMasterRepository.getThemeByThemeName(theme);

            PostMaster postMaster = new PostMaster();
            postMaster.setCaption(caption);
            postMaster.setPhoto(photo.getBytes());
            postMaster.setHashtag(hashtags);
            postMaster.setUser(userMaster);
            postMaster.setTheme(themeMaster);
            postMaster.setDateTime(LocalDateTime.now());
            postMaster.setRating(0l);

            postRepository.save(postMaster);


            System.out.println("User Id Is " + userId);
            System.out.println("Theme: " + theme);
            System.out.println("Caption: " + caption);
            System.out.println("Hashtags: " + hashtags);
            System.out.println("File Name: " + photo.getOriginalFilename());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Post uploaded successfully!");
            response.put("status", "success");

            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
