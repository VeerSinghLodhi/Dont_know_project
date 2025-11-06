package com.example.ProjectHON.SecurityPackage;


import com.example.ProjectHON.User_masterpackage.UserMaster;
import com.example.ProjectHON.User_masterpackage.UserMasterRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserMasterRepository userMasterRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username=authentication.getName();
        System.out.println("Username is "+username);
        UserMaster userMaster=userMasterRepository.findByUsername(username);

        HttpSession session=request.getSession();

        session.setAttribute("user_master",userMaster);

        System.out.println("Success Handler Method!"+userMaster.getUserId());

        for(GrantedAuthority authority : authentication.getAuthorities()){
            String role=authority.getAuthority();
            System.out.println("Success Handler Method! "+role);
            if(role.equals("ROLE_USER")){
                System.out.println("user true");
                response.sendRedirect("/user/dashboard");
                return;
            }
            else if(role.equals("ROLE_ADMIN")){
                response.sendRedirect("/");
                return;
            }
        }

        response.sendRedirect("/");

    }
}
