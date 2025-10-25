package com.example.ProjectHON.parentpackage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/sigupup")
    public String getSignupPage(){
        return "signup";
    }
}
