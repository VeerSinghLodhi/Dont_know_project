package com.example.ProjectHON.User_masterpackage;

import com.example.ProjectHON.Post_masterpackage.PostMaster;
import com.example.ProjectHON.Post_masterpackage.PostRepository;
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

@Controller
public class UserMasterController {
    @Autowired
    UserMasterRepository userMasterRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/login")
    public String getPostLogin(@RequestParam("email")String email,
                               @RequestParam("password")String password,
                               Model model,
                               HttpSession session){

        UserMaster userMaster=userMasterRepository.findByEmailAndPassword(email,password);
        if(userMaster!=null){
            session.setAttribute("user_master",userMaster);
            System.out.println("Redirect Successful!!");
            return "redirect:/user/dashboard";
        }
        System.out.println("Invalid Credential");
        model.addAttribute("error",true);
        return "login";
    }

    @GetMapping("/user/dashboard")
    public String getUserDashboard(Model model,HttpSession session){
        UserMaster userMaster=(UserMaster) session.getAttribute("user_master");

        model.addAttribute("posts",postRepository.findAll());
        model.addAttribute("user_master",userMaster);
        return "user_dashboard";
    }

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
