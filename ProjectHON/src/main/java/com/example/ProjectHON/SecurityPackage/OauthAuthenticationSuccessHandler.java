package com.example.ProjectHON.SecurityPackage;
import com.example.ProjectHON.User_masterpackage.UserMaster;
import com.example.ProjectHON.User_masterpackage.UserMasterRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private  static Logger logger= LoggerFactory.getLogger(OauthAuthenticationSuccessHandler.class);

    @Autowired
    UserMasterRepository userMasterRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        Optional<UserMaster> existingUser = userMasterRepository.findByEmail(email);
        HttpSession session = request.getSession();

        UserMaster user;
        if (existingUser.isEmpty()) {
            user = new UserMaster();
            user.setEmail(email);
            user.setFullName(name);
            user.setPassword("Sagar@123");
            user.setStatus(true);
            user.setRoleList(List.of("ROLE_USER"));
            userMasterRepository.save(user);
        } else {
            user = existingUser.get();
        }

        session.setAttribute("user_master", user);

        // ✅ Replace OAuth2User with your custom UserMaster
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        // ✅ Store authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Redirect based on role
        if (user.getRoleList().contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/dashboard");
            System.out.println("Admin Role");
        }  else {
            response.sendRedirect("/user/dashboard");
            System.out.println("User Role");
        }
    }
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//            logger.info("OauthAuthenticationSuccessHandler");
//
//            // Database entry
//            DefaultOAuth2User user= (DefaultOAuth2User) authentication.getPrincipal();
//
//            logger.info(user.getName());
//
//            user.getAttributes().forEach((key,value)->{logger.info("{}=>{}",key,value);});
//
//            String email= user.getAttribute("email").toString();
//            String name= user.getAttribute("name").toString();
//
//            Optional<UserMaster> userMaster1=userMasterRepository.findByEmail(email);
//
//            HttpSession session=request.getSession();
//
//            if(!userMaster1.isPresent()) {
//                UserMaster userMaster = new UserMaster();
//                userMaster.setEmail(email);
//                userMaster.setFullName(name);
//                userMaster.setPassword("Sagar@123");
//                userMaster.setStatus(true);
//                userMaster.setRoleList(List.of("ROLE_USER"));
//                userMasterRepository.save(userMaster);
//                session.setAttribute("user_master",userMaster);
//            }else{
//                session.setAttribute("user_master",userMaster1.get());
//            }
//
////        authentication
//
//        for(GrantedAuthority authority : userMaster1.get().getAuthorities()){
//            String role=authority.getAuthority();
//            System.out.println("Success Handler Method! "+role);
//            if(role.equals("ROLE_USER")){
//                System.out.println("user true");
//                response.sendRedirect("/user/dashboard");
//                return;
//            }
//            else if(role.equals("ROLE_ADMIN")){
//                response.sendRedirect("/");
//                return;
//            }
//        }
//
//        response.sendRedirect("/");
////            new DefaultRedirectStrategy().sendRedirect(request,response,"/user/dashboard");
//    }
}
